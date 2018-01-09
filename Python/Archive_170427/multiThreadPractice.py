#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "Cchen Zhang"

import time, threading, multiprocessing

balance = 0
lock = threading.Lock()
threadpool = []


def loop():
    print('thread %s is running' % threading.current_thread().name)
    n = 0
    while n < 5:
        n = n + 1
        print('thread %s >>> %s' % (threading.current_thread().name, n))
        time.sleep(1)
    print('thread %s end' % threading.current_thread().name)


def dead_loop():
    n = 0
    while True:
        n = n ^ 1


def change_value(n):
    global balance
    balance = balance + n
    balance = balance - n


def run_thread(n):
    for i in range(1000):
        lock.acquire()
        try:
            change_value(n)
        finally:
            lock.release()


def test_multiThread():
    print('thread %s is running' % threading.current_thread().name)
    t1 = threading.Thread(target=run_thread, name='t1', args=(5,))
    t2 = threading.Thread(target=run_thread, name='t2', args=(-8,))
    t1.start()
    t2.start()
    t1.join()
    t2.join()
    print(balance)
    print('thread %s is end' % threading.current_thread().name)


def test_thread_cpu():
    n = multiprocessing.cpu_count()
    print(n)
    for i in range(n):
        t = threading.Thread(target=dead_loop())
        print('start new thread')
        threadpool.append(t)
        t.start()


def main():
    # test_multiThread()
    test_thread_cpu()
    time.sleep(10)
    for t in threadpool:
        t.terminate()


if __name__ == '__main__':
    main()
