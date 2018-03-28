package com.chen.tools.platform.frame.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * for debug
 * @author Chen
 */
public class TCPClient {
	
//	private static int a = (int) Math.round(Math.random() * 100);

//	private static String host = "10.110.2.104";
	
//	private static String host = "10.110.2.157";
	
//	private static String host = "10.110.2.124"; 
	
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
		// xml
		
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>cell</molevel><moview>cell;bts</moview><timelevel type=\"H\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"ind01\" type=\"3\"><displaysequence>3</displaysequence><unit>%</unit><decima>0</decima><label>kpi_01</label><formula><dbformula>[I].[ind02]+[I].[ind03]*1000</dbformula><original>[I].[ind02]+[I].[ind03]*1000</original></formula><indicatorSetID>ec255a874a6e44fa9c5c473f4ed30ca7_KPI</indicatorSetID></indicator><indicator id=\"ind02\" type=\"0\"><displaysequence>0</displaysequence><unit>Times</unit><decima>0</decima><label>counter_01</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"t_cell\" db=\"pm4h_db\">[I].[ind02]</column><columnHive db=\"db\" table=\"t_cell\">ind02</columnHive><arithmetics><arithmetic id=\"MO\">sum</arithmetic><arithmetic id=\"hourly\">max</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator><indicator id=\"ind03\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>counter_02</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"t_cell\" db=\"pm4h_db\">[I].[ind03]</column><columnHive db=\"db\" table=\"t_cell\">ind03</columnHive><arithmetics><arithmetic id=\"MO\">sum</arithmetic><arithmetic id=\"hourly\">max</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator><indicator id=\"ind04\" type=\"0\"><displaysequence>2</displaysequence><unit>Times</unit><decima>0</decima><label>counter_03</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"t_cell\" db=\"pm4h_db\">[I].[ind04]</column><columnHive db=\"db\" table=\"t_cell\">ind04</columnHive><arithmetics><arithmetic id=\"MO\">sum</arithmetic><arithmetic id=\"hourly\">max</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID></indicator></indicators><moinfos><moinfo id=\"cell\" refIDColumn=\"icell\" refNameColumn=\"ncell\"><objtable>obj_cell</objtable><hiveTable>t_cell</hiveTable></moinfo><moinfo id=\"bts\" refIDColumn=\"ibts\" refNameColumn=\"nbts\"><objtable>obj_bts</objtable><hiveTable>t_bts</hiveTable></moinfo><moinfo id=\"bsc\" refIDColumn=\"ibsc\" refNameColumn=\"nbsc\"><objtable>obj_bsc</objtable><hiveTable>t_bsc</hiveTable></moinfo><moinfo id=\"region\" refIDColumn=\"iregion\" refNameColumn=\"nregion\"><objtable>obj_region</objtable><hiveTable>t_region</hiveTable></moinfo><moinfo id=\"country\" refIDColumn=\"icountry\" refNameColumn=\"ncountry\"><objtable>obj_country</objtable><hiveTable>t_country</hiveTable></moinfo><moinfo id=\"abvc1\" refIDColumn=\"iabvc1\" refNameColumn=\"nabvc1\"><objtable>obj_abvc1</objtable><hiveTable>t_abvc1</hiveTable></moinfo><moinfo id=\"abvc2\" refIDColumn=\"iabvc2\" refNameColumn=\"nabvc2\"><objtable>obj_abvc2</objtable><hiveTable>t_abvc2</hiveTable></moinfo><moinfo id=\"abvc3\" refIDColumn=\"iabvc3\" refNameColumn=\"nabvc3\"><objtable>obj_abvc3</objtable><hiveTable>t_abvc3</hiveTable></moinfo><moinfo id=\"abvc4\" refIDColumn=\"iabvc4\" refNameColumn=\"nabvc4\"><objtable>obj_abvc4</objtable><hiveTable>t_abvc4</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\" offset=\"false\"><from>20180306</from><to>20180323</to><exclude></exclude></date><time type=\"all\" offset=\"false\"><hour>all</hour></time><holiday>holiday</holiday><region><holidays regionid=\"all\">20180318;20180317;20180311;20180310;20180309;20180308</holidays></region><timeformat>yyyy-MM-dd</timeformat></timerange><orderby><top_n type=\"%\" partition=\"\" indicator=\"ind04\" rule=\"desc\">90</top_n><displaysort><sort id=\"cell\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"3\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"ind03\" type=\"indicator\" rule=\"desc\" nullslast=\"true\" order=\"1\"/></displaysort></orderby><filter><indicatorcondition><operator>and</operator><condition indicator=\"ind03\" operator=\"&gt;=\" value=\"0\"/><condition indicator=\"ind04\" operator=\"&lt;=\" value=\"998\"/></indicatorcondition><mocondition><moentity><morelations searchKey=\"cell:cpu10\"><morelation>cell;bts</morelation><table>t_cell|ref_bts</table><morange molevel=\"cell\">1034</morange><morange molevel=\"bts\">1004</morange><condition rule=\"SEARCH\" filterMotypeId=\"cell\" attribute=\"OBJName\" value=\"cpu10\" attrtype=\"String\" relations=\"cell\" tables=\"\"></condition></morelations><morelations searchKey=\"bts:cpu\"><morange molevel=\"cell\">1015</morange><condition rule=\"SEARCH\" filterMotypeId=\"bts\" attribute=\"OBJName\" value=\"cpu\" attrtype=\"String\" relations=\"cell;bts\" tables=\"t_cell|ref_bts\"></condition></morelations><morelations searchKey=\"cell:cpu3\"><morange molevel=\"cell\">1012</morange><condition rule=\"SEARCH\" filterMotypeId=\"cell\" attribute=\"OBJName\" value=\"cpu\" attrtype=\"String\" relations=\"cell\" tables=\"\"></condition></morelations><morelations searchKey=\"region:reg3\"><morelation>cell;bts;bsc;region</morelation><table>t_cell|ref_bts;t_cell|ref_bsc;rel_bsc_region</table><morange molevel=\"cell\">1023</morange><morange molevel=\"bsc\">2023;2024</morange><morange molevel=\"region\">3023</morange><condition rule=\"SEARCH\" filterMotypeId=\"region\" attribute=\"OBJName\" value=\"reg3\" attrtype=\"String\" relations=\"cell;bts;bsc;region\" tables=\"t_cell|ref_bts;t_cell|ref_bsc;rel_bsc_region\"></condition></morelations><morelations searchKey=\"region:reg2\"><morange molevel=\"cell\">1043</morange><condition rule=\"SEARCH\" filterMotypeId=\"region\" attribute=\"OBJName\" value=\"reg2\" attrtype=\"String\" relations=\"cell;bts;bsc;region\" tables=\"t_cell|ref_bts;t_cell|ref_bsc;rel_bsc_region\"></condition></morelations></moentity><moattribute><condition rule=\"EXP\" attribute=\"objid\" operator=\"=\" type=\"AND\"> from pm4h_mo.obj_1457 t where 1=1  and  upper(t.OBJName) like upper('%cpu1%') escape '\\\\'  and t.VERENDTIME is null </condition><conditionHive rule=\"EXP\" motypeId=\"cell\" attribute=\"OBJName\" attrColumn=\"cell_objname\" attrTable=\"t_cell\" attributeId=\"a_objname\" operator=\"like\" value=\"bsc\" type=\"AND\"/><conditionHive rule=\"EXP\" motypeId=\"cell\" attribute=\"IsOnSite\" attrColumn=\"a_isonsite\" attrTable=\"t_cell\" attributeId=\"a_isonsite\" operator=\"=\" value=\"1\" type=\"AND\"/></moattribute></mocondition></filter><orderbyflag>true</orderbyflag><isindicatoralias>true</isindicatoralias><isstandarttablesort>true</isstandarttablesort></conditions><output><summary calculateFirstOrder=\"sum\" summaryFirstOrder=\"mo\"><molevel arithmetic=\"MAX,SUM\" direct=\"0\">country</molevel><summarypath>cell;bts;bsc;region;country</summarypath><table>t_cell|ref_bts;t_cell|ref_bsc;rel_bsc_region;obj_region|ref_country</table><viewtable></viewtable><sourceTimeLevel>H</sourceTimeLevel><timelevel arithmetic=\"DEFAULT\" type=\"W\" direct=\"0\"/></summary><displaymorelation><morelation id=\"1\"><value>cell;bts;bsc;region</value><table>t_cell|ref_bts;t_cell|ref_bsc;rel_bsc_region</table></morelation><morelation id=\"2\"><value>cell;abvc1;abvc2;abvc3;abvc4</value><table>t_cell|ref_abvc1;t_cell|ref_abvc2;rel_abvc2_abvc3;obj_abvc3|ref_abvc4</table></morelation></displaymorelation><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"cell\" type=\"motype\" sequence=\"2\" alias=\"motype_2\">Huawei_CPU</field><field id=\"bts\" attrColumn=\"bts_objname\" type=\"abovemotype\" sequence=\"3\" alias=\"motype_3\">BTS</field><field id=\"region\" attrColumn=\"\" type=\"abovemotype\" sequence=\"4\" alias=\"motype_4\">region</field><field id=\"abvc4\" attrColumn=\"\" type=\"abovemotype\" sequence=\"5\" alias=\"motype_5\">abvc4</field><field id=\"indicators\" type=\"indicators\" sequence=\"6\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath></filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180313100000</queryTimeStamp><queryHiveParam><filename>prf_file</filename></queryHiveParam></analysis></remoteQuery>";

