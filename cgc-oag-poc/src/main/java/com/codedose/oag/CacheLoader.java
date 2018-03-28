package com.codedose.oag;

import com.codedose.oag.cache.CacheDataFrames;
import com.codedose.oag.utils.DB2Properties;
import com.codedose.oag.utils.DataFrameLoader;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.codedose.oag.SparkLoader.Profile.CLUSTER_PERF;
import static com.codedose.oag.SparkLoader.activeProfile;
import static com.codedose.oag.SparkLoader.createOrGetSession;
import static org.apache.spark.sql.functions.*;

public class CacheLoader {

    private static final boolean CUT_LINEAGE = false;
    private static Logger log = LogManager.getLogger(CacheLoader.class.getName());
    private final Dataset<Row> featureAssocTypeDF;
    private DataFrameLoader dataFrameLoader;
    private final Dataset<Row> featureCodingDF;
    private Map<String, Dataset<Row>> dfMap;

    public CacheDataFrames getCacheDFs() {
        return cacheDFs;
    }

    private CacheDataFrames cacheDFs;
    private java.sql.Date currentSqlDate;
    private final Dataset<Row> codingSchemeDF;
    private final Dataset<Row> partyRoleDF;
    private final Dataset<Row> partyRoleNameDF;
    private final Dataset<Row> featureAssocDF;
    private final Dataset<Row> snapCarrierCodingDF;
    private final Dataset<Row> equipmentCodingDF;

    public CacheLoader(DataFrameLoader dataFrameLoader, LocalDate currentDate) {
        log.setLevel(Level.INFO);
        dfMap = new HashMap<>();
        this.dataFrameLoader = dataFrameLoader;
        this.featureCodingDF = dataFrameLoader.loadTable("snapshot.snap_feature_coding").cache();
        this.cacheDFs = new CacheDataFrames();
        this.currentSqlDate = java.sql.Date.valueOf(currentDate);
        codingSchemeDF = dataFrameLoader.loadTable("snapshot.snap_coding_scheme").cache();
        featureAssocTypeDF = dataFrameLoader.loadTable("snapshot.snap_feature_assoc_type").cache();
        partyRoleDF = dataFrameLoader.loadTable("snapshot.snap_party_role").cache();
        partyRoleNameDF = dataFrameLoader.loadTable("snapshot.snap_party_role_name").cache();
        featureAssocDF = dataFrameLoader.loadTable("snapshot.snap_feature_assoc").cache();
        snapCarrierCodingDF = dataFrameLoader.loadTable("snapshot.snap_carrier_coding").cache();
        equipmentCodingDF = dataFrameLoader.loadTable("snapshot.snap_equipment_coding").cache();

        codingSchemeDF.createOrReplaceTempView("coding_scheme");
        featureAssocTypeDF.createOrReplaceTempView("feature_assoc_type");
        snapCarrierCodingDF.createOrReplaceTempView("carrier_coding");

    }

    private void clean() {
        codingSchemeDF.unpersist(false);
        featureAssocTypeDF.unpersist(false);
        snapCarrierCodingDF.unpersist(false);
        partyRoleDF.unpersist(false);
        partyRoleNameDF.unpersist(false);
        featureAssocDF.unpersist(false);
        equipmentCodingDF.unpersist(false);
    }

    private void saveCacheState(Dataset<Row> dataset, String cacheName) {
        Dataset<Row> cache = dataset.cache();
        if (CUT_LINEAGE) {
            RDD<Row> rdd = cache.rdd();
            cache = dataset.sparkSession().createDataFrame(rdd, dataset.schema());
        }
        if (activeProfile() == CLUSTER_PERF) {
            cache.foreachPartition((Iterator<Row> it) -> {
                System.out.println("cache " + cacheName + " local count: " + count(it));
            });
            long count = cache.count();
            log.info(cacheName + " COUNT = " + count);
        }
        dfMap.put(cacheName, cache);
    }

    private static long count(Iterator<Row> it) {
        long sum = 0;
        Iterable<Row> it2 = () -> it;
        Stream<Row> stream = StreamSupport.stream(it2.spliterator(), false);

        return stream.parallel().count();
    }

    public void storeOnDisk(String path) {
        for (Map.Entry<String, Dataset<Row>> entry : dfMap.entrySet()) {
            String key = entry.getKey();
            Dataset<Row> value = entry.getValue();
            log.info(String.format("Storing cache %s on disk", key));
            value.write().parquet(path + "/" + key);
        }
    }

