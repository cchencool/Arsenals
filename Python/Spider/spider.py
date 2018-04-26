#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from urllib import request, parse
import http.cookiejar as cookielib
from bs4 import BeautifulSoup
import requests




def main():
    httpHandler = request.HTTPHandler(debuglevel=1)
    httpsHandler = request.HTTPSHandler(debuglevel=1)

    cookie_file_name = 'cookie.txt'
    # cookie = cookielib.CookieJar()
    cookie = cookielib.MozillaCookieJar(cookie_file_name)
    # cookie.load(filename=cookie_file_name, ignore_expires=True, ignore_discard=True)
    cookieHandler = request.HTTPCookieProcessor(cookie)

    opener = request.build_opener(httpHandler, httpsHandler, cookieHandler)
    request.install_opener(opener)

    url = "https://www.gradsch.cuhk.edu.hk/OnlineApp/OnlineApp_main.aspx"
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        "Accept-Encoding": "gzip, deflate, br",
        "Accept-Language": "en,zh-CN;q=0.9,zh;q=0.8,zh-TW;q=0.7",
        "Cache-Control": "no-cache",
        "Connection": "keep-alive",
        # "Cookie": "_ga=GA1.3.293053461.1505744778; _ga=GA1.4.1308126732.1505727254; _gid=GA1.4.1237165377.1522830091; ASP.NET_SessionId=zvez04sa2plzr1cgp4ca3op2",
        "Host": "www.gradsch.cuhk.edu.hk",
        "Pragma": "no-cache",
        "Referer": "https://www.gradsch.cuhk.edu.hk/OnlineApp/login.aspx",
        "Upgrade-Insecure-Requests": "1",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    }
    data = {
        # "__LASTFOCUS": "",
        # "__EVENTTARGET": "",
        # "__EVENTARGUMENT": "",
        # "__VIEWSTATE": "/wEPDwULLTEzNDU2NDA0OTEPZBYCAgMPZBYCAgMPDxYCHgRUZXh0BTRBcHBsaWNhdGlvbiBmb3IgQWRtaXNzaW9uIHRvIFBvc3RncmFkdWF0ZSBQcm9ncmFtbWVzZGQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgMFC2ltZ2J0bl9iYWNrBQtpbWdidG5faG9tZQUKaW1nYnRuX25ld9GdEHzO/gd++frjgkJzF2u1grDR",
        # "__VIEWSTATEGENERATOR": "3BC4D63E",
        # "__EVENTVALIDATION": "/wEdAAk/+1FJR349Pe0FgVQr/rMfHBQ7TOrtlvf9ueVFPYHRQcnw2LWS6mTfnrK4Vo0agWzFEsnF/rl2ychNeTZnF9kIVNINhpjIUTN7vOPs1YgQKkic4U0aRZK/6ouS8+euw8Q63ROChm+RmJ8W1Fb2+K65dDlmqzbR2b7nqJgQp10tGuaBoqApOGHZyE/zBlOOycamlbJgUGr0PIgJUhuLP7Xn2ze7ow==",
        "app_no": "18300603",
        "hkid": "140105199302130011",
        "txt_dob": "19930213",
        "btn_login": "Login"
    }
    req = request.Request(url, headers=headers, data=bytes(parse.urlencode(data), encoding='utf-8'))
    response = request.urlopen(req);
    # print(response.read().decode('utf-8'))
    # res = response.read().decode('utf-8');

    print()
    # print("open success. res: %d" % len(res))

    print('print cookie info.')
    for item in cookie:
        print('name = ' + item.name);
        print('value = ' + item.value);

    cookie.save(ignore_discard=True, ignore_expires=True)

    file = open('page1.html', 'w')
    file.write(response.read().decode("utf-8"));
    file.flush()
    file.close()


def online():
    httpHandler = request.HTTPHandler(debuglevel=1)
    httpsHandler = request.HTTPSHandler(debuglevel=1)

    cookie_file_name = 'cookie.txt'
    # cookie = cookielib.CookieJar()
    cookie = cookielib.MozillaCookieJar(cookie_file_name)
    cookie.load(filename=cookie_file_name, ignore_expires=True, ignore_discard=True)
    cookieHandler = request.HTTPCookieProcessor(cookie)

    opener = request.build_opener(httpHandler, httpsHandler, cookieHandler)
    request.install_opener(opener)

    url = "https://www.gradsch.cuhk.edu.hk/OnlineApp/viewappform.aspx"
    headers = {
        "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8",
        "Accept-Encoding": "gzip, deflate, br",
        "Accept-Language": "en,zh-CN;q=0.9,zh;q=0.8,zh-TW;q=0.7",
        "Cache-Control": "no-cache",
        "Connection": "keep-alive",
        # "Cookie": "_ga=GA1.3.293053461.1505744778; _ga=GA1.4.1308126732.1505727254; _gid=GA1.4.1237165377.1522830091; ASP.NET_SessionId=zvez04sa2plzr1cgp4ca3op2",
        "Host": "www.gradsch.cuhk.edu.hk",
        "Pragma": "no-cache",
        "Referer": "https://www.gradsch.cuhk.edu.hk/OnlineApp/OnlineApp_menu.aspx",
        "Upgrade-Insecure-Requests": "1",
        "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36"
    }
    req = request.Request(url, headers=headers)
    response = request.urlopen(req);
    print()

    print('print cookie info.')
    for item in cookie:
        print('name = ' + item.name);
        print('value = ' + item.value);

    cookie.save(ignore_discard=True, ignore_expires=True)

    file = open('page2.html', 'w', encoding='utf-8')
    data = response.read().decode('UTF-8');

    file.write(data);
    file.flush()
    file.close()

    soup = BeautifulSoup(data, "html.parser")
    soup.find('a');



# def zhihuCookie():
#     session = requests.session()
#     cookie_file_name = 'cookie.txt'
#     session.cookies = cookielib.LWPCookieJar(cookie_file_name)
#     agent = 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/5.1.2.3000 Chrome/55.0.2883.75 Safari/537.36'
#     headers = {
#         "Host": "www.zhihu.com",
#         "Origin": "https://www.zhihu.com/",
#         "Referer": "http://www.zhihu.com/",
#         'User-Agent': agent
#     }
#
#     postdata = {
#         'password': '*********',  # 填写密码
#         'account': '********',  # 填写帐号
#     }
#     response = session.get("https://www.zhihu.com/signup?next=%2F", headers=headers)
#     soup = BeautifulSoup(response.content, "html.parser")
#     xsrf = soup.find('input', attrs={"name": "_xsrf"}).get("value")
#     postdata['_xsrf'] = xsrf
#     result = session.post('http://www.zhihu.com/login/email', data=postdata, headers=headers)
#     session.cookies.save(ignore_discard=True, ignore_expires=True)


def another():
    url = "http://httpbin.org/post"
    headers = {
        # 伪装一个火狐浏览器
        "User-Agent": 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36',
        "host": 'httpbin.org',
        # debug
        "content-type": "text/html; charset=utf-8",
        "date": "Sat, 31 Mar 2018 03:09:24 GMT",
        "expires": "Fri, 02 Jan 2000 00:00:00 GMT",
        "pragma": "no-cache",
        "server": "nginx",
        "status": "200",
        "vary": "Accept-Encoding",
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
    online()
    # zhihuCookie()
