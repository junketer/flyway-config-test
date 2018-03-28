//TESTED
cacheLoader.loadCarrierCountryCache()

val carrierCntryResults = Map(
  "" -> 402,
  "AB" -> 90471,
  "AC" -> 58,
  "AE" -> 131505,
  "AF" -> 213
)


val withCarrierCountryCache = ivListProcessing.addCarrierCountry(enrichedLegs)

for ((k,v) <- carrierCntryResults)
  assert(v == withCarrierCountryCache.where(col("carrier_cntry").equalTo(k)).count)
