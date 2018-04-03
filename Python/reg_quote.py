#!/usr/bin/env python3
# -*- coding: utf-8 -*-

' a test module '

import re
import sys 
import argparse


def quote(matched):
    return '"' + matched.group() + '"';

def quoteWithComma(matched):
    return '"' + matched.group() + '",';


def main():
    prog = 'python reg_quote.py'
    description = ('A simple command line interface for quote request heards.')

    parser = argparse.ArgumentParser(prog=prog, description=description)
    parser.add_argument('infile', nargs='?', type=argparse.FileType(),
                        help='a txt file to be processed')
#    parser.add_argument('outfile', nargs='?', type=argparse.FileType('w'),
#                        help='write the output of infile to outfile')
    options = parser.parse_args()

    infile = options.infile or sys.stdin
#    outfile = options.outfile or sys.stdout

    with infile:
        str = infile.read()
        # key
        str = re.sub(r'(^[^:]*)(?=: )', quote, str, flags=re.M)
        # value
        str = re.sub(r'(?<=: ).*$', quoteWithComma, str, flags=re.M)
        print(str, end='')

    #if len(args) > 1:
    #    path = args[1]
    #for arg in args:
    #    print(arg)
    #with open(path, 'r') as f:
    #    while True:
    #        s = f.readline()
    #        if s:
    #            #line = re.sub(r'(^[^:]*)', quote, s)
    #            line = re.sub(r'(^[^:]*)(?=: )', quote, s)
    #            line = re.sub(r'(?<=: ).*', quote, line)
    #            print(line, end='')
    #        else:
    #            break;
       
    

#    f = open('/Users/Chen/Desktop/test.txt', 'r');
#    str = infile.read()
#    str = re.sub(r'(^[^:]*)(?=: )', quote, str, flags=re.M)
#    str = re.sub(r'(?<=: ).*$', quote, str, flags=re.M)
#    print(str)
#    f.close()


if __name__ == "__main__":
    main()

