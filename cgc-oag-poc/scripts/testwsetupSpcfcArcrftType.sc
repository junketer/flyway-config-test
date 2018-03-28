//TESTED
val withAircraftType = ivListProcessing.addSpecificAircraftType(enrichedLegs)
// compare with:
// select count(*) from snapshot.wdf_master_data_unenhanced where spcfc_arcrft_type = 'RFS'
val rfsAircraftType = withAircraftType.where(col("spcfc_arcrft_type").equalTo("RFS")).count
assert(rfsAircraftType == 60670)