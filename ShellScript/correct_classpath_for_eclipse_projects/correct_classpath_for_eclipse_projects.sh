#! /bin/bash

dir='/Users/Chen/Development/Work/DevelopmentWorkSpace/PM_Code/Across-PM_V1R2C30-MTN-46'
out_file='/Users/Chen/Desktop/PMv6-AnalysisEngine/cmds.sh'

#commands=()

function replaceCP() {
    org_cp=$1
    rep_cp=$2
    dot_cp_file=$3
    echo " sed -i '#' 's#$org_cp#$rep_cp#g' $dot_cp_file " >> $out_file
    #for f in ${files}; do
       #command=" sed -i '' 's/$org_cp/$rep_cp/g' $f "
       #command_count=${#commands[@]}
       #${commands[$command_count]}=$command
    #done
}

function processFile() {
    file=$1
    echo "processing file: $file start."

    cp_jars=`cat $file | grep '<classpathentry kind="lib" path="/PM-BASE-LIBS' | awk -F '"' '{print $4}'`

    for cp_jar in $cp_jars; do
        full_cp_jar=$dir$cp_jar
        regex_cp_jar=`echo $cp_jar | awk -F '/' '{print $3}' | sed 's/[0-9]/*/g'`
        if [[ ! -f $full_cp_jar ]];then
            echo "find no jar: $cp_jar"
            new_jar=`find $dir -name $regex_cp_jar`
            if [[ -n $new_jar && -f $new_jar ]]; then
                rep_jar=`echo $new_jar | awk -F '/' '{print "/"$(NF-1)"/"$(NF)}'`
                echo "find new jar: $rep_jar"
                trans_cp_jar=`echo $cp_jar | sed 's/\//\\//g'`
                replaceCP $cp_jar $rep_jar $file
            elif [[ -n $new_jar ]]; then
                echo "find no new jar for jar: $regex_cp_jar" 
            fi
        fi
    done

    echo "processing file: $file done."
}

function main() {
    files=`find ${dir} -name '*classpath' | sort`
    for f in ${files}; do
        processFile ${f}
    done
}

main

## print commands
#echo "command list: "
#for cmd in $commands; do
#    echo ${cmd}
#done