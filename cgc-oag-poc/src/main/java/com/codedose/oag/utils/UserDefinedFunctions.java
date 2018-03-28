package com.codedose.oag.utils;

import com.codedose.oag.RationaliseWDFRecord;
import com.codedose.oag.Rationaliser;
import com.oag.core.schedules.model.ls.*;
import com.oag.wdf.master.constants.DataElementIdentifier;
import com.oag.wdf.utils.DateUtil;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF3;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import scala.collection.JavaConversions;
import scala.collection.mutable.WrappedArray;

import javax.xml.bind.JAXBException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

import static java.util.stream.Collectors.toList;
import static org.apache.spark.sql.functions.callUDF;

public class UserDefinedFunctions implements Serializable {
    private final String PADSERVICE = "padservice";
    private final String ADDMINUTES = "addminutes";
    private final String GETDISTANCE = "getdistance";
    private final String SHIFT_OPER_DAYS_OF_WEEK = "shiftoperdaysofweek";
    private final String ADJUST_START_DATE = "adjuststartdate";
    private final String ADJUST_END_DATE = "adjustenddate";
    private final String ADJUST_OPER_DAYS_OF_WEEK = "adjustoperdaysofweek";
    private final String PROCESSDEI = "processdei";
    private final String PROCESSCOMMENT1050ANDDUPLICATELEG = "PROCESSCOMMENT1050ANDDUPLICATELEG";
    private final String GETINTERMEDIATEPORTS = "GETINTERMEDIATEPORTS";
    private final String SORTTUPLE = "SORTTUPLE";
    private final String PROCESSCODESHARE = "PROCESSCODESHARE";
    private final String GETSHAREDAIRLINEDESIGNATORSTOPPING = "GETSHAREDAIRLINEDESIGNATORSTOPPING";
    private final String RATIONALISE = "RATIONALISE";

    private SparkSession spark;

