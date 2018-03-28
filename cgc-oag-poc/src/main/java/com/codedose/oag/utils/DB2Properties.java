package com.codedose.oag.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DB2Properties {

    private static final String PROPERTIES_FILE = "db2.properties";
    String DB_URL;
    String DB_USER;
    String DB_PASSWORD;
    String DB_DRIVER;

    public void loadFromPropertyFile() throws IOException {
        FileInputStream fis = new FileInputStream(new File(PROPERTIES_FILE));

        Properties properties = new Properties();
        properties.load(fis);

        DB_URL = properties.getProperty("db2.url");
        DB_USER = properties.getProperty("db2.user");
        DB_PASSWORD = properties.getProperty("db2.password");
        DB_DRIVER = properties.getProperty("db2.driver");
    }

    public static void main(String[] args) {
        try {
            DB2Properties properties = new DB2Properties();
            properties.loadFromPropertyFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
