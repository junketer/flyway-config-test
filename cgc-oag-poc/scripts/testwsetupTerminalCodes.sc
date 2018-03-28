//TESTED
cacheLoader.loadAirportTerminalCache()

val depTerminalResults = Map(
  "" -> 2865893,
  "0" -> 107668,
  "1" -> 708745,
  "1A" -> 5928,
  "1B" -> 1663
)

val arrTerminalResults = Map(
  "" -> 2838023,
  "0" -> 107017,
  "1" -> 727325,
  "1A" -> 5962,
  "1B" -> 794
)

val withDistanceMiles = ivListProcessing.addTerminalCodes(enrichedLegs)

for ((k,v) <- arrTerminalResults)
  assert(v == withDistanceMiles.where(col("arr_terminal_cd").equalTo(k)).count)

for ((k,v) <- depTerminalResults)
  assert(v == withDistanceMiles.where(col("dep_terminal_cd").equalTo(k)).count)