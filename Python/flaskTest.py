#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from flask import Flask
from flask import request

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def home():
    # small change. 
    return '<h1>Dude, welcome to the world</h1>'
    #if len(request.form) != 0 and request.form['username'] == 'admin' and request.form['password'] == 'password':
    #    return '<h3>Hello, admin</h3>'
    #elif len(request.values) != 0 and request.values['username'] == 'admin' and request.values['password'] == 'password':
    #    return '<h4>Hello, admin</h4>'
    #else:
    #    return '<h1>Home, dude, enter your username & password</h1>'


if __name__ == '__main__':
    # app.run(host='127.0.0.1', port=9999)
    app.run(host='0.0.0.0', port=9999)
