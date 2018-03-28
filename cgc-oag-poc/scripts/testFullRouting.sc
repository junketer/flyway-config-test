//TESTED
import org.apache.spark.sql.functions.{col, asc}
import java.time.LocalDate
import java.time.format.DateTimeFormatter

com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateTime = LocalDate.parse("2017-10-16", formatter)
val legSegs = legReader.queryLegSegRecords(dateTime)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, dateTime)
cacheLoader.retrieveFromDisk("cacheDataFrames", spark)
val ivListProcessing = new com.codedose.oag.IVListProcessing(cacheLoader.getCacheDFs(), udfs, false, dateTime )

val withFullRoutingResults = Map(
  "C" -> 1019971,
  "G" -> 2141370,
  "L" -> 2870185
)

val legs1 =
  ivListProcessing.filterOutOldNonstoppingLegs(
    ivListProcessing.addFullRoutingList(
      ivListProcessing.nonStoppingLegalColumn(
        ivListProcessing.enrichWithIVGroupAttributes(
          ivListProcessing.enrichWithAircraftChangeMarker(
            ivListProcessing.addSpecificAircraftType(
              ivListProcessing.addArrDepCodes(legSegs)
            )
          )
        )
      )
    )
  ).cache()

for ((k,v) <- withFullRoutingResults)
  assert(v == legs1.where(col("full_routing").lt(k)).count)

//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")



