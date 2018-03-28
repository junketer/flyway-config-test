package com.codedose.oag.utils;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.ListOutcome;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.StructType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.google.common.base.Preconditions.checkState;
import static java.util.Arrays.stream;

public class DataFrameLoader {

    private static final String SCHEMA_NAME = "snapshot.";
    private static final Logger log = Logger.getLogger(DataFrameLoader.class);
    public static int DEFAULT_PARTITION_COUNT;

    public static Dataset<Row> cutLineage(Dataset<Row> dataset, String name) {
        RDD<Row> rdd = dataset.rdd();
        rdd.setName(name);
        dataset = dataset.sparkSession().createDataFrame(rdd, dataset.schema());
        return dataset;
    }

    private static class TableMetadata {
        String tableName;
        String partitionColumn;
        long upperBound;
        long lowerBound;
        int numPartitions;
    }

    private final SparkSession spark;
    private final DB2Properties db2Properties;
    private final ConcurrentHashMap<String, TableMetadata> tableMetadata = new ConcurrentHashMap<>();

    public DataFrameLoader(SparkSession spark, DB2Properties props) {
        db2Properties = props;
        this.spark = spark;
        fillMetadata();
    }


    // TODO Maybe save this in file, i.e. CSV? Maybe some heuristic to avoid outliers?
    private void fillMetadata() {
        Connection sqlConnection = Jdbc.createConnection(db2Properties);
        JdbcSession jdbcSession = new JdbcSession(sqlConnection);
        jdbcSession.autocommit(false);
        int partitionCount = Runtime.getRuntime().availableProcessors() / 2;
        fillTableMetadata(SCHEMA_NAME + "snap_feature_coding", "feature_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_coding_scheme", "coding_scheme_category_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_feature_assoc_type", "feature_assoc_type_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_feature_assoc", "feature_assoc_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_party_relationship_member", "relationship_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_party_relationship", "relationship_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_party_role_name", "party_role_name_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_party_role", "party_role_id", jdbcSession, partitionCount);

        fillTableMetadata(SCHEMA_NAME + "snap_leg_opt_element", "leg_seg_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_equipment_cabin_config", "equipment_config_id", jdbcSession, partitionCount);
        fillTableMetadata(SCHEMA_NAME + "snap_sched_version", "carrier_id", jdbcSession, partitionCount);

        partitionCount = Runtime.getRuntime().availableProcessors();
        DEFAULT_PARTITION_COUNT = partitionCount;
        fillTableMetadata(SCHEMA_NAME + "snap_sched_version", "sched_version_id", jdbcSession, partitionCount, true);
        fillTableMetadata(SCHEMA_NAME + "snap_iv", "iv_id", jdbcSession, partitionCount, true);
        fillTableMetadata(SCHEMA_NAME + "snap_leg_seg", "iv_id", jdbcSession, partitionCount, true);

        fillTableMetadata(SCHEMA_NAME + "wdf_master_data_unenhanced", "iv_id", jdbcSession, partitionCount, true);
        Jdbc.closeConnection(sqlConnection);
    }

    private void fillTableMetadata(String tableName, String partitionColumn, JdbcSession jdbcSession) {
        fillTableMetadata(tableName, partitionColumn, jdbcSession, Runtime.getRuntime().availableProcessors());
    }

    private void fillTableMetadata(String tableName, String partitionColumn, JdbcSession jdbcSession, int numberOfPartitions) {
        fillTableMetadata(tableName, partitionColumn, jdbcSession, numberOfPartitions, false);
    }

    private void fillTableMetadata(String tableName, String partitionColumn, JdbcSession jdbcSession, int numberOfPartitions, boolean rewriteQuery) {
        TableMetadata data = new TableMetadata();
        data.tableName = rewriteQuery ? createQuery(tableName, partitionColumn, numberOfPartitions) : tableName;
        data.partitionColumn = rewriteQuery ? "partition_id" : partitionColumn;
        data.numPartitions = numberOfPartitions;

        readBoundsFor(data.tableName, data.partitionColumn, jdbcSession, data);

        tableMetadata.put(tableName, data);
    }

