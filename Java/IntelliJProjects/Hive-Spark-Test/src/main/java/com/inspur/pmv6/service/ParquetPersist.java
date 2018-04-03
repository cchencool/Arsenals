package com.inspur.pmv6.service;

import com.inspur.pmv6.dao.HbaseDao;
import com.inspur.pmv6.dao.HiveDao;
import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hive.ql.io.parquet.write.DataWritableWriteSupport;
import org.apache.hadoop.hive.serde2.io.ParquetHiveRecord;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.spark.api.java.function.VoidFunction;
import parquet.hadoop.ParquetWriter;
import parquet.hadoop.metadata.CompressionCodecName;
import parquet.schema.MessageTypeParser;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by zhang.yi on 12/19/2017.
 */
public class ParquetPersist extends Persist implements Serializable, VoidFunction<String>{

    private CompressionCodecName compressionCodec;
    private ParquetWriter writer = null;
    private Path tmpFilePath;

    public ParquetPersist(Map<String, String> fields, String codec, String hiveTableName) throws IOException {
        super(fields, codec, hiveTableName);
        this.getCompressionCodec(codec);
        logger.info("compress mode is ----> " + codec);
//        logger.info("schema is ----> " + schema);

    }

    @Override
    public void call(String endtime) throws Exception {

        Map<String, List<HiveVO>> resultListMap = super.encapPia(endtime);
        if(resultListMap!=null) {

            int i = 0;
            logger.info("resultListMap size --->" + resultListMap.size());
            if (null == schema) {
                schema = getSchema();
            }
            conf = new Configuration();
            conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
            conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");
            DataWritableWriteSupport.setSchema(MessageTypeParser.parseMessageType(schema), conf);
            for (Map.Entry<String, List<HiveVO>> entry : resultListMap.entrySet()) {

                String sdt = entry.getKey();
                String sd = sdt.substring(0, 8);
                String st = sdt.substring(8);
                logger.info("sd is ----> " + sd);
                logger.info("st is ----> " + st);
                String file = tmppath + hiveTableName + "-" + sd + "-" + st;
                tmpFilePath = new Path(file);
                logger.info("table path is ----> " + file);
                if (isExists(tmpFilePath)) {
                    logger.error("The file " + tmpFilePath + " already existed on hdfs.Drop it first");
                    deleteIfExist(tmpFilePath);
                    logger.info("Drop successfully");
                }
                long writerInitstartTime = System.currentTimeMillis();
                if (null == writer) {
                    writer = new ParquetWriter(tmpFilePath, new DataWritableWriteSupport() {
                        @Override
                        public WriteContext init(Configuration configuration) {
                            if (configuration.get(DataWritableWriteSupport.PARQUET_HIVE_SCHEMA) == null) {
                                configuration.set(DataWritableWriteSupport.PARQUET_HIVE_SCHEMA, schema);
                            }
                            return super.init(configuration);
                        }
                    }, compressionCodec, 128 * 1024 * 1024, 1024 * 1024);
                }
                long writerInitendTime = System.currentTimeMillis();

                logger.info("initialize writer ok, cost time: "+ (writerInitstartTime - writerInitendTime));
                List<HiveVO> resultList = resultListMap.get(sdt);
                logger.info("Start to load data into hive This interval is {},  record size : {} .", sdt, resultList.size());
                long stage3startTime = System.currentTimeMillis();
                for (HiveVO ho : resultList) {
//                    long stage1startTime = System.currentTimeMillis();
                    List<ObjectInspector> structFieldObjectInspectors = new ArrayList<>(fields.size());
                    for (int j = 0; j < ho.getStructFieldNames().size(); ) {
                        String field = ho.getStructFieldNames().get(j);
//                    logger.info("The field of HiveVO is: " + field + " " + fields.get(field));
                        Object value = ho.getFieldValues().get(j);
//                    logger.info("The value of HiveVO is: " + value + " " + value.getClass().getName());
                        if (null == value) {
                            structFieldObjectInspectors.add(j++, PrimitiveObjectInspectorFactory.javaVoidObjectInspector);
                            continue;
                        }
                        ObjectInspector objectInspector = typeObjectInspector.get(fields.get(field));
                        structFieldObjectInspectors.add(j++, objectInspector);
                    }
//                    logger.info("structFieldObjectInspectors size {}", structFieldObjectInspectors.size());
                    structObjectInspector = ObjectInspectorFactory
                            .getStandardStructObjectInspector(ho.getStructFieldNames(), structFieldObjectInspectors);
                    ParquetHiveRecord phr = new ParquetHiveRecord(ho.getFieldValues(), structObjectInspector);
//                    long stage1endTime = System.currentTimeMillis();
//                    logger.info("Par stage1 cost time: " + (stage1endTime - stage1startTime));
//                    long stage2startTime = System.currentTimeMillis();
                    writer.write(phr);
//                    long stage2endTime = System.currentTimeMillis();
//                    logger.info("Par stage2 cost time: " + (stage2endTime - stage2startTime));
                    i++;
                    if ((i % 2000) == 0) {
                        logger.info("2000 record inserted.");
                    }
                }
                closeResource();
                long stage3endTime = System.currentTimeMillis();
                logger.info("Par stage3 cost time: " + (stage3endTime - stage3startTime));
                HiveDao hiveDao = new HiveDao();
                hiveDao.addPartition(hiveTableName, "sd=" + sd + ",st=" + st);
                logger.info("Finished to add partition for hive table");
//
                String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/" + hiveTableName + "/sd=" + sd + "/st=" + st + "/" + hiveTableName + "-" + sd + "-" + st;
                long copyFileStartTime = System.currentTimeMillis();
//            String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/"+hiveTableName+"/"+hiveTableName+ "-" + sd + "-" + st;
                logger.info("finalOutPutFile={}", finalOutPutFile);
                Path finalFilePath = new Path(finalOutPutFile);
                if (!deleteIfExist(finalFilePath)) {
                    logger.error("delete target file failed. skip this one");
                }
                if (!moveFile(tmpFilePath, finalFilePath)) {
                    logger.error("mv {} to {} failed.", tmpFilePath, finalOutPutFile);
                }
                hiveDao.refreshTable(hiveTableName);
//                long endTime = System.currentTimeMillis();
//                logger.info("Write to file success, cost time: " + (endTime - startTime));
                long copyFileEndTime = System.currentTimeMillis();
                logger.info("Par copyFile and refreshTable cost time: " + (copyFileEndTime - copyFileStartTime));
            }
        }
    }

    /**
     * 根据传入的压缩参数转换为对应的压缩算法
     * @param codec
     */
    public void getCompressionCodec(String codec) {

        switch (codec) {
            case "snappy":
                compressionCodec = CompressionCodecName.SNAPPY;
                break;
            case "gzip":
                compressionCodec = CompressionCodecName.GZIP;
                break;
            case "lzo":
                compressionCodec = CompressionCodecName.LZO;
                break;
            default:
                compressionCodec = CompressionCodecName.UNCOMPRESSED;
        }

    }

    public void closeResource() throws IOException {
        this.writer.close();
        this.writer = null;
    }

}
