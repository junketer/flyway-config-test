//TESTED
cacheLoader.loadCarrierCodeICAOCache()

val equipmentCodeIcaoResults = Map(
  "" -> 417803,
  "*" -> 35726,
  "----" -> 178656,
  "A109" -> 15,
  "A139" -> 83
)

val withEquipmentCodeIcao = ivListProcessing.addEquipmentCodeIcao(enrichedLegs)

for ((k,v) <- equipmentCodeIcaoResults)
  assert(v == withEquipmentCodeIcao.where(col("equipment_cd_icao").equalTo(k)).count)

