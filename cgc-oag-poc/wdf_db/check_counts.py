import ibm_db
import sys
import os
import subprocess

CONN_STRING = "HOST=localhost;DATABASE=tries3;PORT=50001;UID=db2inst1;PWD=1db2inst;"

def all_tabs(conn):
	sql = "select tabname from syscat.tables where tabname like 'SNAP_%' and tabschema='SNAPSHOT'"
	stmt = ibm_db.exec_immediate(conn, sql)
	result = ibm_db.fetch_both(stmt)
	all_ts = []
	while result:
		all_ts.append(result[0])
		result=  ibm_db.fetch_both(stmt)
	return all_ts


def delete_from_table(c, t):
	sql = "delete from {}".format(t)
	result = ibm_db.fetch_both(stmt)

	

def get_count(conn, table): 
	sql = "select count(*) from snapshot.{}".format(table)
	stmt = ibm_db.exec_immediate(conn, sql)
	result = ibm_db.fetch_both(stmt)
	if result:
		return int(result[0])

def get_file_count(directory, table):
	files = os.listdir(directory)
	fname = table.upper() + '.txt'
	output = subprocess.check_output(["wc", fname])
	return int(output.split()[0])

def get_file_column(directory, table):
	with open(table.upper() + '.txt') as dump:
		l = dump.readline()
		if len(l) == 0:
			return 0
		else:
			return l.count("^") + 1

def get_column(conn, t):
	sql = "select count(*) from syscat.columns where tabname = '{}' and tabschema='SNAPSHOT'".format(t)
	stmt = ibm_db.exec_immediate(conn, sql)
        result = ibm_db.fetch_both(stmt)
        if result:
                return int(result[0])

def table_in_dir(table, directory):
	return os.path.isfile(os.path.join(directory, table.upper() + '.txt'))

def not_in_export(ts, directory):
	return [t for t in ts if not table_in_dir(t, directory)]

def count_mismatch(conn, directory):
	ts = all_tabs(conn)
	dir_counts = [get_file_count(directory, t) if table_in_dir(t, directory) else 0 for t in ts]
	db_counts = [get_count(conn, t) for t in ts]
	return [z for z in zip(ts, dir_counts, db_counts) if z[1] != z[2]]

def column_mismatch(conn, directory):
	ts = all_tabs(conn)
	dir_counts = [get_file_column(directory, t) if table_in_dir(t, directory) else 0 for t in ts]
	db_counts = [get_column(conn, t) for t in ts]
	return [z for z in zip(ts, dir_counts, db_counts) if z[1] != z[2]]

def get_counts_in_db(conn):
	ts = all_tabs(conn)
	return {t: get_count(conn, t) for t in ts}

def connect(s=None):
	return ibm_db.pconnect(s if s else CONN_STRING, "", "") 

	
def drop_table_content(s=None):
	c = connect(s)
	ts = all_tabs(conn)
	for t in ts:
		delete_from_table(t)


if __name__ == "__main__":
	conn_string = sys.argv[2] if len(sys.argv) >=2 else CONN_STRING
	conn = ibm_db.pconnect(conn_string,"","")
	directory = '.'
	if conn:
		#print (get_column(conn, 'SNAP_LEG_SEG'))
		#print (get_file_column(directory, 'SNAP_LEG_SEG'))
		if sys.argv[1] == "cols":
			print (column_mismatch(conn, directory))
		else:
			print (count_mismatch(conn, directory))

	sys.exit(0)
