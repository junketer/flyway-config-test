package com.codedose.oag.utils;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public class DatasetWriter {

    public static void saveDatasetToCsv(Dataset<Row> dataset, String fileName) {
        dataset.write()
                .option("quoteAll", true)
                .option("ignoreTrailingWhiteSpace", false)
                .option("ignoreLeadingWhiteSpace", false)
                .option("header", "true")
                .csv(fileName);
    }

}
