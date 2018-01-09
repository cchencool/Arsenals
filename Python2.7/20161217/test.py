#!/usr/bin/python
# -*- coding: UTF-8 -*-
import sys;

x = 'python test';
sys.stdout.write(x + '\n\n')
print
"Hello, World!"
if True:
    print
    "True"
else:
    print
    "False"
word = 'word'
sentence = "This is a sentence"
paragraph = """This is a paragraph,
include several sentence."""
print
word
print
sentence
print
paragraph
a = b = c = 1
print
a, b, c
del a
str = "love python"
print
str
print
str[0:4]
print
str * 2
print
str + "Test"

list = ['first', 2, 3.14, 'four', 5.0]
tinylist = ['abc', 123]

print
list
print
list[0]
print
list[1:3]
print
list[2:]
print
list + tinylist

tuple = ('first', 2, '3', 'four', 5.0)
print
tuple

list[1] = 'modify'
print
list

dict = {'name': 'John', 'code': '123456', 'dept': 'sales', 2: 1}
print
dict
print
dict['name']
print
dict[2]
print
dict.keys()
print
dict.values()

a = 10
listNum = [1, 2, 3, 4, 5, 6, 7]
if (a in listNum):
    print
    "10 is in listNum - by in"
else:
    print
    "10 is not in listNum - by in"

if (a not in listNum):
    print
    "10 is not in listNum - by not in"
else:
    print
    "10 is in listNum - by in"

raw_input("\n\nPress the enter key to exit.")
