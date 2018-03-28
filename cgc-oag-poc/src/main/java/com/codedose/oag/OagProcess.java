package com.codedose.oag;

import com.codedose.oag.utils.DB2Properties;
import com.codedose.oag.utils.DataFrameLoader;
import com.codedose.oag.utils.UserDefinedFunctions;
import com.oag.wdf.utils.DateUtil;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.codedose.oag.SparkLoader.createOrGetSession;
import static com.codedose.oag.utils.DatasetWriter.saveDatasetToCsv;
import static com.codedose.oag.utils.PropertiesLoader.isAdjustDepArrTimesEnabled;
import static com.google.common.base.Preconditions.checkState;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

/**
 * Main class for running CGC OAG WDF Jobs.
 *
 * Intentionally not serializable - in case of any mistake in lambda it will fail fast.
 */
public class OagProcess {
    private static final String EMPTY_QUERY = "query_unenhanced.sql";
    private static final Logger log = Logger.getLogger(OagProcess.class.getName());
    private static final String FILE_NAME_BROWARD = "query_broward.sql";
    private static final String FILE_NAME_ADAPTED_BROWARD_STAGE1 = "query_broward_adapted_stage1.sql";
    private static final String FILE_NAME_ADAPTED_BROWARD_STAGE2 = "query_broward_adapted_stage2.sql";
    private static final String FILE_NAME_AA1 = "query_aa1.sql";
    private static final String SNAP_VIEW_UNENHANCED = "snap_unenhanced";
    private static final String SNAP_VIEW_ENHANCED = "snap_enhanced";

    public static void main(String[] args) throws IOException {
        log.setLevel(Level.INFO);

        SparkSession spark = createOrGetSession();
        long totalTime = System.nanoTime();

        long unenhancedTime = System.nanoTime();
        Dataset<Row> wdfRecords = null;
        if (args.length >= 1) {
            String sourceForQueries = args[0];
            log.info("Running from " + sourceForQueries);
            switch (sourceForQueries) {
                case "from_db":
                    wdfRecords = runUnEnhancedDF(spark);
                    break;
                case "from_parquet":
                    wdfRecords = spark.read().parquet("wdfUnenhanced1");
                    break;
                default:
                    wdfRecords = runUnEnhancedDF(spark);
                    break;
            }
        }
        checkState(wdfRecords != null, "wrong source provided and no WDF Unenhanced was launched");

//        wdfRecords = cutLineage(spark, wdfRecords);
        wdfRecords.printSchema();
        wdfRecords.cache(); // cache and pre-load data
        wdfRecords.count();
        wdfRecords.createOrReplaceTempView(SNAP_VIEW_UNENHANCED);
        unenhancedTime = System.nanoTime() - unenhancedTime;
        log.info("UNENHANCED FROM DB2 TIME " + unenhancedTime + "ns = " + TimeUnit.NANOSECONDS.toSeconds(unenhancedTime) + "s");


        String whichQuery = chooseQuery(args[1]);
        boolean runFullProcessing = whichQuery.equals("full");
        if (runFullProcessing || args.length >= 3) {
            String whereToSave = args.length >= 3
                    ? defaultIfNull(args[2], "to_parquet").toLowerCase()
                    : "full";

            if(runFullProcessing || whereToSave.equals("to_parquet")) {
                unenhancedTime = System.nanoTime();
                String query = readQuery(whichQuery, "query_unenhanced.sql");
                Dataset<Row> queryResult = spark.sql(query);
                writeConfigurationToParquetFile(queryResult);
                unenhancedTime = System.nanoTime() - unenhancedTime;
                log.info("UnEnhanced To Parquet TIME " + unenhancedTime + "ns = " + TimeUnit.NANOSECONDS.toSeconds(unenhancedTime) + "s");
            }
            if(runFullProcessing || whereToSave.equals("to_csv")) {
                long browardTime = System.nanoTime();

                String queryStage1 = readQuery(whichQuery, FILE_NAME_ADAPTED_BROWARD_STAGE1); // filtering
                Dataset<Row> queryResult = spark.sql(queryStage1);

                queryResult.repartition(1).createOrReplaceTempView("broward_filtered");

                String queryStage2 = readQuery(whichQuery, FILE_NAME_ADAPTED_BROWARD_STAGE2); // order

                queryResult = spark.sql(queryStage2);
                writeConfigurationToCSVFile(queryResult);
                browardTime = System.nanoTime() - browardTime;
                log.info("Broward from UnEnhanced TIME " + browardTime + "ns = " + TimeUnit.NANOSECONDS.toSeconds(browardTime) + "s");
            }
        }

        //long howManyRecords = wdfRecords.count();
        totalTime = System.nanoTime() - totalTime;
        System.out.println("TIME " + totalTime + "ns = " + TimeUnit.NANOSECONDS.toSeconds(totalTime) + "s");
        //log.info(String.format("Retrieved %d records from DB", howManyRecords));

    }

