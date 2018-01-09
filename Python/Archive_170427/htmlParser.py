#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from html.parser import HTMLParser
from html.entities import name2codepoint


class MyHTMLParser(HTMLParser):
    def handle_starttag(self, tag, attrs):
        print('<%s>' % tag)

    def handle_endtag(self, tag):
        print('</%s>' % tag)

    def handle_startendtag(self, tag, attrs):
        print('<%s/>' % tag)

    def handle_data(self, data):
        print(data)

    def handle_comment(self, data):
        print('<!--', data, '-->')

    def handle_entityref(self, name):
        print('&%s;' % name)

    def handle_charref(self, name):
        print('&#%s;' % name)


with open('/Users/Chen/Desktop/Python.org.html', 'r', encoding='utf-8') as file:
    html = file.read()

parser = MyHTMLParser()
parser.feed(html)
