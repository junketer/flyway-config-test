com.codedose.oag.SparkShellHelper.load
val loader = com.codedose.oag.SparkShellHelper.loader
//val featCoding = loader.loadTable("snapshot.snap_feature_coding")
val legReader = com.codedose.oag.SparkShellHelper.legSegReader
val udfs = com.codedose.oag.SparkShellHelper.udfs
val currDate = java.time.LocalDate.now()
val enrichedLegs = legReader.queryLegSegRecords(currDate)
val cacheLoader = new com.codedose.oag.CacheLoader(loader, currDate)
cacheLoader.loadCaches()

//val wdfEnhancedGrouped3 = com.codedose.oag.SparkShellHelper.wdfGroupedByToCsv(3, "grouped3.csv")



