//TESTED
val elapsedTimeResults = Map(
  "00000" -> 696,
  "00001" -> 4,
  "00002" -> 23,
  "00003" -> 4,
  "00004" -> 20
)


val withElapsedTime = ivListProcessing.addElapsedTime(enrichedLegs)

for ((k,v) <- elapsedTimeResults)
  assert(v == withElapsedTime.where(col("elapsed_time").equalTo(k)).count)


assert(2847738 == withElapsedTime.where(col("elapsed_time").lt("00200")).count)
assert(6044666 == withElapsedTime.where(col("elapsed_time").lt("09000")).count)