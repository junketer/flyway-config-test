//TESTED
val withIntervalDays = ivListProcessing.addArrIntervalDates(enrichedLegs)
// compare with:
// select count(*) from snapshot.wdf_master_data_unenhanced where arr_interval_days = ''
val emptyIntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("")).count
assert(emptyIntervalDays == 5656945)

val with1IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("1")).count
assert(with1IntervalDays == 362543)

val with2IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("2")).count
assert(with2IntervalDays == 12518)

val with3IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("3")).count
assert(with3IntervalDays == 8436)

val with4IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("4")).count
assert(with4IntervalDays == 6247)

val with5IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("5")).count
assert(with5IntervalDays == 3741)

val with6IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("6")).count
assert(with6IntervalDays == 1785)

val with7IntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("7")).count
assert(with7IntervalDays == 798)

val withPIntervalDays = withIntervalDays.where(col("arr_interval_days").equalTo("P")).count
assert(withPIntervalDays == 1683)