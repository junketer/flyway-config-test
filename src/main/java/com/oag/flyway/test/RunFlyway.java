package com.oag.flyway.test;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RunFlyway {
    public static void main(String[] args) {
        Properties flywayConfig = new Properties();

        try (InputStream input = RunFlyway.class.getClassLoader().getResourceAsStream("flyway.config")) {


            // load a properties file
            flywayConfig.load(input);

            Flyway flyway=
                    Flyway.configure()
                            .configuration(flywayConfig)
                           // .dataSource(
                            //        flywayConfig.getProperty("flyway.url"),
                              //      flywayConfig.getProperty("flyway.user"),
                              //      flywayConfig.getProperty("flyway.password"))
//                            //.defaultSchema("SSIMOUT")
                            .load();
            for (MigrationInfo migrationInfo: flyway.info().pending()) {
                System.out.println("pending: " + migrationInfo.getDescription());
            };
            flyway.migrate();
            for (MigrationInfo migrationInfo: flyway.info().applied()) {
                System.out.println("applied : " + migrationInfo.getDescription());
            };

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
