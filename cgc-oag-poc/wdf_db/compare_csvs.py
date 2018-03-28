import sys
import pandas as pd

ours = pd.read_csv(sys.argv[1], header=None, names = ['TABLE', 'COUNT'])
dans = pd.read_csv(sys.argv[2], header=None, names = ['TABLE', 'COUNT'])

ours = ours.set_index('TABLE')
dans = dans.set_index('TABLE')

j = ours.join(dans, lsuffix='o', rsuffix='d')
print(j[j.COUNTo != j.COUNTd])

