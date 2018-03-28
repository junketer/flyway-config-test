#!/bin/bash

#script to submit spark task to spark cluster using 9.5 latest jdbc driver

spark-submit --name "Spark DB2 example" --class $1 --master spark://10.0.2.15:7077 --jars=/home/bantosik/projects/sparktest/jdbc_driver_10.5/db2jcc4.jar --conf spark.sql.shuffle.partitions=6 target/oag-wdf-poc-1.0-SNAPSHOT.jar "jdbc:db2://localhost:50000/sample:user=db2inst1;password=1db2inst;" com.ibm.db2.jcc.DB2Driver
