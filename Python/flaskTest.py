#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from datetime import datetime
from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def home():
    # small change. 
	data = request.values['data']
	now = datetime.now()
	f = open('/home/pi/Development/Git/Arsenals/Python/data.dat', 'a')
	print("--------------------------")
	print("now: %s\ndata\n: %s" % (now, data))
	print("--------------------------")
	f.write("-----------------------------------------\n")
	f.write("now: %s\ndata\n: %s\n" % (now, data))
	f.close()
	#if len(request.form) != 0 and request.form['username'] == 'admin' and request.form['password'] == 'password':
	#    return '<h3>Hello, admin</h3>'
	#elif len(request.values) != 0 and request.values['username'] == 'admin' and request.values['password'] == 'password':
	#    return '<h4>Hello, admin</h4>'
	#else:
	#    return '<h1>Home, dude, enter your username & password</h1>'
	return '<h1>Dude, welcome to the world</h1>'


if __name__ == '__main__':
	app.run(host='127.0.0.1', port=9999)
#    app.run(host='0.0.0.0', port=9999)
