import os
import sys

SCHEMA = "SNAPSHOT"
EXCLUDES = 'exclusions.txt'

def prepare_sql_import_file(tables, filename):
	with open(filename, 'w+') as sqlfile:
		sqlfile.writelines(["IMPORT FROM {}.txt OF DEL MODIFIED BY COLDEL^ CODEPAGE=1252 INSERT INTO {}.{};\n".format(fname, SCHEMA, fname) for fname in tables])	

def is_backup(fname):
	for i in range(10):
		if str(i) in fname:
			return True
	return False

def prepare_table_names(directory):
	files = os.listdir(directory)
	return [f.split(".")[0] for f in files if f.startswith("SNAP_") and not is_backup(f)]


if __name__ == "__main__":
	directory = sys.argv[1]
	tab_names = prepare_table_names(directory)
	sql_file = sys.argv[2]
	prepare_sql_import_file(tab_names, os.join(directory, sql_file))

