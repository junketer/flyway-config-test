package com.codedose.oag;

import com.codedose.oag.cache.CacheDataFrames;
import com.codedose.oag.utils.UserDefinedFunctions;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.codedose.oag.utils.DataFrameLoader.DEFAULT_PARTITION_COUNT;
import static com.codedose.oag.utils.ScalaUtils.seq;
import static com.codedose.oag.utils.SqlFunctions.intLength;
import static org.apache.spark.sql.functions.*;

@SuppressWarnings("WeakerAccess") // because it's used in Scala scripts
public class IVListProcessing {

    private final CacheDataFrames cacheDataFrames;
    private final UserDefinedFunctions udfs;
    private final boolean shouldAdjustArrTimeLocal;
    private final LocalDate currentDate;

    private static final String EVERYDAY_OF_WEEK = "1234567";

    public IVListProcessing(CacheDataFrames cacheDataFrames, UserDefinedFunctions udfs, boolean shouldAdjustArrTimeLocal, LocalDate currentDate) {
        this.cacheDataFrames = cacheDataFrames;
        this.udfs = udfs;
        this.shouldAdjustArrTimeLocal = shouldAdjustArrTimeLocal;
        this.currentDate = currentDate;
    }

    public Dataset<Row> process(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> dataset = legsIvsWithLatestSchedule;
        dataset = addArrDepCodes(dataset);
        dataset = addCarrierCode(dataset);
        dataset = addServiceNumStr(dataset);
        dataset = addLegSegEffEndDate(dataset);
        dataset = addLegSegEffStartDate(dataset);
        dataset = addOperDaysOfWeek(dataset);
        dataset = adjustArrivalDepartureLocalTime(dataset);
        dataset = addSpecificAircraftType(dataset); //
        dataset = enrichWithAircraftChangeMarker(dataset);
        dataset = enrichWithIVGroupAttributes(dataset);
        dataset = addOperatingMarker(dataset);
        dataset = nonStoppingLegalColumn(dataset);
        dataset = addFullRoutingList(dataset);
        dataset = anyNonStoppingLegal(dataset);
        dataset = filterOutOldNonstoppingLegs(dataset);
        dataset = addIntermediatePorts(dataset);
        dataset = addSharedAirlineDesignatorNonStopping(dataset);
        dataset = addSharedAirlineDesignatorStopping(dataset);
        dataset = addAliases(dataset);

        dataset = dataset.drop("markers", "routing_cd_array_with_pos", "routing_cd_array", "sharing_keys", "collected_legs_info");
        return dataset;
    }

    public Dataset<Row> enrichWithAircraftChangeMarker(Dataset<Row> legs) {
        return legs
                .withColumn("aircraft_change_marker",
                        udfs.processDEIElement(col("dep_port_cd"), col("spcfc_arcrft_type"), col("OPTIONAL_ELEMENT_XML")));
    }

    public Dataset<Row> enrichWithIVGroupAttributes(Dataset<Row> legsIvsWithLatestSchedule) {
        //enrich with longest sector, override ROUTING_FEATURE_ID_LIST with first value.
        WindowSpec byIvId = Window.partitionBy("iv_id").orderBy(col("num_of_stops_cnt").desc(), col("leg_seqnum"));
        return legsIvsWithLatestSchedule
                .withColumn("LONGEST_SECTOR_MARKER", when(row_number().over(byIvId).equalTo(1), lit("L")).otherwise(lit("")))
                .withColumn("CGWDF_IVFULL_ROUTING", first("routing_feature_id_list").over(byIvId))
                .withColumn("aircraft_change", sum("aircraft_change_marker").over(byIvId))
                .withColumn("ROWNUM", row_number().over(byIvId));
    }

    public Dataset<Row> addOperatingMarker(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule
                .withColumn("markers", udfs.processComment1050AndDuplicateLegXML(
                        col("DUPLICATE_LEG_XML"),
                        col("NUM_OF_STOPS_CNT"),
                        col("aircraft_change"),
                        col("carrier_id")
                        )
                )
                .withColumn("operating_marker", col("markers.operating_marker"));
    }