		// all net
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>307e7982c8b645618f85e075aea0c387</molevel><moview>307e7982c8b645618f85e075aea0c387</moview><timelevel type=\"M5\" arithmetic=\"default\" direct=\"0\"></timelevel><indicators><indicator id=\"a11a9c0927ec46acb484f23a966e7bd0\" type=\"0\"><displaysequence>2</displaysequence><unit>Times</unit><decima>0</decima><label>R9700:Cumulative CPU Usage_05</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M5_99918_2\" db=\"pm4h_db\">IND_M5_99918_2_010</column><columnHive table=\"IND_M5_99361\" db=\"db\">i30908</columnHive><arithmetics><arithmetic id=\"MO\">pm4h_ad.F_MULTI</arithmetic><arithmetic id=\"hourly\">pm4h_ad.F_UNION</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID><summary><timeSum><sourcePeriod>H</sourcePeriod></timeSum><moSum><morelation>motypeId1;motypeId2</morelation><table>moSumTab|moSumTabColumn</table></moSum></summary></indicator><indicator id=\"f393dacdae99476daf1c2d692e97a2f7\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>AR9702:Mean CPU Usage_05</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M5_99918_2\" db=\"pm4h_db\">IND_M5_99918_2_140</column><columnHive table=\"IND_M5_99361\" db=\"db\">i45187</columnHive><arithmetics><arithmetic id=\"MO\">pm4h_ad.F_MULTI</arithmetic><arithmetic id=\"hourly\">pm4h_ad.F_UNION</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID><summary><timeSum><sourcePeriod>H</sourcePeriod></timeSum><moSum><morelation>motypeId1;motypeId2</morelation><table>moSumTab|moSumTabColumn</table></moSum></summary></indicator><indicator id=\"196fd24dd39c4d5e82ffc0371e61bbc7\" type=\"0\"><displaysequence>3</displaysequence><unit>Times</unit><decima>0</decima><label>R9701:CPU Usage Measurement Times_05</label><whmhArithmetic>SUM</whmhArithmetic><column table=\"IND_M5_99918_1\" db=\"pm4h_db\">IND_M5_99918_1_034</column><columnHive table=\"IND_M5_99361\" db=\"db\">i10336</columnHive><arithmetics><arithmetic id=\"MO\">pm4h_ad.F_MULTI</arithmetic><arithmetic id=\"hourly\">pm4h_ad.F_UNION</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID><summary><timeSum><sourcePeriod>H</sourcePeriod></timeSum><moSum><morelation>motypeId1;motypeId2</morelation><table>moSumTab|moSumTabColumn</table></moSum></summary></indicator></indicators><moinfos><moinfo id=\"307e7982c8b645618f85e075aea0c387\" refNameColumn=\"n46972\" refIDColumn=\"r46972\"><objtable>obj_1269</objtable><vertable>obj_ver_1269</vertable><hiveTable>IND_M5_99361</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\"><from>20180326</from><to>20180326</to><exclude></exclude></date><time type=\"designation\"><hour>40000;80000</hour></time><holiday>holiday</holiday><region><holidays regionid=\"all\"></holidays></region></timerange><orderby><displaysort><sort id=\"307e7982c8b645618f85e075aea0c387\" type=\"objid\" rule=\"asc\" nullslast=\"false\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" nullslast=\"false\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations><morelation>307e7982c8b645618f85e075aea0c387</morelation><table></table><morange molevel=\"307e7982c8b645618f85e075aea0c387\">1001;1000</morange></morelations></moentity><moattribute/></mocondition></filter><orderbyflag>true</orderbyflag><isindicatoralias>true</isindicatoralias><isstandarttablesort>true</isstandarttablesort></conditions><output><displaymorelation/><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"307e7982c8b645618f85e075aea0c387\" type=\"motype\" sequence=\"2\" alias=\"motype_2\">Huawei_BSC</field><field id=\"indicators\" type=\"indicators\" sequence=\"3\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><filepath></filepath><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180327111500</queryTimeStamp><queryHiveParam><filename>ORGDATA_1204985749_1522120582795_6</filename></queryHiveParam></analysis></remoteQuery>";
		
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
