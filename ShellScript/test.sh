#!/bin/bash

echo "Param count \$#: $#"
echo "Param list \$*: $*"
echo "PID : $$"
echo "Last PID in Sys : $!"
echo "Param list : $@"
echo "Opentions : $-"
echo "Exit status : $?"

echo "Print params one by one from \$* : "
for each in "$*"; do
	echo $each
done

echo "Print params one by one from \$@ : "
for each in "$@"; do
	echo $each
done


echo "Print params one by one : "
int=1
while(( $int<=$#))
do
	echo $int
	let "int++"
done
