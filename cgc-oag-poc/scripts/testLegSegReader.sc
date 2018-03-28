import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.apache.spark.sql.functions.{col, max}
import org.apache.spark.sql.{Dataset, Row}

com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
val dateTime = LocalDate.parse("2017-10-16", formatter)

val enrichedLegs = legReader.queryLegSegRecords(dateTime)
val enrichedLegsCount = enrichedLegs.count
assert(enrichedLegsCount == 6054846)

//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")
