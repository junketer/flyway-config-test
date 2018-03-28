//TESTED
cacheLoader.loadCarrierCodeICAOCache()

val carrierCodeIcaoResults = Map(
  "" -> 173332,
  "8AA" -> 2,
  "8AB" -> 2,
  "8AC" -> 6,
  "AAL" -> 395352
)

val withCarrierCodeIcao = ivListProcessing.addCarrierCodeICAO(enrichedLegs)

for ((k,v) <- carrierCodeIcaoResults)
  assert(v == withCarrierCodeIcao.where(col("carrier_cd_icao").equalTo(k)).count)