    public UserDefinedFunctions(SparkSession spark) {
        this.spark = spark;
        this.spark.udf().register(PADSERVICE, (Integer service, Integer padLength) -> getServiceNumStr(service, padLength), DataTypes.StringType);
        this.spark.udf().register(GETDISTANCE, (BigDecimal depLat, BigDecimal depLong, BigDecimal arrLat, BigDecimal arrLong) -> getDistance(depLat, depLong, arrLat, arrLong), DataTypes.DoubleType);
        this.spark.udf().register(SHIFT_OPER_DAYS_OF_WEEK, (String operDays, Integer diffDays) -> DateUtil.shiftOperDaysOfWeek(operDays, diffDays), DataTypes.StringType);
        this.spark.udf().register(ADJUST_START_DATE, (Date currentDate, Date legEffStartDate, String operDaysOfWeek) -> java.sql.Date.valueOf(DateUtil.adjustStartDate(currentDate.toLocalDate(), legEffStartDate.toLocalDate(), operDaysOfWeek)), DataTypes.DateType);
        this.spark.udf().register(ADJUST_END_DATE, (Date legEffStartDate, String operDaysOfWeek) -> java.sql.Date.valueOf(DateUtil.adjustEndDate( legEffStartDate.toLocalDate(), operDaysOfWeek)), DataTypes.DateType);
        this.spark.udf().register(ADDMINUTES, (Timestamp time, Integer minutes) -> new Timestamp(time.getTime() + minutes * 60 * 1000 ) , DataTypes.TimestampType);
        this.spark.udf().register(ADJUST_OPER_DAYS_OF_WEEK, (Date legEffStartDate, Date legEffEndDate, String operDaysOfWeek) -> (DateUtil.adjustOperatingDaysOfWeek(legEffStartDate.toLocalDate(), legEffEndDate.toLocalDate(), operDaysOfWeek)), DataTypes.StringType);
        this.spark.udf().register(PROCESSDEI, (UDF3<String, String, String, Object>) this::processDEIElements, DataTypes.IntegerType);
        this.spark.udf().register(PROCESSCOMMENT1050ANDDUPLICATELEG, (String duplicateLegXML, Integer numberOfStops, Long aircraftChange, Long carrierId) -> processComment1050AndDuplicateLegXML(duplicateLegXML, numberOfStops, aircraftChange, carrierId) ,
                DataTypes.createStructType(new StructField[]{
                        DataTypes.createStructField("comment1050", DataTypes.StringType, false),
                        DataTypes.createStructField("operating_marker", DataTypes.StringType, false),
                        DataTypes.createStructField("ghost_flight_marker", DataTypes.StringType, false),
                        DataTypes.createStructField("duplicate_flight_marker", DataTypes.StringType, false)
                }));

        this.spark.udf().register(PROCESSCODESHARE, (String duplicateLegXML, Integer numberOfStops) -> processCodeShareAndAircraftOwnerAndCrewProvider(duplicateLegXML, numberOfStops) ,
                DataTypes.createStructType(new StructField[]{
                        DataTypes.createStructField("commercial", DataTypes.LongType, true),
                        DataTypes.createStructField("sharing", DataTypes.LongType, true)
                }));
        this.spark.udf().register(GETINTERMEDIATEPORTS, (WrappedArray<String> routingArray, Integer numberOfStops, String depPortCd) -> getIntermediatePorts(routingArray, numberOfStops, depPortCd), DataTypes.StringType);
        this.spark.udf().register(SORTTUPLE, (WrappedArray<Row> routingArray) -> sortTuple(routingArray), DataTypes.createArrayType(DataTypes.StringType));
        this.spark.udf().register(GETSHAREDAIRLINEDESIGNATORSTOPPING, (WrappedArray<Row> collected_legs_info,
                                                                       String intermediatePorts,
                                                                       Integer numOfStops,
                                                                       String depPortCode,
                                                                       Long legSegId) -> getSharedAirlineDesignator(collected_legs_info, intermediatePorts, numOfStops, depPortCode, legSegId), DataTypes.StringType);
        this.spark.udf().register(RATIONALISE, (WrappedArray<Row> to_rationalise) -> rationalise(to_rationalise), DataTypes.createArrayType(
                DataTypes.createStructType(new StructField[]{
                        DataTypes.createStructField("leg_seg_id", DataTypes.LongType, false),
                        DataTypes.createStructField("oper_days_of_week", DataTypes.StringType, false),
                        DataTypes.createStructField("leg_seg_eff_start_date", DataTypes.DateType, false),
                        DataTypes.createStructField("leg_seg_eff_end_date", DataTypes.DateType, false),
                        DataTypes.createStructField("is_oxo_dated", DataTypes.BooleanType, false)
                })
        ));
    }

    private List<Row> rationalise(WrappedArray<Row> in) {
        List<RationaliseWDFRecord> to_rationalise = JavaConversions.seqAsJavaList(in).stream()
                .map(r -> new RationaliseWDFRecord(r.getLong(0), r.getString(1), r.getDate(2).toLocalDate(), r.getDate(3).toLocalDate()))
                .collect(toList());
        Rationaliser r = new Rationaliser(to_rationalise);
        List<Row> to_output = r.rationalise().stream()
                .map(record -> RowFactory.create(record.getLegSegId(), record.getOperDaysOfWeek(), java.sql.Date.valueOf(record.getEffectiveStartDate()), java.sql.Date.valueOf(record.getEffectiveEndDate()), record.isOxoDated()))
                .collect(toList());
        return to_output;
    }

