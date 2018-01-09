#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from wsgiref.simple_server import make_server
from wsgiServerApplication import application

# with make_server('', 9999, application) as httpd:
#     print('Serving HTTP on port 9999...')
#     # Respond to requests until process is killed
#     httpd.serve_forever()
#     # Alternative: serve one request, then exit
#     httpd.handle_request()

# 创建一个服务器，IP地址为空，端口是8000，处理函数是application:
httpd = make_server('', 9999, application)
print('Serving HTTP on port 9999...')
# 开始监听HTTP请求:
httpd.serve_forever()