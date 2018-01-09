#! /usr/bin/env python3
# -*- coding: utf-8 -*-

__author__ = "Cchen Zhang"

from multiprocessing import Process, Pool, Queue
import os, time, random, subprocess


def startNewProcess():
    print('Process (%s) start...' % os.getpid())
    pid = os.fork()
    print('fork result : %s' % pid)
    if pid == 0:
        print('I\'m a child process (%s) and my parent process is (%s)' % (os.getpid(), os.getppid()))
    else:
        print('I (%s) just create a child process (%s)' % (os.getpid(), pid))


def run_proc(name):
    print('Run task process %s (%s)' % (name, os.getpid()))
    start = time.time()
    # time.sleep(random.random() * 3)
    a = 1
    while True:
        a = a + 1
        if a == 9999999:
            break
    end = time.time()
    print('Task %s run for %0.2f seconds.' % (os.getpid(), (end - start)))


def processTest():
    print('Parent process %s' % os.getpid())
    p = Process(target=run_proc, args=('test',))
    print('Child process will start')
    p.start()
    p.join()
    print('Child process end')


def processPoolTest():
    print('Parent process %s' % os.getpid())
    p = Pool(2)
    for i in range(5):
        p.apply_async(run_proc, args=(i,))
    print('Waiting for all task subprocess will done...')
    p.close()
    p.join()
    print('All task subprocess end.')


def subprocessTest():
    print('$ nslookup wwww.python.org')
    r = subprocess.call(['nslookup', 'www.python.org'])
    print('Exit code : %d' % r)
    print('')
    p = subprocess.Popen(['nslookup'], stdin=subprocess.PIPE, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    output, error = p.communicate(b'set q=mx\npython.org\nexit\n')
    print(output.decode('utf-8'))
    print('Exit code', p.returncode)


def write(q):
    print('Process to wirte: %s' % os.getpid())
    for value in ['A', 'B', 'C']:
        print('Put %s to queue...' % value)
        q.put(value)
        time.sleep(random.random())


def read(q):
    print('Process to read: %s' % os.getpid())
    while True:
        value = q.get(True)
        print('Get %s from queue' % value)


def subprocessCommunication():
    q = Queue()
    pw = Process(target=write, args=(q,))
    pr = Process(target=read, args=(q,))
    pw.start()
    pr.start()
    pw.join()
    pr.terminate()


def main():
    # startNewProcess()
    # processTest()
    # processPoolTest()
    # subprocessTest()
    subprocessCommunication()

if __name__ == '__main__':
    main()
