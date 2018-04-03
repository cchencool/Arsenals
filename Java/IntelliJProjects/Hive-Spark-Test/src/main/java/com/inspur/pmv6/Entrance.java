package com.inspur.pmv6;


import com.inspur.pmv6.dao.HiveDao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

/**
 * Created by zhang.yi on 12/18/2017.
 */
public class Entrance {

    public static void main(String[] args) {

//        Logger  logger = LoggerFactory.getLogger(Entrance.class);
//        String hdfsFileFormat = "Parquet";
//        String compressMode = "snappy";
//        String startsdt="20171221000000";
//        String endsdt="20171221000000";
//
//        logger.info("Program start..");
//        if(args.length <4 ) {
//            logger.error("Input parameter less than 4. Please input 4 parameters: hdfsformat, compressmode, startdaytime, enddaytime");
//            System.exit(1);
//
//        } else {
//            hdfsFileFormat = args[0];
//            compressMode = args[1];
//            startsdt = args[2];
//            endsdt = args[3];
//        }
//        logger.info("Input parameters are: {} {} {} {}", hdfsFileFormat, compressMode, startsdt, endsdt);
//
//        Hbase2HiveService h2h = new Hbase2HiveService();
//        h2h.startwork(hdfsFileFormat, compressMode, startsdt, endsdt);


        myTestCodes();
    }

    private static void myTestCodes() {
        ConcurrentHashMap<Integer, String> sqlMap = new ConcurrentHashMap<Integer, String>(10);

//        sqlMap.put(1, "select t.id as id, t.value as value from test_table t");
//        sqlMap.put(2, "select count(1) as id, count(*)+10 as value from test_table");
//        sqlMap.put(3, "insert into test_table2 values ($1, $2, $3)");
//        sqlMap.put(4, "insert into test_table values ($1, $2)");
//        sqlMap.put(5, "insert into test_table3 partition (day=$3) values ($1, $2)");

//        String sql_example = "insert into test_table_int values ($1, $2, $3)";


        // create table test_table_double ( startday bigint, starttime bigint, value double);

//        insert into test_table_double values (20180415, 0, 0);
//        insert into test_table_double values (20180413, 0, 0);
//        insert into test_table_double values (20180412, 0, 0);
//        insert into test_table_double values (20180411, 0, 0);

        String sql_example = "insert into test_table_double values ($1, $2, $3)";

        HiveDao hiveDao = new HiveDao();
//        hiveDao.executesql("insert into test_table values ('4', '5')");

        GregorianCalendar gc = new GregorianCalendar();
        Date date = new Date();
        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);
        gc.setTime(date);

        // 造十天数据

        IntStream.range(1, 10).forEach(i -> {
            gc.add(Calendar.DATE, 1);
            IntStream.range(1, 24).forEach(j ->
                {
                    gc.add(Calendar.HOUR, 1);
                    String sql = sql_example;
                    sql = sql.replace("$1", (new SimpleDateFormat("yyyyMMdd")).format(gc.getTime()));
                    sql = sql.replace("$2", (new SimpleDateFormat("hhmmss")).format(gc.getTime()));
                    sql = sql.replace("$3", String.valueOf(Math.random()*100));
                    System.out.println(hiveDao.executesql(sql));
                });
        });


        // select t1.* from (select t.*, percent_rank() over (order by t.startday asc, t.value desc) as rank from test_table_int t) t1;

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//
//        executorService.submit( () -> {
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(1)));
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(2)));
//            return;
//        });
//
//        executorService.submit( () -> {
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(1)));
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(2)));
//            return;
//        });
//
//        executorService.submit( () -> {
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(1)));
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(2)));
//            return;
//        });
//
//        executorService.submit( () -> {
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(1)));
//            System.out.println(hiveDao.executesqlAndGetResult(sqlMap.get(2)));
//            return;
//        });
//
//        executorService.shutdown();
//        if (executorService.isTerminated()) {
//            System.exit(0);
//        }
    }

}
