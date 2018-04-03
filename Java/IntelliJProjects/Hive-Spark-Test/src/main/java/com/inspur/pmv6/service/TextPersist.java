package com.inspur.pmv6.service;

import com.inspur.pmv6.vo.HiveVO;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.io.Text;

import org.apache.spark.api.java.function.VoidFunction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang.yi on 12/21/2017.
 */
public class TextPersist extends Persist implements  VoidFunction<String>{

    private String compress;
    private Text csv = new Text();
    private final String delmited = ",";
    private OutputStream out;

    public TextPersist(Map<String, String> fields, String codec, String hiveTableName) throws IOException, ClassNotFoundException {
        super(fields, codec,hiveTableName);
        getCompressionCodec(codec);
        OutputStream out =  fs.create(tmpFilePath);

    }

    @Override
    public void call(String endtime) throws Exception {
//        Map<String, List<HiveVO>> resultListMap = super.encapPia(endtime);
//        for (Map.Entry<String, List<HiveVO>> entry : resultListMap.entrySet()) {
//
//            String sdt = entry.getKey();
//            String sd = sdt.substring(0, 8);
//            String st = sdt.substring(8);
//            int i = 1;
//            StringBuilder line = null;
//
//            for (Result r : results) {
//                line = new StringBuilder();
//                super.initDataSource(r);
//                if (0 == i) {
//                    for (String col : structFieldNames) {
//                        line.append(col).append(delmited);
//                    }
//                    line.deleteCharAt(line.length() - 1);
//                    line.append("\n");
//                }
//                for (Object value : fieldValues) {
//                    line.append(String.valueOf(value));
//                }
//                line.deleteCharAt(line.length() - 1);
//                line.append("\n");
//            }
//
//            out.write(line.toString().getBytes());
//        }
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
        this.out.close();
    }

}
