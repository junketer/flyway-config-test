package com.oag.wdf.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {

    public static final DateTimeFormatter CCYYMMDDFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter CCYY_MM_DDFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter HHmmFormatter = DateTimeFormatter.ofPattern("HHmm");

    private static final String EVERYDAY_OF_WEEK = "1234567";

    public static LocalDate adjustStartDate(LocalDate runDate, LocalDate ivEffStartDate, String operDaysOfWeek) {
        if (ivEffStartDate.isAfter(runDate)) {
            runDate = ivEffStartDate;

        }

        String effectiveOperDaysOfWeek = operDaysOfWeek.substring(runDate.getDayOfWeek().getValue() - 1).trim();
        int firstOperDayOfWeek = Integer.parseInt(operDaysOfWeek.trim().substring(0, 1));
        if (!effectiveOperDaysOfWeek.equals("")) {
            firstOperDayOfWeek = Integer.parseInt(effectiveOperDaysOfWeek.trim().substring(0, 1));
            return runDate.plusDays(firstOperDayOfWeek
                    - runDate.getDayOfWeek().getValue());
        } else {
            return runDate.plusDays((7 - runDate.getDayOfWeek().getValue()) + firstOperDayOfWeek);
        }
    }

    public static LocalDate adjustEndDate(LocalDate endDate, String operDaysOfWeek) {

        // If the end date is 01-01-2038, then do not modify it
        if (endDate.equals(LocalDate.of(2038, 01, 01))) {
            return endDate;
        }

        int lastOperDayOfWeek = Integer.parseInt(operDaysOfWeek.trim().substring(operDaysOfWeek.trim().length() - 1));
        if (endDate.getDayOfWeek().getValue() <= lastOperDayOfWeek) {
            return endDate;
        } else {
            return endDate.minusDays(endDate.getDayOfWeek().getValue() - lastOperDayOfWeek);
        }
    }

    public static String shiftOperDaysOfWeek(String operDaysOfWeek, int diffDays) {

        char[] operDaysResult = { ' ', ' ', ' ', ' ', ' ', ' ', ' ' };

        if (diffDays != 0
                && !operDaysOfWeek.equals(EVERYDAY_OF_WEEK)) {

            char[] operDays = operDaysOfWeek.toCharArray();
            int resultIndex = 0;
            for (int i = 0; i <= 6; i++) {
                resultIndex = i + diffDays;
                if (resultIndex >= 0
                        && resultIndex < 6) {
                } else if (resultIndex < 0) {
                    resultIndex += 7;
                } else if (resultIndex > 6) {
                    resultIndex -= 7;
                }
                if (operDays[i] != ' ') {
                    operDaysResult[resultIndex] = (char) ('0' + resultIndex + 1);
                }
            }

        } else {
            operDaysResult = operDaysOfWeek.toCharArray();
        }
        return String.valueOf(operDaysResult);

    }

    public static String adjustOperatingDaysOfWeek(LocalDate startDate, LocalDate endDate, String operDaysOfWeek) {

        char[] newOperDays = { ' ', ' ', ' ', ' ', ' ', ' ', ' ' };
        long differenceInDays = ChronoUnit.DAYS.between(startDate, endDate);
        char[] operDays = operDaysOfWeek.toCharArray();
        int noOfOperatingDays = 0;

        for(int i=0; i<operDays.length; i++){
            if (operDays[i] != ' ') {
                noOfOperatingDays++;
            }
        }

        if((differenceInDays+1 < noOfOperatingDays) && !operDaysOfWeek.equals(EVERYDAY_OF_WEEK)){

            for (int i = 0; i < endDate.getDayOfWeek().getValue(); i++) {
                newOperDays[i] = operDays[i];
            }

        } else {
            newOperDays = operDaysOfWeek.toCharArray();
        }
        return String.valueOf(newOperDays);
    }
}
