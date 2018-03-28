import sys
import os

def groups_from_file(fname):
	res = []
	with open(fname) as f:
		lines_so_far = []
		for l in f:
			if 'select ' in l:
				if len(lines_so_far) > 0:
					res.append(lines_so_far)
				lines_so_far = []
			lines_so_far.append(l)
	return res

def get_name(r):
	s = r[0]
	i = s.find("from")
	return s[i+5:].strip().split('.')[1]

def get_count(r):
	await_num = False
	for l in r:
		if l == '-----------\n':
			await_num = True
		elif await_num:
			return l.strip()
	
if __name__ == "__main__":
	fname = sys.argv[1]
	fname2 = sys.argv[2]
	d = {get_name(r): get_count(r) for r in groups_from_file(fname)}
	import pandas as pd
	counts = pd.Series(d)
	counts.to_csv(fname2)


#	d = {} 
#	for r in res:
#		if len(r) > 0:
#			d[get_name(r)] = get_count(r)

