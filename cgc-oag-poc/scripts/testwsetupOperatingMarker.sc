//TESTED
//from: testSetup
val operatingMarkerResults = Map(
  "" -> 2242481,
  "N" -> 2279455,
  "O" -> 1532760
)


val withOperatingMarker = ivListProcessing.addOperatingMarker(enrichedLegs)

for ((k,v) <- operatingMarkerResults)
  assert(v == withOperatingMarker.where(col("operating_marker").equalTo(k)).count)
