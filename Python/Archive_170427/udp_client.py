#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

import socket

s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

while True:
    data = input('Input: ')
    if data == 'exit':
        break
    # for data in [b'Michael', b'Tracy', b'Sarah']:
    # 发送数据:
    s.sendto(data.encode('utf-8'), ('127.0.0.1', 9999))
    print(s.recv(1024).decode('utf-8'))
s.send(b'exit')
s.close()