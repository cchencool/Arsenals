package com.chen.tools.platform.frame.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Optional;

/**
 * for debug
 * @author Chen
 */
public class TCPClient {
	
//	private static int a = (int) Math.round(Math.random() * 100);

//	private static String host = "10.110.2.104";
	
//	private static String host = "10.110.2.157";
	
//	private static String host = "10.110.2.124";
	
//	private static String host = "10.110.2.106"; 
	
	private static String host = "127.0.0.1"; 
	
	private static int port = 15204;//MsdFinals.MSD_SERVER_PORT;
	
	public static void main(String[] args)
	{
		
		if (args.length < 2) {
			callServerMethodOne();
		} else {
			callServerByArgs(args[0], args[1]);
		}
		
//		callServerMethodTwo();
		
		
	}

	protected static void callServerMethodOne() 
	{
		String xml = "";
		String filename = "query.xml";
		FileInputStream fileInputStream = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		try {
			
			StringBuffer sb = new StringBuffer();
			fileInputStream = new FileInputStream(filename);
			reader = new InputStreamReader(fileInputStream);
			br = new BufferedReader(reader);
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			
			xml = sb.toString();
			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
			Optional.ofNullable(fileInputStream).ifPresent(o -> {
				try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			Optional.ofNullable(reader).ifPresent(o -> {
				try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			Optional.ofNullable(br).ifPresent(o -> {
				try {
					o.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>068eef92a46e4da0809d1e50ff2c3277</molevel><moview>068eef92a46e4da0809d1e50ff2c3277;068eef92a46e4da0809c1e50ff2c3234</moview><timelevel type=\"M15\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"cab87c28d0c8461ba3ec1b4cec332b3b\" type=\"0\"><displaysequence>3</displaysequence><decima>3</decima><label>CELEVENTD_DISETA</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M15_356_2\" db=\"pm4h_db\">IND_M15_356_2_038</column><columnHive table=\"IND_M15_12\" db=\"db\">i38153</columnHive><arithmetics><arithmetic id=\"MO\">SUM</arithmetic><arithmetic id=\"hourly\">SUM</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>75275d3d32d34d66981e5933e2724333</indicatorSetID></indicator><indicator id=\"b7375a80c1a245889a3f66f6f11e4a6c\" type=\"0\"><displaysequence>1</displaysequence><decima>3</decima><label>CELEVENTD_DISBQA</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M15_356_2\" db=\"pm4h_db\">IND_M15_356_2_007</column><columnHive table=\"IND_M15_12\" db=\"db\">i34971</columnHive><arithmetics><arithmetic id=\"MO\">SUM</arithmetic><arithmetic id=\"hourly\">SUM</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>75275d3d32d34d66981e5933e2724333</indicatorSetID></indicator><indicator id=\"01593bc2ff024329a90150d1803c3c5a\" type=\"0\"><displaysequence>2</displaysequence><decima>3</decima><label>CELEVENTD_DISBSS</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M15_356_1\" db=\"pm4h_db\">IND_M15_356_1_002</column><columnHive table=\"IND_M15_12\" db=\"db\">i6092</columnHive><arithmetics><arithmetic id=\"MO\">SUM</arithmetic><arithmetic id=\"hourly\">SUM</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>75275d3d32d34d66981e5933e2724333</indicatorSetID></indicator><indicator id=\"kpi01\" type=\"3\"><displaysequence>4</displaysequence><unit>%</unit><decima>0</decima><label>kpi_01</label><formula><dbformula>[I].[01593bc2ff024329a90150d1803c3c5a]+[I].[b7375a80c1a245889a3f66f6f11e4a6c]*1000</dbformula><original>[I].[01593bc2ff024329a90150d1803c3c5a]+[I].[b7375a80c1a245889a3f66f6f11e4a6c]*1000</original></formula><indicatorSetID>75275d3d32d34d66981e5933e2724333_KPI</indicatorSetID></indicator></indicators><moinfos><moinfo id=\"068eef92a46e4da0809d1e50ff2c3277\" refNameColumn=\"n46932\" refIDColumn=\"r46932\"><objtable>obj_1274</objtable><vertable>obj_ver_1274</vertable><hiveTable>IND_M15_12</hiveTable></moinfo><moinfo id=\"068eef92a46e4da0809c1e50ff2c3234\" refNameColumn=\"n46952\" refIDColumn=\"r46952\"><objtable>obj_1293</objtable><vertable>obj_ver_1293</vertable><hiveTable>IND_M15_11</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\"><from>20180417</from><to>20180417</to><exclude></exclude></date><time type=\"all\"><hour>all</hour></time><holiday>all</holiday></timerange><orderby><displaysort><sort id=\"068eef92a46e4da0809d1e50ff2c3277\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations><morelation>068eef92a46e4da0809d1e50ff2c3277</morelation><table></table><objTable></objTable><morange molevel=\"068eef92a46e4da0809d1e50ff2c3277\">1448;1447;1446;1467</morange></morelations></moentity><moattribute/></mocondition></filter><orderbyflag>true</orderbyflag><isindicatoralias>true</isindicatoralias><isstandarttablesort>true</isstandarttablesort></conditions><output><summary calculateFirstOrder=\"cal\"><timelevel type=\"H\" arithmetic=\"MAX,AVG,SUM\" direct=\"0\"></timelevel></summary><displaymorelation/><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"068eef92a46e4da0809d1e50ff2c3277\" type=\"motype\" sequence=\"2\" alias=\"motype_2\"></field><field id=\"indicators\" type=\"indicators\" sequence=\"3\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath>/opt/netwatcher/pm4h2/work/tmp/ReportPreview/bac13e04de264b09a3e0e01a63f1d1b9/orginal/ORGDATA_93df5bdbfa2f41ab85b5cc21a8d9f7bf.dat</filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180418150000</queryTimeStamp><queryHiveParam><filename>ORGDATA_1615431970_1524035580366_684</filename></queryHiveParam></analysis></remoteQuery>";
		
		// tempMSum NN Region
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>cell</molevel><moview>cell;bts</moview><timelevel type=\"H\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"ind03\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>counter_02</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"ind_cell\" db=\"pm4h_db\">[I].[ind03]</column><columnHive db=\"db\" table=\"ind_cell\">ind03</columnHive><arithmetics><arithmetic id=\"MO\">sum</arithmetic><arithmetic id=\"hourly\">max</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator></indicators><moinfos><moinfo id=\"cell\" refIDColumn=\"icell\" refNameColumn=\"ncell\"><objtable>obj_cell</objtable><hiveTable>ind_cell</hiveTable></moinfo><moinfo id=\"bts\" refIDColumn=\"ibts\" refNameColumn=\"nbts\"><objtable>obj_bts</objtable><hiveTable>ind_bts</hiveTable></moinfo><moinfo id=\"bsc\" refIDColumn=\"ibsc\" refNameColumn=\"nbsc\"><objtable>obj_bsc</objtable><hiveTable>ind_bsc</hiveTable></moinfo><moinfo id=\"region\" refIDColumn=\"iregion\" refNameColumn=\"nregion\"><objtable>obj_region</objtable><hiveTable>ind_region</hiveTable></moinfo><moinfo id=\"country\" refIDColumn=\"icountry\" refNameColumn=\"ncountry\"><objtable>obj_country</objtable><hiveTable>ind_country</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\" offset=\"false\"><from>20180306</from><to>20180323</to><exclude></exclude></date><time type=\"all\" offset=\"false\"><hour>all</hour></time><timeformat>yyyy-MM-dd</timeformat></timerange><orderby><displaysort><sort id=\"region\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations searchKey=\"cell:cpu10\"><morelation>cell;bts</morelation><table>ind_cell|ref_bts</table><morange molevel=\"cell\">1034</morange><morange molevel=\"bts\">1004</morange><condition rule=\"SEARCH\" filterMotypeId=\"cell\" attribute=\"OBJName\" value=\"cpu10\" attrtype=\"String\" relations=\"cell\" tables=\"\"></condition></morelations></moentity></mocondition></filter></conditions><output><summary calculateFirstOrder=\"sum\" summaryFirstOrder=\"mo\"><molevel arithmetic=\"MAX\" direct=\"0\">region</molevel><summarypath>cell;bts;bsc;region</summarypath><table>ind_cell|ref_bts;ind_cell|ref_bsc;rel_bsc_region</table></summary><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"region\" type=\"motype\" sequence=\"2\" alias=\"motype_2\">region</field><field id=\"indicators\" type=\"indicators\" sequence=\"6\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath></filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180313100000</queryTimeStamp><queryHiveParam><filename>prf_file</filename></queryHiveParam></analysis></remoteQuery>";
		
		// tempMSum N1 bsc
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>cell</molevel><moview>cell;bts</moview><timelevel type=\"H\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"ind03\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>counter_02</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"ind_cell\" db=\"pm4h_db\">[I].[ind03]</column><columnHive db=\"db\" table=\"ind_cell\">ind03</columnHive><arithmetics><arithmetic id=\"MO\">sum</arithmetic><arithmetic id=\"hourly\">max</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator></indicators><moinfos><moinfo id=\"cell\" refIDColumn=\"icell\" refNameColumn=\"ncell\"><objtable>obj_cell</objtable><hiveTable>ind_cell</hiveTable></moinfo><moinfo id=\"bts\" refIDColumn=\"ibts\" refNameColumn=\"nbts\"><objtable>obj_bts</objtable><hiveTable>ind_bts</hiveTable></moinfo><moinfo id=\"bsc\" refIDColumn=\"ibsc\" refNameColumn=\"nbsc\"><objtable>obj_bsc</objtable><hiveTable>ind_bsc</hiveTable></moinfo><moinfo id=\"region\" refIDColumn=\"iregion\" refNameColumn=\"nregion\"><objtable>obj_region</objtable><hiveTable>ind_region</hiveTable></moinfo><moinfo id=\"country\" refIDColumn=\"icountry\" refNameColumn=\"ncountry\"><objtable>obj_country</objtable><hiveTable>ind_country</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\" offset=\"false\"><from>20180306</from><to>20180323</to><exclude></exclude></date><time type=\"all\" offset=\"false\"><hour>all</hour></time><timeformat>yyyy-MM-dd</timeformat></timerange><orderby><displaysort><sort id=\"bsc\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations searchKey=\"cell:cpu10\"><morelation>cell;bts</morelation><table>ind_cell|ref_bts</table><morange molevel=\"cell\">1034</morange><morange molevel=\"bts\">1004</morange><condition rule=\"SEARCH\" filterMotypeId=\"cell\" attribute=\"OBJName\" value=\"cpu10\" attrtype=\"String\" relations=\"cell\" tables=\"\"></condition></morelations></moentity></mocondition></filter></conditions><output><summary calculateFirstOrder=\"sum\" summaryFirstOrder=\"mo\"><molevel arithmetic=\"MAX\" direct=\"0\">bsc</molevel><summarypath>cell;bts;bsc</summarypath><table>ind_cell|ref_bts;ind_cell|ref_bsc</table></summary><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"bsc\" type=\"motype\" sequence=\"2\" alias=\"motype_2\">region</field><field id=\"indicators\" type=\"indicators\" sequence=\"6\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath></filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180313100000</queryTimeStamp><queryHiveParam><filename>prf_file</filename></queryHiveParam></analysis></remoteQuery>";
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>262162147c974552bd6a1e03f1f575eb</molevel><moview>262162147c974552bd6a1e03f1f575eb;307e7982c8b645618f85e075aea0c387</moview><timelevel type=\"H\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"f393dacdae99476daf1c2d692e97a2f7\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>AR9702:Mean CPU Usage_05</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_H_387_2\" db=\"pm4h_db\">IND_H_387_2_140</column><columnHive table=\"IND_H_45\" db=\"db\">i46240</columnHive><arithmetics><arithmetic id=\"MO\">pm4h_ad.F_MULTI</arithmetic><arithmetic id=\"hourly\">pm4h_ad.F_UNION</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator></indicators><moinfos><moinfo id=\"262162147c974552bd6a1e03f1f575eb\" refNameColumn=\"n47207\" refIDColumn=\"r47207\"><objtable>obj_1416</objtable><vertable>obj_ver_1416</vertable><hiveTable>IND_H_45</hiveTable></moinfo><moinfo id=\"307e7982c8b645618f85e075aea0c387\" refNameColumn=\"n46928\" refIDColumn=\"r46928\"><objtable>obj_1270</objtable><vertable>obj_ver_1270</vertable><hiveTable>IND_H_61</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\"><from>20180411</from><to>20180412</to><exclude></exclude></date><time type=\"all\"><hour>all</hour></time><holiday>all</holiday></timerange><orderby><displaysort><sort id=\"307e7982c8b645618f85e075aea0c387\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations><morelation>262162147c974552bd6a1e03f1f575eb</morelation><table></table><objTable></objTable><morange molevel=\"262162147c974552bd6a1e03f1f575eb\">1006;1005;1014</morange></morelations></moentity><moattribute/></mocondition></filter><orderbyflag>true</orderbyflag><isindicatoralias>true</isindicatoralias><isstandarttablesort>true</isstandarttablesort></conditions><output><summary calculateFirstOrder=\"sum\" summaryFirstOrder=\"mo\"><molevel arithmetic=\"MIN\">307e7982c8b645618f85e075aea0c387</molevel><summarypath>262162147c974552bd6a1e03f1f575eb;307e7982c8b645618f85e075aea0c387</summarypath><table>IND_H_45|r46928</table><viewtable></viewtable></summary><displaymorelation/><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"307e7982c8b645618f85e075aea0c387\" type=\"motype\" sequence=\"2\" alias=\"motype_2\" attrColumn=\"n46928\">Huawei_BSC</field><field id=\"indicators\" type=\"indicators\" sequence=\"3\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath>/opt/netwatcher/pm4h2/work/tmp/ReportPreview/5aa5c3937fb249c6ad26a7e46dad80d3/orginal/ORGDATA_47ebc821a72848e48682330836cf02bc.dat</filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180413150000</queryTimeStamp><queryHiveParam><filename>ORGDATA_3418838856_1523605808808_28</filename></queryHiveParam></analysis></remoteQuery>";
		
		// msd
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><missingDataQuery><queryType>query_ind</queryType><page><start>1</start><end>500</end></page><moTypeId>93c7df0a0a484c03be6d5d18cc64c080</moTypeId><period>M15</period><timeSet><timeRange start=\"20180408000000\" end=\"20180408235959\"/></timeSet><indicators><indicator id=\"02194eff18e843e6b62753e9d35ef719\" type=\"1\"><unit>%</unit><decima>0</decima><label>1111</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"98e2a62e25ee4bdfbf81bd9fd0c3ee45\" type=\"0\"><unit>bit</unit><decima>3</decima><label>CCCHLOAD_CSIMMASS</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"0aa12f78a0114717aa9d3314303ca883\" type=\"0\"><unit>bps</unit><decima>3</decima><label>CCCHLOAD_DISCIMMASS</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"0ce16e20eddf413f83f64b8b58c04193\" type=\"0\"><unit>10ms</unit><decima>3</decima><label>CCCHLOAD_PSIMMASS</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"408eb6a9b74f4bab9e579ee297a320e0\" type=\"0\"><unit>%</unit><decima>3</decima><label>CCCHLOAD_REJCSIMMASS</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"113313911ce1458392ed910adaf86e41\" type=\"0\"><unit>bit</unit><decima>3</decima><label>CCCHLOAD_REJPSIMMASS</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator><indicator id=\"fff499e4da6942049b5f68627777a0ae\" type=\"1\"><unit>%</unit><decima>3</decima><label>testsun</label><indicatorSetID>fb3148a940344cb3ace2019e78570101</indicatorSetID></indicator></indicators><moentities><mo objid=\"1201\" objname=\"BS17CA3\"/></moentities><filename>MSD_2877268842_1523428629480</filename><queryTimeStamp>20180411143709</queryTimeStamp></missingDataQuery></remoteQuery>";
		
		try (SocketChannel socketChannel = SocketChannel.open())
		{
			socketChannel.configureBlocking(true);
			socketChannel.connect(new InetSocketAddress(host, port));
			byte[] bs = xml.getBytes("UTF-8");
			ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES + bs.length);
			buff.putInt(bs.length);
			buff.put(bs);
			buff.flip();
			while (buff.hasRemaining())
			{
				socketChannel.write(buff);
			}
			buff.clear();
			
			
			StringBuffer sb = new StringBuffer();
			int numBytesRead;
			while ((numBytesRead = socketChannel.read(buff)) != -1)
			{
				if (numBytesRead == 0)
				{
					// 如果没有数据，则稍微等待一下
					try
					{
						Thread.sleep(10);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					continue;
				}
				// 转到最开始
				buff.flip();
//				while (buff.remaining() > 0)
//				{
//					System.out.print((char) buff.get());
//				}
		        // 也可以转化为字符串，不过需要借助第三个变量了。
				String result = new String(buff.array(), 0, numBytesRead, "UTF-8");
				sb.append(result);
//				System.out.println(result);
				// 复位，清空
				buff.clear();
				if (numBytesRead < 19) {
					break;
				}
			}
			System.out.println(sb.toString());
//			socketChannel.read(buff);
//			buff.flip();
//			bs = new byte[buff.limit()];
//			buff.get(bs);
//			String result = new String(bs, "UTF-8");
//			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected static void callServerByArgs(String ip, String xml) {
		
		host = ip;
		
		try (SocketChannel socketChannel = SocketChannel.open())
		{
			socketChannel.configureBlocking(true);
			socketChannel.connect(new InetSocketAddress(host, port));
			byte[] bs = xml.getBytes("UTF-8");
			ByteBuffer buff = ByteBuffer.allocate(Integer.BYTES + bs.length);
			buff.putInt(bs.length);
			buff.put(bs);
			buff.flip();
			while (buff.hasRemaining())
			{
				socketChannel.write(buff);
			}
			buff.clear();
			
			
			StringBuffer sb = new StringBuffer();
			int numBytesRead;
			while ((numBytesRead = socketChannel.read(buff)) != -1)
			{
				if (numBytesRead == 0)
				{
					// 如果没有数据，则稍微等待一下
					try
					{
						Thread.sleep(10);
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					continue;
				}
				// 转到最开始
				buff.flip();
		        // 也可以转化为字符串，不过需要借助第三个变量了。
				String result = new String(buff.array(), 0, numBytesRead, "UTF-8");
				sb.append(result);
				// 复位，清空
				buff.clear();
				if (numBytesRead < 19) {
					break;
				}
			}
			System.out.println(sb.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
