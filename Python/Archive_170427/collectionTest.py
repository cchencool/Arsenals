#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from collections import namedtuple, OrderedDict
import hashlib


def main():
    # Point = namedtuple('Point', ['x', 'y'])
    # p = Point(1, 2)
    # print(p.x)
    # print(p.y)

    # Space = namedtuple('Space', ['x', 'y', 'z'])
    # center = Space(0, 0, 0)
    # p1 = Space(1, 2, 3)
    #
    # d = dict([('a', 1), ('b', 2), ('c', 3)]);
    # od = OrderedDict([('a', 1),('b', 2),('c', 3)]);
    # print(d.get('dd'))

    md5 = hashlib.md5()
    md5.update('1'.encode('utf-8'))
    print(md5.hexdigest())


if __name__ == '__main__':
    main()