    public Dataset<Row> addCarrierCode(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> cargoDuplicate = cacheDataFrames.getCargoDuplicateIndicatorDF();
        Dataset<Row> combinedCarrier = cacheDataFrames.getCombinedCarrierDF();
        Dataset<Row> carrierCodesDF = combinedCarrier
                .join(broadcast(cargoDuplicate), seq("carrier_id"), "left_outer")
                .withColumn("carrier_cd",
                        when(
                                cargoDuplicate.col("oag_cargo_duplicate_ind").equalTo("Y"),
                                concat(lit("7"),
                                        col("scheme_carrier_cd"))
                        )
                        .otherwise(col("scheme_carrier_cd"))
                )
                .repartition(DEFAULT_PARTITION_COUNT, col("carrier_id"));

//        return legsIvsWithLatestSchedule.join(broadcast(cutLineage(carrierCodesDF, "carrierCodesDF")), "carrier_id");
        return legsIvsWithLatestSchedule.join(broadcast(carrierCodesDF), "carrier_id");
    }

    public Dataset<Row> addServiceNumStr(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> carrierServiceZeroPadding = cacheDataFrames.getCarrierServiceZeroPaddingDF()
                .select("srvc_num_1_digit_lead_zero_cnt", "srvc_num_2_digit_lead_zero_cnt", "srvc_num_3_digit_lead_zero_cnt", "carrier_id", "sched_version_name");

        Column serviceNum = legsIvsWithLatestSchedule.col("service_num");
        Column serviceNumLenCol = intLength(serviceNum);
        return legsIvsWithLatestSchedule
                .join(broadcast(carrierServiceZeroPadding), seq("carrier_id", "sched_version_name"), "left_outer")
                .withColumn("service_num_str",
                        udfs.padService(
                                serviceNum,
                                when(serviceNumLenCol.equalTo(1), coalesce(carrierServiceZeroPadding.col("srvc_num_1_digit_lead_zero_cnt"), lit(0)))
                                    .when(serviceNumLenCol.equalTo(2), coalesce(carrierServiceZeroPadding.col("srvc_num_2_digit_lead_zero_cnt"), lit(0)))
                                    .when(serviceNumLenCol.equalTo(3), coalesce(carrierServiceZeroPadding.col("srvc_num_3_digit_lead_zero_cnt"), lit(0)))
                                    .otherwise(0)
                        )
                );
    }

    public Dataset<Row> addFullRoutingList(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> locationCodeIATA = cacheDataFrames.getLocationCodeIATADF();
        Dataset<Row> routingFeatureId = legsIvsWithLatestSchedule
                .withColumn("routing_list", split(legsIvsWithLatestSchedule.col("CGWDF_IVFULL_ROUTING"), " "))
                .select("leg_seg_id", "routing_list");
        Dataset<Row> explodedRoutingFeatureId = routingFeatureId
                .select(col("leg_seg_id"), posexplode(routingFeatureId.col("routing_list")).as(seq("pos", "val")));

        Dataset<Row> explodedRoutingFeatureCd = explodedRoutingFeatureId
                .join(broadcast(locationCodeIATA), explodedRoutingFeatureId.col("val").equalTo(locationCodeIATA.col("feature_id")));
        explodedRoutingFeatureCd.explain(true);

        Dataset<Row> routingFeatureCd = explodedRoutingFeatureCd
                .withColumn("routingStruct", struct("pos", "feature_cd"))
                .groupBy("leg_seg_id")
                .agg(collect_list("routingStruct").as("routing_cd_array_with_pos"))
                .withColumn("routing_cd_array", udfs.sortTuple(col("routing_cd_array_with_pos")))
                .withColumn("full_routing", concat_ws("", col("routing_cd_array")))
                .repartition(DEFAULT_PARTITION_COUNT, col("leg_seg_id"));
        return legsIvsWithLatestSchedule.join(routingFeatureCd, "leg_seg_id");
    }

    public Dataset<Row> addIntermediatePorts(Dataset<Row> legsIvsWithLatestSchedule) {
        //consider this mapping before exploding routing list.
        return legsIvsWithLatestSchedule
                .withColumn("intmdte_ports",
                        when(col("any_non_stopping_legal").and(col("num_of_stops_cnt").gt(0)), udfs.getIntermediatePorts(col("routing_cd_array"), col("num_of_stops_cnt"), col("dep_port_cd")))
                                .otherwise(lit(""))
        );
    }

