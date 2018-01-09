#! /usr/bin/env python3
# -*- coding: utf-8 -*-

""" a test model """

__author__ = 'Chen'

from PIL import Image

im = Image.open('test.jpg')
print(im.format, im.size, im.mode)
im.thumbnail((60,60))
im.save('thumb.jpg', 'JPEG')