package com.chen.tools.platform.business.functest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import com.inspur.pmv5.common.log.util.LogParam;
import com.inspur.pmv5.common.util.DBUtil;
import com.inspur.pmv5.common.util.PropertiesUtils;


public class HiveOrOracleTest {

	public static final String INDATA_KRB5_CONF = "/opt/netwatcher/pm4h2/app/opt/krb5-1.15.2/conf/indata/krb5.conf";
	public static final String INDATA_HIVE_KRB5_KETAB = "/opt/netwatcher/pm4h2/app/opt/krb5-1.15.2/var/krb5kdc/hive.service.keytab";

	private static void authenticate(String principal) {
		System.setProperty("java.security.krb5.conf", INDATA_KRB5_CONF);
		try {
			Configuration conf = new Configuration();
			conf.set("hadoop.security.authentication", "kerberos");
			conf.set("hadoop.security.authorization", "true");
			UserGroupInformation.setConfiguration(conf);
			UserGroupInformation.loginUserFromKeytab(principal, INDATA_HIVE_KRB5_KETAB);
		} catch (IOException e1) {
			System.out.println("loginUserFromKeytab erro!!");
			e1.printStackTrace();
		}
	}
	
	public static String getOracleUrl() {

		boolean isBalance = true;
		
		String addressList = DBUtil.getDBAddressList();
//        String adUser = DBUtil.getADUser();
//        String adPwd = DBUtil.getADUserPwd();
//        String dbUser = DBUtil.getDBUser();
//        String dbPwd = DBUtil.getDBUserPwd();
//        String moUser = DBUtil.getMOUser();
//        String moPwd = DBUtil.getMOUserPwd();
//        String viUser = DBUtil.getVIUser();
//        String viPwd = DBUtil.getVIUserPwd();
//        String systemUser = DBUtil.getSystemUser();
//        String systemPwd = DBUtil.getSystemUserPwd();
//        String bptmpUser = DBUtil.getTMPUser();
//        String bptmpPwd = DBUtil.getBPTMPUserPwd();
        String sid = DBUtil.getDBSid();
        
        String tag = ")";

		StringBuffer oracleUrl = new StringBuffer();
		
		oracleUrl.append("jdbc:oracle:thin:@(description=(address_list=");
		/****************************** exchange address ***************************/
		String logFileName = System.getProperty(LogParam.FILENAME);
		String bussinessModule = "NOT SET MODULE NAME MUST NOT PM MODULE";
		if (StringUtils.isNotEmpty(logFileName)) {
			String[] strArray = logFileName.split("_");
			bussinessModule = strArray[strArray.length - 1];
		}
		if (StringUtils.containsIgnoreCase(bussinessModule, "WebService")
				|| StringUtils.containsIgnoreCase(bussinessModule, "def")
				|| StringUtils.containsIgnoreCase(bussinessModule, "BaselineService")
				|| StringUtils.containsIgnoreCase(bussinessModule, "RTMonitor")) {
			isBalance = false;
			int racNodesNumberInt = 1;
			String racNodesNumberString = PropertiesUtils.getProperties("RAC_NODES_NUMBER");
			if (StringUtils.isNotEmpty(racNodesNumberString)) {
				racNodesNumberInt = Integer.parseInt(racNodesNumberString);
			}
			if (racNodesNumberInt > 1) {
				String[] ORACLE_IP = new String[racNodesNumberInt];
				String[] ORACLE_PORT = new String[racNodesNumberInt];
				for (int i = 0; i < racNodesNumberInt; i++) {
					String oracleIP = "ORACLE_IP_" + (i + 1);
					String oraclePORT = "ORACLE_PORT_" + (i + 1);
					ORACLE_IP[i] = PropertiesUtils.getProperties(oracleIP);
					ORACLE_PORT[i] = PropertiesUtils.getProperties(oraclePORT);
				}

				StringBuffer addressStringTmp = new StringBuffer();
				for (int i = racNodesNumberInt - 1; i >= 0; i--) {
					addressStringTmp.append("(ADDRESS=(HOST=");
					addressStringTmp.append(ORACLE_IP[i]);
					addressStringTmp.append(")(PROTOCOL=TCP)(PORT=");
					addressStringTmp.append(ORACLE_PORT[i]);
					addressStringTmp.append("))");
				}
				addressList = addressStringTmp.toString();
			}
		}
		/****************************** exchange address ***************************/
		oracleUrl.append(addressList);
		oracleUrl.append("(FAILOVER=YES)");
		if (isBalance) {
			oracleUrl.append("(LOAD_BALANCE=yes))");
		} else {
			oracleUrl.append(")");
		}
		oracleUrl.append("(connect_data =");
		oracleUrl.append("(server = dedicated)");
		oracleUrl.append("(service_name=");
		oracleUrl.append(sid);
		oracleUrl.append(tag);
		oracleUrl.append("(failover_mode =");
		oracleUrl.append("(TYPE = SELECT)");
		oracleUrl.append("(METHOD = BASIC)");
		oracleUrl.append("(RETRIES=20)");
		oracleUrl.append("(DELAY=5))");
		oracleUrl.append("))");
		
		return oracleUrl.toString();
	}

