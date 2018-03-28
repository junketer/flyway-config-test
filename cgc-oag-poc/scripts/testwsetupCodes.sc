val withCodes = ivListProcessing.addArrDepCodes(enrichedLegs)
// compare with:
// select count(*) from snapshot.wdf_master_data_unenhanced where dep_cntry_cd = 'AE'
val AAAdepcitycd = withCodes.where(col("dep_city_cd").equalTo("AAA")).count
val AADdepcitycd = withCodes.where(col("dep_city_cd").equalTo("AAD")).count
val AAEdepcitycd = withCodes.where(col("dep_city_cd").equalTo("AAE")).count
val AALdepcitycd = withCodes.where(col("dep_city_cd").equalTo("AAL")).count
assert(AAAdepcitycd == 39)
assert(AADdepcitycd == 1)
assert(AAEdepcitycd == 95)
assert(AALdepcitycd == 1512)
//AAA,68
//AAD,1
//AAE,102
//AAL,1525
val AAAarrcitycd = withCodes.where(col("arr_city_cd").equalTo("AAA")).count
val AADarrcitycd = withCodes.where(col("arr_city_cd").equalTo("AAD")).count
val AAEarrcitycd = withCodes.where(col("arr_city_cd").equalTo("AAE")).count
val AALarrcitycd = withCodes.where(col("arr_city_cd").equalTo("AAL")).count

assert(AAAarrcitycd == 68)
assert(AADarrcitycd == 1)
assert(AAEarrcitycd == 102)
assert(AALarrcitycd == 1525)

val dep_usdot_cntry0030 = withCodes.where(col("dep_usdot_cntry").equalTo("0030")).count
val dep_usdot_cntry0040 = withCodes.where(col("dep_usdot_cntry").equalTo("0040")).count
val dep_usdot_cntry0051 = withCodes.where(col("dep_usdot_cntry").equalTo("0051")).count
val dep_usdot_cntry0053 = withCodes.where(col("dep_usdot_cntry").equalTo("0053")).count

assert(dep_usdot_cntry0030 == 10177)
assert(dep_usdot_cntry0040 == 2101)
assert(dep_usdot_cntry0051 == 998)
assert(dep_usdot_cntry0053 == 166)

val arr_usdot_cntry0030 = withCodes.where(col("arr_usdot_cntry").equalTo("0030")).count
val arr_usdot_cntry0040 = withCodes.where(col("arr_usdot_cntry").equalTo("0040")).count
val arr_usdot_cntry0051 = withCodes.where(col("arr_usdot_cntry").equalTo("0051")).count
val arr_usdot_cntry0053 = withCodes.where(col("arr_usdot_cntry").equalTo("0053")).count

assert(arr_usdot_cntry0030 == 9184)
assert(arr_usdot_cntry0040 == 1990)
assert(arr_usdot_cntry0051 == 969)
assert(arr_usdot_cntry0053 == 161)

