#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

def application(environ, start_response):
    params = environ['QUERY_STRING'];
    print(params)
    start_response('200 OK', [('Content-Type', 'text/html')])
    return  [b'<h1>hello, world</h1>' + str.encode(params)]

