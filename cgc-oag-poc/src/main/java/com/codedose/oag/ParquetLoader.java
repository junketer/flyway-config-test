package com.codedose.oag;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class ParquetLoader {
    private final SparkSession spark;
    private final String path;
    private static Logger log = LogManager.getLogger(ParquetLoader.class.getName());

    public ParquetLoader(SparkSession spark, String path) {
        this.spark = spark;
        this.path = path;
    }
    public Dataset<Row> get(String directory)
    {
        log.info(String.format("Retrieving %s from parquet files", directory));
        return spark.read().parquet(path + "/" + directory);
    }
}
