//TESTED
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.functions.col


com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateTime = LocalDate.parse("2017-10-16", formatter)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, dateTime)
cacheLoader.retrieveFromDisk("cacheDataFrames", spark)
val legs1 = spark.read.parquet("withNonStoppingLegal")
//val intermediatePortsResults = Map(
//  "C" -> 5528413,
//  "G" -> 5640042,
//  "L" -> 5714138
//)

val ivListProcessing = new com.codedose.oag.IVListProcessing(cacheLoader.getCacheDFs(), udfs, false, dateTime)

//val legs1 = ivListProcessing.nonStoppingLegalColumn(
//  ivListProcessing.enrichWithIVGroupAttributes(
//    ivListProcessing.enrichWithAircraftChangeMarker(
//      ivListProcessing.addSpecificAircraftType(
//        ivListProcessing.addArrDepCodes(legs)
//      )
//    )
//  )
//).cache()

val withFullRouting = ivListProcessing.filterOutOldNonstoppingLegs(ivListProcessing.addFullRoutingList(legs1))

//val enrichedLegs =
//  ivListProcessing.filterOutOldNonstoppingLegs(
//    ivListProcessing.addIntermediatePortsIndex(
//     ivListProcessing.addFullRoutingList(
//        ivListProcessing.anyNonStoppingLegal(
//          legs1
//        )
//      )
//    )
//  )

//for ((k,v) <- intermediatePortsResults)
//  assert(v == enrichedLegs.where(col("intmdte_ports").lt(k)).count)



//val withIntmdte = ivListProcessing.addIntermediatePorts(ivListProcessing.addFullRoutingList(withLegal))
//
//withIntmdte.where(col("any_non_stopping_legal").and(col("num_of_stops_cnt").gt(0)))

//val withIntmdte = ivListProcessing.addIntermediatePorts(ivListProcessing.addFullRoutingList(ivListProcessing.anyNonStoppingLegal(legs1)))
//val withIntmdteF = ivListProcessing.filterOutOldNonstoppingLegs(ivListProcessing.addIntermediatePorts(ivListProcessing.addFullRoutingList(ivListProcessing.anyNonStoppingLegal(legs1))))
