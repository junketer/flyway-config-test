import fileinput

print (max(len(line) for line in fileinput.input()))

