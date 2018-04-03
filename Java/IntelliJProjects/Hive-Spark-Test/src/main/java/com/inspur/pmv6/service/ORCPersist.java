package com.inspur.pmv6.service;

import com.inspur.pmv6.dao.HiveDao;
import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hive.ql.io.orc.CompressionKind;
import org.apache.hadoop.hive.ql.io.orc.OrcFile;
import org.apache.hadoop.hive.ql.io.orc.Writer;
import org.apache.hadoop.hive.ql.io.parquet.write.DataWritableWriteSupport;
import org.apache.hadoop.hive.serde2.io.ParquetHiveRecord;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.spark.api.java.function.VoidFunction;
import parquet.hadoop.ParquetWriter;
import parquet.schema.MessageTypeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang.yi on 12/20/2017.
 */
public class ORCPersist extends Persist implements VoidFunction<String> {

    private CompressionKind compress;
    private Writer writer;

    public ORCPersist(Map<String, String> fields, String codec, String hiveTableName) throws IOException {
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

            List<HiveVO> resultList = resultListMap.get(sdt);
            logger.info("Start to load data into hive This interval is {},  record size : {} .", sdt, resultList.size());
            long stage3startTime = System.currentTimeMillis();
            for(HiveVO ho : resultList) {
//                long stage1startTime = System.currentTimeMillis();
                List<ObjectInspector> structFieldObjectInspectors = new ArrayList<>(fields.size());
                for (int j=0; j < ho.getStructFieldNames().size();) {
                    String field = ho.getStructFieldNames().get(j);
                    ObjectInspector objectInspector = typeObjectInspector.get(fields.get(field));
                    structFieldObjectInspectors.add(j++, objectInspector);
                }
//                logger.info("structFieldObjectInspectors size {}", structFieldObjectInspectors.size());
                structObjectInspector = ObjectInspectorFactory
                        .getStandardStructObjectInspector(ho.getStructFieldNames(), structFieldObjectInspectors);
                if(null == this.writer) {
                    this.writer = OrcFile.createWriter(tmpFilePath, OrcFile.writerOptions(conf)
                            .stripeSize(256L*1024*1024)
                            .bufferSize(256*1024)
                            .rowIndexStride(10000)
                            .compress(compress)
                            .inspector(structObjectInspector)
                    );
                    logger.info("initialize writer ok ");
                }
//                long stage1endTime = System.currentTimeMillis();
//                logger.info("ORCstage1 cost time: " + (stage1endTime - stage1startTime));
//                long stage2startTime = System.currentTimeMillis();
                this.writer.addRow(ho.getFieldValues());
//                long stage2endTime = System.currentTimeMillis();
//                logger.info("ORCstage2 cost time: " + (stage2endTime - stage2startTime));
                i++;
                if((i%2000)==0) {
                    logger.info("2000 record inserted.");
                }
            }
            closeResource();
            long stage3endTime = System.currentTimeMillis();
            logger.info("ORCstage3 cost time: " + (stage3endTime - stage3startTime));
//            HiveDao hiveDao = new HiveDao();
////            hiveDao.addPartition(hiveTableName,"sd="+sd+",st="+st);
////            logger.info("Finished to add partition for hive table");
//            long copyFileStartTime = System.currentTimeMillis();
//            String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/"+hiveTableName+"/"+hiveTableName+ "-" + sd + "-" + st;
//
////            String finalOutPutFile = "hdfs://pm174:8020/apps/hive/warehouse/"+hiveTableName+"/sd="+sd+"/st="+st+"/"+hiveTableName+ "-" + sd + "-" + st;
//            logger.info("finalOutPutFile={}", finalOutPutFile);
//            Path finalFilePath = new Path(finalOutPutFile);
//            if (!deleteIfExist(finalFilePath)) {
//                logger.error("delete target file failed. skip this one");
//            }
//            if (!moveFile(tmpFilePath, finalFilePath)) {
//                logger.error("mv {} to {} failed.", tmpFilePath, finalOutPutFile);
//            }
//            hiveDao.refreshTable(hiveTableName);
//            long copyFileEndTime = System.currentTimeMillis();
//            logger.info("ORC copyFile and refreshTable cost time: " + (copyFileEndTime - copyFileStartTime));

        }


    }

    /**
     * 根据传入的压缩参数转换为对应的压缩算法
     * @param codec
     */
    public void getCompressionCodec(String codec) {

        switch (codec) {
            case "snappy":
                compress = CompressionKind.SNAPPY;
                break;
            case "gzip":
                compress = CompressionKind.ZLIB;
                break;
            case "lzo":
                compress = CompressionKind.LZO;
                break;
            default:
                compress = CompressionKind.NONE;
        }

    }

    public void closeResource() throws IOException {
        this.writer.close();
    }
}
