package com.inspur.sqlabout;

import java.sql.*;

public class checkEleConfig {
    private static final String driver = "oracle.jdbc.driver.OracleDriver";
    private static final String url = "jdbc:oracle:thin:@10.110.2.157:1521:acrosspm";
    private static final String user = "pm4h_ad";
    private static final String password = "ACROSS_ad_2013";

    private static final String system_user = "system";
    private static final String system_password = "iNOC_oper_001";

    public static void main(String[] args) throws InterruptedException {
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, system_user, system_password);
            stmt = conn.createStatement();
            System.out.println("connect success!...");

            String sql = "select te.elementid   as eleid,"
                    + "te.motypeid    as elemotypeid,"
                    + "ti.indicatorid as eleindid "
                    + "from pm4h_ad.dsb_element te, pm4h_ad.dsb_element_ind ti "
                    + "where te.elementid = ti.elementid";

            rs = stmt.executeQuery(sql);


            while (rs.next()) {
                String str = rs.getString("eleid") + "|" + rs.getString("elemotypeid") + "|" + rs.getString("eleindid");
                System.out.println(str);

            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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
                e.printStackTrace();
            }
        }
    }


}