    private String getSharedAirlineDesignator(WrappedArray<Row> collected_legs_info, String intermediatePorts, Integer numOfStops, String depPortCode, Long leg_seg_id) {
        List<Row> nonStoppingLegsOrderedList = JavaConversions.seqAsJavaList(collected_legs_info).stream()
                .sorted(Comparator.comparing((Row r) -> -r.getInt(4))
                        .thenComparing(r -> r.getInt(5)))
                .filter(r -> r.getInt(0) == 1).collect(toList());
        int startingComponentLegIndex = 0;
        for(Row r : nonStoppingLegsOrderedList) {
            if(intermediatePorts.substring(0,3).equals(r.getString(1)) && r.getString(2).equals(depPortCode)) {
                break;
            }
            startingComponentLegIndex += 1;
        }

        String sharedAirlineDesignatorCode = "";
        if(nonStoppingLegsOrderedList.size() > 0) {
            sharedAirlineDesignatorCode = nonStoppingLegsOrderedList.get(startingComponentLegIndex).getString(3);
            for (int i = startingComponentLegIndex; i <= (startingComponentLegIndex
                    + numOfStops); i++) {
                if (!sharedAirlineDesignatorCode
                        .contentEquals(nonStoppingLegsOrderedList.get(i).getString(3))) {

                    sharedAirlineDesignatorCode = "";
                }
            }
        }
        return sharedAirlineDesignatorCode;
    }

    private static List<String> sortTuple(WrappedArray<Row> routingArrayList)
    {
        return JavaConversions.seqAsJavaList(routingArrayList).stream()
                .sorted(Comparator.comparingInt(lhs -> lhs.getInt(0)))
                .map(lhs -> lhs.getString(1))
                .collect(toList());
    }

    private static String pad(String input, char pad, int requiredLength, boolean left) {

        StringBuffer buffer = input != null ? new StringBuffer(input) : new StringBuffer(requiredLength);
        if ( buffer.length() < requiredLength ) {
            char[] padded = new char[requiredLength - buffer.length()];
            Arrays.fill(padded, pad);
            if ( left ) {
                buffer.reverse();
            }
            buffer.append(padded);
            if ( left ) {
                buffer.reverse();
            }
        }
        return buffer.toString();
    }

    private String getIntermediatePorts(WrappedArray<String> routingArrayList, Integer numberOfStops, String depPortCd)
    {
        List<String> routingList = JavaConversions.seqAsJavaList(routingArrayList);
        int intermediatePortsStartIndex = routingList.indexOf(depPortCd) + 1;

        String intermediatePorts = "";

        for (int i = 0; i < numberOfStops; i++) {

            intermediatePorts += routingList.get(intermediatePortsStartIndex + i);
        }

        return intermediatePorts;
    }

    private String getServiceNumStr(int serviceNum, int padLength) {
        if (serviceNum < 1000) { // i.e. if serviceNumber is less than 4 digits

/*			return com.oag.core.common.util.StringUtil.padLeft(String.valueOf(serviceNum), '0',
					padLength + String.valueOf(serviceNum).length());*/
            return pad(String.valueOf(serviceNum),'0', padLength+String.valueOf(serviceNum).length(), true);

        } else {
            return String.valueOf(serviceNum);
        }
    }
    private Double getDistance(BigDecimal depPortIdLatitude, BigDecimal depPortIdLongitude, BigDecimal arrPortIdLatitude, BigDecimal arrPortIdLongitude)
    {
        double distace = 0.0D;
        distace = Math.acos(Math.sin(Math.toRadians(depPortIdLatitude.doubleValue())) * Math.sin(Math.toRadians(arrPortIdLatitude.doubleValue())) +
                Math.cos(Math.toRadians(depPortIdLatitude.doubleValue())) * Math.cos(Math.toRadians(arrPortIdLatitude.doubleValue())) * Math.cos(Math.toRadians(arrPortIdLongitude.doubleValue()) - Math.toRadians(depPortIdLongitude.doubleValue())));
        distace = Math.toDegrees(distace);
        distace = distace * 60D * 1.1509992099999999D;
        return distace;
    }

