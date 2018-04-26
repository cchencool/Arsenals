package com.inspur.httpabout;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );


        String result = doHttpPost("{name=\"2333\"}", "http://localhost:8080/greeting?name=23");
//        String result = doHttpPost("{name=\"123\"", "http://localhost:8080/greeting?name=\"2333\"");


        System.out.println(result);


        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:8080/greeting?name=HttpClient");


        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            InputStream content = response.getEntity().getContent();
            byte[] bis = IOUtils.toByteArray(content);
            String responseStr = new String(bis, "UTF-8");
            System.out.println(responseStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发送Http post请求
     *
     * @param xmlInfo
     *            json转化成的字符串
     * @param URL
     *            请求url
     * @return 返回信息
     */
    public static String doHttpPost(String xmlInfo, String URL) {
        System.out.println("发起的数据:" + xmlInfo);
        byte[] xmlData = xmlInfo.getBytes();
        InputStream instr = null;
        java.io.ByteArrayOutputStream out = null;
        try {
            java.net.URL url = new URL(URL);
            URLConnection urlCon = url.openConnection();
            urlCon.setDoOutput(true);
            urlCon.setDoInput(true);
            urlCon.setUseCaches(false);
            urlCon.setRequestProperty("content-Type", "application/json");
            urlCon.setRequestProperty("charset", "utf-8");
            urlCon.setRequestProperty("Content-length", String.valueOf(xmlData.length));
            System.out.println(String.valueOf(xmlData.length));
            DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());
            printout.write(xmlData);
            printout.flush();
            printout.close();
            instr = urlCon.getInputStream();
            byte[] bis = IOUtils.toByteArray(instr);
            String ResponseString = new String(bis, "UTF-8");
            if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
                System.out.println("返回空");
            }
            System.out.println("返回数据为:" + ResponseString);
            return ResponseString;

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        } finally {
            try {
                out.close();
                instr.close();

            } catch (Exception ex) {
                return "0";
            }
        }
    }
}
