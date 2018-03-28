//TESTED
cacheLoader.loadLocationCodeICAOCache()

val withPortCodeIcaoResults = Map(
  "" -> 124782,
  "AGAF" -> 1,
  "AGAR" -> 4,
  "AGGA" -> 16,
  "AGGC" -> 34
)

val withPortCodeIcaoBoundsResults = Map(
  "GAAA" -> 1373982,
  "LAAA" -> 3958660,
  "RAAA" -> 5062465
)

val withPortCodeIcao = ivListProcessing.addDepArrPortCodeICAO(enrichedLegs)

for ((k,v) <- withPortCodeIcaoResults)
  assert(v == withPortCodeIcao.where(col("dep_port_cd_icao").equalTo(k)).count)

for ((k,v) <- withPortCodeIcaoBoundsResults)
  assert(v == withPortCodeIcao.where(col("dep_port_cd_icao").lt(k)).count)

