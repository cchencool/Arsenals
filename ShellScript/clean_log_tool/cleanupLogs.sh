#! /bin/bash

. /Users/Chen/.bash_profile

basepath=$(cd `dirname $0`; pwd)
path=/Users/Chen/Desktop/temp/crontabLog/
#terminalLogDir=/private/var/log/asl/*.asl
filename=`date "+%Y-%m-%d"`

touch ${path}${filename}

/bin/rm $PM4H_LOG/* >/dev/null 2>&1
cd $JBOSS_LOG/ >/dev/null 2>&1
ls $JBOSS_LOG | grep -i ^.[^e].* | xargs -n1 /bin/rm  >/dev/null 2>&1
cd - >/dev/null 2>&1
if [ $? -eq 0 ];then
	echo "clean pm log success" >> ${path}${filename} 2>&1
else
	echo "no pm log found" >> ${path}${filename} 2>&1
fi

#${basepath}/cmdExcp.sh /bin/rm -rf '/private/var/log/asl/*.asl' >> ${path}${filename} 2>&1 #${terminalLogDir} >> ${path}${filename} 2>&1 
#if [ $? -eq 0 ];then
#	echo "clean terminal log with pwd success" >> ${path}${filename} 2>&1
#else
#	sudo /bin/rm -rf ${terminalLogDir} >> ${path}${filename} 2>&1
#	echo "clean terminal log without pwd success" >> ${path}${filename} 2>&1
#fi
echo "crontabTest run success at `date "+%Y-%m-%d %H:%M:%S"`\n" >> ${path}${filename}
