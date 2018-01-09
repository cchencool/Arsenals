#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"


from urllib import request, parse

req = request.Request("http://127.0.0.1:8080/AcrossPM-Custom/alarm/exportData_alr.do");

req.add_header('Host', '10.110.2.106:16298')
req.add_header('Connection', 'keep-alive')
req.add_header('Content-Length', '5662')
req.add_header('Pragma', 'no-cache')
req.add_header('Cache-Control', 'no-cache')
req.add_header('Origin', 'https://10.110.2.106:16298')
req.add_header('User-Agent', 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36')
req.add_header('Content-Type', 'application/x-www-form-urlencoded; charset=UTF-8')
req.add_header('Accept', 'application/json, text/javascript, */*; q=0.01')
req.add_header('X-Requested-With', 'XMLHttpRequest')
req.add_header('Referer', 'https://10.110.2.106:16298/AcrossPM-Custom/alarm/overDomain_alr.do')
req.add_header('Accept-Encoding', 'gzip, deflate, br')
req.add_header('Accept-Language', 'en,zh-CN;q=0.8,zh;q=0.6,zh-TW;q=0.4')


# need to change to current
req.add_header('csrftoken', 'ee0e27cc-618c-41db-adf5-6378c5cc9a99')
req.add_header('Cookie', 'JSESSIONID=SWUoqXX8wczq-sY9XhbJbPzrHLAkF0eGSEfUjDrx.pmapp05; route_lc=de4257328524ab08700a485d5a370902')


with open('/Users/Chen/Desktop/element.json', 'r') as file:
    elementJson = file.read()


reqData = parse.urlencode([
    ('tableElement', elementJson),
    ('tableProcess', ''),
    ('graphElements', ''),
    ('networkSystem', '2G;3G'),
    ('graphStyleDefines', ''),
    ('instantQueryVersion', 'csv')
])

with request.urlopen(req, data=reqData.encode('utf-8')) as f:
    data = f.read()
    print('status: ', f.status, f.reason)
    for k, v in f.getheaders():
        print('%s: %s' % (k, v))
    print('Data: ', data.decode('utf-8'))