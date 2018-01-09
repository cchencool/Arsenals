#! /usr/bin/env python2.7
# -*- coding: utf-8 -*-

""" Oracle test """

import cx_Oracle

__author__ = 'Chen'

print("cx_Oracle.version:", cx_Oracle.version)
host = "10.110.2.156"
port = "1522"
sid = "acrosspm"
dsn = cx_Oracle.makedsn(host, port, sid)
print('dsn : ' + dsn)
connection = cx_Oracle.connect("pm4h_ad", "ACROSS_ad_2013", dsn)
cursor = cx_Oracle.Cursor(connection)  # 返回连接的游标对象

print("======")

sql = "select * from pm4h_ad.special_query_configuration t"
if True:
    cursor.execute(sql)
    for i in cursor:
        print(i)
        # cursor.execute(sql)
        # print(len(cursor))# 抛出异常 TypeError: object of type 'cx_Oracle.Cursor' has no len()
        # 虽然执行了cursor.execute(sql)，但是cursor并没有存储查询操作返回的结果集，
        # 它应该是实时从连接里面获取下一个结果的，所以它并不知道这个SQL总共查询得到了多少行结果。
        # 所以对它执行len操作显然是错误的。
print("======")

sql = "select * from pm4h_ad.special_query_configuration t"
if True:
    # print("cursor1:", cursor)  # 由打印的信息可以知道，cursor.execute语句之前的cursor和该语句之后的cursor是同等地位的。
    # cursor = cursor.execute(sql)  # 所以这个赋值语句是不必要的。另外，上面的例子和下面的例子也都没有执行赋值操作。
    # print("cursor2:", cursor)
    results = cursor.fetchall()  # 将所有(剩余)的行作为序列的序列。
    print("len(results):", len(results))
print("======")

sql = "select * from pm4h_ad.special_query_configuration t"
if True:
    cursor.execute(sql)
    while True:
        result = cursor.fetchone()  # 把查询的结果集中的下一行保存为序列，或者None。
        if result == None:
            break
        print("result:", result)
print("======")

connection.close()
print("EXIT")
