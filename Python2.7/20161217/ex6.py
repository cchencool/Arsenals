def nop(arg=None):
    print('hello')
    pass

def My_abs(x):
    if not isinstance(x, (int, float)):
        raise TypeError('Bad operand type')
    if x >= 0:
        return x, -x
    else:
        return -x, x

def calc(*numbers):
	sum = 0
	for n in numbers:
		sum = sum + n * n
	return sum

def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args = ', args, 'kw = ', kw)

h = My_abs(-3)
print(h)

#m = calc([1,2,3,4,5,6])
#print(m)

num = [1,2,3,4,5,6,7,8,9]

n = calc(*num)
print(n)

print(nop())

print(nop("Helllllh"))

print(f1(1,2))
print(f1(1, 2, c=3))
print(f1(1,2,3,'a','b'))
print(f1(1,2,3,'a','b',x=99))
print(f1(1,2,3,'a'))
