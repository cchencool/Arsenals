package com.inspur.pmv6.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by zhang.yi on 12/18/2017.
 */
public class HbaseDao {

    private static Configuration conf;
    private static Connection conn;
    private static Logger logger;

    static {
        conf = HBaseConfiguration.create();
        conf.addResource("/usr/hdp/2.6.2.0-205/hbase/conf/hbase-site.xml");
        try {
            conn = ConnectionFactory.createConnection(conf);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

    }

    public ResultScanner getHbaseData(String tableName, String startsdt, String endsdt) {

        ResultScanner rsscanner = null;

        try {
            Table table = conn.getTable(TableName.valueOf(tableName));
            byte[] startrow = initialMark(startsdt,0);
            byte[] endrow = initialMark(endsdt,Integer.MAX_VALUE);
            Scan scan = new Scan();
            scan.setCaching(2000);
            scan.setStartRow(startrow);
            scan.setStopRow(endrow);
            rsscanner = table.getScanner(scan);

        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        return rsscanner;
    }

    public byte[] initialMark(String sdt, int moentity) {

        ByteBuffer buf = ByteBuffer.allocate(Integer.BYTES+Integer.BYTES+Integer.BYTES);
        int sd = Integer.parseInt(sdt.substring(0,8));
        int st = Integer.parseInt(sdt.substring(8,14));
        buf.putInt(sd);
        buf.putInt(st);
        buf.putInt(moentity);

        return buf.array();

    }

}
