# .bash_profile

export CHEN_DEVP="/Users/Chen/Development"
export CHEN_GITS="$CHEN_DEVP/Gits/"
export CHEN_HEXO="$CHEN_DEVP/Gits/Hexo"
export CHEN_SCRIPT_PATH="$CHEN_DEVP/Gits/Arsenals/ShellScript"

export PM4H_DEBUG="$CHEN_DEVP/DebugFiles/temp"
export PM4H_WORK="/opt/netwatcher/pm4h2/work"
export PM4H_CONF="$PM4H_WORK/conf"
export PM4H_TMP="$PM4H_WORK/tmp/"
export PM4H_MODULE="/opt/netwatcher/pm4h2/app/module"
export PM4H_LOG="$PM4H_WORK/log/app"

export JBOSS_HOME="$CHEN_DEVP/Work/DevelopmentWorkSpace/wildfly-9.0.1.Final"
export JBOSS_LOG="$JBOSS_HOME/standalone/log"

export JAVA_HOME="/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/Contents/Home"
export CLASSPATH=".:/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/lib/dt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_66.jdk/lib/tools.jar"

export ORACLE_HOME="/Library/Oracle/instantclient_12_1"
export ICLOUD="/Users/Chen/Library/Mobile\ Documents/com~apple~CloudDocs"

# alias
alias p="python"
alias p2="python2.7"
alias p3="python3"
alias oe="open -n /Applications/Eclipse.app"
alias oep="oe --args -data $CHEN_DEVP/Workspaces/EclipsePlayground/"
alias oet="oe --args -data $CHEN_DEVP/Work/DevelopmentWorkSpace/PM_Code/Across-PM_V2R1_23_TR6-Trunk/"
alias oe26="oe --args -data $CHEN_DEVP/Work/DevelopmentWorkSpace/PM_Code/Across-PM_V2R1_26_Brazil_unckReq/"
alias sw="sudo sh $CHEN_SCRIPT_PATH/switch_env_tool/downloadPMCfgFile.sh "
alias cj="sudo sh $CHEN_SCRIPT_PATH/clean_log_tool/leanupjboss.sh "
alias blockJsse="sh $CHEN_SCRIPT_PATH/block_jssecacerts_tool/jsseBlocker.sh "
alias s="screen"
alias sl="screen -ls"
alias sr="screen -r"

