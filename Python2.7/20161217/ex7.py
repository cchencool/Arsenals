# __debug__ = False
def fact(n):
    return fact_iter(n, 1)


def fact_iter(num, product):
    if 1 == num:
        return product
    return fact_iter(num - 1, num * product)


def move(n, a, b, c):
    print('n = ', n, ' a =', a, ' b =', b, ' c =', c)
    if 1 == n:
        print('move', a, '-->', c)
        return
    move(n - 1, a, b, c)
    print('move', a, '-->', c)
    move(n - 1, b, a, c)


def hannoi(n, x, y, z):
    if 1 == n:
        print(x, '-->', z)
    else:
        hannoi(n - 1, x, z, y)
        hannoi(1, x, y, z)
        # assert isinstance(z, int)
        hannoi(n - 1, y, x, z)


def fib(max=1):
    n, a, b = 0, 0, 1
    while n < max:
        yield b  # because of the key word yield, this method becomes to a generator
        a = b
        b = a + b
        n += 1
    return 'done'


# print(fact(500))
# move(3, 'A', 'B', 'C')
# hannoi(10, 'A', 'B', 'C')
# L = []
# n = 1
# while n < 10:
#     L.append(n)
#     n += 1
# print(L)
#
# print(__debug__)
#
# L = list(range(100))
# print(L)
# H = L
# H[0] = 999
# print(L)
# H = L[:]    # duplicate object(list) L
# H[0] = 9999
# print(L)
# print(H)

l1 = list(range(100))
l2 = [i for i in range(0, 10, 3) if i % 2 == 0]
for i, v in enumerate(l2):
    print(l2[i])

l = ['HELLO', 'WORLD', ',', 12333, 'MY', 'NAME', 'IS', 'FUCCK', 12321, 'YOU']
l = [char.lower() for char in l if isinstance(char, str)]
string = ' '.join(l)
print(string)

g = (x * x for x in range(10))  # this is a generator
print(g)
for v in g:
    print(v)

x = fib(10)
print(x)
for v in x:
    print(v)
