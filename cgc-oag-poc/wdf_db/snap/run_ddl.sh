#!/bin/bash

export DATABASENAME=TRIES3
#this call starts backend process
db2 connect to ${DATABASENAME}
db2 "UPDATE db cfg for ${DATABASENAME} using LOGFILSIZ 680000"

#SNAPSHOT schema
sed -e "s!/tries/!/${DATABASENAME}/!g" db_template.sql > db_template_processed.sql
db2 -tvmf db_template_processed.sql
python sanitize_ddl.py Full_table.sql Full_table_processed.sql
db2 -tvmf Full_table_processed.sql
db2 -tvmf oagdb2load_proc.sql
db2 -tvmf lpad_proc.sql
db2 -tvmf dataformat_func.sql

#load from .txt snapshot files
python load_sql_tabs.py . load_sql_tabs.sql
db2 -tvmf load_sql_tabs.sql

#tables with mismatched cols rows with respect to export files (to be verified manually)
python check_counts.py cols "HOST=localhost;DATABASE=${DATABASENAME};PORT=50001;UID=db2inst1;PWD=1db2inst;"
python check_counts.py rows "HOST=localhost;DATABASE=${DATABASENAME};PORT=50001;UID=db2inst1;PWD=1db2inst;"

