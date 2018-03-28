package com.codedose.oag;

import com.codedose.oag.utils.DataFrameLoader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import java.time.LocalDate;

import static org.apache.spark.sql.functions.*;


public class LegSegReader {
    private final DataFrameLoader dataFrameLoader;

    LegSegReader(DataFrameLoader dataFrameLoader) {
        this.dataFrameLoader = dataFrameLoader;
    }

    public Dataset<Row> queryLegSegRecords(LocalDate reportDate) {
        dataFrameLoader.getSpark().sparkContext().setJobDescription("Reading Snap IV");
        Dataset<Row> snapIv = dataFrameLoader.loadTable("snapshot.snap_iv")
                .select("iv_id", "last_update_timestamp")
                .as("snap_Iv")
                .repartition(24, col("iv_id"))
                .sortWithinPartitions("iv_id")
                ;
        snapIv.cache();
        snapIv.count();

        dataFrameLoader.getSpark().sparkContext().setJobDescription("Reading Snap Leg Seg");
        Dataset<Row> snapLegSeg = dataFrameLoader.loadTable("snapshot.snap_leg_seg").as("snapLegSeg")
                .withColumnRenamed("leg_seg_eff_start_date", "snap_leg_seg_leg_seg_eff_start_date")
                .withColumnRenamed("leg_seg_eff_end_date", "snap_leg_seg_leg_seg_eff_end_date")
                .withColumnRenamed("oper_days_of_week", "snap_leg_seg_oper_days_of_week")
                .repartition(24, col("iv_id"))
                .sortWithinPartitions("iv_id");
        snapLegSeg.cache();
        snapLegSeg.count();
        dataFrameLoader.getSpark().sparkContext().setJobDescription(null);

        Dataset<Row> snapSchedVersion = dataFrameLoader.loadTable("snapshot.snap_sched_version")
                .cache()
                .as("snapSchedVersion");

        Dataset<Row> latestSchedule = snapSchedVersion
                .groupBy("carrier_id", "sched_version_name_id")
                .agg(max("release_sell_start_date")).as("latestSchedule");
        Dataset<Row> withLatestSchedule = latestSchedule
                .join(broadcast(snapSchedVersion),
                    col("snapSchedVersion.carrier_id").equalTo(col("latestSchedule.carrier_id"))
                        .and(col("snapSchedVersion.sched_version_name_id").equalTo(col("latestSchedule.sched_version_name_id")))
                        .and(col("snapSchedVersion.release_sell_start_date").equalTo(col("latestSchedule.max(release_sell_start_date)"))))
                .select("sched_version_id")
                .repartition(24, col("sched_version_id"))
                ;

        return snapIv
                .join(snapLegSeg, "iv_id")
                .join(withLatestSchedule, "sched_version_id")
                .where((col("num_of_stops_cnt").equalTo(0)
                        .or(
                                col("num_of_stops_cnt").gt(0).and(col("seg_enhanced_ind").equalTo("Y"))))
                                .and(col("snap_leg_seg_leg_seg_eff_end_date").geq(java.sql.Date.valueOf(reportDate))
                        )
                )
                .withColumnRenamed("oper_prefix", "leg_seg_oper_prefix")
                .withColumnRenamed("oper_suffix", "leg_seg_oper_suffix");

    }
}
