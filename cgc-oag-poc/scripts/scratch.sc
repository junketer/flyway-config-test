val custs = Seq(
  (1, "Widget Co", 120000.00, 0.00, "AZ"),
  (2, "Acme Widgets", 410500.00, 500.00, "CA"),
  (3, "Widgetry", 410500.00, 200.00, "CA"),
  (4, "Widgets R Us", 410500.00, 0.0, "CA"),
  (3, "Widgetry", 410500.00, 200.00, "CA"),
  (5, "Ye Olde Widgete", 500.00, 0.0, "MA"),
  (6, "Widget Co", 12000.00, 10.00, "AZ")
)
val customerRows = spark.sparkContext.parallelize(custs, 4)
val customerDF = customerRows.toDF("id", "name", "sales", "discount", "state")