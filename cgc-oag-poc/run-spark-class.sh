#script to submit spark task to spark cluster using 9.5 latest jdbc driver
spark-submit --name "Spark DB2 example" --class $1 --master spark://127.0.1.1:7077 --executor-memory 7g --conf spark.sql.shuffle.partitions=6 target/oag-wdf-poc-1.0-SNAPSHOT.jar;