    public Dataset<Row> addAliases(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule
                .withColumnRenamed("num_of_stops_cnt", "stops")
                .withColumnRenamed("equip_group_cd", "gen_arcrft_type");
    }

    public Dataset<Row> adjustArrivalDepartureLocalTime(Dataset<Row> legsIvsWithLatestSchedule) {
        if (shouldAdjustArrTimeLocal) {
            return legsIvsWithLatestSchedule
                    .withColumn("arr_time_lcl", date_format(when(date_format(col("sched_pax_arr_time"), "HHmm").equalTo("0000"), udfs.addMinutes(col("sched_pax_arr_time"), lit(-1))).otherwise(col("sched_pax_arr_time")), "HHmm"))
                    .withColumn("dep_time_lcl", date_format(when(date_format(col("sched_pax_dep_time"), "HHmm").equalTo("0000"), udfs.addMinutes(col("sched_pax_dep_time"), lit(1))).otherwise(col("sched_pax_dep_time")), "HHmm"));
        } else {
            return legsIvsWithLatestSchedule
                    .withColumn("arr_time_lcl", date_format(col("sched_pax_arr_time"), "HHmm"))
                    .withColumn("dep_time_lcl", date_format(col("sched_pax_dep_time"), "HHmm"));
        }
    }

    public Dataset<Row> addSpecificAircraftType(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> equipmentCodesIATA = cacheDataFrames.getEquipmentCodeIATADF();
        return legsIvsWithLatestSchedule
                .join(broadcast(equipmentCodesIATA), "equipment_type_id")
                .withColumnRenamed("equipment_type_cd", "spcfc_arcrft_type");
    }

    public Dataset<Row> addArrIntervalDates(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule
                .withColumn("arr_interval_days",
                        coalesce(
                                when(col("seg_arr_interval_days").notEqual(0),
                                when(col("seg_arr_interval_days").gt(0), col("seg_arr_interval_days"))
                                        .otherwise(lit("P"))), lit("")
                        )
                );
    }

