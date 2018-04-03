package com.inspur.pmv6.service;

import com.inspur.pmv6.dao.HbaseDao;
import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.StructObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by zhang.yi on 12/20/2017.
 */
public class Persist implements Serializable {

    protected Path tmpFilePath;
    protected Path outputFilePath;
    protected Map<String, String> fields;

    protected String schema;
    protected String hiveTableName;
    protected String tmppath = "hdfs://pm174:8020/tmp/h2h/";
//    protected String tmppath = "/root/zhangyi/storetest";

    protected Logger logger = LoggerFactory.getLogger(Persist.class);
    protected transient Configuration conf = new Configuration();
    protected transient FileSystem fs;

    protected StructObjectInspector structObjectInspector;

    protected static Map<String, ObjectInspector> typeObjectInspector = new HashMap<>();

    static {
        typeObjectInspector.put("int", PrimitiveObjectInspectorFactory.javaIntObjectInspector);
        typeObjectInspector.put("bigint", PrimitiveObjectInspectorFactory.javaLongObjectInspector);
        typeObjectInspector.put("double", PrimitiveObjectInspectorFactory.javaDoubleObjectInspector);
        typeObjectInspector.put("string", PrimitiveObjectInspectorFactory.javaStringObjectInspector);
    }

    private String hbastable = "T_A9854185037742838E0B96BE28894D7C_M15_HBASE";
    private byte[] family = Bytes.toBytes("c");

    public Persist(Map<String, String> fields, String codec, String hiveTableName) throws IOException {

        this.fields = fields;
        this.schema = getSchema();
        this.hiveTableName = hiveTableName;
//        conf.set("hadoop.security.authentication", "Kerberos");
        conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
        conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");
//        UserGroupInformation.setConfiguration(conf);
//        UserGroupInformation.loginUserFromKeytab("hive/indata-172-31-2-56.indata.com@INDATA.COM", "/etc/security/keytabs/hive.service.keytab");
        fs = FileSystem.get(conf);

    }

