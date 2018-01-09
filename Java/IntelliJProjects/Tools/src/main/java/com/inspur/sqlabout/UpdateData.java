package com.inspur.sqlabout;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateData {
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@10.110.2.156:1522:acrosspm";
    private static final String user = "pm4h_ad";
    private static final String password = "ACROSS_ad_2013";

    private static final String system_user = "system";//"pm4h_ad";  
    private static final String system_password = "iNOC_oper_001";//"ACROSS_ad_2013";

    public static void main(String[] args) throws InterruptedException {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            String selfId = getSessionID(stmt);
            System.out.println("connect success!...session id : " + selfId);

//			killSessioin(selfId);

//			String sql = "select * from pm4h_ad.rpt_tmptable_index t where t.tablename = 'TMP1_1487570902118_40' for update";
//			stmt.executeQuery(sql);
//			System.out.println("execute select sql success!...");

            String sql = "select * from pm4h_ad.cfg_parameter t where t.parameterid like 'Export.EXCEL07.MAXCOUNT'";
            int n = 335965;

            String start = getCurrentTimeMillisec();
            System.out.println("start: " + start);
            for (int i = 0; i < n / 100; i++) {
                // sleep 0.01ms
//				if (i % 100 == 0) {					
//					Thread.sleep(1);
//				}
                stmt.executeQuery(sql);
            }
            String end = getCurrentTimeMillisec();
            System.out.println("end: " + end);
            System.out.println("cost: " + getDiffSSSs(end, start));


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//		catch (InterruptedException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
        finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
//				e.printStackTrace();
                System.out.println("your session has already been killed");
            }
        }
    }


    public static String getSessionID(Statement stmt) throws SQLException {
        String id = "";
        String idSQL = "select sid||','||serial# as id from v$session where audsid=userenv('sessionid')";
        ResultSet rs = stmt.executeQuery(idSQL);
        while (rs.next()) {
            id = rs.getString("ID");
            System.out.println("ID : " + id);
        }
        return id;
    }


    public static void killSessioin(String id) {
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, system_user, system_password);
            stmt = conn.createStatement();
            String selfId = getSessionID(stmt);
            System.out.println("killSessioin connect success!...session id : " + selfId);

            String killSQL = "alter system kill session '" + id + "'";
            stmt.executeUpdate(killSQL);//stmt.execute(killSQL);
            System.out.println("kill session " + id + " success");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得系统当前时间，精确到毫秒。
     * 格式为：yyyy-MM-dd HH:mm:ss.SSS
     * （请求处理耗时性能测试格式要求）
     *
     * @return
     * @author yang.jie
     * @see 相关函数，对于重要的函数建议注释
     * @since Across PM V100R001C20, 2015年3月19日
     */
    public static String getCurrentTimeMillisec() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    /**
     * 返回date1 - date2 的千分秒数
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 相隔秒数
     */
    public static long getDiffSSSs(String date1, String date2) {
        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        long millSec = 0L;
        long diffmillsec = 0L;
        try {
            millSec = dfs.parse(date1).getTime() - dfs.parse(date2).getTime();
            millSec = Math.abs(millSec);
            diffmillsec = millSec; // / 1000;
        } catch (ParseException e) {
            System.out.println(e);
        }
        return diffmillsec;
    }
}



