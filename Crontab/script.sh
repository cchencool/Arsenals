#! /bin/bash
path=/Users/Chen/Desktop/crontabLog/
filename=`date "+%Y-%m-%d"`
touch ${path}${filename}
/bin/rm /opt/netwatcher/pm4h2/work/log/app/* 2>> ${path}${filename}
#rm /opt/netwatcher/pm4h2/work/log/app/*
echo "crontabTest run success at `date "+%Y-%m-%d %H:%M:%S"`\n" >> ${path}${filename}
