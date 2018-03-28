package com.codedose.oag.examples;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class SparkLargeDatasetSQL {

    public static void main(String[] args) {
        // Define spark context
        // Uncomment master local to run it in intellij
        SparkSession spark = SparkSession
                .builder()
                .appName("Java Spark SQL basic example")
                .config("spark.sql.shuffle.partitions", "6")
                //.master("local")
                .getOrCreate();

        // Read data
        // inferSchema - automatic column types matching
        Dataset<Row> data = spark.read()
                .option("delimiter", ";")
                .option("header", "true")
                .option("inferSchema", "true")
                .csv("data.csv");


        data.createOrReplaceTempView("orders");
        //data.printSchema();
        //data.show();

        Dataset<Row> result = spark.sql(
                "SELECT O.* FROM ORDERS O " +
                        "ORDER BY price ");

        // save results
        result.coalesce(1).write().csv("result-spark-sql-2");


    }

}
