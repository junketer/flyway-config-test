import sys
import check_counts

def dump_counts(fname, s=None):
	import pandas as pd
	c = check_counts.connect(s)
	counts = pd.Series(check_counts.get_counts_in_db(c))
	counts.to_csv(fname)

if __name__ == "__main__":
	dump_counts(sys.argv[1])