    private Integer processDEIElements(String depPortCode, String specificAirCraftType, String optionalElementXML) {

        OptionalElementList oEList = new OptionalElementList(optionalElementXML);

        for (int i = 0; i < oEList.size(); i++) {
            OptionalElement element = (OptionalElement) oEList.get(i);
            int dei = element.getOptionalElementNum();
            if (dei == DataElementIdentifier.SUBJECT_TO_GOVT_APPROVAL.getCode()) {

                //TODO: CGConsulting: wdfRecord.setGovtApprovalMarker("X");

            } else if (dei == DataElementIdentifier.PLANE_CHANGE_AT_BOARD_POINT_WITHOUT_AIRCRAFT_TYPE_CHANGE
                    .getCode()) {

                //TODO: CGConsulting: aircraftChangeMarkerPortList.add(wdfRecord.getDepPortCode());
                //TODO: CGConsulting: aircraftChangeTypesDEI210List.add(wdfRecord.getSpecificAircraftType());
                return 1;

            } else if (dei == DataElementIdentifier.IN_FLGHT_SERVICE_INFORMATION.getCode()) {

                //TODO: CGConsulting: wdfRecord.setInFlightService(element.getValueListAsString().replace(' ', '/'));

            } else if (dei == DataElementIdentifier.INTERNATIONAL_DOMESTIC_STATUS_OVERRIDE.getCode()) {

                //TODO: CGConsulting: wdfRecord.setDomIntMarker(element.getValueListAsString().replace(" ", ""));

            }

            /*
            TODO: CGConsulting:
            if (legSegRecord.getNumberOfStops() == 0) {

                String secureFlightFlag = LegSegDefaultDEIValueCache.get(legSegRecord.getLegSegId());

                String eTicketing = CarrierDefaultDEIValueCache.getDefaultETicketingValueForCarrier(
                        legSegRecord.getCarrierId(), legSegRecord.getSchedVersionId());

                String automatedCheckin = CarrierDefaultDEIValueCache.getDefaultAutomatedCheckInValueForCarrier(
                        legSegRecord.getCarrierId(), legSegRecord.getSchedVersionId());

                if (dei == DataElementIdentifier.SECURE_FLGHT_INFORMATION.getCode()) {

                    secureFlightFlag = element.getValueListAsString();

                } else if (dei == DataElementIdentifier.ELECTRONIC_TICKETING_INFORMATION.getCode()) {

                    eTicketing = element.getValueListAsString();

                } else if (dei == DataElementIdentifier.AUTOMATED_CHECK_IN_INFORMATION.getCode()) {

                    automatedCheckin = element.getValueListAsString();

                }

                if (!secureFlightFlag.equals("")) {

                    secureFlightValue = secureFlightFlag;

                    secureFlightValueSet.add(secureFlightFlag);
                    wdfRecord.setSecureFlightMarker(secureFlightValue);

                    secureFlightPortsSet.add(LocationCodeIATACache.get(legSegRecord.getDepPortId()));
                    secureFlightPortsSet.add(LocationCodeIATACache.get(legSegRecord.getArrPortId()));
                }

                if (!eTicketing.equals("")) {

                    eTicketingValue = eTicketing;

                    wdfRecord.seteTicketing(eTicketingValue);

                    eTicketingPortsSet.add(LocationCodeIATACache.get(legSegRecord.getDepPortId()));
                    eTicketingPortsSet.add(LocationCodeIATACache.get(legSegRecord.getArrPortId()));

                }

                if (!automatedCheckin.equals("")) {

                    automatedCheckinValue = automatedCheckin;

                    wdfRecord.setAutomatedCheckIn(automatedCheckinValue);

                    eTicketingPortsSet.add(LocationCodeIATACache.get(legSegRecord.getDepPortId()));
                    eTicketingPortsSet.add(LocationCodeIATACache.get(legSegRecord.getArrPortId()));

                }

            }*/
        }
        return 0;
    }

