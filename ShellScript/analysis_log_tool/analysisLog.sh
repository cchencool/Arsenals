#!/bin/bash

basepath=$(cd `dirname $0`; pwd)
anlysRstDir=${basepath}/lgalysisResult
fileSuffix=.log # * .log 2.log

keyword_log=lgalysis_part1
keyword_tasks=lgalysis_tasks
keyword_timeconsume=lgalysis_timeConsume
keyword_startStr="exportSheet start"
keyword_endStr="exportSheet end"
#keyword_startStr="before writeTable1FMTFile"
#keyword_endStr="after writeTable1FMTFile"


if [ "$#" -eq 4 ] || [ "$#" -eq 1 ]; then
	method=$1
	str=$2
	end=$3
	limit=$4
#elif [ "$#" -eq 3 ]; then
else
	str=$1
	end=$2
	limit=$3
fi

function initParama()
{
	if [ ! -d "${anlysRstDir}" ]; then
		mkdir ${anlysRstDir}
	fi
	
	if [ ! -n "${limit}" ]; then
		limit=3
	fi
	
	if [ ! -n "${str}" ]; then
		str=1
	fi
	
	if [ ! -n "${end}" ]; then
		end=3
	fi

	echo "start: ${str}, end: ${end}, limit: ${limit}"
}

function abstractTask()
{
	str_doing=1
	for line in `cat ${basepath}/${keyword_tasks}` 
	do
		if [[ "${str_doing}" -ge "${str}" && "${str_doing}" -le "${end}" ]]; then
			echo "start: ${str}, end: ${end}, abstracting ${str_doing} ..."
			cat ${basepath}/${keyword_log} | grep -i ${line} > ${anlysRstDir}/part2_${line}
			let limit--
		fi
	
		let	str_doing++
	
		if [ ${limit} -lt 1 ]; then
			break
		fi
	done
}

function analysisCost_time()
{
	#cd ${anlysRstDir}
	for file in ${anlysRstDir}/*
	do
		if [ -f ${file} ]; then
			startTimeStr=`cat ${file} | grep -i "${keyword_startStr}" | awk -F ']' '{print $1}'`
			endTimeStr=`cat ${file} | grep -i "${keyword_endStr}" | awk -F ']' '{print $1}'`
			
			startTimeStr=${startTimeStr##\[}
			endTimeStr=${endTimeStr##\[}

			if [ -n "${startTimeStr}" ] && [ -n "${endTimeStr}" ]; then

				startTime=`date -j -f "%Y-%m-%d %H:%M:%S" "${startTimeStr}" +%s`
				endTime=`date -j -f "%Y-%m-%d %H:%M:%S" "${endTimeStr}" +%s`
				
				let diff=${endTime%%#}-${startTime%%#}
	
				id=${file##*/}
				id=${id%\]}
	
				if [ -n "${diff}" ]; then
					echo "${id} : ${diff}s"
				fi
			fi
		fi
	done
	#cd -
}

function initData(){
	cat ${basepath}/11.12.16.53_DEF_WebService${fileSuffix} | grep -i '【debug】' > lgalysis_part1
	cat lgalysis_part1 | awk -F '[' '{print $3}' | awk '{print $2}' | awk '!a[$0]++' > lgalysis_tasks
	echo "task count :`cat lgalysis_tasks | wc -l`"
	echo "abstract - export action start"
	cat 11.12.16.53_DEF_WebService${fileSuffix} | grep -i 'export action start' > lgalysis_exp_start
	echo "abstract - export action end"
	cat 11.12.16.53_DEF_WebService${fileSuffix} | grep -i 'export action end' > lgalysis_exp_end
	echo "abstract - export action response"
	cat 11.12.16.53_DEF_WebService${fileSuffix} | grep -i 'export action response' > lgalysis_exp_rep
}


if [ ! -n "${method}" ] || [ "${method}" == "-a" ]; then
	echo "enter 1"
	initParama
	abstractTask
	analysisCost_time 2> /dev/null | tee ${basepath}/${keyword_timeconsume}
elif [ "${method}" = "-i" ]; then
	initData
elif [ "${method}" = "-t" ]; then
	echo "enter 2"
	initParama
	abstractTask
elif [ "${method}" = "-c" ]; then
	echo "enter 3"
	initParama
	analysisCost_time 2> /dev/null | tee ${basepath}/${keyword_timeconsume}
fi