    private static String createQuery(String tableName, String partitionColumn, int numberOfPartitions) {
        return String.format("(select mod(s.%s, %s) as partition_id, s.* from %s s)", partitionColumn, numberOfPartitions, tableName);
    }

    private void readBoundsFor(String tableName, String partitionColumn, JdbcSession jdbcSession, TableMetadata data) {
        if (data.partitionColumn.equalsIgnoreCase("partition_id")) {
            data.upperBound = data.numPartitions;
            data.lowerBound = 0;
            return;
        }
        String query = String.format("select min(a.%s) as lowerBound, max(a.%s) as upperBound from %s a", partitionColumn, partitionColumn, tableName);

        try {
            List<Pair<Long, Long>> values = jdbcSession.sql(query).select(new ListOutcome<>(rset -> Pair.of(rset.getLong("lowerBound"), rset.getLong("upperBound"))));
            checkState(values.size() == 1, "wrong number of results %s for bound query for table %s", values.size(), tableName);
            data.upperBound = values.get(0).getRight();
            data.lowerBound = values.get(0).getLeft();
            checkState(data.lowerBound <= data.upperBound, "lower bound can't be greater than upper bound, check for table: %s", tableName);
        } catch (SQLException e) {
            throw new RuntimeException("error during reading bounds for table " + tableName, e);
        }
    }

    public Dataset<Row> loadTable(String tableName){
        return loadTableGeneric(tableName, true, false);
    }

    @SuppressWarnings("SameParameterValue")
    private Dataset<Row> loadTableGeneric(String tableName, boolean useMetadata, boolean cache) {
        String fetchSize = "256";

        DataFrameReader dataFrameReader = spark.read().format("jdbc")
                .option("url", db2Properties.DB_URL + ":fetchSize=" + fetchSize + ";enableRowsetSupport=1;")
                .option("user", db2Properties.DB_USER)
                .option("password", db2Properties.DB_PASSWORD)
                .option("dbtable", tableName(tableName, useMetadata))
                .option("block size", fetchSize)
                .option("fetchsize", fetchSize)
                .option("driver", db2Properties.DB_DRIVER);

        if (useMetadata && tableMetadata.containsKey(tableName)) {
            TableMetadata data = this.tableMetadata.get(tableName);
            dataFrameReader
                    .option("upperBound", data.upperBound)
                    .option("lowerBound", data.lowerBound)
                    .option("numPartitions", data.numPartitions)
                    .option("partitionColumn", data.partitionColumn);
        }
        Dataset<Row> dataset = dataFrameReader
                .load();
//        dataset = convertColumnsToLowerCase(dataset); // comment in case of performance problems on big datasets
        if(cache)
            dataset.cache();
        return dataset;
    }

    private static Dataset<Row> convertColumnsToLowerCase(Dataset<Row> dataset) {
        String[] columns = dataset.columns();
        dataset = dataset.select(
                stream(columns)
                        .map(col -> new Column(col.toLowerCase()))
                        .toArray(Column[]::new)
        );
        return dataset;
    }

    private String tableName(String tableName, boolean useMetadata) {
        if (useMetadata && tableMetadata.containsKey(tableName)) {
            return tableMetadata.get(tableName).tableName;
        }
        return tableName;
    }

    public DataFrameReader getDataFrameReader() {
        return spark.read().format("jdbc")
                .option("url", db2Properties.DB_URL)
                .option("user", db2Properties.DB_USER)
                .option("password", db2Properties.DB_PASSWORD)
                .option("fetchsize", "128")
                .option("driver", db2Properties.DB_DRIVER);
    }

    public Dataset<Row> executeSQL(String sqlQuery){
        return spark.sql(sqlQuery);
    }

    public Dataset<Row> loadFromCsvFile(String fileName, StructType schema){
        return spark.read()
                .option("delimiter", ";")
                .schema(schema)
                .csv(fileName);
    }

    public SparkSession getSpark() {
        return spark;
    }
}