    public void retrieveFromDisk(String path, SparkSession spark) {
        ParquetLoader p = new ParquetLoader(spark, path);
        cacheDFs.setAirportTerminalDF(p.get("AirportTerminal"));
        cacheDFs.setAirportToStateDF(p.get("AirportToState"));
        cacheDFs.setAllianceLookupDF(p.get("AllianceLookup"));
        cacheDFs.setCargoDuplicateIndicatorDF(p.get("CargoDuplicateIndicator"));
        cacheDFs.setCarrierCodeICAODF(p.get("CarrierCodeICAO"));
        cacheDFs.setCarrierCountryDF(p.get("CarrierCountry"));
        cacheDFs.setCarrierDefaultDEIValueDF(p.get("CarrierDefaultDEIValue"));
        cacheDFs.setCarrierNameDF(p.get("CarrierName"));
        cacheDFs.setCarrierServiceZeroPaddingDF(p.get("CarrierServiceZeroPadding"));
        cacheDFs.setCityToCountryDF(p.get("CityToCountry"));
        cacheDFs.setCombinedCarrierDF(p.get("CombinedCarrier"));
        cacheDFs.setCountryDotCodeDF(p.get("CountryDotCode"));
        cacheDFs.setEquipmentCodeIATADF(p.get("EquipmentCodeIATA"));
        cacheDFs.setEquipmentCodeICAODF(p.get("EquipmentCodeICAO"));
        cacheDFs.setEquipmentDefaultCargoClassDF(p.get("EquipmentDefaultCargoClass"));
        cacheDFs.setIVIdDF(p.get("IvId"));
        cacheDFs.setLegSegDefaultDEIValueDF(p.get("LegSegDefaultDEIValue"));
        cacheDFs.setLocationCodeIATADF(p.get("LocationCodeIATA"));
        cacheDFs.setLocationCodeICAODF(p.get("LocationCodeICAO"));
        cacheDFs.setOntimePerformance501DF(p.get("OntimePerformance501"));
        cacheDFs.setOntimePerformance502DF(p.get("OntimePerformance502"));
        cacheDFs.setPartyRoleNameDF(p.get("PartyRoleName"));
        cacheDFs.setPassengerClassDF(p.get("PassengerClass"));
        cacheDFs.setPortCoordinatesDF(p.get("PortCoordinates"));
        cacheDFs.setPortsDF(p.get("Ports"));
        cacheDFs.setPortTimezoneIATADF(p.get("PortTimezoneIATA"));
        cacheDFs.setPortTimezoneOAGDF(p.get("PortTimezoneOAG"));
        cacheDFs.setPortToRegionDF(p.get("PortToRegion"));
        cacheDFs.setSeatsDF(p.get("SeatsCache"));
        cacheDFs.setSharedAirlineDesignatorDF(p.get("SharedAirlineDesignatorCache"));
    }

    public void loadCaches() {
        this.loadAirportTerminalCache();
        this.loadAirpotyToStateCache();
        this.loadAllianceLookupCache();
        this.loadCargoDuplicateIndicatorCache();
        this.loadCarrierCodeICAOCache();
        this.loadCarrierCountryCache();
        this.loadCarrierDefaultDEIValueCache();
        this.loadCarrierNameCache();
        this.loadCarrierServiceZeroPaddingCache();
        this.loadCityToCountryCache();
        this.loadCombinedCarrierCache();
        this.loadCountryDotCodeDFCache();
          this.loadEquipmentCodeIATACache();
        this.loadEquipmentCodeICAOCache();
        this.loadEquipmentDefaultCargoClassCache();
//        this.loadIvIdCache(); // TODO Duplicate logic
        this.loadLegSegDefaultDEIValueCache();
          this.loadLocationCodeIATACache();
        this.loadLocationCodeICAOCache();
//        this.loadOntimePerformance501Cache();
//        this.loadOntimePerformance502Cache();
        this.loadPartyRoleNameCache();
        this.loadPassengerClassCache();
        this.loadPortCoordinatesCache();
        this.loadPortsCache();
        this.loadPortTimezoneIATACache();
        this.loadPortTimezoneOAGCache();
        this.loadPortToRegionCache();
        this.loadSeatsCache();
        this.loadSharedAirlineDesignatorCache();
    }

    private Object selectCodingSchemeId(String codingSchemeName) {
        return dataFrameLoader.executeSQL("SELECT coding_scheme_id FROM coding_scheme WHERE coding_scheme_name = '" + codingSchemeName + "'").first().get(0);
    }

    private Object selectFeatureTypeAssocId(String featureAssocTypeDescr) {
        return dataFrameLoader.executeSQL("SELECT feature_assoc_type_id FROM feature_assoc_type WHERE feature_assoc_type_desc = '"
                + featureAssocTypeDescr + "'").first().get(0);
    }

    public void loadAirportTerminalCache() {
        Object codingSchemeId = selectCodingSchemeId("IATA Terminal Codes");
        cacheDFs.setAirportTerminalDF(featureCodingDF
                .filter(col("effective_start_date").leq(currentSqlDate))
                .filter(col("eff_end_date").gt(currentSqlDate))
                .filter(col("coding_scheme_id").equalTo(codingSchemeId))
                .select("feature_id", "feature_cd"));
        saveCacheState(cacheDFs.getAirportTerminalDF(), "AirportTerminal");
    }

    public void loadAirpotyToStateCache() {
        Object featureAssocTypeId = selectFeatureTypeAssocId("State has / Airport is located in");
        Object codingSchemeId = selectCodingSchemeId("IATA State Codes - 2 Letter Codes");

        cacheDFs.setAirportToStateDF(featureAssocDF
                .join(featureCodingDF, featureAssocDF.col("feature_from_id").equalTo(featureCodingDF.col("feature_id")))
                .filter(featureCodingDF.col("effective_start_date").leq(currentSqlDate))
                .filter(featureCodingDF.col("eff_end_date").gt(currentSqlDate))
                .filter(featureAssocDF.col("feature_assoc_type_id").equalTo(featureAssocTypeId))
                .filter(featureCodingDF.col("coding_scheme_id").equalTo(codingSchemeId))
                .select("feature_to_id", "feature_cd"));
        saveCacheState(cacheDFs.getAirportToStateDF(), "AirportToState");
    }

