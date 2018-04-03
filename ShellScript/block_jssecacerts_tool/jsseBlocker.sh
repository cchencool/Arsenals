#! /bin/bash

sw=$1

JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home"
file="$JAVA_HOME/jre/lib/security/jssecacerts"
file_block="$JAVA_HOME/jre/lib/security/jssecacerts_bak"

if [ -f "$file" ]; then
	sudo mv $file $file_block
	echo "block jssecacerts turn on"
elif [ -f "$file_block" ]; then
	sudo mv $file_block $file
	echo "block jssecacerts turn off"
fi
