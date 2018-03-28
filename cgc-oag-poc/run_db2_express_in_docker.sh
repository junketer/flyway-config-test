#!/bin/bash

docker run -it -p 50000:50000 -e DB2INST1_PASSWORD=1db2inst -e LICENSE=accept ibmcom/db2express-c:latest bash
