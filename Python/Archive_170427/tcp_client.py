#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

import socket

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
# 建立连接:
# s.connect(('127.0.0.1', 9999))
s.connect(('10.72.239.31', 9999))
# 接收欢迎消息:
print(s.recv(1024).decode('utf-8'))
while True:
    data = input('Input: ')
    if data == 'exit':
        break
    # for data in [b'Michael', b'Tracy', b'Sarah']:
    # 发送数据:
    s.send(data.encode('utf-8'))
    print(s.recv(1024).decode('utf-8'))
s.send(b'exit')
s.close()

# s.connect(('www.sina.com.cn', 80))
# print(s)
# s.send(b'GET / HTTP/1.1\r\nHost: www.sina.com.cn\r\nConnection: close\r\n\r\n')
# buffer = []
# while True:
#     d = s.recv(1024)
#     if d:
#         buffer.append(d)
#     else:
#         break
# s.close()
# data = b''.join(buffer)
# header, html = data.split(b'\r\n\r\n', 1)
# print(header.decode('utf-8'))
# # 把接收的数据写入文件:
# with open('/Users/Chen/Desktop/sina.html', 'wb') as f:
#     f.write(html)
