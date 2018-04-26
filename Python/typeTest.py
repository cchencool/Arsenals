#!/usr/bin/env python
# -*- coding: utf-8 -*-

# @Time    : 23/04/2018 16:22
# @Author  : chen
# @Site    : 
# @File    : typeTest.py
# @Software: PyCharm

__author__ = "chen"


class O_Cool():
    __name__: str = ""

    def __init__(self, name):
        self.__name__ = name;

    def get_name_and_print(self):
        if self.__name__ == None:
            print("I have no name yet")
        else:
            print("my name is " + self.__name__)
        return self.__name__


# def func2(cool):
#     return  cool.get_name_and_print();


def func(cool: O_Cool) -> O_Cool:
    cool.get_name_and_print()
    return cool


def main():
    res = func(O_Cool("ZHANG"))
    name: str = res.get_name_and_print()
    print(name.lower())

    mylist: list = [x * x for x in range(3)]
    for i in mylist:
        print(i)

    mygenerator = (x * x for x in range(10))
    for i in mygenerator:
        print(i)


if __name__ == '__main__':
    main();
















