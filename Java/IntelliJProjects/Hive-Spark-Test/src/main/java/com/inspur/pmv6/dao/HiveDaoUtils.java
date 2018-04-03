package com.inspur.pmv6.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public  class HiveDaoUtils {
//    private static Properties prop;
//    private static Connection conn;
//    private static Logger logger;
//    private static String hiveUrl;
//    private static String username;
//    private static String userpwd;
//
//
//    private HiveDaoUtils(){
//    }
//    static{
//        prop = new Properties();
//        logger = LoggerFactory.getLogger(HiveDaoUtils.class);
//        try {
//            prop.load(HiveDao.class.getResourceAsStream("/config.properties"));
//        } catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }
//
//        // hive配置
//        hiveUrl = prop.getProperty("hive.url");
//        username = prop.getProperty("hive.username");
//        userpwd = prop.getProperty("hive.userpwd");
//
////        //mysql配置
////        String mysqlUrl = prop.getProperty("mysql.url");
////        String mysqlusrname = prop.getProperty("mysql.username");
////        String mysqlusrpwd = prop.getProperty("mysql.userpwd");
////        String url = mysqlUrl + "?useUnicode=true&characterEncoding=UTF8";
//
//        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
//            Class.forName("org.apache.hive.jdbc.HiveDriver");
//        } catch (ClassNotFoundException e) {
//            logger.error(e.getMessage(), e);
//        }
//    }
//    public static Connection getConn() throws SQLException {
//        Connection hiveconn = null;
//        try {
////            this.mysqlconn = DriverManager.getConnection(url, mysqlusrname, mysqlusrpwd);
//
//            logger.info("hivedao parameters : {} {} {}", hiveUrl, username, userpwd);
//            System.out.println("hivedao parameters : " +  hiveUrl + " " + username + " " +userpwd);
//            hiveconn = DriverManager.getConnection(hiveUrl, username, userpwd);
//            if(null == hiveconn) {
//                logger.error("Hiveconn is NULL");
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//        }
//        return hiveconn;
//    }
//    public static void free(ResultSet rs, Statement st, Connection conn) {
//        try {
//            if (rs != null) {
//                rs.close();
//            }
//        } catch(SQLException e){
//            e.printStackTrace();
//        }finally{
//            try {
//                if (st != null) {
//                    st.close();
//                }
//            } catch(SQLException e){
//                e.printStackTrace();
//            }finally{
//                try {
//                    if (conn != null) {
//                        conn.close();
//                    }
//                } catch(SQLException e){
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
