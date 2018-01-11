#! /usr/bin/expect

set cmd [lindex $argv 0]
set cnd [lindex $argv 1]
set prm [lindex $argv 2]

set timeout 1
set password cchencool

#spawn sudo -k ${cmd} ${cnd} ${prm}
#spawn sudo -k /bin/rm -rf /private/var/log/asl/*.asl
#spawn sudo -k /bin/rm -rf `ls /private/var/log/asl/*.asl`
expect {
"*asswor*" {send "$password\n"}
}

#expect eof
#catch wait result
#exit [lindex $result 3]

interact
