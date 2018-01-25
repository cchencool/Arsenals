#!/bin/bash

#ssh -t pi@192.168.31.246 "/home/pi/Script/clock.sh"

line[0]="      "
line[1]=".     "
line[2]="..    "
line[3]="...   "
line[4]="....  "
line[5]="..... "
line[6]="......"
line[7]=" ....."
line[8]="  ...."
line[9]="   ..."
line[10]="    .."
line[11]="     ."


j=63
if [ -n "$1" ]; then
  j=$1
fi

i=0
k=0

s=$((j+1))
n=$((s/2))
dt="."
sp=" "

for((i=0;i<j;i++));do
  line_n=""
  if [[ $i -lt $n ]];then
    for((m=0;m<i;m++));do
      line_n=$line_n$dt
    done
    for((m=0;m<n-i;m++));do
      line_n=$line_n$sp
    done
  else
    for((m=0;m<i-n;m++));do
      line_n=$line_n$sp
    done
    lft=$((s-i))
    for((m=0;m<lft;m++));do
      line_n=$line_n$dt
    done
  fi
  #echo $line_n
  line[$i]=$line_n
done

i=0

while true;do
	dt=`gdate "+%Y-%m-%d %H:%M:%S.%N"`
	#echo -ne "\r$dt"

	#####
	#printf "\r$dt"
  #printf "${line[$i]}"
	
	#####
	tput cup 1 0
  echo "$dt"
  #printf "\r$dt"
  tput cup 2 0
  #echo "${line[$i]}"
  printf "\r${line[$i]}\r"

#	sleep 0.0166
  #sleep 0.028
  k=$((k+1))
  if [ $k%2=0 ]; then
    i=$((i+1))
    i=$((i%j))
  fi

  if read -t 1 -n 1; then
	  break
  fi
done
