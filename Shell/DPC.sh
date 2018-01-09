#! /usr/bin/expect

set ip_addr [lindex $argv 0]
set password [lindex $argv 1]
set server_dir [lindex $argv 2]
set local_dir [lindex $argv 3]

set timeout 30

spawn scp acrosspm@${ip_addr}:${server_dir} ${local_dir}
expect {
"(yes/no)?" {send "yes\n"; exp_continue;}
"*assword:*" {send "$password\n"}
}

expect eof
catch wait result
exit [lindex $result 3]

interact

