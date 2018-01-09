查询pia及完整率数据工具
工具目录：10.110.2.104:/home/acrosspm/chen/GeneralTools.jar
################################################
# general tools by tcp requsts
#
# step 1. run server.
# java -cp .:/home/acrosspm/chen/GeneralTools.jar:/opt/netwatcher/pm4h2/app/opt/hbase/lib/guava-12.0.1.jar:${PM4H_LIB}/common/main/* com.chen.tools.platform.frame.main.GeneralTools
# 
# step 2. send query request.
# java -cp .:/home/acrosspm/chen/GeneralTools.jar:/opt/netwatcher/pm4h2/app/opt/hbase/lib/guava-12.0.1.jar:${PM4H_LIB}/common/main/* com.chen.tools.platform.frame.client.Client cfg
#
###############################################
## Usage 1.
# get missing data fie content by filekey.
# java -cp .:/home/acrosspm/chen/GeneralTools.jar:/opt/netwatcher/pm4h2/app/opt/hbase/lib/guava-12.0.1.jar:${PM4H_LIB}/common/main/* com.chen.tools.platform.frame.client.Client msd 931070b3cf_M15_20171226_0345_83e83e83e_0000009604_1
#
## Usage 2.
# get pia file content by filekey.
# java -cp .:/home/acrosspm/chen/GeneralTools.jar:/opt/netwatcher/pm4h2/app/opt/hbase/lib/guava-12.0.1.jar:${PM4H_LIB}/common/main/* com.chen.tools.platform.frame.client.Client pia M5_20171226_0025_75eec2ec24e9362f0c10000035610_indicatorid
#
## Usage 3.
# get pia & missing data file content by cfg.properties
#
# step1. vi cfg.properties, set queryvo condition.
sourceMotypeId=55e7c3eb919a4d9d9ac5b65e3bfc98cb
equipmentEntityId=9604
indicatorSetid=5d7948e12e804fee97611a89a64ad1be
startDay=20171226
startTime=104500
period=M15
#
# step2. command.
# java -cp .:/home/acrosspm/chen/GeneralTools.jar:/opt/netwatcher/pm4h2/app/opt/hbase/lib/guava-12.0.1.jar:${PM4H_LIB}/common/main/* com.chen.tools.platform.frame.client.Client cfg
#
#
# step3. check result
# res_pia -> save pia file content.
# res_msd_ind -> save indicator missing data file content.
# res_msd_set -> save indicatorset missing data file content.