    public String getSchema() {
        if (null == fields || fields.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder("message example { ");
        for (String field : fields.keySet()) {
            String type = fields.get(field);
            switch (type) {
                case "int":
                    sb.append(" optional int32 ").append(field).append("; ");
                    break;
                case "bigint":
                    sb.append(" optional int64 ").append(field).append("; ");
                    break;
                case "double":
                    sb.append(" optional double ").append(field).append("; ");
                    break;
                case "string":
                    sb.append(" optional binary ").append(field).append(" (UTF8); ");
                    break;
                default:
            }
        }
        sb.append("}");
//        logger.info("The value of schema is: " + sb.toString());
        return sb.toString();
    }

    public void closeResource() throws IOException {
    }

    protected boolean deleteIfExist(Path p) {
        boolean r = true;
        try {
            if(fs == null) {
                conf = new Configuration();
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");
                fs = p.getFileSystem(conf);
            }
            if (fs.exists(p)) {
                logger.info("file exist, now delete it ({}).", p.getName());
                r = fs.delete(p, false);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            r = false;
        }
        return r;
    }

    protected boolean isExists(Path p) {
        boolean r = true;
        try {
            if(fs == null) {
                conf = new Configuration();
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");
                fs = p.getFileSystem(conf);
            }
            r = fs.exists(p);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            r = false;
        }
        return r;
    }

    protected boolean moveFile(Path src, Path dst) {
        boolean r = true;
        try {
            if(fs == null) {
                conf = new Configuration();
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/core-site.xml");
                conf.addResource("/etc/hadoop/2.6.2.0-205/0/hdfs-site.xml");
                fs = src.getFileSystem(conf);
            }
            r = fs.rename(src, dst);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
            r = false;
        }
        return r;
    }

    protected Map<String, List<HiveVO>> encapPia(String endsdt2) {
        long starttime = System.currentTimeMillis();
        List<HiveVO> rList = new ArrayList<>();
        Map<String, List<HiveVO>> mapList = new HashMap<>();
        String[] filePath = new String[50];
        for(int num = 0; num < 50; num ++){
            String tmpFilepath = "/root/zhangyi/generatepia/equipmentmo_9" + String.valueOf(4000+4000*num) + "_" + endsdt2 + ".pia";
            filePath[num] = tmpFilepath;
        }
//        String[] filePath = {"/root/zhangyi/storetest/bigfile/equipmentmo_94000_20180208001500.pia"};
        //从文件中读取数据
        for(int j = 0; j < filePath.length; j++) {
            logger.info("start to process the file:" + filePath[j]);
            File tmpFile1 = new File(filePath[j]);
            if(!tmpFile1.exists()){
                logger.info(filePath[j]+" doesn't exist");
                continue;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(filePath[j])), Charset.forName("UTF-8")))) {
                //判空
                if (reader == null) {
                    return null;
                }
                String line = null;
//                int columnLength = 1118;
                while ((line = reader.readLine()) != null) {
                    String[] columnValue = line.split("\\|");
//                    logger.info("The length of columnValue is: " + columnValue.length);
//                    for(String cV:columnValue){
//                        logger.info("value"+ cV);
//                    }
                    List<String> structFieldNames = new ArrayList<String>(fields.size());
//                    logger.info("fields size"+fields.size());
                    structFieldNames.addAll(fields.keySet());
                    List<Object> fieldValues = new ArrayList<Object>(fields.size());
                    for (int i = 0; i < 8; i++) {
                        if (i == 3) {
                            String val = columnValue[i];
                            if (null == val) {
                                fieldValues.add(i, null);
                            } else {
                                // hbase attr data type is not correct by set it to string, to deal with it.
                                fieldValues.add(i, Long.parseLong(val));
                            }
                        } else {
                            String val = columnValue[i];
                            if (null == val) {
                                fieldValues.add(i, null);
                            } else {
                                // hbase attr data type is not correct by set it to string, to deal with it.
//                                logger.info("start to add value " + val);
                                fieldValues.add(i, Integer.valueOf(val));
                            }
                        }
                    }
                    for (int i = 8; i < 1008; i++) {
                        String val = columnValue[i];
                        if (null == val) {
                            fieldValues.add(i, null);
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add(i, Double.valueOf(val));
                        }
                    }
                    for (int i = 1008; i < 1108; i++) {
                        String val = columnValue[i];
                        if (null == val) {
                            fieldValues.add(i, null);
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add(i, val);
                        }
                    }
                    for (int i = 1108; i < 1118; i++) {
                        String val = columnValue[i];
                        if (null == val) {
                            fieldValues.add((i-1108)*2 + 1108, null);
                            int tempI = (i-1108)*2 + 1108 + 1;
                            fieldValues.add(tempI, null);
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add((i-1108)*2 + 1108, Integer.valueOf(val));
                            int tempI =(i-1108)*2 + 1108 + 1;
                            fieldValues.add(tempI, val);
                        }
                    }
                    HiveVO hiveVO = new HiveVO(structFieldNames,  fieldValues);
                    rList.add(hiveVO);
                }


            } catch (FileNotFoundException e) {
                logger.error("encapPia error");
            } catch (IOException e) {
                logger.error("encapPia error");
            }

        }
        if(rList!=null) {
            mapList.put(String.valueOf(endsdt2), rList);
        }
        return mapList;

    }

    protected Map<String, List<HiveVO>> encapHbase(String endsdt2) {
        long starttime = System.currentTimeMillis();
        //从hbase表中读取数据
        logger.info("Start to scan hbase data, scope is {} to {}",endsdt2, endsdt2);
        HbaseDao hbaseDao = new HbaseDao();
        ResultScanner rs = hbaseDao.getHbaseData(hbastable, endsdt2,endsdt2);
        logger.info("Scan hbase data end, cost {} millseconds.",(System.currentTimeMillis()-starttime) );

        long lastsdt = 0;
        List<HiveVO> rList = new ArrayList<>();
        Map<String, List<HiveVO>> mapList = new HashMap<>();
        //封装List为<每个周期<每个周期内每行(实体)数据>>，对数据按周期分组
//        int o = 0;
        for(Result r : rs) {
            byte[] row = r.getRow();
            int sd = Bytes.toInt(row, 0, 4);
            int st = Bytes.toInt(row, 4, 4);

            long sdt = Long.parseLong(sd+""+st);

            List<String> structFieldNames = new ArrayList<String>(fields.size());
            List<Object> fieldValues = new ArrayList<Object>(fields.size());
            int i = 0;
            for (String field : fields.keySet()) {
                String type = fields.get(field).toLowerCase();

                structFieldNames.add(field);

                byte[] val = r.getValue(family, Bytes.toBytes(field.toUpperCase()));
                if (null == val) {
                    fieldValues.add(i++, null);
                    continue;
                }

                switch (type) {
                    case "int":
                        if (val.length == 4) {
                            fieldValues.add(i++, Bytes.toInt(val));
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add(i++, Integer.valueOf(Bytes.toString(val)));
                        }
                        break;
                    case "bigint":
                        if (val.length == 8) {
                            fieldValues.add(i++, Bytes.toLong(val));
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add(i++, Long.valueOf(Bytes.toString(val)));
                        }
                        break;
                    case "double":
                        if (val.length == 8) {
                            fieldValues.add(i++, Bytes.toDouble(val));
                        } else {
                            // hbase attr data type is not correct by set it to string, to deal with it.
                            fieldValues.add(i++, Double.valueOf(Bytes.toString(val)));
                        }
                        break;
                    case "string":
                        fieldValues.add(i++, Bytes.toString(val));
                        break;
                    default:
                }
            }

            HiveVO hiveVO = new HiveVO(structFieldNames,  fieldValues);

            if(0 == lastsdt) {
                lastsdt = sdt;
            }
            if(lastsdt != sdt) {
                mapList.put(String.valueOf(lastsdt),rList);
                lastsdt = sdt;
                rList = new ArrayList<>();
                mapList = new HashMap<>();
            }
            rList.add(hiveVO);
//            if(o++==3) {
//                break;
//            }
        }
        mapList.put(String.valueOf(lastsdt),rList);
        logger.info("Finish group by startdaytime.");

        return mapList;
    }
}
