#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from xml.parsers.expat import ParserCreate


class defaultSaxHandler(object):
    def start_element(self, name, attrs):
        print('sax: start_ele: %s, attrs: %s' % (name, str(attrs)))

    def end_element(self, name):
        print('sax: end_ele: %s' % name)

    def char_data(self, data):
        print('sax: char_data: %s' % data)


with open('/Users/Chen/Desktop/xml.xml', 'r') as file:
    xml = file.read()

handler = defaultSaxHandler()
parser = ParserCreate()
parser.StartElementHandler = handler.start_element
parser.EndElementHandler = handler.end_element
parser.CharacterDataHandler = handler.char_data
parser.Parse(xml)