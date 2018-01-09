#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "Cchen Zhang"

import os
import pickle
import json
from io import StringIO

fileName = '/Users/Chen/Desktop/data'


def readAndWrite():
    print('call fun() start')
    a = 0
    with open(fileName, 'r') as f:
        while True:
            a = a + 1
            s = f.read(20);
            if s == '\n':
                print();
            else:
                print(s, end='')
            if s is '':
                break;
    print('\nread %d times\n' % a)

    with open(fileName, 'a') as f:
        f.write('Hahaha');

    print('call fun() end')


def stringIOWrite():
    f = StringIO()
    f.write('hello')
    f.write(', world!')
    # f.flush()
    with open(fileName, 'a') as c:
        c.write('\n')
        c.write(f.getvalue())

    print(f.getvalue())


def stringIORead():
    with open(fileName, 'r') as c:
        f = StringIO(c.read())
        while True:
            s = f.readline()
            if s == '':
                break
        print(f.getvalue())


def osTest():
    print(os.name)
    # print(os.uname())
    # env = os.environ;
    # for e in env:
    #     print(e + '=' + env.get(e))
    path = os.path.abspath('.')
    upPath = os.path.split(path)[0]
    text = dict(name='Cchen', age=24, weigth=65)
    # with open('test','wb') as f:
    # pickle.dump(text, f)
    # afterpickle = pickle.dumps(text)
    # f.write(afterpickle)
    # os.rename('test', 'Hello');
    # os.remove('Hello')
    print(os.listdir(path))
    print(upPath)
    textJson = json.dumps(text)
    print(textJson)


def main():
    print('run main start...')
    # readAndWrite()
    # stringIOWrite()
    # stringIORead()
    osTest()
    print('run main end...')


if __name__ == '__main__':
    main()