    public void loadAllianceLookupCache() {
        //WARNING: original code relies on implicit ordering of the query from wdf-publisher AllianceLookupCache.java, which makes
        //the content of `cache` map different on spark and different in DB2.
        //here for POC the ordering is explicit but we have to consult with OAG if this is acceptable.

        Dataset<Row> partyRelMemberDF = dataFrameLoader.loadTable("snapshot.snap_party_relationship_member");
        Dataset<Row> partyRelDF = dataFrameLoader.loadTable("snapshot.snap_party_relationship");
        Dataset<Row> alliancePrecedenceDF = dataFrameLoader.loadTable("snapshot.snap_alliance_precedence");

        Dataset<Row> allianceLookupStage1 = partyRelMemberDF
                .join(partyRelDF, partyRelDF.col("relationship_id").equalTo(partyRelMemberDF.col("relationship_id"))
                        .and(partyRelDF.col("relationship_type_id").equalTo(2))
                        .and(partyRelMemberDF.col("member_eff_start_date").leq(currentSqlDate))
                        .and(partyRelMemberDF.col("member_eff_end_date").geq(currentSqlDate)))
                .join(partyRoleNameDF, partyRoleNameDF.col("party_role_id").equalTo(partyRelMemberDF.col("member_party_role_id"))
                        .and(partyRoleNameDF.col("name_type_id").equalTo(3))
                        .and(partyRoleNameDF.col("eff_start_date").leq(currentSqlDate))
                        .and(partyRoleNameDF.col("eff_end_date").geq(currentSqlDate)))
                .join(broadcast(alliancePrecedenceDF), col("parent_party_role_id").equalTo(col("alliance_id")))
                .join(partyRoleDF, partyRoleDF.col("party_role_id").equalTo(partyRoleNameDF.col("party_role_id")))
                .select("member_party_role_id", "parent_party_role_id", "precedence");

//        log.info("alliance stage1 ==> " + allianceLookupStage1.count());
        Dataset<Row> allianceLookupStage2 = allianceLookupStage1
                .join(partyRoleNameDF, partyRoleNameDF.col("party_role_id").equalTo(allianceLookupStage1.col("parent_party_role_id"))
                        .and(partyRoleNameDF.col("name_type_id").equalTo(9)))
                .select("member_party_role_id", "parent_party_role_id", "party_role_id", "party_role_name", "precedence").toDF();
        cacheDFs.setAllianceLookupDF(allianceLookupStage2
                .join(partyRoleDF, partyRoleDF.col("party_role_id").equalTo(allianceLookupStage2.col("party_role_id")))
                .select(allianceLookupStage2.col("member_party_role_id").as("carrier_id"),
                        allianceLookupStage2.col("parent_party_role_id").as("alliance_id"),
                        allianceLookupStage2.col("precedence"),
                        col("party_role_name").as("alliance")));
        saveCacheState(cacheDFs.getAllianceLookupDF(), "AllianceLookup");
    }

    public void loadCargoDuplicateIndicatorCache() {
        Dataset<Row> carrierDF = dataFrameLoader.loadTable("snapshot.snap_carrier");
        cacheDFs.setCargoDuplicateIndicatorDF(carrierDF
                .where(col("carrier_coding_end_date").gt(currentSqlDate)
                        .and(col("oag_cargo_duplicate_ind").equalTo("Y")))
                .select("carrier_id", "oag_cargo_duplicate_ind"));
        saveCacheState(cacheDFs.getCargoDuplicateIndicatorDF(), "CargoDuplicateIndicator");
    }

    public void loadCarrierCodeICAOCache() {
        Object codingSchemeId = selectCodingSchemeId("ICAO Codes - 3 Letter Codes");

        cacheDFs.setCarrierCodeICAODF(snapCarrierCodingDF
                .where(snapCarrierCodingDF.col("coding_scheme_id").equalTo(codingSchemeId)
                        .and(col("carrier_coding_start_date").leq(currentSqlDate))
                        .and(col("carrier_coding_end_date").gt(currentSqlDate))));
        saveCacheState(cacheDFs.getCarrierCodeICAODF(), "CarrierCodeICAO");
    }

