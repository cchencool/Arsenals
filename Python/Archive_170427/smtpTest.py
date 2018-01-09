#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from email.mime.text import MIMEText
import smtplib

msg = MIMEText('Hello, send by Python...', 'plain', 'utf-8')

from_addr = 'cchencool@gmail.com'
password = ''

to_addr = 'cchencool@foxmail.com'

smtp_server = 'smtp.gmail.com'

server = smtplib.SMTP(smtp_server, 587)
server.starttls()
server.set_debuglevel(1)
server.login(from_addr, password)
server.sendmail(from_addr, [to_addr], msg.as_string())
server.quit()