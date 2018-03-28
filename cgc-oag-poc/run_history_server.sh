#!/bin/bash

mkdir spark-events
spark-class org.apache.spark.deploy.history.HistoryServer spark-events