    // todo: check!
    public void loadCarrierCountryCache() {
        Dataset<Row> featureVersionDF = dataFrameLoader.loadTable("snapshot.snap_feature_version");
        Dataset<Row> featureDF = dataFrameLoader.loadTable("snapshot.snap_feature");

        Dataset<Row> joinFeatureDF = featureVersionDF
                .join(featureDF, featureVersionDF.col("feature_id").equalTo(featureDF.col("feature_id")))
                .join(featureCodingDF, featureCodingDF.col("feature_id").equalTo(featureDF.col("feature_id")))
                .select(featureDF.col("feature_id"),
                        featureVersionDF.col("feature_default_name"),
                        featureCodingDF.col("feature_cd"),
                        featureDF.col("feature_type_id"),
                        featureCodingDF.col("coding_scheme_id"));

        Dataset<Row> joinFeatureStateDF = joinFeatureDF.where(col("feature_type_id").equalTo(3)
                .and(col("coding_scheme_id").equalTo(14)))
                .select("feature_id", "feature_default_name", "feature_cd");

        Dataset<Row> joinFeatureCountryDF = joinFeatureDF.where(col("feature_type_id").equalTo(1)
                .and(col("coding_scheme_id").equalTo(2)))
                .select("feature_id", "feature_default_name", "feature_cd");

        cacheDFs.setCarrierCountryDF(partyRoleDF.as("pr")
                .join(partyRoleNameDF.as("prn"), col("pr.party_role_id").equalTo(col("prn.party_role_id"))
                        .and(col("prn.name_type_id").equalTo(3))
                        .and(col("prn.eff_start_date").leq(currentSqlDate))
                        .and(col("prn.eff_end_date").geq(currentSqlDate))
                )
                .join(joinFeatureStateDF.as("feature_state"),
                        partyRoleDF.col("oper_base_feature_id").equalTo(col("feature_state.feature_id")),
                        "left_outer")
                .join(joinFeatureCountryDF.as("feature_country"),
                        partyRoleDF.col("oper_base_feature_id").equalTo(col("feature_country.feature_id")),
                        "left_outer")
                .join(featureAssocDF,
                        col("feature_state.feature_id").equalTo(featureAssocDF.col("feature_to_id"))
                                .and(featureAssocDF.col("feature_assoc_type_id").equalTo(2)),
                        "left_outer")
                .join(snapCarrierCodingDF, snapCarrierCodingDF.col("carrier_id").equalTo(partyRoleDF.col("party_role_id")))
                .groupBy(snapCarrierCodingDF.col("carrier_id"),
                        coalesce(col("feature_country.feature_cd"), col("feature_state.feature_cd")))
                .agg(snapCarrierCodingDF.col("carrier_id"),
                        coalesce(col("feature_country.feature_cd"), col("feature_state.feature_cd")).as("feature_cd"))
                .select("carrier_id", "feature_cd"));

        saveCacheState(cacheDFs.getCarrierCountryDF(), "CarrierCountry");
    }

    public void loadCarrierDefaultDEIValueCache() {
        Dataset<Row> schedDefaultOptionalElementDF = dataFrameLoader.loadTable("snapshot.snap_sched_default_optional_element");
        cacheDFs.setCarrierDefaultDEIValueDF(schedDefaultOptionalElementDF
                .select("carrier_id",
                        "sched_version_id",
                        "optional_element_num",
                        "sched_deflt_optnl_elemt_value"));
        saveCacheState(cacheDFs.getCarrierDefaultDEIValueDF(), "CarrierDefaultDEIValue");
    }

    public void loadCarrierNameCache() {
        cacheDFs.setCarrierNameDF(partyRoleNameDF.where(col("name_type_id").equalTo(3)
                .and(col("eff_start_date").leq(currentSqlDate))
                .and(col("eff_end_date").gt(currentSqlDate)))
                .select("party_role_id", "party_role_name"));
        saveCacheState(cacheDFs.getCarrierNameDF(), "CarrierName");
    }

    public void loadCarrierServiceZeroPaddingCache() {
        Dataset<Row> carrierSchedVersionDF = dataFrameLoader.loadTable("snapshot.snap_carrier_sched_version");
        Dataset<Row> schedVersionNameDF = dataFrameLoader.loadTable("snapshot.snap_sched_version_name");

        cacheDFs.setCarrierServiceZeroPaddingDF(carrierSchedVersionDF.join(schedVersionNameDF)
                .where(carrierSchedVersionDF.col("sched_version_name_id").equalTo(schedVersionNameDF.col("sched_version_name_id"))
                        .and(col("srvc_num_1_digit_lead_zero_cnt").gt(0)
                                .or(col("srvc_num_2_digit_lead_zero_cnt").gt(0))
                                .or(col("srvc_num_3_digit_lead_zero_cnt").gt(0))))
                .select(col("carrier_id"),
                        schedVersionNameDF.col("sched_version_name_id"),
                        schedVersionNameDF.col("sched_version_name"),
                        col("srvc_num_1_digit_lead_zero_cnt"),
                        col("srvc_num_2_digit_lead_zero_cnt"),
                        col("srvc_num_3_digit_lead_zero_cnt")));
        saveCacheState(cacheDFs.getCarrierServiceZeroPaddingDF(), "CarrierServiceZeroPadding");
    }

    public void loadCityToCountryCache() {
        Dataset<Row> sqlDFassoc = dataFrameLoader.executeSQL("SELECT feature_assoc_type_id FROM feature_assoc_type WHERE feature_assoc_type_desc = 'Country has / Metropolitan Area is located in' OR feature_assoc_type_desc = 'Subcountry has / Metropolitan Area is located in'");
        Object codingSchemeId = selectCodingSchemeId("ISO Country Codes");

        cacheDFs.setCityToCountryDF(featureAssocDF.join(featureCodingDF)
                .where(featureAssocDF.col("feature_assoc_type_id").isin(sqlDFassoc.collectAsList().stream().map(row -> row.get(0)).toArray())
                        .and(featureCodingDF.col("coding_scheme_id").equalTo(codingSchemeId))
                        .and(featureAssocDF.col("feature_from_id").equalTo(featureCodingDF.col("feature_id")))
                        .and(featureCodingDF.col("effective_start_date").leq(currentSqlDate))
                        .and(featureCodingDF.col("eff_end_date").gt(currentSqlDate)))
                .select(featureAssocDF.col("feature_to_id"), featureCodingDF.col("feature_cd")));
        saveCacheState(cacheDFs.getCityToCountryDF(), "CityToCountry");
    }

