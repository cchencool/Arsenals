#! /usr/bin/python
# -*- coding: utf-8 -*-

import time
import os
import cv2
import numpy as np

counter=0
wait_sec = 5
sleep_duration=1

img_dir='/Users/Chen/Desktop/capturedImg'

cap=cv2.VideoCapture(0)

while(1):    # get a frame   
  ret, frame = cap.read()    # show a frame   
  #cv2.imshow("capture", frame)
  counter = counter + 1
  if (counter > wait_sec):
    current = str(time.strftime('%Y%m%d%H%M%S', time.localtime()))
    if (not os.path.exists(img_dir)):
      os.mkdir(img_dir)
    cv2.imwrite(img_dir + '/' + current + '.jpeg', frame)
    sleep_duration = 5
  time.sleep(sleep_duration)
  #if cv2.waitKey(1) & 0xFF == ord('q'):
  if (counter > wait_sec + 10):
    break
cap.release()
cv2.destroyAllWindows()
