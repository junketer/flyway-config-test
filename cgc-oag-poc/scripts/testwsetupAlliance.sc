//TESTED
cacheLoader.loadAllianceLookupCache()

val allianceResults = Map(
  "" -> 2454157,
  "SkyTeam" -> 1268950,
  "Star Alliance" -> 1041468,
  "U-Fly Alliance" -> 3743,
  "WOW Cargo Alliance/Star Alliance" -> 126322,
  "oneworld" -> 1138584,
  "oneworld affiliate" -> 21472
)

val withAlliance = ivListProcessing.addAlliance(enrichedLegs)

for ((k,v) <- allianceResults)
  assert(v == withAlliance.where(col("alliance").equalTo(k)).count)