    public Dataset<Row> addDepPortCode(Dataset<Row> legsIvsWithLatestSchedule) {

        Dataset<Row> locationCodesIATA = cacheDataFrames.getLocationCodeIATADF()
                .withColumnRenamed("feature_id", "dep_port_id");

        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), seq("dep_port_id"), "left_outer")
                .withColumn("dep_port_cd", coalesce(col("feature_cd"), lit("")) )
                .drop("feature_cd");
    }

    public Dataset<Row> addDepCityCode(Dataset<Row> legsIvsWithLatestSchedule) {

        Dataset<Row> locationCodesIATA = cacheDataFrames.getLocationCodeIATADF().withColumnRenamed("feature_id", "dep_city_id");
        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), seq("dep_city_id"), "left_outer")
                .withColumn("dep_city_cd", coalesce(col("feature_cd"), lit("")) )
                .drop("feature_cd");
    }

    public Dataset<Row> addDepCntryCode(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> locationCodesIATA = cacheDataFrames.getCityToCountryDF().withColumnRenamed("feature_to_id", "dep_city_id");
        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), seq("dep_city_id"), "left_outer")
                .withColumn("dep_cntry_cd", coalesce(col("feature_cd"), lit("")) )
                .drop("feature_cd");
    }

    public Dataset<Row> addArrPortCode(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> locationCodesIATA = cacheDataFrames.getLocationCodeIATADF()
                .withColumnRenamed("feature_id", "arr_port_id");
        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), "arr_port_id")
                .withColumnRenamed("feature_cd", "arr_port_cd");
    }

    public Dataset<Row> addArrCityCode(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> locationCodesIATA = cacheDataFrames.getLocationCodeIATADF()
                .withColumnRenamed("feature_id", "arr_city_id");
        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), "arr_city_id")
                .withColumnRenamed("feature_cd", "arr_city_cd");
    }

    public Dataset<Row> addArrCntryCode(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> locationCodesIATA = cacheDataFrames.getCityToCountryDF()
                .withColumnRenamed("feature_to_id", "arr_city_id");
        return legsIvsWithLatestSchedule
                .join(broadcast(locationCodesIATA), "arr_city_id")
                .withColumnRenamed("feature_cd", "arr_cntry_cd");
    }

    public Dataset<Row> addDomIntMarker(Dataset<Row> legsIvsWithLatestSchedule) {
        //TODO: add processing as is done in IVListProcessor.processDEIElements
        return legsIvsWithLatestSchedule
                .withColumn("dom_int_marker", concat(col("OUTBOUND_INTL_DOM_STATUS_CD"), col("INBOUND_INTL_DOM_STATUS_CD")));
    }

    public Dataset<Row> addArrDepCodes(Dataset<Row> legsIvsWithLatestSchedule) {
        return addArrUSDotCountry(addDepUSDotCountry(addArrPortCode(addArrCityCode(addArrCntryCode(
                addDepPortCode(
                addDepCityCode(addDepCntryCode(legsIvsWithLatestSchedule))))))));
    }

    public Dataset<Row> addArrUSDotCountry(Dataset<Row> rowDatasetWithDepCountryCode) {
        Dataset<Row> countryDotCodeCache = cacheDataFrames.getCountryDotCodeDF().withColumnRenamed("country_code", "arr_cntry_cd");
        return rowDatasetWithDepCountryCode.join(broadcast(countryDotCodeCache), "arr_cntry_cd").withColumnRenamed("dot_code", "arr_usdot_cntry");

    }

    public Dataset<Row> addDepUSDotCountry(Dataset<Row> rowDatasetWithDepCountryCode) {
        Dataset<Row> countryDotCodeCache = cacheDataFrames.getCountryDotCodeDF().withColumnRenamed("country_code", "dep_cntry_cd");
        return rowDatasetWithDepCountryCode.join(broadcast(countryDotCodeCache), "dep_cntry_cd").withColumnRenamed("dot_code", "dep_usdot_cntry");
    }

    public Dataset<Row> addRegions(Dataset<Row> rowDataset) {
        //TODO: check if needed after POC phase. For the output configurations we are targeting BROWARD, AA1, BA1, GOOG dep_region and arr_region are not used.
        //WARNING: for now we not fully understand business requirements for this one (PortToRegionCache puts what is effectively many-to-many table to hashmap, implicitly
        //depending on ordering we could not determine). Example of anomalies detected using old code PortToRegionCache: dep_port_id: 3973017 belongs to regions AS100 and AS50,
        // the same as 3973448, but PortToRegionCache selects AS50 for the former and AS100 for the latter.
        Dataset<Row> depPortToRegionCache = cacheDataFrames.getPortToRegionDF().withColumnRenamed("feature_to_id", "port_id").withColumnRenamed("feature_cd", "region_cd").alias("port_to_region");
        Dataset<Row> with_dep_region = rowDataset.join(broadcast(depPortToRegionCache), rowDataset.col("dep_port_id").equalTo(col("port_to_region.port_id")), "left_outer")
                .withColumn("dep_region", coalesce(col("region_cd"), lit(""))).alias("with_dep_region");


        return with_dep_region;//.join(broadcast(depPortToRegionCache), col("with_dep_region.arr_port_id").equalTo(col("port_to_region.port_id")), "left_outer")
        //.withColumn("arr_region", coalesce(col("region_cd"), lit("")));
    }

    public Dataset<Row> addArrOperDaysOfWeek(Dataset<Row> rowDataset) {
        return rowDataset.withColumn("arr_oper_days_of_week", udfs.shiftOperDaysOfWeek(col("snap_leg_seg_oper_days_of_week"), col("seg_arr_interval_days")));
    }

    public Dataset<Row> addSharedAirlineDesignatorNonStopping(Dataset<Row> rowDataset) {
        Dataset<Row> sharedAirlineDesignatorCache = cacheDataFrames.getSharedAirlineDesignatorDF()
                .withColumn("participant_name_cut", substring(col("participant_name"), 0, 17))
                .dropDuplicates("participant_name_cut", "carrier_code_from_mshrdres");

        Dataset<Row> combinedCarrierCache = cacheDataFrames.getCombinedCarrierDF()
                .withColumnRenamed("carrier_id", "combined_carrier_id")
                .withColumnRenamed("scheme_carrier_cd", "combined_scheme_carrier_cd");

        Dataset<Row> partyRoleNameWithKeyForMshrdesLookup =
                cacheDataFrames.getPartyRoleNameDF()
                        .withColumn("mshrdes_lookup", rtrim(upper(substring(col("party_role_name"), 0, 17))));

        Dataset<Row> partyRoleNameWithCarrierCode = partyRoleNameWithKeyForMshrdesLookup
                .join(broadcast(sharedAirlineDesignatorCache), col("mshrdes_lookup").equalTo(col("participant_name_cut")))
                .repartition(DEFAULT_PARTITION_COUNT);

        Dataset<Row> withSharingKeys = rowDataset
                .withColumn("sharing_keys", udfs.processCodeShare(col("PARTICIPANT_XML"), col("num_of_stops_cnt")));
        return withSharingKeys
                .join(broadcast(partyRoleNameWithCarrierCode), col("sharing_keys.sharing").equalTo(col("party_role_name_id")), "left_outer")
                .withColumnRenamed("carrier_code_from_mshrdres", "shared_airline_nonstopping_1")
                .join(broadcast(combinedCarrierCache), col("sharing_keys.commercial").equalTo(col("combined_carrier_id")), "left_outer")
                .withColumnRenamed("combined_scheme_carrier_cd", "shared_airline_nonstopping_2")
                .withColumn("shared_airline_nonstopping", coalesce(col("shared_airline_nonstopping_1"), col("shared_airline_nonstopping_2"), lit("")));
    }

    public Dataset<Row> addSharedAirlineDesignatorStopping(Dataset<Row> rowDataset) {
        //WindowSpec byIvId = Window.partitionBy("iv_id").orderBy(col("num_of_stops_cnt").desc(), col("leg_seqnum"));
        //WARNING: adding order to window changes semantics of collect_list
        WindowSpec byIvId = Window.partitionBy("iv_id");
        Dataset<Row> withCollectedLegs = rowDataset.withColumn("collected_legs_info",
                collect_list(
                        struct(
                                col("non_stopping_legal"),
                                col("arr_port_cd"),
                                col("dep_port_cd"),
                                col("shared_airline_nonstopping"),
                                col("num_of_stops_cnt"),
                                col("leg_seqnum")
                        ))
                        .over(byIvId));
        return withCollectedLegs.withColumn(
                "shared_airline_designator",
                when(col("num_of_stops_cnt").equalTo(0), col("shared_airline_nonstopping"))
                        .otherwise(udfs.getSharedAirlineDesignatorStopping(
                                col("collected_legs_info"),
                                col("intmdte_ports"),
                                col("num_of_stops_cnt"),
                                col("dep_port_cd"),
                                col("leg_seg_id")
                        ))
        );
    }

    public Dataset<Row> addLegSegEffStartDate(Dataset<Row> rowDataset) {
        return rowDataset
                .withColumn(
                        "leg_seg_eff_start_date",
                        udfs.adjustStartDate(
                                lit(java.sql.Date.valueOf(currentDate)),
                                col("snap_leg_seg_leg_seg_eff_start_date"),
                                col("snap_leg_seg_oper_days_of_week")
                        )
                );
    }

    public Dataset<Row> addLegSegEffEndDate(Dataset<Row> rowDataset) {
        return rowDataset
                .withColumn("leg_seg_eff_end_date",
                when(col("snap_leg_seg_oper_days_of_week").equalTo(lit(EVERYDAY_OF_WEEK)), col("snap_leg_seg_leg_seg_eff_end_date"))
                        .otherwise(udfs.adjustEndDate(col("snap_leg_seg_leg_seg_eff_end_date"), col("snap_leg_seg_oper_days_of_week"))));
    }

    public Dataset<Row> nonStoppingLegalColumn(Dataset<Row> legsIvsWithLatestSchedule) {
        WindowSpec byIvId = Window.partitionBy("iv_id", "num_of_stops_cnt").orderBy(col("leg_seqnum"));
        return legsIvsWithLatestSchedule.withColumn("hrownum", row_number().over(byIvId))
                .withColumn("non_stopping_legal", when(
                        col("num_of_stops_cnt").equalTo(0).and(col("leg_seqnum").leq(col("hrownum")))
                        , lit(1)).otherwise(0));
    }

    public Dataset<Row> filterOutOldNonstoppingLegs(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule
                .where(col("num_of_stops_cnt").gt(0).or(col("non_stopping_legal").equalTo(1)));
    }

    public Dataset<Row> anyNonStoppingLegal(Dataset<Row> legsIvsWithLatestSchedule) {
        WindowSpec byIvId = Window.partitionBy("iv_id");
        return legsIvsWithLatestSchedule
                .withColumn("any_non_stopping_legal_s", sum(col("non_stopping_legal")).over(byIvId))
                .withColumn("any_non_stopping_legal", col("any_non_stopping_legal_s").gt(0));

    }

    public Dataset<Row> addDirectDistances(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> depPortCoordinates = cacheDataFrames.getPortCoordinatesDF().withColumnRenamed("feature_id", "dep_port_id");
        Dataset<Row> arrPortCoordinates = cacheDataFrames.getPortCoordinatesDF().withColumnRenamed("feature_id", "arr_port_id");
        Dataset<Row> withDepCoordinates = legsIvsWithLatestSchedule.join(broadcast(depPortCoordinates), "dep_port_id")
                .withColumnRenamed("lat", "dep_lat").withColumnRenamed("long", "dep_long");
        Dataset<Row> withArrCoordinates = withDepCoordinates.join(broadcast(arrPortCoordinates), "arr_port_id")
                .withColumnRenamed("lat", "arr_lat").withColumnRenamed("long", "arr_long");
        Dataset<Row> withDistanceInMilesFloat = withArrCoordinates.withColumn("distance_miles_float", udfs.getDistance(col("dep_lat"), col("dep_long"), col("arr_lat"), col("arr_long")));
        return withDistanceInMilesFloat.withColumn("distance_miles", floor(col("distance_miles_float")))
                .withColumn("distance_kms", floor(col("distance_miles_float").multiply(lit(1.60934))))
                .withColumn("distance_naut_miles", floor(col("distance_miles_float").multiply(lit(1.60934)).multiply(0.53995)));
        //"distance_acc_stat_miles"
        //"distance_acc_naut_miles"
        //"distance_acc_kms"
        //"distance_acc_gcm"
    }

    public Dataset<Row> addTerminalCodes(Dataset<Row> legsIvsWithLatestSchedule) {
        Dataset<Row> depAirportTerminalCache = cacheDataFrames.getAirportTerminalDF().withColumnRenamed("feature_id", "DEP_TERMINAL_ID");
        Dataset<Row> arrAirportTerminalCache = cacheDataFrames.getAirportTerminalDF().withColumnRenamed("feature_id", "ARR_TERMINAL_ID");
        Dataset<Row> withDepTermcode = legsIvsWithLatestSchedule.join(broadcast(depAirportTerminalCache), seq("dep_terminal_id"), "left_outer").withColumn("dep_terminal_cd", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
        return withDepTermcode
                .join(broadcast(arrAirportTerminalCache), seq("arr_terminal_id"), "left_outer")
                .withColumn("arr_terminal_cd", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
    }

    public Dataset<Row> addElapsedTime(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule.withColumn("elapsed_time",
                format_string("%03d%02d", floor(col("ELAPSED_TIME_INTERVAL_MINS").divide(60)), floor(col("ELAPSED_TIME_INTERVAL_MINS").mod(60))));
    }

    public Dataset<Row> addLastIVUpdate(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule.withColumnRenamed("last_update_timestamp", "last_iv_update");
    }

    public Dataset<Row> addOperPrefixSuffix(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule.withColumn("oper_prefix", coalesce(col("leg_oper_prefix"), lit("")))
                .withColumn("oper_suffix", coalesce(col("leg_oper_suffix"), lit("")));
    }

    public Dataset<Row> addCarrierCodeICAO(Dataset<Row> legsIvsWithLatestSchedule) {
        List<String> joincols = Collections.singletonList("carrier_id");
        Dataset<Row> carrierCodeICAOCache = cacheDataFrames.getCarrierCodeICAODF().select("carrier_id", "scheme_carrier_cd");
        return legsIvsWithLatestSchedule.join(broadcast(carrierCodeICAOCache), seq(joincols), "left_outer").withColumn("carrier_cd_icao", coalesce(col("scheme_carrier_cd"), lit(""))).drop("scheme_carrier_cd");
    }

    public Dataset<Row> addDepArrPortCodeICAO(Dataset<Row> legsIvsWithLatestSchedule) {
        List<String> joincol2 = Collections.singletonList("arr_port_id");

        Dataset<Row> locationCodeICAODepCache = cacheDataFrames.getLocationCodeICAODF().select("feature_id", "feature_cd").withColumnRenamed("feature_id", "dep_port_id");
        Dataset<Row> locationCodeICAOArrCache = cacheDataFrames.getLocationCodeICAODF().select("feature_id", "feature_cd").withColumnRenamed("feature_id", "arr_port_id");
        Dataset<Row> withDepPortIcao = legsIvsWithLatestSchedule
                .join(broadcast(locationCodeICAODepCache).repartition(DEFAULT_PARTITION_COUNT, col("dep_port_id")), seq("dep_port_id"), "left_outer")
                .withColumn("dep_port_cd_icao", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
        return withDepPortIcao.join(broadcast(locationCodeICAOArrCache), seq(joincol2), "left_outer")
                .withColumn("arr_port_cd_icao", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
    }

    public Dataset<Row> addEquipmentCodeIcao(Dataset<Row> legsIvsWithLatestSchedule) {
        List<String> joincol1 = Collections.singletonList("equipment_type_id");

        Dataset<Row> equipmentCodeIcaoCache = cacheDataFrames.getEquipmentCodeICAODF().select("equipment_type_id", "equipment_type_cd");
        return legsIvsWithLatestSchedule.join(broadcast(equipmentCodeIcaoCache), seq(joincol1), "left_outer")
                .withColumn("equipment_cd_icao", coalesce(col("equipment_type_cd"), lit(""))).drop("equipment_type_cd");
    }

    public Dataset<Row> addStates(Dataset<Row> legsIvsWithLatestSchedule) {
        List<String> joincol1 = Collections.singletonList("dep_port_id");
        List<String> joincol2 = Collections.singletonList("arr_port_id");

        Dataset<Row> depAirportToStateCache = cacheDataFrames.getAirportToStateDF().select("feature_to_id", "feature_cd").withColumnRenamed("feature_to_id", "dep_port_id");
        Dataset<Row> arrAirportToStateCache = cacheDataFrames.getAirportToStateDF().select("feature_to_id", "feature_cd").withColumnRenamed("feature_to_id", "arr_port_id");
        Dataset<Row> withDepState = legsIvsWithLatestSchedule.join(broadcast(depAirportToStateCache), seq(joincol1), "left_outer")
                .withColumn("dep_state", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
        return withDepState.join(broadcast(arrAirportToStateCache), seq(joincol2), "left_outer")
                .withColumn("arr_state", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
    }

    public Dataset<Row> addAlliance(Dataset<Row> legsIvsWithLastestSchedule) {
        List<String> joincol1 = Collections.singletonList("carrier_id");

        Dataset<Row> allianceCacheWitList = cacheDataFrames.getAllianceLookupDF().select("carrier_id", "alliance", "precedence").orderBy("precedence").groupBy("carrier_id").agg(collect_list("alliance").as("alliance_list"));
        Dataset<Row> allianceLookup = allianceCacheWitList.withColumn("alliance_concat", concat_ws("/", allianceCacheWitList.col("alliance_list")));
        return legsIvsWithLastestSchedule.join(broadcast(allianceLookup), seq(joincol1), "left_outer")
                .withColumn("alliance", coalesce(col("alliance_concat"), lit(""))).drop("alliance_concat");
    }

    public Dataset<Row> addCarrierCountry(Dataset<Row> legsIvsWithLatestSchedule) {
        List<String> joincol1 = Collections.singletonList("carrier_id");

        Dataset<Row> carrierCountryCache = cacheDataFrames.getCarrierCountryDF();
        return legsIvsWithLatestSchedule.join(broadcast(carrierCountryCache), seq(joincol1), "left_outer")
                .withColumn("carrier_cntry", coalesce(col("feature_cd"), lit(""))).drop("feature_cd");
    }

    public Dataset<Row> addOperDaysOfWeek(Dataset<Row> legsIvsWithLatestSchedule) {
        return legsIvsWithLatestSchedule.withColumn(
                "oper_days_of_week",
                udfs.adjustOperDaysOfWeek(
                        col("leg_seg_eff_start_date"),
                        col("leg_seg_eff_end_date"),
                        col("snap_leg_seg_oper_days_of_week")
                )
        );
    }


}
