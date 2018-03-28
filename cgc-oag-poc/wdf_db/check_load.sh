#!/bin/bash

export DATABASENAME=TRIES2
#this call starts backend process
db2 connect to ${DATABASENAME}

#tables with mismatched cols rows with respect to export files (to be verified manually)
python check_counts.py cols "HOST=localhost;DATABASE=${DATABASENAME};PORT=50001;UID=db2inst1;PWD=1db2inst;"
python check_counts.py rows "HOST=localhost;DATABASE=${DATABASENAME};PORT=50001;UID=db2inst1;PWD=1db2inst;"

