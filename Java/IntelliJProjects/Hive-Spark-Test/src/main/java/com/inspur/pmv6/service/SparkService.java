package com.inspur.pmv6.service;

import com.inspur.pmv6.vo.HiveVO;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.slf4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by zhang.yi on 12/19/2017.
 */
public class SparkService {

    private SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("hivetest");

    private Logger logger = org.slf4j.LoggerFactory.getLogger(SparkService.class);
    private JavaSparkContext sc = new JavaSparkContext(conf);


    public void submitIntoHive(List<String> rList, Map<String, String> fields, String hdfsFileFormat, String compress, String hiveTableName) {

        JavaRDD<String> rRDD =  sc.parallelize(rList);
//        JavaRDD<String> rRDD =  sc.parallelize(rList,16);
        Persist persist = null;
        try {
            logger.info("hdfs mode is  ----->"+hdfsFileFormat);
            switch (hdfsFileFormat) {
                case "Parquet":
                    persist = new ParquetPersist(fields, compress, hiveTableName);
                    rRDD.foreach((ParquetPersist)persist);
                    break;
                case "ORC":
                    persist = new ORCPersist(fields, compress,hiveTableName);
                    rRDD.foreach((ORCPersist)persist);
                    break;
                case "SequenceFile":
                    persist = new SequnceFilePersist(fields, compress, hiveTableName);
                    rRDD.foreach((SequnceFilePersist)persist);
                    break;
//                case "textfile":
//                    persist = new TextPersist(fields, compress);
//                    rRDD.foreach((TextPersist)persist);
//                    break;
            }


        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }

    }
}