    private static Dataset<Row> runUnEnhancedDF(SparkSession spark) throws IOException {
        DB2Properties db2Properties = new DB2Properties();
        db2Properties.loadFromPropertyFile();
        DataFrameLoader dataFrameLoader = new DataFrameLoader(spark, db2Properties);
        LocalDate reportDate = getReportDate();
        UserDefinedFunctions udfs = new UserDefinedFunctions(spark);
        CacheLoader cacheLoader = new CacheLoader(dataFrameLoader, reportDate);
        cacheLoader.loadCaches();

        boolean adjustingEnabled = isAdjustDepArrTimesEnabled();

        LegSegReader legSegReader = new LegSegReader(dataFrameLoader);
        IVListProcessing ivListProcessing = new IVListProcessing(cacheLoader.getCacheDFs(), udfs, adjustingEnabled, reportDate);
        return ivListProcessing.process(legSegReader.queryLegSegRecords(reportDate));
    }

    private static LocalDate getReportDate() {
        String runDate = System.getProperty("runDate");
        LocalDate dateToReturn = null;
        if (runDate != null) {
            try {
                dateToReturn = LocalDate.parse(runDate, DateUtil.CCYY_MM_DDFormatter);
            } catch (DateTimeException e) {
                log.error("Error in supplied date. Correct format is yyyy-mm-dd. Using current date to proceed", e);
            }
        }
        if (dateToReturn == null) {
            log.warn("empty date to run - choosing now()");
            dateToReturn = LocalDate.now();
        }

        return dateToReturn;
    }

    private static String chooseQuery(String arg) {
        switch (arg) {
            case "with_query": return FILE_NAME_ADAPTED_BROWARD_STAGE1;
            case "without_query": return EMPTY_QUERY;
        }
        return "full";
    }

    @SuppressWarnings("SameParameterValue")
    private static String readQuery(String product, String productWhenFull) throws IOException {
        //TODO: consider doing query adaptation in sql generation (old WDFConfig) instead of hardcoding
        //String query = Files.readAllLines(Paths.get("resources/" + FILE_NAME_ADAPTED_BROWARD_ALL)).stream().collect(Collectors.joining(" "));
        if (product.equals("full")) {
            product = productWhenFull;
        }
        return Files.readAllLines(Paths.get("resources/" + product)).stream().collect(Collectors.joining(" "));
    }

    private static Dataset<Row> cutLineage(SparkSession spark, Dataset<Row> wdfRecords) {
        wdfRecords.cache();
        JavaRDD<Row> rdd = wdfRecords.javaRDD();
        wdfRecords = spark.createDataFrame(rdd, wdfRecords.schema());
        return wdfRecords;
    }

    private static void writeConfigurationToCSVFile(Dataset<Row> queryResult) {
        saveDatasetToCsv(queryResult, "dataset-test.csv");
    }

    private static void writeConfigurationToParquetFile(Dataset<Row> queryResult) {
        queryResult
                .write()
                .parquet("wdfUnenhanced");
    }
}

