//TESTED
//from: testInit.sc

val sadResults = Map(
  "C" -> 4164087,
  "G" -> 4803163,
  "L" -> 5025210
)

val withSadNonstopping = ivListProcessing.addSharedAirlineDesignatorNonStopping(withIntmdtePorts)
val withSad = ivListProcessing.addSharedAirlineDesignatorStopping(withSadNonstopping)


for ((k,v) <- sadResults)
  assert(v == withSad.where(col("shared_airline_designator").lt(k)).count)
