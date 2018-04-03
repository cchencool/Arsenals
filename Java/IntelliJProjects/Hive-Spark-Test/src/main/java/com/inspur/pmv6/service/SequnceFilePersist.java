package com.inspur.pmv6.service;

import com.inspur.pmv6.dao.HiveDao;
import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.io.ObjectWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.util.ReflectionUtils;
import org.apache.spark.api.java.function.VoidFunction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang.yi on 12/20/2017.
 */
public class SequnceFilePersist extends Persist implements VoidFunction<String> {

    private String compress;
    private SequenceFile.Writer writer;

    public SequnceFilePersist(Map<String, String> fields, String codec, String hiveTableName) throws IOException, ClassNotFoundException {
        super(fields, codec, hiveTableName);
        getCompressionCodec(codec);
    }

    @Override
    public void call(String endtime) throws Exception {

        Map<String, List<HiveVO>> resultListMap = super.encapPia(endtime);
        long startTime=System.currentTimeMillis();
        int i = 0;
        logger.info("resultListMap size --->" + resultListMap.size());
        conf = new Configuration();
        conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
        conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");

        Text key = new Text();
        ObjectWritable valueser = new ObjectWritable();

        for(Map.Entry<String, List<HiveVO>> entry : resultListMap.entrySet()) {
            String sdt = entry.getKey();
            String sd = sdt.substring(0,8);
            String st = sdt.substring(8);
            logger.info("sd is ----> " + sd);
            logger.info("st is ----> " + st);
            String file = tmppath + hiveTableName + "-" + sd + "-" + st;
            tmpFilePath = new Path(file);
            logger.info("table path is ----> " + file);
            if(isExists(tmpFilePath)) {
                logger.error("The file " + tmpFilePath + " already existed on hdfs.Drop it first");
                deleteIfExist(tmpFilePath);
                logger.info("Drop successfully");
            }

            if(null == this.writer) {
                this.writer = SequenceFile.createWriter(conf, SequenceFile.Writer.keyClass(Text.class),
                        SequenceFile.Writer.valueClass(ObjectWritable.class),
                        SequenceFile.Writer.bufferSize(256*1024),
                        SequenceFile.Writer.compression(SequenceFile.CompressionType.RECORD, (CompressionCodec)ReflectionUtils.newInstance( Class.forName(compress), conf) ),
                        SequenceFile.Writer.file(tmpFilePath)
                );
            }

            List<HiveVO> resultList = resultListMap.get(sdt);
            logger.info("Start to load data into hive This interval is {},  record size : {} .", sdt, resultList.size());
            for(HiveVO ho : resultList) {
                for (int j=0; j < ho.getStructFieldNames().size();) {
                    String field = ho.getStructFieldNames().get(j);
                    Object value = ho.getFieldValues().get(j);
                    key.set(field);
                    valueser.set(value);
                    this.writer.append(key, value);
                }

                logger.info("initialize writer ok ");
                i++;
                if((i%2000)==0) {
                    logger.info("2000 record inserted.");
                }
            }
            closeResource();

            HiveDao hiveDao = new HiveDao();
//            hiveDao.addPartition(hiveTableName,"sd="+sd+",st="+st);
            logger.info("Finished to add partition for hive table");
            String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/"+hiveTableName+"/"+hiveTableName+ "-" + sd + "-" + st;

//            String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/"+hiveTableName+"/sd="+sd+"/st="+st+"/"+hiveTableName+ "-" + sd + "-" + st;
            logger.info("finalOutPutFile={}", finalOutPutFile);
            Path finalFilePath = new Path(finalOutPutFile);
            if (!deleteIfExist(finalFilePath)) {
                logger.error("delete target file failed. skip this one");
            }
            if (!moveFile(tmpFilePath, finalFilePath)) {
                logger.error("mv {} to {} failed.", tmpFilePath, finalOutPutFile);
            }
            hiveDao.refreshTable(hiveTableName);
            long endTime=System.currentTimeMillis();
            logger.info("Write to file success, cost time: " + (endTime - startTime));
        }

    }


    /**
     * 根据传入的压缩参数转换为对应的压缩算法
     * @param codec
     */
    public void getCompressionCodec(String codec) {

        switch (codec) {
            case "snappy":
                compress = "org.apache.hadoop.io.compress.SnappyCodec";
                break;
            case "gzip":
                compress = "org.apache.hadoop.io.compress.GzipCodec";
                break;
            case "lzo":
                compress = "com.hadoop.compression.lzo.LzopCodec";
                break;
            default:
                compress = "org.apache.hadoop.io.compress.SnappyCodec";
        }

    }

    public void closeResource() throws IOException {
        this.writer.close();
    }
}
