#!/bin/bash
mvn clean package -DskipTests -Pcluster

scp resources/* oag@tesla:/home/oag/resources/
scp target/oag-wdf-poc-1.0-SNAPSHOT.jar oag@tesla:/home/oag/