    public void loadCombinedCarrierCache() {
        Dataset<Row> airlineDesignatorCarriersDF = dataFrameLoader.executeSQL("SELECT scc.carrier_id, scc.scheme_carrier_cd FROM carrier_coding scc WHERE  coding_scheme_id = (SELECT coding_scheme_id FROM coding_scheme WHERE  coding_scheme_name = 'Airline Designator Codes')")
                .where(col("carrier_coding_start_date").leq(currentSqlDate))
                .where(col("carrier_coding_end_date").gt(currentSqlDate));

        Dataset<Row> icaoDF = dataFrameLoader.executeSQL("SELECT scc.carrier_id, scc.scheme_carrier_cd FROM carrier_coding scc WHERE  coding_scheme_id = (SELECT coding_scheme_id FROM coding_scheme WHERE  coding_scheme_name = 'ICAO Codes - 3 Letter Codes') " +
                " AND NOT EXISTS(SELECT * FROM  carrier_coding scc1 " +
                "                WHERE  scc1.carrier_id = scc.carrier_id " +
                "                AND scc1.coding_scheme_id = (SELECT coding_scheme_id " +
                "                                             FROM   coding_scheme " +
                "                                             WHERE  coding_scheme_name = 'Airline Designator Codes')) ")
                .where(col("carrier_coding_start_date").leq(currentSqlDate))
                .where(col("carrier_coding_end_date").gt(currentSqlDate));

        Dataset<Row> cargoDF = dataFrameLoader.executeSQL("SELECT scc.carrier_id, scc.scheme_carrier_cd FROM carrier_coding scc WHERE  coding_scheme_id = (SELECT coding_scheme_id FROM coding_scheme WHERE  coding_scheme_name = 'Cargo Codes') " +
                " AND NOT EXISTS(SELECT * FROM  carrier_coding scc1 " +
                "                WHERE  scc1.carrier_id = scc.carrier_id " +
                "                AND scc1.coding_scheme_id IN (SELECT coding_scheme_id " +
                "                                             FROM   coding_scheme " +
                "                                             WHERE  coding_scheme_name IN( 'Airline Designator Codes', 'ICAO Codes - 3 Letter Codes' ))) ")
                .where(col("carrier_coding_start_date").leq(currentSqlDate))
                .where(col("carrier_coding_end_date").gt(currentSqlDate));

        Dataset<Row> uspsCodesDF = dataFrameLoader.executeSQL("SELECT scc.carrier_id, scc.scheme_carrier_cd FROM carrier_coding scc WHERE  coding_scheme_id = (SELECT coding_scheme_id FROM coding_scheme WHERE  coding_scheme_name = 'USPS Codes') " +
                " AND NOT EXISTS(SELECT * FROM  carrier_coding scc1 " +
                "                WHERE  scc1.carrier_id = scc.carrier_id " +
                "                AND scc1.coding_scheme_id IN (SELECT coding_scheme_id " +
                "                                             FROM   coding_scheme " +
                "                                             WHERE  coding_scheme_name IN( 'Airline Designator Codes', 'ICAO Codes - 3 Letter Codes', 'Cargo Codes' ))) ")
                .where(col("carrier_coding_start_date").leq(currentSqlDate))
                .where(col("carrier_coding_end_date").gt(currentSqlDate));
        cacheDFs.setCombinedCarrierDF(airlineDesignatorCarriersDF.union(icaoDF).union(cargoDF).union(uspsCodesDF).select("carrier_id", "scheme_carrier_cd"));
        saveCacheState(cacheDFs.getCombinedCarrierDF(), "CombinedCarrier");
    }

    public void loadCountryDotCodeDFCache() {
        Object codingSchemeId1 = selectCodingSchemeId("DOT Country Codes");
        Object codingSchemeId2 = selectCodingSchemeId("ISO Country Codes");

        cacheDFs.setCountryDotCodeDF(featureCodingDF.as("scf1").join(featureCodingDF.as("scf2")).where(col("scf1.feature_id").equalTo(col("scf2.feature_id"))
                .and(col("scf1.coding_scheme_id").equalTo(codingSchemeId1))
                .and(col("scf2.coding_scheme_id").equalTo(codingSchemeId2))
                .and(col("scf1.effective_start_date").leq(currentSqlDate))
                .and(col("scf1.eff_end_date").gt(currentSqlDate))
                .and(col("scf2.effective_start_date").leq(currentSqlDate))
                .and(col("scf2.eff_end_date").gt(currentSqlDate)))
                .select(col("scf2.feature_cd").as("country_code"), col("scf1.feature_cd").as("dot_code")));
        saveCacheState(cacheDFs.getCountryDotCodeDF(), "CountryDotCode");
    }

    public void loadEquipmentCodeIATACache() {
        Object codingSchemeId = selectCodingSchemeId("IATA Equipment Codes");

        cacheDFs.setEquipmentCodeIATADF(equipmentCodingDF
                .where(col("coding_scheme_id").equalTo(codingSchemeId))
                .select("equipment_type_id", "equipment_type_cd"));

        saveCacheState(cacheDFs.getEquipmentCodeIATADF(), "EquipmentCodeIATA");
    }

