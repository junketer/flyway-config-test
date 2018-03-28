package com.oag.wdf.utils;

public class StringUtils {

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static void main(String[] args) {

        System.out.println(String.format("%1$5s", 0).replace(' ', '0'));

    }

}
