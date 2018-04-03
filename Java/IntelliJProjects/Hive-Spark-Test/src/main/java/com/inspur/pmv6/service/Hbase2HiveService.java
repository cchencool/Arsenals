package com.inspur.pmv6.service;

import com.inspur.pmv6.dao.HbaseDao;
import com.inspur.pmv6.dao.HiveDao;
import com.inspur.pmv6.vo.Columns;
import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhang.yi on 12/18/2017.
 */
public class Hbase2HiveService {

    private Logger logger = LoggerFactory.getLogger(Hbase2HiveService.class);
    private Calendar ca = Calendar.getInstance();


    public void startwork(String hdfsFileFormat, String compressMode, String startsdt, String endsdt) {

        String hiveTableName = "t_hivetest_"+("".equals(hdfsFileFormat)?"orghdfs":hdfsFileFormat).toLowerCase()+("".equals(compressMode)?"nocompress":compressMode).toLowerCase() + "_m15";
        logger.info("input table is " + hiveTableName);

        //如果表存在，先drop
        HiveDao hiveDao = new HiveDao();
//        logger.info("drop table {} start..",hiveTableName);
//        boolean dropresult =  hiveDao.dropTable(hiveTableName);
//        if(!dropresult) {
//            logger.error("drop table failed.");
////            return;
//        }
//        logger.info("drop table {} end..",hiveTableName);

        //重建表
        logger.info("create table {} start..",hiveTableName);
        LinkedHashMap<String, String> fields = Columns.getColumns();
        boolean createresult =  hiveDao.createTable(hiveTableName, fields, hdfsFileFormat, compressMode);
        if(!createresult) {
            logger.error("create table failed.");
            return;
        }
        logger.info("create table {} end..",hiveTableName);

        hiveDao.closeResource();

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            ca.setTime(format.parse(startsdt));
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }

        SparkService sparkexec = new SparkService();
        String endsdt2 = startsdt;
        List<String> timescope = new ArrayList<>();
        while(Long.parseLong(endsdt2) <= Long.parseLong(endsdt)) {
            timescope.add(endsdt2);
            ca.add(Calendar.MINUTE, 15);
            endsdt2 = format.format(ca.getTime());
        }
        sparkexec.submitIntoHive(timescope, fields, hdfsFileFormat, compressMode, hiveTableName);

    }
}