    public void loadEquipmentCodeICAOCache() {
        Object codingSchemeId = selectCodingSchemeId("ICAO Equipment Codes");

        cacheDFs.setEquipmentCodeICAODF(equipmentCodingDF
                .where(col("coding_scheme_id").equalTo(codingSchemeId))
                .select("equipment_type_id", "equipment_type_cd"));

        saveCacheState(cacheDFs.getEquipmentCodeICAODF(), "EquipmentCodeICAO");
    }

    public void loadEquipmentDefaultCargoClassCache() {
        cacheDFs.setEquipmentDefaultCargoClassDF(dataFrameLoader.loadTable("snapshot.snap_equipment_type")
                .select("equipment_type_id", "default_cargo_class_cd"));

        saveCacheState(cacheDFs.getEquipmentDefaultCargoClassDF(), "EquipmentDefaultCargoClass");
    }

    public void loadIvIdCache() {
        Dataset<Row> schedVersion = dataFrameLoader.loadTable("snapshot.snap_sched_version")
                .cache()
                .where(col("release_sell_start_date").leq(currentSqlDate))
                .select("sched_version_id");
        cacheDFs.setIVIdDF(dataFrameLoader.loadTable("snapshot.snap_iv")
                .repartition(24)
                .cache()
                .where(col("sched_version_id").isin(schedVersion.collectAsList().stream().map(row -> row.get(0)).toArray()))
                .select("iv_id"));

        saveCacheState(cacheDFs.getIVIdDF(), "IvId");
    }

    public void loadLegSegDefaultDEIValueCache() {
        // TODO Very high skewness
        cacheDFs.setLegSegDefaultDEIValueDF(
                dataFrameLoader.loadTable("snapshot.snap_leg_opt_element")
                        .repartition(15)
                        .where(col("optional_element_num").equalTo(504))
                        .select("leg_seg_id", "optional_element_num", "optional_element_value"));

        saveCacheState(cacheDFs.getLegSegDefaultDEIValueDF(), "LegSegDefaultDEIValue");
    }

    public void loadLocationCodeIATACache() {
        Object codingSchemeId = selectCodingSchemeId("IATA Location Codes - 3 Letter Codes");

        cacheDFs.setLocationCodeIATADF(featureCodingDF.where(col("coding_scheme_id").equalTo(codingSchemeId)
                .and(col("effective_start_date").leq(currentSqlDate))
                .and(col("eff_end_date").gt(currentSqlDate)))
                .select("feature_id", "feature_cd"));

        saveCacheState(cacheDFs.getLocationCodeIATADF(), "LocationCodeIATA");
    }

    public void loadLocationCodeICAOCache() {
        Object codingSchemeId = selectCodingSchemeId("ICAO Location Codes");

        cacheDFs.setLocationCodeICAODF(featureCodingDF.where(col("coding_scheme_id").equalTo(codingSchemeId)
                .and(col("effective_start_date").leq(currentSqlDate))
                .and(col("eff_end_date").gt(currentSqlDate)))
                .select("feature_id", "feature_cd"));

        saveCacheState(cacheDFs.getLocationCodeICAODF(), "LocationCodeICAO");
    }

    public void loadOntimePerformance501Cache() {
        Dataset<Row> optHistory = dataFrameLoader.loadTable("snapshot.snap_otp_history");
        Dataset<Row> schedVersionName = dataFrameLoader.loadTable("snapshot.snap_sched_version_name");

        cacheDFs.setOntimePerformance501DF(optHistory.join(schedVersionName, optHistory.col("sched_version_name_id").equalTo(schedVersionName.col("sched_version_name_id"))
                .and(optHistory.col("pcnt_qualifier_cd").isNotNull()))
                .select(col("carrier_id"),
                        col("service_number"),
                        schedVersionName.col("sched_version_name_id"),
                        schedVersionName.col("sched_version_name"),
                        col("departure_port_id"),
                        col("arrival_port_id"),
                        col("departure_time"),
                        col("otp_pcnt"),
                        concat(
                                when(optHistory.col("pcnt_qualifier_cd").equalTo("T"), rtrim(col("otp_pcnt")))
                                        .otherwise(optHistory.col("pcnt_qualifier_cd")),
                                lit(" "),
                                upper(trunc(optHistory.col("otp_date"), "mon")),
                                trunc(optHistory.col("otp_date"), "yy")
                        ).as("otp_value")
                )
                .orderBy(optHistory.col("otp_date").asc()));

        saveCacheState(cacheDFs.getOntimePerformance501DF(), "OntimePerformance501");
    }

    public void loadOntimePerformance502Cache() {
        Dataset<Row> optHistory = dataFrameLoader.loadTable("snapshot.snap_otp_history_text");
        Dataset<Row> schedVersionName = dataFrameLoader.loadTable("snapshot.snap_sched_version_name");

        cacheDFs.setOntimePerformance502DF(optHistory
                .join(schedVersionName, optHistory.col("sched_version_name_id").equalTo(schedVersionName.col("sched_version_name_id")))
                .select(col("carrier_id"),
                        col("service_number"),
                        schedVersionName.col("sched_version_name_id"),
                        schedVersionName.col("sched_version_name"),
                        col("departure_port_id"),
                        col("arrival_port_id"),
                        col("arrival_time"),
                        concat(
                                col("otp_text"),
                                lit(" "),
                                upper(trunc(col("otp_text_date"), "mon")),
                                trunc(col("otp_text_date"), "yy")
                        ).as("otp_value")
                )
                .orderBy(optHistory.col("otp_text_date").asc()));

        saveCacheState(cacheDFs.getOntimePerformance502DF(), "OntimePerformance502");
    }

