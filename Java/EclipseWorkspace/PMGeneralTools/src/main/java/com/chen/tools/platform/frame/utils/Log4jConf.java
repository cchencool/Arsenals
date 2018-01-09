package com.chen.tools.platform.frame.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * 
 * 加载log4j.properties文件
 * 
 * @author zanglb zanglb@inspur.com
 */
public final class Log4jConf
{ 
    
    private static final Logger logger = Logger.getLogger(Log4jConf.class);
    
    private static Log4jConf instance = null;
    
    private Log4jConf(){};
    
    public static Log4jConf getLog4jConf(){
    	if(instance == null){
    		instance = new Log4jConf();
    	}
    	return instance;
    	
    }
    
    /** 
     * 初始化Log4j
     * @see [类、类#方法、类#成员]
     */
    public static void initLog4j()
    {
        FileInputStream fin = null;
        try
        {
            // String log4jPath = PropertiesUtil.getLog4jPropertiesFilePath();
            // if(!StringUtil.isEmpty(log4jPath)){
//            //使用默认的配置信息，不需要写log4j.properties
//            BasicConfigurator.configure();
//            //设置日志输出级别为info，这将覆盖配置文件中设置的级别
//            logger.setLevel(Level.INFO);
//            //下面的消息将被输出
//            logger.info("this is an info");
//            logger.warn("this is a warn");
//            logger.error("this is an error");
//            logger.fatal("this is a fatal");

        	String log4jXmlPath = System.getProperty("LOG_PATH");//"/opt/netwatcher/pm4h2/work/conf/log4j/log4j.properties";
        	
        	if (StringUtils.isEmpty(log4jXmlPath)) {
        		log4jXmlPath = PropertiesUtils.getProperties("LOG_PATH");
        		if (StringUtils.isEmpty(log4jXmlPath)) {        			
        			log4jXmlPath = "conf/log4j.properties"; 
        		}
			}
        	
            File log4jFile = new File(log4jXmlPath);
            
            if (log4jFile.exists())
            {
            	
            	System.setProperty("filename", "GeneralTools");
                fin = new FileInputStream(log4jXmlPath);
                Properties log4jProperties = new Properties();
                log4jProperties.load(fin);
                PropertyConfigurator.configure(log4jProperties);
//                DOMConfigurator.configure(log4jXmlPath);
            }
        }
        catch (IOException e)
        {
            logger.debug(e.toString());
        }
        finally
        {
            if (fin != null)
            {
                try
                {
                    fin.close();
                }
                catch (IOException e)
                {
                    logger.debug(e.toString());
                }
            }
        }
    }
}
