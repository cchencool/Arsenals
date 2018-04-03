#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from urllib import request, parse
import json.tool

def main():
    req = request.Request("http://www.baidu.com")
    response = request.urlopen(req);
    print(response.read().decode('utf-8'))


def another():
    url = "http://httpbin.org/post"
    headers = {
        # 伪装一个火狐浏览器
        "User-Agent": 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36',
        "host": 'httpbin.org',
        # debug
        "cache-control": "private,no-store,max-age=0,no-cache,must-revalidate,post-check=0,pre-check=0",
        "content-encoding": "br",
        "content-security-policy": "default-src * blob:;img-src * data: blob:;frame-src 'self' *.zhihu.com getpocket.com note.youdao.com safari-extension://com.evernote.safari.clipper-Q79WDW8YH9 weixin: zhihujs: v.qq.com v.youku.com www.bilibili.com *.vzuu.com;script-src 'self' *.zhihu.com *.google-analytics.com zhstatic.zhihu.com res.wx.qq.com 'unsafe-eval' unpkg.zhimg.com unicom.zhimg.com blob:;style-src 'self' *.zhihu.com unicom.zhimg.com 'unsafe-inline';connect-src * wss:",
        "content-type": "text/html; charset=utf-8",
        "date": "Sat, 31 Mar 2018 03:09:24 GMT",
        "expires": "Fri, 02 Jan 2000 00:00:00 GMT",
        "pragma": "no-cache",
        "server": "nginx",
        "status": "200",
        "vary": "Accept-Encoding",
        "x-backend-server": "heifetz.heifetz.047e2d23---10.5.27.2:31027[10.5.27.2:31027]",
        "x-frame-options": "DENY",
        "x-req-id": "4C81E6F5ABEFBE3",
        "x-req-ssl": "proto=TLSv1.2,sni=api.zhihu.com,cipher=ECDHE-RSA-AES256-GCM-SHA384"
    }
    dict = {
        "name": "Germey"
    }
    data = bytes(parse.urlencode(dict), encoding="utf8")
    req = request.Request(url=url, data=data, headers=headers, method="POST")
    response = request.urlopen(req)
    print(response.read().decode("utf-8"))


if __name__ == '__main__':
    main()