    public void loadPartyRoleNameCache() {
        cacheDFs.setPartyRoleNameDF(partyRoleNameDF
                .where(col("eff_start_date").leq(currentSqlDate)
                        .and(col("eff_end_date").gt(currentSqlDate)))
                .select("party_role_name_id", "party_role_name"));

        saveCacheState(cacheDFs.getPartyRoleNameDF(), "PartyRoleName");
    }

    public void loadPassengerClassCache() {
        cacheDFs.setPassengerClassDF(dataFrameLoader
                .loadTable("snapshot.snap_booking_class_alt")
                .select("booking_class_cd", "default_config_class_cd"));

        saveCacheState(cacheDFs.getPassengerClassDF(), "PassengerClass");
    }

    public void loadPortCoordinatesCache() {
        Dataset<Row> featureVersion = dataFrameLoader.loadTable("snapshot.snap_feature_version");
        Dataset<Row> featureType = dataFrameLoader.loadTable("snapshot.snap_feature_type");
        Dataset<Row> feature = dataFrameLoader.loadTable("snapshot.snap_feature");

        cacheDFs.setPortCoordinatesDF(featureCodingDF.join(feature, featureCodingDF.col("feature_id").equalTo(feature.col("feature_id")))
                .join(featureVersion, featureVersion.col("feature_id").equalTo(feature.col("feature_id")))
                .join(featureType, feature.col("feature_type_id").equalTo(featureType.col("feature_type_id")))
                .where(featureType.col("feature_group_id").equalTo(1))
                .select(feature.col("feature_id"), featureVersion.col("lat"), featureVersion.col("long")).distinct());

        saveCacheState(cacheDFs.getPortCoordinatesDF(), "PortCoordinates");
    }

    public void loadPortsCache() {
        Object codingSchemeId = selectCodingSchemeId("IATA Location Codes - 3 Letter Codes");

        cacheDFs.setPortsDF(featureCodingDF
                .where(col("coding_scheme_id").equalTo(codingSchemeId)
                        .and(col("effective_start_date").leq(currentSqlDate))
                        .and(col("eff_end_date").gt(currentSqlDate)))
                .select("feature_id", "feature_cd"));

        saveCacheState(cacheDFs.getPortsDF(), "Ports");
    }

    public void loadPortTimezoneIATACache() {
        Object codingSchemeId = selectCodingSchemeId("IATA TimeZone Codes");

        Dataset<Row> sqlDFassoc = dataFrameLoader.executeSQL("SELECT feature_assoc_type_id FROM feature_assoc_type " +
                "WHERE feature_assoc_type_desc = 'Airport has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Rail Station has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Harbour has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Bus Station has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Offpoint has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Metropolitan Area has / Timezone Area is attached to'");

        cacheDFs.setPortTimezoneIATADF(featureAssocDF.join(featureCodingDF)
                .where(featureAssocDF.col("feature_to_id").equalTo(featureCodingDF.col("feature_id"))
                        .and(featureCodingDF.col("coding_scheme_id").equalTo(codingSchemeId))
                        .and(featureAssocDF.col("feature_assoc_type_id").isin(sqlDFassoc.collectAsList().stream().map(row -> row.get(0)).toArray())))
                .select(featureAssocDF.col("feature_from_id"), featureCodingDF.col("feature_cd")));

        saveCacheState(cacheDFs.getPortTimezoneIATADF(), "PortTimezoneIATA");
    }

    public void loadPortTimezoneOAGCache() {
        Object codingSchemeId = selectCodingSchemeId("OAG TimeZone Codes");

        Dataset<Row> sqlDFassoc = dataFrameLoader.executeSQL("SELECT feature_assoc_type_id FROM feature_assoc_type " +
                "WHERE feature_assoc_type_desc = 'Airport has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Rail Station has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Harbour has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Bus Station has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Offpoint has / Timezone Area is attached to' " +
                "OR feature_assoc_type_desc = 'Metropolitan Area has / Timezone Area is attached to'");

        cacheDFs.setPortTimezoneOAGDF(featureAssocDF.join(featureCodingDF)
                .where(featureAssocDF.col("feature_to_id").equalTo(featureCodingDF.col("feature_id"))
                        .and(featureCodingDF.col("coding_scheme_id").equalTo(codingSchemeId))
                        .and(featureAssocDF.col("feature_assoc_type_id").isin(sqlDFassoc.collectAsList().stream().map(row -> row.get(0)).toArray())))
                .select(featureAssocDF.col("feature_from_id"), featureCodingDF.col("feature_cd")));

        saveCacheState(cacheDFs.getPortTimezoneOAGDF(), "PortTimezoneOAG");

    }

    public void loadPortToRegionCache() {
        Object codingSchemeId = selectCodingSchemeId("OAG Region Codes");
        Object featureAssocTypeId = selectFeatureTypeAssocId("Region has / Airport is Located in");

        cacheDFs.setPortToRegionDF(
                featureCodingDF.join(featureAssocDF)
                .where(featureAssocDF.col("feature_from_id").equalTo(featureCodingDF.col("feature_id"))
                        .and(featureCodingDF.col("coding_scheme_id").equalTo(codingSchemeId))
                        .and(featureAssocDF.col("feature_assoc_type_id").equalTo(featureAssocTypeId))
                        .and(featureAssocDF.col("eff_end_date").gt(currentSqlDate))
                        .and(featureCodingDF.col("effective_start_date").leq(currentSqlDate))
                        .and(featureCodingDF.col("eff_end_date").gt(currentSqlDate)))
                .select("feature_id", "feature_cd"));

        saveCacheState(cacheDFs.getPortToRegionDF(), "PortToRegion");
    }

