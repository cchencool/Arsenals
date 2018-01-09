from functools import reduce


def normalize(name):
    name = str.lower(name)
    return str.capitalize(name)


def proc(arg=None):
    if isinstance(arg, list):
        return reduce(lambda x, y: x * y, arg)


def str2int(arg):
    if isinstance(arg, str) and arg != '.':
        return int(arg)


def str2float(arg):
    if isinstance(arg, str):
        head, end = arg.split('.')
        lhead = list(map(str2int, head))
        lend = list(map(str2int, end))
        lhead.extend(lend)
        return reduce(lambda x, y: x * 10 + y, lhead) / pow(10, len(lend))


L1 = ['adam', 'LISA', 'barT']
L2 = list(map(normalize, L1))
print(L2)

print('3 * 5 * 7 * 9 =', proc([3, 5, 7, 9]))
print(str2float('123.456'))
