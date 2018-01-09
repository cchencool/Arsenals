package com.chen.tools.platform.frame.main;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.chen.tools.platform.frame.utils.PropertiesUtils;
import com.chen.tools.platform.frame.ws.ServerHandler;
import com.chen.tools.platform.frame.ws.SocketServer;

/**
 * Hello world!
 *
 */
public class GeneralTools 
{ 
	
	static String HOST_IP = "127.0.0.1";//"0.0.0.0";
	
	static String HOST_PORT = "12700";
	
	public static void main(String args[]) throws Exception {	// NOPMD
		
		if (args.length > 0) {
			boolean isOut = StringUtils.equalsIgnoreCase("true", args[0]);
			if (isOut) {
				HOST_IP = "0.0.0.0";
			}
		}
		
		(new GeneralTools()).start();
		
	}
	
	public void start() {
		
		init();
		
		launch();
		
	}

	private void init() {
		
		PropertiesUtils.loadProperties();
		
//		Log4jConf.initLog4j();
		
		com.inspur.pmv5.common.log.Logger.initLog("GeneralTools", "");
		
	}

	private void launch() { 
		
		Logger logger = Logger.getLogger(GeneralTools.class);
		
		logger.info("start Scoket Server.");
		
		logger.info("host ip: " + HOST_IP);
		logger.info("host port: " + HOST_PORT);
		
		SocketServer server = new SocketServer(HOST_IP, Integer.valueOf(HOST_PORT), new ServerHandler());
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				this.setName("system-kill-hook");
				logger.info("service gracefully exit start");
				server.stop();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					logger.error("system-kill-hook sleep err", e);
				}
				logger.info("service gracefully exit end");
			}
		});
		
		server.start();
		
	}
}
