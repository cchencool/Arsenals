#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from multiprocessing import Process, Pool, Queue
from multiprocessing.managers import BaseManager
import os, time, random, queue


class QueueManager(BaseManager):
    pass


# 从task队列取任务,并把结果写入result队列:
def process_task(task, result):
    while True:
        try:
            n = task.get(timeout=1)
            print('run task %d * %d...' % (n, n))
            r = 'Process by %s : %d * %d = %d ' % (os.getpid(), n, n, n * n)
            result.put(r)
            time.sleep(2)
        except queue.Empty:
            print('No task found')
        time.sleep(2)


def main():
    # 由于这个QueueManager只从网络上获取Queue，所以注册时只提供名字:
    QueueManager.register('get_task_queue')
    QueueManager.register('get_result_queue')

    # 连接到服务器，也就是运行task_master.py的机器:
    server_addr = '127.0.0.1'
    print('Connect to server %s...' % server_addr)
    # 端口和验证码注意保持与task_master.py设置的完全一致:
    manager = QueueManager(address=(server_addr, 5000), authkey=b'abc')

    print('worker connect master.')
    manager.connect()

    # 获取Queue的对象:
    task = manager.get_task_queue()
    result = manager.get_result_queue()

    process_task(task, result)

    print('worker exist.')


if __name__ == '__main__':
    main()
