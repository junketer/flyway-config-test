//TESTED
cacheLoader.loadPortCoordinatesCache()

val milesResults = Map(
  2000 -> 5464149,
  4000 -> 5887063,
  6000 -> 6017875,
  8000 -> 6049817
)

val kmsResults = Map(
  4000 -> 5693399,
  8000 -> 5963876,
  12000 -> 6045015,
  16000 -> 6054117
)

val withDistanceMiles = ivListProcessing.addDirectDistances(enrichedLegs)

for ((k,v) <- milesResults)
  assert(v == withDistanceMiles.where(col("distance_miles").lt(k)).count)

for ((k,v) <- kmsResults)
  assert(v == withDistanceMiles.where(col("distance_kms").lt(k)).count)