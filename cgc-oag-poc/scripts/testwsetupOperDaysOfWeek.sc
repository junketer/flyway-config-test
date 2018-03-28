//TESTED
val operDaysOfWeekResults = Map(
  "1      " -> 418118,
  "1     7" -> 54289,
  "1234   " -> 45870,
  "1  45  " -> 19306,
  " 2  56 " -> 3265
)


val withLegSegEffStartDate = ivListProcessing.addLegSegEffStartDate(enrichedLegs)
val withLegSegEffEndDate = ivListProcessing.addLegSegEffEndDate(withLegSegEffStartDate)
val withOperDaysOfWeek = ivListProcessing.addOperDaysOfWeek(withLegSegEffEndDate)

for ((k,v) <- operDaysOfWeekResults)
  assert(v == withOperDaysOfWeek.where(col("oper_days_of_week").equalTo(k)).count)


//assert(2847738 == withOperDaysOfWeek.where(col("oper_days_of_week").lt("00200")).count)
//assert(6044666 == withOperDaysOfWeek.where(col("oper_days_of_week").lt("09000")).count)