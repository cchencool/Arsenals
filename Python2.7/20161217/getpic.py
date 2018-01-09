# coding=utf-8
import urllib
import re


def getHtml(url):
    page = urllib.urlopen(url)
    html = page.read()
    return html


def getImg(html):
    reg = r'src="(.+?\.png)" alt'
    imgre = re.compile(reg)
    imglist = re.findall(imgre, html)
    x = 0
    for imgurl in imglist:
        urllib.urlretrieve(imgurl, '%s.png' % x)
        x += 1


# return imglist

html = getHtml("http://www.cnblogs.com/fnng/p/3576154.html")
print
getImg(html)
print
"Done."
