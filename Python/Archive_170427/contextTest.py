#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from contextlib import contextmanager


class Query(object):
    def __init__(self, name):
        self.name = name

    def query(self):
        print('Query info about %s...' % self.name)


@contextmanager
def create_query(name):
    print('Begin')
    q = Query(name)
    yield q
    print('End')


@contextmanager
def tag(name):
    print("<%s>" % name)
    yield
    print("<\%s>" % name)


with tag("header"):
    with create_query('Hehe') as q:
        q.query()