	public static void main(String[] args) throws SQLException {
		
		//参数
		String url = "jdbc:hive2://172.31.2.56:10016/default;principal=hive/indata-172-31-2-56.indata.com@INDATA.COM";//thrift服务地址
		String principal = "hive/indata-172-31-2-56.indata.com@INDATA.COM";//认证凭证
		String oracleUrl = getOracleUrl();
		String user = DBUtil.getADUser();
		String password = "ACROSS_ad_2013";//DBUtil.getADUserPwd();
		String tableName = "pm4h_mo.exc_1582";//需要查询的oracle表名
		String tempTableName = "hive_exc_1582_test";//想要创建oracle数据源对应hive的临时表名
		
		//kerberos认证
		authenticate(principal);
		
		String sql1 = "create temporary view " + tempTableName + '\n'
				+ "using org.apache.spark.sql.jdbc options ( " + '\n'
				+ "url '"+oracleUrl+"',"  + '\n'
				+ "dbtable '"+tableName+"'," + '\n'
				+ "driver 'oracle.jdbc.driver.OracleDriver'," + '\n'
				+ "user '"+user+"', " + '\n'
				+ "password '"+password+"' )";
		
//		String sql2 = "select * from "+tempTableName;
//		String sql2 = "select cast(t.startday as bigint) startday, cast(t.starttime as bigint) starttime, cast(t.endtime as bigint) endtime, t.motypeid as motypeid, cast(t.moentityid as bigint) moentityid, cast(t.seqno as bigint) seqno from hive_exc_1582_test t";
		
		String sql2 = "select t_obj.objid, t_obj.startday, t_obj.starttime, \n" + 
				"cast(t_obj.startday as bigint) * 1000000 + t_obj.starttime as time_obj, \n" + 
				"cast(T_EXC.startday as bigint) * 1000000 + T_EXC.starttime as time_exc_start, \n" + 
				"cast(T_EXC.startday as bigint) * 1000000 + T_EXC.endtime as time_exc_end \n" + 
				"from (select  \n" + 
				"cast(t.startday as bigint) startday, \n" + 
				"cast(t.starttime as bigint) starttime, \n" + 
				"cast(t.endtime as bigint) endtime, \n" + 
				"t.motypeid as motypeid, \n" + 
				"cast(t.moentityid as bigint) objid, \n" + 
				"cast(t.seqno as bigint) seqno  \n" + 
				"from hive_exc_1582_test t) T_EXC, obj_1225 t_obj \n" + 
				"where T_EXC.objid = t_obj.objid \n" + 
				"and cast(t_obj.startday as bigint) * 1000000 + t_obj.starttime >= cast(T_EXC.startday as bigint) * 1000000 + T_EXC.starttime \n" + 
				"and cast(t_obj.startday as bigint) * 1000000 + t_obj.starttime <= cast(T_EXC.startday as bigint) * 1000000 + T_EXC.endtime";
		
		//加载驱动
		try {
			Class.forName("org.apache.hive.jdbc.HiveDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//进行连接
		Connection conn = DriverManager.getConnection(url,"hive","");
		Statement stmt = conn.createStatement();
//		PreparedStatement pstmt = conn.prepareStatement(sql1);
		//执行创建oracle数据为hive临时表的sql
//		pstmt.execute();
		stmt.execute(sql1);
		
//        PreparedStatement ps = null;
//
//        ps = conn.prepareStatement(sql1);
//        ps.execute();
            
		//进行查询oracle
		ResultSet res = stmt.executeQuery(sql2);
		System.out.println("----------------oracle查询结果----------------");
		while(res.next()){
			String str = "";
			for(int i=1;i<=res.getMetaData().getColumnCount();i++){  
                str += res.getString(i)+"\t  ";  
            } 
			System.out.println(str);
		}
	}
}
