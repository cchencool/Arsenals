#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "chen"

from multiprocessing import Process, Pool, Queue
from multiprocessing.managers import BaseManager
import os, time, random, queue, threading

# 发送任务的队列:
task_queue = queue.Queue()
# 接收结果的队列:
result_queue = queue.Queue()


# 从BaseManager继承的QueueManager:
class QueueManager(BaseManager):
    pass


def task_generator(task):
    while True:
        # for i in range(10):
        n = random.randint(0, 10000)
        print('Put task %d...' % n)
        task.put(n)
        time.sleep(1)


def result_acquire(result):
    while True:
        print('Try get results...')
        try:
            r = result.get(timeout=1)
            text = 'Result: %s' % r
        except queue.Empty:
            text = 'No result found'
            print(text)

        print(text)
        with open('./result.dat', 'a') as f:
            f.write(text + '\n')

        time.sleep(1)


def main():
    # 把两个Queue都注册到网络上, callable参数关联了Queue对象:
    QueueManager.register('get_task_queue', callable=lambda: task_queue)
    QueueManager.register('get_result_queue', callable=lambda: result_queue)

    # 绑定端口5000, 设置验证码'abc':
    manager = QueueManager(address=('', 5000), authkey=b'abc')

    # 启动Queue:
    print('master start.')
    manager.start()

    # 获得通过网络访问的Queue对象:
    task = manager.get_task_queue()
    result = manager.get_result_queue()

    # 分别启动任务生成线程和结果获取线程
    taskThread = threading.Thread(target=task_generator, args=(task,), name='task_generateor_thread')
    resultThread = threading.Thread(target=result_acquire, args=(result,), name='result_acquire_thread')

    taskThread.start()
    resultThread.start()

    taskThread.join()
    resultThread.join()

    manager.shutdown()
    print('master exist.')


if __name__ == '__main__':
    main()
