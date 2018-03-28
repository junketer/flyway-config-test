//TESTED
cacheLoader.loadAirpotyToStateCache()

val depStatesResults = Map(
  "" -> 2913390,
  "AB" -> 42686,
  "AC" -> 6018,
  "AK" -> 36234,
  "AL" -> 12978
)

val withStates = ivListProcessing.addStates(enrichedLegs)

for ((k,v) <- depStatesResults)
  assert(v == withStates.where(col("dep_state").equalTo(k)).count)
