package com.inspur.pmv6.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanDescriptor;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.hadoop.security.UserGroupInformation;

import javax.sql.DataSource;

/**
 * Created by zhang.yi on 12/18/2017.
 */
public class HiveDao {

    private Properties prop = null;

    private Logger logger = LoggerFactory.getLogger(HiveDao.class);

//    private Connection mysqlconn;

    private DataSource ds = null;

    private Connection hiveconn;

    /**
     * 初始化hive和mysql的连接配置
     */
    public HiveDao() {

        // 加载配置
        prop = new Properties();
        try {
            prop.load(HiveDao.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

//        String hiveUrl = "jdbc:hive2://" + indata_hive_ip
//                + ":10000/default;principal=" + indata_hive_principal
//                + ";java.security.krb5.conf=" + indata_krb5conf;
//
//        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
//        conf.set("hadoop.security.authentication", "Kerberos");
//        System.setProperty("java.security.krb5.conf", indata_krb5conf);
//        try {
//            UserGroupInformation.setConfiguration(conf);
//            UserGroupInformation.loginUserFromKeytab(indata_hive_principal, indata_hive_keytab);
//        } catch (Exception e) {
//            logger.error("1001", "HiveTest error.hiveURL:" + hiveUrl, e);
//        }

        // hive配置
        String hiveDriverClass = prop.getProperty("hive.driveClass");
        String hiveUrl = prop.getProperty("hive.url");
        String username = prop.getProperty("hive.username");
        String userpwd = prop.getProperty("hive.userpwd");

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {

            cpds.setDriverClass(hiveDriverClass);
            cpds.setJdbcUrl(hiveUrl);
            cpds.setUser(username);
            cpds.setPassword(userpwd);

            cpds.setMinPoolSize(1);
            cpds.setAcquireIncrement(1);
            cpds.setMaxPoolSize(2);

//            cpds.setCheckoutTimeout(100);

            this.ds = cpds;

        } catch (PropertyVetoException e) {
            logger.error(e.getMessage(), e);
        }

//        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
//        conf.set("hadoop.security.authentication", "Kerberos");
//        //mysql配置
//        String mysqlUrl = prop.getProperty("mysql.url");
//        String mysqlusrname = prop.getProperty("mysql.username");
//        String mysqlusrpwd = prop.getProperty("mysql.userpwd");
//        String url = mysqlUrl + "?useUnicode=true&characterEncoding=UTF8";

//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            UserGroupInformation.setConfiguration(conf);
//            UserGroupInformation.loginUserFromKeytab("hive/indata-172-31-2-56.indata.com@INDATA.COM", "/etc/security/keytabs/hive.service.keytab");
//            Class.forName(hiveDriverClass);
//        } catch (ClassNotFoundException e) {
//            logger.error(e.getMessage(), e);
//        }
//        catch (IOException e) {
//            logger.error(e.getMessage(), e);
//        }

//        try {
////            this.mysqlconn = DriverManager.getConnection(url, mysqlusrname, mysqlusrpwd);
//            logger.info("hivedao parameters : {} {} {}", hiveUrl, username, userpwd);
//            System.out.println("hivedao parameters : " +  hiveUrl + " " + username + " " +userpwd);
//            hiveconn = DriverManager.getConnection(hiveUrl, username, userpwd);
//            if(null == hiveconn) {
//                System.out.println("it's nuill");
//            }
//        } catch (SQLException e) {
//            logger.error(e.getMessage(), e);
//        }
    }

    protected Connection openConn(){
        if (this.ds != null) {
            try {
                return ds.getConnection();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return  null;
    }

    protected void closeConn(Connection conn) throws  SQLException{
        if (conn != null) {
            conn.close();
        }
    }

    public boolean dropTable(String tableName) {

        String sql = "drop table if exists "+tableName;
        return executesql(sql);

    }

    public boolean executesql(String sql) {
        logger.info("start to execute the sql :" + sql);
        long startTime=System.currentTimeMillis();
        System.out.println(sql);
        PreparedStatement ps = null;
        boolean result = true;
        Connection conn = openConn();
        try {
            ps = conn.prepareStatement(sql);
            ps.execute();
            long endTime=System.currentTimeMillis();
            logger.info("executesql success, cost time: " + (endTime - startTime));
        } catch (SQLException e) {
            logger.error("ERROR SQL: {}", sql);
            logger.error(e.getMessage(), e);
            result = false;
        } finally {
            closeResouces(conn, ps, null);
        }

        return result;
    }

    public Map<String, String> executesqlAndGetResult(String sql) {
        logger.info("start to execute the sql :" + sql);
        long startTime=System.currentTimeMillis();
        System.out.println(sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        Map<String, String> result = new HashMap<String, String>();
        Connection conn = openConn();
        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String value = rs.getString("value");
                result.put(id, value);
            }

            long endTime=System.currentTimeMillis();
            logger.info("executesql success, cost time: " + (endTime - startTime));
        } catch (SQLException e) {
            logger.error("ERROR SQL: {}", sql);
            logger.error(e.getMessage(), e);
        } finally {
            closeResouces(conn, ps, rs);
        }

        return result;
    }

    private void closeResouces(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (conn != null) {
            try {
                closeConn(conn);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }


    public boolean createTable(String tableName, Map<String, String> fields, String hdfsFileFormat, String compressMode) {
        StringBuilder sql = new StringBuilder();
        sql.append("create table  if not exists " + tableName + " (");
        for(Map.Entry<String, String> field : fields.entrySet()) {
            sql.append(field.getKey()).append(" ").append(field.getValue()).append(",");
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(") partitioned by ( sd int, st int)");
//        sql.append(") ");
        if("Parquet".equals(hdfsFileFormat)) {
            sql.append("stored as parquet tblproperties (\"parquet.compression\"=\"snappy\")");
//            sql.append("stored as parquet");
        } else if("ORC".equals(hdfsFileFormat)) {
//            sql.append("stored as orc tblproperties (\"orc.compression\"=\"snappy\")");
            sql.append("stored as orc");
        } else if("SequenceFIle".equals(hdfsFileFormat)) {
            sql.append("stored as sequencefile");
        } else if("textfile".equals(hdfsFileFormat)) {
            sql.append("stored as textfile");
        }

        return executesql(sql.toString());

    }

    public void closeResource() {
        if(hiveconn!=null) {
            try {
                hiveconn.close();
            } catch (SQLException e) {
                logger.error(e.getMessage(),e);
            }
        }
    }

    public void refreshTable(String tableName) {
        String sql = "refresh table " + tableName;
        executesql(sql);
    }

    public void addPartition(String tableName, String partitionName) {
        String sql = "alter table " + tableName + " add if not exists partition(" + partitionName + ")";
        executesql(sql);
    }


    public boolean executeGroupbysql(String sql) {
        logger.info("start to execute the Groupby sql :" + sql);
        long startTime=System.currentTimeMillis();
       // System.out.println(sql);
        PreparedStatement ps = null;
        boolean result = true;
        try {
            ps = this.hiveconn.prepareStatement(sql);
            ps.executeQuery();
            long endTime=System.currentTimeMillis();
            logger.info("executeGroupbysql success, cost time: " + (endTime - startTime));
        } catch (SQLException e) {
            logger.error("ERROR SQL: {}", sql);
            logger.error(e.getMessage(), e);
            result = false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }

        return result;
    }
//
//    public void sortBy(String outSql){
//        long startTime=System.currentTimeMillis();
//        Connection conn = null;
//        PreparedStatement ps = null;
//        ResultSet rs = null;
//        try {
//            conn=HiveDaoUtils.getConn();
//            String sql = outSql;
//            ps = conn.prepareStatement(sql);
//            rs = ps.executeQuery();
//            long endTime=System.currentTimeMillis();
//            logger.info("sortby success, cost time: " + (endTime - startTime));
//        } catch (SQLException e) {
//            logger.error(e.getMessage(),e);
//        } finally {
//            HiveDaoUtils.free(rs,ps,conn);
//        }
//    }

}
