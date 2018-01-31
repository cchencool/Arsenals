#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

import os
import matplotlib.pyplot as plt

def load_file_name(filepath):
    return os.listdir(filepath);


def load_file_data(file):
    a=[]
    i=0;
    with open('./data/'+file, 'r') as f:
        while True:
            line = f.readline()
            if line == '':
                break
            if line != '' and i > 2 and line.find('|') != -1: 
                a.append(line);
            i=i+1
    return a


def trans_data(data_in=[]):
    data_out=[]
    for record in data_in:
        data_out.append(list(map(lambda d: d.strip(), record.split('|'))))
    return data_out


def extract_data(filepath):
    filedata_map={}
    points_map={}
    files = load_file_name(filepath);
    #print(files)
    for f in files:
        data = load_file_data(f)
        filedata_map[f]=data
    #print(filedata_map)
    for key in filedata_map:
        points = trans_data(filedata_map.get(key))
        points_map[key]=points
    #print(points_map)
    return points_map

def do_plot(points):
    fig = plt.figure()
    plt.p 

def main():
    points = extract_data('./data/')
    print(points)
    keys=list(points.keys())
    fig = plt.figure()
    plt.plot(list(map(lambda d: float(d[0]), points[keys[0]])), 'b--')
    plt.plot(list(map(lambda d: float(d[1]), points[keys[0]])), 'g--')
    plt.plot(list(map(lambda d: float(d[2]), points[keys[0]])), 'r--')
    plt.plot(list(map(lambda d: float(d[3]), points[keys[0]])), 'k--')
    plt.show()


if __name__ == "__main__":
    main()
