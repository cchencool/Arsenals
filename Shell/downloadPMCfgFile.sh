#! /usr/bash

basepath=$(cd `dirname $0`; pwd)
server=$1

function initParam()
{
	ip_prefix=10.110.2.
	ip_last=${server}
	usr=acrosspm
	
	local_cmn=/opt/netwatcher/pm4h2/work/conf/
	local_jsc=/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home/jre/lib/security/
	local_wrk=/encrypt
	local_rsc=/encrypt/rootsecu
	local_erp=/encrypt/encrypt
	local_drp=/encrypt/decrypt
	local_dsk=/Users/Chen/Desktop
	
	server_cmn=/opt/netwatcher/pm4h2/app/conf/common.properties
	server_jsc=/opt/netwatcher/pm4h2/app/opt/jdk1.8.0_${jdk_ver}/jre/lib/security/jssecacerts
	server_wrk=/opt/netwatcher/pm4h2/encrypt/work_key.properties
	server_rsc=/opt/netwatcher/pm4h2/work/conf/security/rootsecu/rootsecu.txt
	server_erp=/opt/netwatcher/pm4h2/work/conf/encrypt/encrypt.txt
	server_drp=/opt/netwatcher/pm4h2/work/conf/decrypt/decrypt.txt
}

function doScp()
{
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_cmn} ${local_cmn} > /dev/null 2>&1
	result=`getResult`
	echo "Get common.properties ${result}"
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_jsc} ${local_jsc} > /dev/null 2>&1
	result=`getResult`
	echo "Get jssecacerts ${result}"
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_wrk} ${local_wrk} > /dev/null 2>&1
	result=`getResult`
	echo "Get work_key.properties ${result}"
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_rsc} ${local_rsc} > /dev/null 2>&1
	result=`getResult`
	echo "Get rootsecu.txt ${result}"
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_erp} ${local_erp} > /dev/null 2>&1
	result=`getResult`
	echo "Get encrypt.txt ${result}"
	${basepath}/DPC.sh ${ip_prefix}${ip_last} $1 ${server_drp} ${local_drp} > /dev/null 2>&1
	result=`getResult`
	echo "Get decrypt.tx ${result}"
}

function begin()
{
	jdk_ver=$1
	initParam
	doScp $2
}

function getResult()
{
	if [ $? -eq 0 ];then
		echo "success"
	else
		echo "failed"
	fi
}

function chAuth()
{
	chmod 664 ${local_jsc}jssecacerts 
	chmod 664 ${local_cmn}common.properties 
	chmod 664 ${local_wrk}work_key.properties 
	chmod 664 ${local_rsc}rootsecu.txt 
	chmod 664 ${local_erp}encrypt.txt
	chmod 664 ${local_drp}decrypt.txt
}

if [ ${server} -eq 56 ];then
	begin 101 Across_app@123
elif [ ${server} -eq 57 ];then
	begin 101 Across_pwd_001
elif [ ${server} -eq 124 ];then
	begin 101 acrosspm1
elif [ ${server} -eq 158 ];then
	begin 121 Across_pwd_001
elif [ ${server} -eq 156 ];then
	begin 121 acrosspm
elif [ ${server} -eq 102 ];then
	begin 101 Across_pwd@2016
elif [ ${server} -eq 104 ];then
	begin 112 PM_app@123
elif [ ${server} -eq 106 ];then
	begin 121 PM_app@123
fi

chAuth > /dev/null 2>&1

