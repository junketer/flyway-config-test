package com.codedose.oag;

import com.codedose.oag.utils.UserDefinedFunctions;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import static org.apache.spark.sql.functions.*;

public class RationaliserProcessing {
    public static Dataset<Row> process(UserDefinedFunctions udfs, Dataset<Row> input) {
        Dataset<Row> rationalised = input
                .withColumn("rationaliseRecord",
                        struct(
                                col("leg_seg_id"),
                                col("oper_days_of_week"),
                                col("leg_seg_eff_start_date"),
                                col("leg_seg_eff_end_date")
                        )
                )
                .select("rationaliseRecord", "carrier_cd", "service_num", "sched_version_name", "dep_port_cd", "arr_port_cd", "dep_time_lcl", "arr_time_lcl", "arr_interval_days", "elapsed_time", "stops", "intmdte_ports", "arcrft_change_marker", "govt_approval_marker",
                        "comment_10_50", "spcfc_arcrft_type", "service_type_cd", "avl_seats", "freight_tons", "pax_class", "freight_classes", "restriction", "dom_int_marker", "full_routing", "shared_airline_designator", "arcrft_owner",
                        "operating_marker", "ghost_flght_marker", "meals", "dep_terminal_cd", "arr_terminal_cd", "oper_suffix", "avl_seats_first", "avl_seats_business", "avl_seats_ecnmy", "release_sell_start_date", "secure_flght_marker",
                        "in_flght_service", "e_ticketing", "cockpit_crew_provider", "cabin_crew_provider", "automated_check_in", "on_time_performance_501","on_time_performance_502", "avl_seats_prem_ecnmy", "alliance", "dup_leg_xml"
                )
                .groupBy(
                        "carrier_cd", "service_num", "sched_version_name", "dep_port_cd", "arr_port_cd", "dep_time_lcl", "arr_time_lcl", "arr_interval_days", "elapsed_time", "stops", "intmdte_ports", "arcrft_change_marker", "govt_approval_marker",
                         "comment_10_50", "spcfc_arcrft_type", "service_type_cd", "avl_seats", "freight_tons", "pax_class", "freight_classes", "restriction", "dom_int_marker", "full_routing", "shared_airline_designator", "arcrft_owner", 
                          "operating_marker", "ghost_flght_marker", "meals", "dep_terminal_cd", "arr_terminal_cd", "oper_suffix", "avl_seats_first", "avl_seats_business", "avl_seats_ecnmy", "release_sell_start_date", "secure_flght_marker", 
                          "in_flght_service", "e_ticketing", "cockpit_crew_provider", "cabin_crew_provider", "automated_check_in", "on_time_performance_501","on_time_performance_502", "avl_seats_prem_ecnmy", "alliance", "dup_leg_xml"
                )
                .agg(collect_list("rationaliseRecord").as("to_rationalise"))
                .withColumn("rationalised_legs", explode(udfs.rationalise(col("to_rationalise"))));

        return input.join(rationalised, col("rationalised_legs.leg_seg_id").equalTo(col("leg_seg_id")));
    } 
}
