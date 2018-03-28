//TESTED
val results = Map(
"      7" -> 668520,
"     6 "  -> 676794,
"     67" -> 64519,
"    5  " -> 380791,
"    5 7" -> 29417,
"    56 " -> 32458,
"   4 67" -> 4560,
"   45  " -> 65778,
"   45 7" -> 10301,
"   456 " -> 20611,
"   4567" -> 8036,
"  3    " -> 309941,
"  3   7" -> 24016,
"  3  6 " -> 14332,
"  3  67" -> 4414,
"  3 5  " -> 36018,
"  3 5 7" -> 21988,
"  3 56 " -> 5626,
"  3 567" -> 3405,
"123456 " -> 83107,
"1234567" -> 598232
)

val withIntervalDays = ivListProcessing.addArrOperDaysOfWeek(enrichedLegs)

for ((k,v) <- results)
  assert(v == withIntervalDays.where(col("arr_oper_days_of_week").equalTo(k)).count)