    private Row processComment1050AndDuplicateLegXML(String duplicateLegXML, Integer numberOfStops, Long aircraftChange, Long carrierId)
            throws JAXBException {
        DuplicateLegList dupLegList = new DuplicateLegList(duplicateLegXML);

        String comment10_50 = "";
        String operatingMarker = "";
        String duplicateFlightMarker = "";
        long firstDupCarrierId = 0;
        String ghostFlightMarker = "";

        Set<Integer> legSeqSet = new HashSet<Integer>();
        boolean hasAllLegsInfo = false;
        boolean tenFlag = false;
        boolean fiftyFlag = false;
        for (int i = 0; i < dupLegList.size(); i++) {
            DuplicateLeg dupLeg = (DuplicateLeg) dupLegList.get(i);

            int PTCd = Integer.parseInt(dupLeg.getParticipantTypeCode());
            if ((PTCd % 50) == 0) {
                fiftyFlag = true;
            } else if ((PTCd % 10) == 0) {
                tenFlag = true;
            }

            if (i == 0) {
                firstDupCarrierId = dupLeg.getCarrierId();
            }

            legSeqSet.add(dupLeg.getLegSequenceNum());
        }

        if ((legSeqSet.size() == (numberOfStops + 1))
                || (numberOfStops == 0)) {
            hasAllLegsInfo = true;
        }

        if (tenFlag || fiftyFlag) {

            duplicateFlightMarker = "D";

            if (tenFlag && fiftyFlag) {

                comment10_50 = "015";

            } else if (tenFlag && !fiftyFlag) {
                if (hasAllLegsInfo) {

                    comment10_50 = "010";
                    operatingMarker = "O";

                } else {

                    comment10_50 = "011";

                }
            } else if (!tenFlag && fiftyFlag) {

                if (hasAllLegsInfo) {

                    comment10_50 = "050";
                    operatingMarker = "N";

                    if (firstDupCarrierId == carrierId
                            && aircraftChange.equals(1)) {

                        ghostFlightMarker = "G";

                    }
                } else {

                    comment10_50 = "055";
                }
            }
        }

        return RowFactory.create(comment10_50, operatingMarker, ghostFlightMarker, duplicateFlightMarker);

        //wdfRecord.setComment1050(comment10_50);
        //wdfRecord.setOperatingMarker(operatingMarker);
        //wdfRecord.setGhostFlightMarker(ghostFlightMarker);
        //wdfRecord.setDuplicateFlightMarker(duplicateFlightMarker);

        //processDupCarriersAndDupFlights(legSegRecord, wdfRecord);
    }

    private Row processCodeShareAndAircraftOwnerAndCrewProvider(String participantXML, Integer numberOfStops) {

        ParticipantList participantList = new ParticipantList(participantXML);

        Long keyForSharedAirlineDesignatorCommercial = null;
        Long keyForSharedAirlineDesignatorSharing = null;
        for (int i = 0; i < participantList.size(); i++) {
            Participant participant = (Participant) participantList.get(i);

            if (numberOfStops == 0) {

                if (participant.getParticipantTypeCode()
                        .equals(DataElementIdentifier.CODE_SHARING_COMMERCIAL_DUPLICATE.getCodeStr())) {
                    keyForSharedAirlineDesignatorCommercial = participant.getParticipantId();
                    keyForSharedAirlineDesignatorSharing = null;

                    //wdfRecord.setSharedAirlineDesignator(CombinedCarrierCache.get(participant.getParticipantId()));
                    //wdfRecord.setSharedAirlineDesignatorName(CarrierNameCache.get(participant.getParticipantId()));

                } else if (participant.getParticipantTypeCode()
                        .startsWith(DataElementIdentifier.CODE_SHARING_OPERATING_AIRLINE_DISCLOSURE.getCodeStr())) {

                    // LOGGER.info(PartyRoleNameCache.get(participant.getParticipantNameId()));
                    keyForSharedAirlineDesignatorSharing = participant.getParticipantNameId();
                    keyForSharedAirlineDesignatorCommercial = null;
                    //wdfRecord
                    //        .setSharedAirlineDesignator(getCarrierCodeFromMshrdres(participant.getParticipantNameId()));
                    //wdfRecord
                    //        .setSharedAirlineDesignatorName(PartyRoleNameCache.get(participant.getParticipantNameId()));

                } else if (participant.getParticipantTypeCode()
                        .startsWith(DataElementIdentifier.AIRCRAFT_OWNER_SPECIFICATION.getCodeStr())) {

                    //wdfRecord.setAircraftOwner(getCarrierCodeFromMshrdres(participant.getParticipantNameId()));

                } else if (participant.getParticipantTypeCode()
                        .startsWith(DataElementIdentifier.COCKPIT_CREW_EMPLOYER_SPECIFICATION.getCodeStr())) {

                    //wdfRecord.setCockpitCrewProvider(getCarrierCodeFromMshrdres(participant.getParticipantNameId()));

                } else if (participant.getParticipantTypeCode()
                        .startsWith(DataElementIdentifier.CABIN_CREW_EMPLOYER_SPECIFICATION.getCodeStr())) {

                    //wdfRecord.setCabinCrewProvider(getCarrierCodeFromMshrdres(participant.getParticipantNameId()));

                }
            }

        }
        return RowFactory.create(keyForSharedAirlineDesignatorCommercial, keyForSharedAirlineDesignatorSharing);
    }

