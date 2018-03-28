//TESTED
import java.time.LocalDate
import java.time.format.DateTimeFormatter

com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateTime = LocalDate.parse("2017-10-16", formatter)
val enrichedLegs = legReader.queryLegSegRecords(dateTime)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, dateTime)
cacheLoader.loadCaches()
val ivListProcessing = new com.codedose.oag.IVListProcessing(cacheLoader.getCacheDFs(), udfs, false, )
val withCarrierCd = ivListProcessing.addCarrierCode(ivListProcessing.filterOutOldNonstoppingLegs(enrichedLegs))
val withCarrierCd0Bcount = withCarrierCd.where(col("carrier_cd").equalTo("0B")).count
val withCarrierCd7Fcount = withCarrierCd.where(col("carrier_cd").equalTo("7F")).count
assert(withCarrierCd0Bcount == 4891)
assert(withCarrierCd7Fcount == 495)



//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")



