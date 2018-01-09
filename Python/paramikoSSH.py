# import paramiko
# import sys
# ssh_fd=paramiko.SSHClient()
# ssh_fd.set_missing_host_key_policy( paramiko.AutoAddPolicy() )
# #ssh_fd.connect('server.ngrok.cc', 1277, 'pi', 'cchencool')
# ssh_fd.connect('10.110.2.157', 22, 'acrosspm', 'Across_app@123')
# stdin,stdout,stderr=ssh_fd.exec_command('ls')
# err_list=stderr.readline()
# if len(err_list) == 0:
#     for item in stdout:
#        print(item)
# else:
#     for err_content in err_list:
#         print('ERROR: ' + err_content)
# ssh_fd.close()

import paramiko
import sys
def ssh_connect( _host, _port, _username, _password ):
    try:
        _ssh_fd = paramiko.SSHClient()
        _ssh_fd.set_missing_host_key_policy( paramiko.AutoAddPolicy() )
        _ssh_fd.connect( _host, _port, username = _username, password = _password )
    except Exception(e):
        print('Authorization Failed!Please check the username,password or your device is connected to the Internet.')
        exit()
    return _ssh_fd

def ssh_exec_cmd( _ssh_fd, _cmd ):
    return _ssh_fd.exec_command( _cmd )

def ssh_close( _ssh_fd ):
    _ssh_fd.close()

def print_ssh_exec_cmd_return(_ssh_fd,_cmd):
    stdin,stdout,stderr=ssh_exec_cmd(_ssh_fd,_cmd)
    err_list = stderr.readlines()
    if len( err_list ) > 0:
        for err_content in err_list:
            print('ERROR:' + err_content)
        exit()
    for item in stdout:
        print(item)

def sftp_connect(sshc):
    # sftp = paramiko.SFTPClient.from_transport(sshc.get_transport())
    sftp = sshc.open_sftp()
    return sftp


if __name__ == '__main__':
    # sshd = ssh_connect( '192.168.1.121', sys.argv[1], sys.argv[2])
    sshd = ssh_connect('10.110.2.157', 22, 'acrosspm', 'Across_app@123')
    print('Executing \''+sys.argv[1]+'\' command,remote controlling raspberrypi.')
    if len(sys.argv) == 2:
        print_ssh_exec_cmd_return(sshd,sys.argv[1])
    ssh_close(sshd)
