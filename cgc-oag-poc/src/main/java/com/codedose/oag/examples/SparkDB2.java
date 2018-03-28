package com.codedose.oag.examples;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.util.Date;
import java.util.GregorianCalendar;

import static org.apache.spark.sql.functions.col;

//this example shows how to connect to DB2 database
public class SparkDB2 {
    public static void main(String[] args) throws ClassNotFoundException {
        // Define spark context
        // Uncomment master local to run it in intellij

        Logger log = LogManager.getRootLogger();
        log.setLevel(Level.INFO);

        SparkConf sparkConf = new SparkConf();
        sparkConf.setMaster("spark://10.0.2.15:7077");
        SparkSession spark = SparkSession.builder()
                .config(sparkConf)
                .getOrCreate();

//        String url = args[0];
//        String driver = args[1];


        //for some reason jdbc driver cannot be auto-loaded
        //use driver option: https://spark.apache.org/docs/2.2.0/sql-programming-guide.html#jdbc-to-other-databases

        Dataset<Row> df = spark.read().format("jdbc")
                .option("url", "jdbc:db2://192.168.1.25:50000/sample")
                .option("user", "db2inst1")
                .option("password", "1db2inst")
                .option("dbtable", "EMPLOYEE")
                .option("driver", "com.ibm.db2.jcc.DB2Driver")
                .load();

        Date utilDate = new GregorianCalendar(1984, 1, 1).getTime();
        //Spark does not support java.util.Date RuntimeException: Unsuported literal type class, use java.sql.Date instead
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        Dataset<Row> d = df.filter(col("BIRTHDATE").gt(sqlDate));
        for(Row r : d.collectAsList()) {
            log.info(r);
        }
    }
}