    public void loadSeatsCache() {
        Dataset<Row> equipmentConfig = dataFrameLoader.loadTable("snapshot.snap_equipment_cabin_config")
                .select("equipment_config_id", "cabin_seqnum", "config_class_cd", "seats_qty", "cargo_capacity_weight");
        Dataset<Row> configCombination = dataFrameLoader.loadTable("snapshot.snap_config_combination");

        Dataset<Row> seatsCache = dataFrameLoader.loadTable("snapshot.snap_equipment_config").as("sec")
                .join(configCombination.as("scc"), col("scc.config_combination_id").equalTo(col("sec.config_combination_id")))
                .join(equipmentConfig.as("first_class"),
                        col("sec.equipment_config_id").equalTo(col("first_class.equipment_config_id"))
                                .and(ltrim(rtrim(col("first_class.config_class_cd"))).equalTo("F")), "left_outer")
                .join(equipmentConfig.as("business_class"),
                        col("sec.equipment_config_id").equalTo(col("business_class.equipment_config_id"))
                                .and(ltrim(rtrim(col("business_class.config_class_cd"))).equalTo("C")), "left_outer")
                .join(equipmentConfig.as("prem_economy"),
                        col("sec.equipment_config_id").equalTo(col("prem_economy.equipment_config_id"))
                                .and(ltrim(rtrim(col("prem_economy.config_class_cd"))).equalTo("W")), "left_outer")
                .join(equipmentConfig.as("economy_class"),
                        col("sec.equipment_config_id").equalTo(col("economy_class.equipment_config_id"))
                                .and(ltrim(rtrim(col("economy_class.config_class_cd"))).equalTo("Y")), "left_outer")
                .join(equipmentConfig.as("freight_class"),
                        col("sec.equipment_config_id").equalTo(col("freight_class.equipment_config_id"))
                                .and(length(ltrim(rtrim(col("first_class.config_class_cd")))).equalTo(2)), "left_outer")
                .select(
                        col("sec.party_role_id"),
                        col("sec.equipment_type_id"),
                        col("scc.service_type_cd"),
                        col("sec.fleet_size_qty"),
                        col("scc.service_haul_category_cd"),
                        when(col("first_class.seats_qty").isNotNull(), col("first_class.seats_qty")).otherwise(0).as("first_seats"),
                        when(col("business_class.seats_qty").isNotNull(), col("business_class.seats_qty")).otherwise(0).as("business_seats"),
                        when(col("prem_economy.seats_qty").isNotNull(), col("prem_economy.seats_qty")).otherwise(0).as("prem_economy_seats"),
                        when(col("economy_class.seats_qty").isNotNull(), col("economy_class.seats_qty")).otherwise(0).as("economy_seats"),
                        when(col("first_class.seats_qty").isNotNull(), col("first_class.seats_qty")).otherwise(0).plus(
                                when(col("business_class.seats_qty").isNotNull(), col("business_class.seats_qty")).otherwise(0)).plus(
                                when(col("prem_economy.seats_qty").isNotNull(), col("prem_economy.seats_qty")).otherwise(0)).plus(
                                when(col("economy_class.seats_qty").isNotNull(), col("economy_class.seats_qty")).otherwise(0))
                                .as("total_seats"),
                        coalesce(col("freight_class.config_class_cd")).as("freight_class"),
                        col("freight_class.cargo_capacity_weight").as("freight_capacity"),
                        col("sec.cargo_data_source_cd")
                )
                .orderBy(col("sec.party_role_id"), col("sec.equipment_type_id"), col("sec.fleet_size_qty").desc(), col("total_seats"));

        cacheDFs.setSeatsDF(seatsCache);
        saveCacheState(cacheDFs.getSeatsDF(), "SeatsCache");
    }

    public void loadSharedAirlineDesignatorCache() {
        StructType schema = DataTypes
                .createStructType(new StructField[]{
                        DataTypes.createStructField("participant_name", DataTypes.StringType, true),
                        DataTypes.createStructField("carrier_code_from_mshrdres", DataTypes.StringType, true)
                });
        cacheDFs.setSharedAirlineDesignatorDF(dataFrameLoader.loadFromCsvFile("prod2.max.seq.mshrdes", schema));

        saveCacheState(cacheDFs.getSharedAirlineDesignatorDF(), "SharedAirlineDesignatorCache");
    }

    public static void main(String[] args) throws IOException {

        DB2Properties db2Properties = new DB2Properties();
        db2Properties.loadFromPropertyFile();

        SparkSession spark = createOrGetSession();
        DataFrameLoader dataFrameLoader = new DataFrameLoader(spark, db2Properties);

        CacheLoader cacheLoader = new CacheLoader(dataFrameLoader, LocalDate.of(2017, 10, 25));
        long time = System.nanoTime();
        cacheLoader.loadCaches();
        log.info("EXECUTION TIME OF CACHE LOADER: " + (System.nanoTime() - time));
    }
}
