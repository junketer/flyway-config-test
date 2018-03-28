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
val legs = legReader.queryLegSegRecords(dateTime)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, dateTime)
cacheLoader.loadCaches()

val ivListProcessing = new com.codedose.oag.IVListProcessing(cacheLoader.getCacheDFs(), udfs, false, dateTime)
val enrichedLegs = ivListProcessing.nonStoppingLegalColumn(
  ivListProcessing.enrichWithIVGroupAttributes(
    ivListProcessing.enrichWithAircraftChangeMarker(
      ivListProcessing.addSpecificAircraftType(
        ivListProcessing.addArrDepCodes(legs)
      )
    )
  )
)
// compare with:
// select count(*) from snapshot.wdf_master_data_unenhanced where dep_cntry_cd = 'AE'
val checkNonStoppingLeg = enrichedLegs.where(col("non_stopping_legal").equalTo(0).and(col("num_of_stops_cnt").equalTo(0))).count
assert(checkNonStoppingLeg == 150)




//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")


