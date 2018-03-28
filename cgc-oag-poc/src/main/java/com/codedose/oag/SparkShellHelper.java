package com.codedose.oag;
import com.codedose.oag.utils.DB2Properties;
import com.codedose.oag.utils.DataFrameLoader;
import com.codedose.oag.utils.UserDefinedFunctions;
import org.apache.spark.sql.*;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import java.io.IOException;

import static com.codedose.oag.SparkLoader.createOrGetSession;
import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.row_number;

public class SparkShellHelper {
    public static LegSegReader legSegReader;
    public static DataFrameLoader loader;
    public static UserDefinedFunctions udfs;

    public static void load() throws IOException {
        DB2Properties props = new DB2Properties();
        props.loadFromPropertyFile();
        SparkSession spark = createOrGetSession();
        loader = new DataFrameLoader(spark, props);
        legSegReader = new LegSegReader(loader);
        udfs = new UserDefinedFunctions(spark);
    }

    public static Dataset<Row> wdfEnhanced() throws IOException {
        long lb = 866874L;
        long ub = 8396844196L;

        String tableName = "snapshot.wdf_master_data_codedose";
        String partitionColumn = "leg_seg_id";
        int numPartitions = Runtime.getRuntime().availableProcessors();

        DataFrameReader dataFrameReader = loader.getDataFrameReader();

        dataFrameReader
                .option("dbtable", tableName)
                .option("upperBound", ub)
                .option("lowerBound", lb)
                .option("numPartitions", numPartitions)
                .option("partitionColumn", partitionColumn);

        return dataFrameReader.load();
    }
    public static String[] group_cols = {"carrier_cd", "service_num", "sched_version_name", "dep_port_cd", "arr_port_cd", "dep_time_lcl", "arr_time_lcl", "arr_interval_days", "elapsed_time", "stops", "intmdte_ports", "arcrft_change_marker", "govt_approval_marker", "comment_10_50", "spcfc_arcrft_type", "service_type_cd", "avl_seats", "pax_class", "freight_classes", "restriction", "dom_int_marker", "full_routing", "shared_airline_designator", "arcrft_owner", "operating_marker", "ghost_flght_marker", "meals", "dep_terminal_cd", "arr_terminal_cd", "oper_suffix", "avl_seats_first", "avl_seats_business", "avl_seats_ecnmy", "release_sell_start_date", "secure_flght_marker", "in_flght_service", "e_ticketing", "cockpit_crew_provider", "cabin_crew_provider", "automated_check_in", "on_time_performance_501", "on_time_performance_502", "avl_seats_prem_ecnmy", "alliance", "dup_leg_xml"};
    public static WindowSpec getSpec(int columns)
    {
        Column[] arr = new Column[columns];
        for(int i =0; i < columns; i++) {
            arr[i] = col(group_cols[i]);
        }

        return Window.partitionBy(arr).orderBy("leg_seg_id");
    }
    public static Dataset<Row> wdfGroupedBy(int columns) throws IOException {
        Dataset<Row> dataset = wdfEnhanced();
        WindowSpec w = getSpec(columns);
        return dataset.select(col("leg_seg_id"), row_number().over(w).as("rowNum"));
    }
    public static Dataset<Row> wdfCheckDiff(int columns1, int columns2) throws IOException {
        Dataset<Row> dataset = wdfEnhanced();
        Dataset<Row> df1 = dataset.select(col("leg_seg_id"), row_number().over(getSpec(columns1)).as("rowNum")).as("df1");
        Dataset<Row> df2 = dataset.select(col("leg_seg_id"), row_number().over(getSpec(columns2)).as("rowNum")).as("df2");
        return df1.join(df2, "leg_seg_id").filter(col("df1.rowNum").notEqual(col("df2.rowNum")));
    }


    public static void wdfGroupedByToCsv(int columns, String fname) throws IOException {
        Dataset<Row> dataset = wdfGroupedBy(columns);
        dataset.write().option("header", "false").csv(fname);
    }


}
