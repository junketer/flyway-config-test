package com.codedose.oag;

import com.oag.wdf.utils.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Rationaliser {
    private static final Logger LOGGER = LogManager.getLogger(Rationaliser.class.getName());

    private static final String EMPTY_OPER_DAYS_OF_WEEK = "       ";

    private List<Long> legSegIdList = new ArrayList<Long>();

    public List<Long> getLegSegIdList() {
        return legSegIdList;
    }

    private String groupingCriteria = "";

    private List<RationaliseWDFRecord> wdfRecordList = new ArrayList<RationaliseWDFRecord>();

    private List<RationaliseWDFRecord> wdfRecordDebugList = new ArrayList<RationaliseWDFRecord>();

    private List<RationaliseWDFRecord> finallegSegList = new ArrayList<RationaliseWDFRecord>();

    private List<Long> oxoDatedLegSegs = new ArrayList<Long>();

    private LocalDate earliestStartDate = null;

    private LocalDate latestStartDate = null;

    private LocalDate latestEndDate = null;

    private LocalDate latestOXODate = null;

    private boolean isAnyLegOXODated = false;

    public Rationaliser(List<RationaliseWDFRecord> recordsList) {
        wdfRecordList = recordsList;
    }

    private Map<LocalDate, String> weekOperDaysMap = new LinkedHashMap<LocalDate, String>();

    public List<RationaliseWDFRecord> rationalise() {

        try {

            populateCalendarDates();

            createEmptyCalendar();

            if (!weekOperDaysMap.isEmpty()) {
                populateCalendarWithLegSegInfo();
                rationaliseCalendar();
                return updateRecords();
            }

        } catch (Exception e) {

            LOGGER.info("Error in rationaliser ", e);
            LOGGER.error("Error in rationaliser ", e);
        }
        return wdfRecordList;
    }


    private List<RationaliseWDFRecord> updateRecords() {
        if (finallegSegList.size() < wdfRecordList.size()) {
            List<RationaliseWDFRecord> updateList = new ArrayList<RationaliseWDFRecord>();
            // Set<Long> updatedLegSegs = new HashSet<Long>();

            for (RationaliseWDFRecord updateRecord : finallegSegList) {
                updateRecord.setLegSegId(wdfRecordList.get(0).getLegSegId());
                updateList.add(updateRecord);
                wdfRecordList.remove(0);
            }
            return updateList;
        }
        return wdfRecordList;
    }

    private void populateCalendarDates() {

        for (RationaliseWDFRecord wdfRecord : wdfRecordList) {

            if (wdfRecord.getEffectiveEndDate().isAfter(LocalDate.of(2037, 12, 1))) {
                wdfRecord.setOxoDated(true);
                if (latestOXODate == null || latestOXODate.isBefore(wdfRecord.getEffectiveEndDate())) {
                    latestOXODate = wdfRecord.getEffectiveEndDate();
                }
                oxoDatedLegSegs.add(wdfRecord.getLegSegId());
                isAnyLegOXODated = true;
            }

            if (getEarliestStartDate() == null
                    || getEarliestStartDate().isAfter(wdfRecord.getEffectiveStartDate())) {
                setEarliestDate(wdfRecord.getEffectiveStartDate());
            }
            if ((getLatestEndDate() == null
                    || (wdfRecord.getEffectiveEndDate().isAfter(getLatestEndDate())))) {
                setLatestEndDate(wdfRecord.getEffectiveEndDate());
            }

            if (latestStartDate == null
                    || latestStartDate.isBefore(wdfRecord.getEffectiveStartDate())) {
                latestStartDate = wdfRecord.getEffectiveStartDate();
            }
        }

        // If all the legsegs are oxodated
        // create the calendar for two more weeks after the latest start date
        // else if any of the leg segs is oxodated then add two more weeks to
        // the latest enddate that is not OXO
        if (getLatestEndDate() == null || isAnyLegOXODated) {

            latestStartDate = latestStartDate.plusWeeks(2);

            if (getLatestEndDate() != null && getLatestEndDate().isAfter(latestStartDate)) {
                latestStartDate = getLatestEndDate().plusWeeks(2);
            }
            setLatestEndDate(latestStartDate.plusDays(7 - latestStartDate.getDayOfWeek().getValue()));

            for (RationaliseWDFRecord wdfRecord : wdfRecordList) {
                if (wdfRecord.isOxoDated()) {
                    wdfRecord.setEffectiveEndDate(getLatestEndDate());
                }
            }
        }
    }

    /**
     * Create empty Calendar array for the weeks covering current group of IVs
     */

    public void createEmptyCalendar() {

        LocalDate weekStartDate = null;
        if (getEarliestStartDate().getDayOfWeek() == DayOfWeek.MONDAY) {
            weekStartDate = getEarliestStartDate();
        } else {
            weekStartDate = getEarliestStartDate().plusDays(1 - getEarliestStartDate().getDayOfWeek().getValue());
        }

        while (getLatestEndDate().isAfter(weekStartDate)
                || getLatestEndDate().compareTo(weekStartDate) == 0) {
            weekOperDaysMap.put(weekStartDate, EMPTY_OPER_DAYS_OF_WEEK);
            weekStartDate = weekStartDate.plusDays(7);

        }

        LOGGER.debug("Created Empty Calendar for " + wdfRecordList + weekOperDaysMap);
    }

    /**
     * Populate the empty calendar created for the full period with the flights schedule for individual week as
     * available in the individual legSeg records
     *
     */
    public void populateCalendarWithLegSegInfo() {

        for (RationaliseWDFRecord legSeg : getWDFRecordList()) {
            LocalDate legSegBeginWeekStartDate = legSeg.getEffectiveStartDate()
                    .plusDays(1 - legSeg.getEffectiveStartDate().getDayOfWeek().getValue());
            LocalDate legSegEndWeekStartDate = legSeg.getEffectiveEndDate()
                    .plusDays(1 - legSeg.getEffectiveEndDate().getDayOfWeek().getValue());

            if(weekOperDaysMap.entrySet().iterator().next().getKey().equals(legSegEndWeekStartDate)){
                String firstWeekOperDays = mergeOperDays(
                        padOperDays(legSeg.getOperDaysOfWeek()
                                .substring(legSeg.getEffectiveStartDate().getDayOfWeek().getValue() - 1,legSeg.getEffectiveEndDate().getDayOfWeek().getValue())),
                        weekOperDaysMap.get(legSegBeginWeekStartDate).toString());
                weekOperDaysMap.put(legSegBeginWeekStartDate, firstWeekOperDays);
            }else{
                String firstWeekOperDays = mergeOperDays(
                        padOperDays(legSeg.getOperDaysOfWeek()
                                .substring(legSeg.getEffectiveStartDate().getDayOfWeek().getValue() - 1)),
                        weekOperDaysMap.get(legSegBeginWeekStartDate).toString());
                weekOperDaysMap.put(legSegBeginWeekStartDate, firstWeekOperDays);
            }

            LocalDate subsequentWeekStartDate = legSegBeginWeekStartDate.plusDays(7);
            while (subsequentWeekStartDate.isBefore(legSegEndWeekStartDate)) {
                weekOperDaysMap.put(subsequentWeekStartDate,
                        mergeOperDays(legSeg.getOperDaysOfWeek(), weekOperDaysMap.get(subsequentWeekStartDate)));
                subsequentWeekStartDate = subsequentWeekStartDate.plusDays(7);
            }

            if(weekOperDaysMap.entrySet().iterator().next().getKey().equals(legSegEndWeekStartDate)){
                String lastWeekOperDays = mergeOperDays(
                        padOperDays(legSeg.getOperDaysOfWeek()
                                .substring(legSeg.getEffectiveStartDate().getDayOfWeek().getValue() - 1,legSeg.getEffectiveEndDate().getDayOfWeek().getValue())),
                        weekOperDaysMap.get(legSegBeginWeekStartDate).toString());
                weekOperDaysMap.put(legSegBeginWeekStartDate, lastWeekOperDays);
            }else{
                String lastWeekOperDays = mergeOperDays(
                        padOperDays(
                                legSeg.getOperDaysOfWeek().substring(0,
                                        legSeg.getEffectiveEndDate().getDayOfWeek().getValue())),
                        weekOperDaysMap.get(legSegEndWeekStartDate).toString());
                weekOperDaysMap.put(legSegEndWeekStartDate, lastWeekOperDays);
            }

        }
        LOGGER.debug("After populate " + weekOperDaysMap);

    }

    private String getOperDaysAsX(String operDays) {
        char[] operDaysArray = operDays.toCharArray();
        String returnString = "";
        for (int i = 0; i < 7; i++) {
            if (operDaysArray[i] != ' ') {
                returnString += "X ";
            } else {
                returnString += "  ";
            }

        }

        return returnString;
    }

    public void rationaliseCalendar() {

        long count = 1;

        boolean operDaysSameAsPreviousWeek = false;

        RationaliseWDFRecord legSegRationalised = new RationaliseWDFRecord(count);

        for (LocalDate weekStartDate : weekOperDaysMap.keySet()) {
            String operDaysOfWeek = weekOperDaysMap.get(weekStartDate).toString();
            boolean firstConsecutiveWeek = false;

            // If there are no days of operation in the current week
            // then end the previous leg and start a new one
            if (operDaysOfWeek == null || operDaysOfWeek.trim().equals("")) {

                if (legSegRationalised.getEffectiveStartDate() != null) {
                    getFinallegSegList().add(legSegRationalised);
                    legSegRationalised = new RationaliseWDFRecord(++count);
                }

            } else {

                // Check if the this is the first value in new Leg
                if (legSegRationalised.getEffectiveStartDate() == null) {
                    legSegRationalised
                            .setEffectiveStartDate(weekStartDate.plusDays(getFirstOperatingDay(operDaysOfWeek) - 1));
                    legSegRationalised.setOperDaysOfWeek(operDaysOfWeek);
                    legSegRationalised.setEffectiveEndDate(
                            weekStartDate.plusDays(getLastOperatingDay(operDaysOfWeek) - 1));
                } else {
                    // Check to to see if the new pattern
                    // matches the previous running pattern
                    if (operDaysOfWeek.equals(legSegRationalised.getOperDaysOfWeek())) {
                        operDaysSameAsPreviousWeek = true;
                    } else {

                        // Check to see if this is the first consecutive week in the currentLeg
                        // In that case there will only be a part match
                        // If the part pattern from previous week matches the new pattern
                        // then club them together
                        if (weekStartDate.minusDays(7).compareTo(legSegRationalised.getEffectiveStartDate()) <= 0) {

                            operDaysSameAsPreviousWeek = isPartMatch(operDaysOfWeek,
                                    legSegRationalised.getOperDaysOfWeek(),
                                    legSegRationalised.getEffectiveStartDate().getDayOfWeek().getValue() - 1);
                            firstConsecutiveWeek = true;

                        }
                    }

                    if (operDaysSameAsPreviousWeek) {
                        legSegRationalised.setOperDaysOfWeek(
                                mergeOperDays(operDaysOfWeek, legSegRationalised.getOperDaysOfWeek()));
                        legSegRationalised.setEffectiveEndDate(
                                weekStartDate.plusDays(getLastOperatingDay(operDaysOfWeek) - 1));
                        operDaysSameAsPreviousWeek = false;
                    } else {

                        String firstMatchingPattern = "";

                        String remainingPattern = "";

                        if (!firstConsecutiveWeek) {
                            // The patterns do not match, so try doing a partial match
                            // Starting from Monday, go as far as you find match with the previous leg
                            // Break at the match, and start a new Leg for the non matching pattern

                            firstMatchingPattern = firstMatchingOperDays(legSegRationalised.getOperDaysOfWeek(),
                                    operDaysOfWeek);
                            remainingPattern = operDaysOfWeek.substring(firstMatchingPattern.length());

                            if (firstMatchingPattern != null
                                    && !firstMatchingPattern.trim().equals("")) {
                                legSegRationalised.setEffectiveEndDate(
                                        weekStartDate.plusDays(getLastOperatingDay(firstMatchingPattern) - 1));
                            }

                        } else {
                            // The operDays pattern do not match for the first consecutive week
                            // so end the current leg at the last operating day before the running pattern
                            // e.g. if the running pattern is ' 67' and the new pattern is '123 5 7'
                            // then end the current leg at day 5 and start the new leg from 7

                            int legSegStartDay = legSegRationalised.getEffectiveStartDate().getDayOfWeek().getValue();

                            firstMatchingPattern = firstMatchingOperDays(
                                    legSegRationalised.getOperDaysOfWeek().substring(
                                            legSegStartDay - 1),
                                    operDaysOfWeek.substring(
                                            legSegStartDay - 1));

                            if (legSegStartDay > 1) {
                                firstMatchingPattern = operDaysOfWeek.substring(0,
                                        legSegStartDay - 1) + firstMatchingPattern;
                            }

                            remainingPattern = operDaysOfWeek
                                    .substring(StringUtils.padLeft(firstMatchingPattern,
                                            legSegRationalised.getEffectiveStartDate().getDayOfWeek().getValue())
                                            .length());

                            // operDaysOfWeek.substring(0,
                            // legSegRationalised.getEffectiveStartDate().getDayOfWeek().getValue() - 1);

                            if (firstMatchingPattern != null
                                    && !firstMatchingPattern.trim().equals("")) {

                                legSegRationalised
                                        .setEffectiveEndDate(getLastOperatingDate(weekStartDate, firstMatchingPattern));
                                legSegRationalised.setOperDaysOfWeek(
                                        mergeOperDays(padOperDays(firstMatchingPattern),
                                                legSegRationalised.getOperDaysOfWeek()));
                            }

                            firstConsecutiveWeek = false;

                        }

                        getFinallegSegList().add(legSegRationalised);
                        legSegRationalised = new RationaliseWDFRecord(++count);

                        if (remainingPattern != null
                                && !remainingPattern.trim().equals("")) {

                            legSegRationalised.setEffectiveStartDate(
                                    weekStartDate.plusDays(getFirstOperatingDay(remainingPattern) - 1));
                            legSegRationalised.setOperDaysOfWeek(padOperDays(remainingPattern));
                            legSegRationalised.setEffectiveEndDate(
                                    weekStartDate.plusDays(getLastOperatingDay(remainingPattern) - 1));
                        }

                    }

                }

            }

        }

        if (legSegRationalised.getEffectiveStartDate() != null) {
            finallegSegList.add(legSegRationalised);
        }
        if (isAnyLegOXODated) {
            finallegSegList.get(finallegSegList.size() - 1).setEffectiveEndDate(latestOXODate);
        }

    }

    private String mergeOperDays(String operDaysOfWeek, String operDaysOfWeek2) {
        char[] mergedDays = new char[7];
        for (int i = 0; i < 7; i++) {
            char charFromFirst = operDaysOfWeek.charAt(i);
            char charFromSecond = operDaysOfWeek2.charAt(i);
            if (charFromFirst == charFromSecond) {
                mergedDays[i] = charFromFirst;
            } else if (charFromFirst == ' ') {
                mergedDays[i] = charFromSecond;
            } else {
                mergedDays[i] = charFromFirst;
            }
        }
        return String.copyValueOf(mergedDays);

    }

    private int getFirstOperatingDay(String operDaysofweek) {
        return Integer.parseInt(operDaysofweek.trim().substring(0, 1));
    }

    private int getLastOperatingDay(String operDaysofweek) {
        return Integer.parseInt(operDaysofweek.trim().substring(operDaysofweek.trim().length() - 1));
    }

    private LocalDate getLastOperatingDate(LocalDate weekStartDate, String operDaysofweek) {
        return weekStartDate
                .plusDays(Integer.parseInt(operDaysofweek.trim().substring(operDaysofweek.trim().length() - 1)) - 1);
    }

    private boolean isPartMatch(String fullOperDayString, String partialOperDayString, int beginIndex) {
        return partialOperDayString.substring(beginIndex)
                .equals(fullOperDayString.substring(beginIndex));
    }

    private String firstMatchingOperDays(String currentOperDays, String newOperDays) {
        char[] currentOperDaysArray = currentOperDays.toCharArray();
        char[] newOperDaysArray = newOperDays.toCharArray();
        String matchingOperDays = "";
        int i = 0;
        while (i < 7 && currentOperDaysArray[i] == newOperDaysArray[i]) {
            matchingOperDays += currentOperDaysArray[i];
            i++;
        }
        return matchingOperDays;

    }

    public String padOperDays(String operDays) {
        operDays = operDays.trim();
        if (!operDays.equals("")) {
            int firstDay = Integer.parseInt(operDays.substring(0, 1));
            operDays = StringUtils.padLeft(operDays, firstDay + operDays.length() - 1);
            operDays = StringUtils.padRight(operDays, 7);
            return operDays;
        } else
            return "       ";
    }

    public List<RationaliseWDFRecord> getFinallegSegList() {
        return this.finallegSegList;
    }

    public void setWDFRecordList(List<RationaliseWDFRecord> legSegList) {
        this.wdfRecordList = legSegList;
    }

    public void setEarliestDate(LocalDate earliestDate) {
        this.earliestStartDate = earliestDate;
    }

    public void setLatestEndDate(LocalDate latestDate) {
        this.latestEndDate = latestDate;
    }

    public LocalDate getEarliestStartDate() {
        return this.earliestStartDate;
    }

    public LocalDate getLatestEndDate() {
        return this.latestEndDate;
    }

    public List<RationaliseWDFRecord> getWDFRecordList() {
        return wdfRecordList;
    }

    public LocalDate getLatestOXODate() {
        return latestOXODate;
    }

    public void setLatestOXODate(LocalDate latestOXODate) {
        this.latestOXODate = latestOXODate;
    }

    public String getGroupingCriteria() {
        return groupingCriteria;
    }

    public void setGroupingCriteria(String groupingCriteria) {
        this.groupingCriteria = groupingCriteria;
    }

}
