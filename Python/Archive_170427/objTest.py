#! /usr/bin/env python3
# -*- coding: utf-8 -*-

""" a test model """

import sys

__author__ = 'Chen'


class Animal(object):

    theClzAttr = 'I\'m great!'

    def __init__(self, species='animal'):
        self.species = species

    def __str__(self):
        return "Ha! " + self.species;

    def run(self):
        print('An animal is running...')



class Student(Animal):
    def __init__(self, name='', score=''):  # give the params default values.
        super(Student, self).__init__('student')  # call father class method
        self.__name = name  # __ makes a variable private.
        self.__score = score

    @property
    def name(self):
        return self.__name

    @property
    def score(self):
        return self.__score

    @name.setter
    def name(self, name):
        if isinstance(name, str):
            self.__name = name
        else:
            raise ValueError('name must be a str');

    # def set_score(self, score):
    #     self.__score = score

    def print_score(self):
        print('%s : %s ' % (self.__name, self.__score))
        # self.run()

    def run(self):
        super(Student, self).run()
        print('Wow, the animal is a(n) %s !' % self.species)

class Fib():
    def __init__(self, limit=10000):
        self.a = 0
        self.b = 1
        self.limit = limit

    def __iter__(self):
        return self

    def __next__(self):
        self.c = self.a
        self.a = self.b
        self.b = self.c + self.b
        # self.a, self.b = self.b, self.a + self.b
        # print(str(self.a) + "," + str(self.b))
        if self.a > self.limit:
            raise StopIteration()
        return self.a

class Chain():
    def __init__(self, path=''):
        self._path = path

    def __getattr__(self, path):
        return Chain('%s/%s' % (self._path, path))

    def __str__(self):
        return self._path

    __repr__ = __str__

def testFib(arg=1000):
    fib = Fib(limit=arg);
    for n in fib:
        print(n)

def testObj(args):
    if len(args) == 3:
        new = Student()
        new.set_name(args[1])
        new.set_score(args[2])
        new.print_score()
    else:
        bart = Student('Bart Simpson', 59)
        lisa = Student('Lisa Simpson', 87)
        bart.name = 'Bartty Simpson';
        # bart.name = 123;
        # bart.score = 123;
        print(bart.name)
        print(lisa)
        bart.print_score()
        lisa.print_score()
        dog = Animal()
        bart.run()
        print(type(bart), isinstance(bart, Student))
        print(type(dog), isinstance(dog, Student))
        print(isinstance(lisa, Animal))
        print(hasattr(bart, 'read'))

def main():
    args = sys.argv
    # testObj(args)
    # testFib(12)
    print(Chain().status.user.timeline.list.hahaha)

    # print(Animal.theClzAttr)
    # image_file = open('/Users/Chen/Desktop/data', mode='r+', buffering=-1, encoding='UTF-8', errors=None, newline=None, closefd=True)
    # if hasattr(image_file, 'read'):
    #     print('Is read bale')
    #     while True:
    #         pic = image_file.readline()
    #         if pic == None or pic == '':
    #             break
    #         print(pic, end='')
    #     image_file.write('Yes!');
    #     image_file.close()
    # else:
    #     print(False)

if __name__ == '__main__':
    main()