    public Column padService(Column service, Column padLength) {
        return callUDF(PADSERVICE, service, padLength);
    }
    public Column addMinutes(Column date, Column minutes) {
        return callUDF(ADDMINUTES, date, minutes);
    }
    public Column getDistance(Column depLat, Column depLong, Column arrLat, Column arrLong) {
        return callUDF(GETDISTANCE, depLat, depLong, arrLat, arrLong);
    }

    public Column shiftOperDaysOfWeek(Column leg_seg_oper_days_of_week, Column seg_arr_interval_days) {
        return callUDF(SHIFT_OPER_DAYS_OF_WEEK, leg_seg_oper_days_of_week, seg_arr_interval_days);
    }

    public Column adjustStartDate(Column currentData, Column legEffStartDate, Column operDaysOfWeek) {
        return callUDF(ADJUST_START_DATE, currentData, legEffStartDate, operDaysOfWeek);
    }
    public Column adjustEndDate(Column legEffStartDate, Column operDaysOfWeek) {
        return callUDF(ADJUST_END_DATE, legEffStartDate, operDaysOfWeek);
    }

    public Column adjustOperDaysOfWeek(Column legEffStartDate, Column legEffEndDate, Column operDaysOfWeek) {
        return callUDF(ADJUST_OPER_DAYS_OF_WEEK, legEffStartDate, legEffEndDate, operDaysOfWeek);
    }
    
    public Column processDEIElement(Column depPortCode, Column specificAirCraftType, Column optionalElementXML) {
        return callUDF(PROCESSDEI, depPortCode, specificAirCraftType, optionalElementXML);
    }
    
    public Column processComment1050AndDuplicateLegXML(Column duplicateLegXML, Column numberOfStops, Column aircraftChange, Column carrierId) {
        return callUDF(PROCESSCOMMENT1050ANDDUPLICATELEG, duplicateLegXML, numberOfStops, aircraftChange, carrierId);
    }

    public Column getIntermediatePorts(Column routing_cd_array, Column num_of_stops_cnt, Column dep_port_cd) {
        return callUDF(GETINTERMEDIATEPORTS, routing_cd_array, num_of_stops_cnt, dep_port_cd);
    }

    public Column sortTuple(Column routing_cd_array) {
        return callUDF(SORTTUPLE, routing_cd_array);
    }

    public Column processCodeShare(Column participantList, Column numberOfStops) {
        return callUDF(PROCESSCODESHARE, participantList, numberOfStops);
    }

    public Column getSharedAirlineDesignatorStopping(Column collected_legs_info, Column intmdte_ports, Column num_of_stops_cnt, Column dep_port_code, Column legSegId) {
        return callUDF(GETSHAREDAIRLINEDESIGNATORSTOPPING, collected_legs_info, intmdte_ports, num_of_stops_cnt, dep_port_code, legSegId);
    }

    public Column rationalise(Column collected_legs_to_rationalise) {
        return callUDF(RATIONALISE, collected_legs_to_rationalise);
    }
}
