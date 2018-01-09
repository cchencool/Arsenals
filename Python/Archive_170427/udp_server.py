#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

import socket, threading, time

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM) # UDP
s.bind(('127.0.0.1', 9999))

print('Bind UDP on 9999...')
while True:
    data, addr = s.recvfrom(1024)
    print('Receive from %s:%s' % addr)
    s.sendto(b'Hello, %s' % data, addr)

