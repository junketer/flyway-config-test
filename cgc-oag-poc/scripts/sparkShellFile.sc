import java.time.LocalDate
import java.time.format.DateTimeFormatter

com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateTime = LocalDate.parse("2016-10-16", formatter)
val enrichedLegs = legReader.queryLegSegRecords(dateTime)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, dateTime)
cacheLoader.loadCaches()
val ivListProcessing = new com.codedose.oag.IVListProcessing(cacheLoader.getCacheDFs(), udfs, true, dateTime)
//val ivEnriched = ivListProcessing.enrichWithIVGroupAttributes(enrichedLegs)
//val frouting = ivListProcessing.addFullRoutingList(ivEnriched)
val withCarrierCd = ivListProcessing.addCarrierCode(enrichedLegs)

//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")



