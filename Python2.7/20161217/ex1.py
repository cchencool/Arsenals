#code=UTF-8
import sys;
#import cx_Oracle;
print "Hello world!";

# a = 1
# while True:
# 	a = a + 1
# 	if a % 999999 == 0:
# 		print "\n"
# 		for i in ["/","-","|","\\","|"]:
# 			print "%s\r" % i,
# 	else:
# 		print "%s\r" % a,

print "How old are you ?",
age = raw_input("Haha:")

print "How tall are you ?",
height = raw_input()

print "How much do you weight ?",
weight = raw_input()

print "So, you're %s years old, %s tall and %s heavy. Total equals %d" % (age, height, weight, int(age)+int(height)+int(weight))


raw_input("\n\nPlease enter any key to continue");
