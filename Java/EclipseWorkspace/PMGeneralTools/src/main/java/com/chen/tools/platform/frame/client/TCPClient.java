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
		
//		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>262162147c974552bd6a1e03f1f575eb</molevel><moview>262162147c974552bd6a1e03f1f575eb;307e7982c8b645618f85e075aea0c387</moview><timelevel type=\"H\" direct=\"0\"/><indicators><indicator id=\"f393dacdae99476daf1c2d692e97a2f7\" type=\"0\"><displaysequence>1</displaysequence><unit>%</unit><decima>2</decima><label>AR9702:Mean CPU Usage_05</label><whmhArithmetic>SUM</whmhArithmetic><column db=\"pm4h_db\" table=\"IND_H_72928_2\">IND_H_72928_2_140</column><columnHive db=\"db\" table=\"IND_H_99345\">i45187</columnHive><arithmetics><arithmetic id=\"MO\">pm4h_ad.F_MULTI</arithmetic><arithmetic id=\"hourly\">pm4h_ad.F_UNION</arithmetic><arithmetic id=\"daily\">AVG</arithmetic><arithmetic id=\"multidays\">AVG</arithmetic></arithmetics><indicatorSetID>fe6837cc353f4f61989b7cdb044af717</indicatorSetID><summary summaryOrder=\"mo\"><timeSum><sourcePeriod>H</sourcePeriod></timeSum><moSum><morelation>motypeId1;motypeId2</morelation><table>moSumTab|moSumTabColumn</table></moSum></summary></indicator></indicators><moinfos><moinfo id=\"262162147c974552bd6a1e03f1f575eb\" refNameColumn=\"n47207\" refIDColumn=\"r47207\"><objtable>obj_1457</objtable><vertable>obj_ver_1457</vertable><hiveTable>IND_H_99345</hiveTable></moinfo><moinfo id=\"307e7982c8b645618f85e075aea0c387\" refNameColumn=\"n46972\" refIDColumn=\"r46972\"><objtable>obj_1269</objtable><vertable>obj_ver_1269</vertable><hiveTable>IND_H_99361</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\" offset=\"false\"><from>20180325</from><to>20180326</to><exclude>20180325</exclude></date><time type=\"all\" offset=\"false\"><hour>all</hour></time><holiday>all</holiday><timeformat>yyyy-MM-dd HH:mm:ss</timeformat></timerange><orderby><displaysort><sort id=\"262162147c974552bd6a1e03f1f575eb\" type=\"objid\" rule=\"asc\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations><morelation>262162147c974552bd6a1e03f1f575eb</morelation><morange molevel=\"262162147c974552bd6a1e03f1f575eb\">1007;1006;1005;1014</morange></morelations></moentity><moattribute/></mocondition></filter></conditions><output><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"262162147c974552bd6a1e03f1f575eb\" type=\"motype\" alias=\"motype_2\" sequence=\"2\">Huawei_CPU</field><field id=\"indicators\" type=\"indicators\" sequence=\"3\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180403150000</queryTimeStamp><queryHiveParam><filename>prf_file</filename></queryHiveParam></analysis></remoteQuery>";

		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><remoteQuery><analysis><algorithm class=\"com.inspur.pmv5.service.impl.report.analysisengine.service.v2.StandardQueryV2\"><parameter id=\"1\">Monday</parameter><parameter id=\"UnionSplitOptimize\">15</parameter></algorithm><datasource><molevel>ec255a874a6e44fa9c5c473f4ed30ca7</molevel><moview>ec255a874a6e44fa9c5c473f4ed30ca7;0ef50f20b2674b639697d9ec9505e078</moview><timelevel type=\"H\" direct=\"0\"/><indicators><indicator id=\"f265e6f3d0dd4a8b98edaa6d6c8add08\" type=\"0\"><displaysequence>1</displaysequence><unit>S</unit><decima>2</decima><label>Interface_Collect_time</label><whmhArithmetic>SUM</whmhArithmetic><column db=\"pm4h_db\" table=\"IND_H_73285_1\">IND_H_73285_1_002</column><columnHive db=\"db\" table=\"IND_H_99895\">i44564</columnHive><arithmetics><arithmetic id=\"MO\">SUM</arithmetic><arithmetic id=\"hourly\">SUM</arithmetic><arithmetic id=\"daily\">SUM</arithmetic><arithmetic id=\"multidays\">SUM</arithmetic></arithmetics><indicatorSetID>d7edc83d2adb43e9b8979d39b4928266</indicatorSetID><summary summaryOrder=\"mo\"><timeSum><sourcePeriod>H</sourcePeriod></timeSum><moSum><morelation>motypeId1;motypeId2</morelation><table>moSumTab|moSumTabColumn</table></moSum></summary></indicator></indicators><moinfos><moinfo id=\"ec255a874a6e44fa9c5c473f4ed30ca7\" refNameColumn=\"n47226\" refIDColumn=\"r47226\"><objtable>obj_1476</objtable><vertable>obj_ver_1476</vertable><hiveTable>IND_H_99895</hiveTable></moinfo><moinfo id=\"0ef50f20b2674b639697d9ec9505e078\" refNameColumn=\"n47126\" refIDColumn=\"r47126\"><objtable>obj_1202</objtable><vertable>obj_ver_1202</vertable><hiveTable>IND_H_99320</hiveTable></moinfo></moinfos><attributes/></datasource><conditions><timerange><date type=\"specific\" offset=\"false\"><from>20180325</from><to>20180326</to><exclude>20180325</exclude></date><time type=\"all\" offset=\"false\"><hour>all</hour></time><holiday>all</holiday><timeformat>yyyy-MM-dd HH:mm:ss</timeformat></timerange><orderby><displaysort><sort id=\"ec255a874a6e44fa9c5c473f4ed30ca7\" type=\"objid\" rule=\"asc\" order=\"2\"/><sort id=\"time\" type=\"time\" rule=\"asc\" order=\"1\"/></displaysort></orderby><filter><mocondition><moentity><morelations><morelation>ec255a874a6e44fa9c5c473f4ed30ca7;0ef50f20b2674b639697d9ec9505e078</morelation><table>IND_H_99895|r47126</table><morange molevel=\"0ef50f20b2674b639697d9ec9505e078\">UNKNOWN</morange><viewtable></viewtable></morelations></moentity><moattribute/></mocondition></filter></conditions><output><displayfields><field id=\"time\" type=\"time\" sequence=\"1\">Time</field><field id=\"ec255a874a6e44fa9c5c473f4ed30ca7\" type=\"motype\" alias=\"motype_2\" sequence=\"2\">Interface</field><field id=\"indicators\" type=\"indicators\" sequence=\"3\">Indicators</field></displayfields><page><startnum>0</startnum><endnum>0</endnum></page><isordered>true</isordered><isfirstrow>true</isfirstrow><fetchsize>100</fetchsize><shortname>false</shortname><disableCache>false</disableCache></output><queryTimeStamp>20180403160000</queryTimeStamp><queryHiveParam><filename>prf_file</filename></queryHiveParam></analysis></remoteQuery>";
		
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
