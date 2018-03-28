package com.codedose.oag;

import com.codedose.oag.utils.DB2Properties;
import com.codedose.oag.utils.DataFrameLoader;
import com.codedose.oag.utils.UserDefinedFunctions;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import static com.codedose.oag.SparkLoader.createOrGetSession;

/**
 * Main class for running CGC OAG WDF Jobs.
 *
 * Intentionally not serializable - in case of any mistake in lambda it will fail fast.
 */
public class RationaliseProcess {
    private static final Logger log = Logger.getLogger(RationaliseProcess.class.getName());

    public static void main(String[] args) throws IOException {
        log.setLevel(Level.INFO);

        SparkSession spark = createOrGetSession();
        long totalTime = System.nanoTime();
        UserDefinedFunctions udfs = new UserDefinedFunctions(spark);
        Dataset<Row> wdfUnenhancedRecords;
        if(args[0].equals("db"))
            wdfUnenhancedRecords = getUnEnhancedDF(spark);
        else
            wdfUnenhancedRecords = spark.read().parquet("wdfDBUnenhanced");
        wdfUnenhancedRecords.cache();
        long count = wdfUnenhancedRecords.count();
        System.out.println("UnEnhanced count " + count);
        Dataset<Row> rationalised = RationaliserProcessing.process(udfs, wdfUnenhancedRecords);

        long howManyRecords = rationalised.count();
        System.out.println("AFTER RATIONALISATION COUNT " + howManyRecords);

        totalTime = System.nanoTime() - totalTime;
        System.out.println("TIME " + totalTime + "ns = " + TimeUnit.NANOSECONDS.toSeconds(totalTime) + "s");
        log.info(String.format("Retrieved %d records from DB", howManyRecords));

    }

    private static Dataset<Row> getUnEnhancedDF(SparkSession spark) throws IOException {
        DB2Properties db2Properties = new DB2Properties();
        db2Properties.loadFromPropertyFile();
        DataFrameLoader dataFrameLoader = new DataFrameLoader(spark, db2Properties);
        return dataFrameLoader.loadTable("snapshot.wdf_master_data_unenhanced");
    }
}

