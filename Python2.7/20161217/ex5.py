formatter = "%s %s %s %s"

print formatter % (1, 2, 3, 5)
print formatter % ("asd", 123, "2123se", 333)

a = 1
while True:
	a = a + 1
	if a % 999999 == 0:
		print "\n"
		for i in ["/","-","|","\\","|"]:
			print "%s\r" % i,
	else:
		print "%s\r" % a,
