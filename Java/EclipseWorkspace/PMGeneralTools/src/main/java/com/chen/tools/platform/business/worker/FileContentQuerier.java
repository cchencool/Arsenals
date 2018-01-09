package com.chen.tools.platform.business.worker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.chen.tools.platform.frame.ws.ServerHandler;
import com.inspur.pm.backend.core.fileengine.FileEngineItf;
import com.inspur.pm.backend.core.fileengine.itfimpl.FileEngineItfGen;
import com.inspur.pm.backend.core.util.FSException;
import com.inspur.pmv5.common.dataaccess.SqlSessionInitFactory;
import com.inspur.pmv5.common.util.PropertiesUtils;

public class FileContentQuerier {
	
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ServerHandler.class);
	
	private static FileEngineItf fe = null;
	
	static {
		
//		Logger.initLog("FileContentQuerier", ""); 
		
		PropertiesUtils.loadProperties();
		
		SqlSessionInitFactory.init();
		
		fe = FileEngineItfGen.getFileSystemEngine();

	}
	
	public String call(String[] args) {
		
		String type = "";
		if (args.length > 0) {
			type = args[0];			
		}
		
		String fileName = "";
		if (args.length > 1) {			
			fileName = args[1];
		}
		
		String result = "";
		
		if ("cfg".equalsIgnoreCase(type))
		{
			result = getCfgConten(fe);
		}
		else if ("pia".equalsIgnoreCase(type)) 
		{
			result = getPiaContent(fe, fileName);
		} 
		else if ("msd".equalsIgnoreCase(type)) 
		{
			result = getMsdContent(fe, fileName, StringUtils.endsWith(fileName, "_0"));
		}
		else
		{
			logger.info("arg[0] should be {cfg|pia|msd}");
		}
		
		return result;

	}
	
	

	protected String getCfgConten(FileEngineItf fe) {
		
		StringBuffer sb = new StringBuffer();
		
		Properties properties = new Properties();
		try {
		
			FileInputStream in;
			in = new FileInputStream("cfg.properties");
			properties.load(in);
		
			String sourceMotypeId = properties.getProperty("sourceMotypeId");
	//		String targetMotypeId = properties.getProperty("targetMotypeId");
			String targetMotypeId = sourceMotypeId;
			
			String equipmentEntityId = properties.getProperty("equipmentEntityId");
			String period = properties.getProperty("period");
			String startDay = properties.getProperty("startDay");
			String startTime = properties.getProperty("startTime");
			String indicatorSetid = properties.getProperty("indicatorSetid");
			
			String emsEntityId = "";
			String bhid = "";
			
			List<String> msds = FileKeyQuerier.getMSDFilekeyList(period, startDay, startTime, equipmentEntityId, indicatorSetid, sourceMotypeId, targetMotypeId, emsEntityId, bhid, null);
			String pia = FileKeyQuerier.getFilekey(period, startDay, startTime, equipmentEntityId, indicatorSetid, sourceMotypeId, targetMotypeId, emsEntityId, bhid);
			String msd_set = msds.get(0);
			String msd_ind = msds.get(1);
			
			System.out.print("\n");
			logger.info("pia: " + pia + "\n");
			logger.info("msd set:" + msd_set + "\n");
			logger.info("msd ind:" + msd_ind + "\n");
			logger.info("-----------------------------------------------------");
	//		logger.info("pia file: ");
			String piaContent = getPiaContent(fe, pia);
			logger.info("-----------------------------------------------------");
	//		logger.info("msd set file: ");
			String msd_setContent = getMsdContent(fe, msd_set, true);
			logger.info("-----------------------------------------------------");
	//		logger.info("msd ind file: ");
			String msd_indContent = getMsdContent(fe, msd_ind, false);
			logger.info("-----------------------------------------------------");
			writeFile("./res_pia", pia, piaContent);
			writeFile("./res_msd_set", msd_set, msd_setContent);
			writeFile("./res_msd_ind", msd_ind, msd_indContent);
			
			sb.append("pia: " + pia + "\n");
			sb.append("msd set:" + msd_set + "\n");
			sb.append("msd ind:" + msd_ind + "\n");
			sb.append("-----------------------------------------------------\n");
			sb.append("msd pia:\n" + piaContent + "\n");
			sb.append("-----------------------------------------------------\n");
			sb.append("msd set:\n" + msd_setContent + "\n");
			sb.append("-----------------------------------------------------\n");
			sb.append("msd ind:\n" + msd_indContent + "\n");
			sb.append("-----------------------------------------------------\n");
		
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return sb.toString();
	}

	protected void writeFile(String fn, String n, String c) throws IOException {
		File file = new File(fn);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter sm = new FileWriter(fn);
		sm.write(n + "\n" + c);
		sm.flush();
		sm.close();
	}

	protected String getMsdContent(FileEngineItf fe, String fileName, boolean isSet) {
		String fileContent = "";
		
		boolean isFileKeyExist = false;
		try {
			isFileKeyExist = fe.isFilekeyExist(fileName);
		} catch (FSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (isSet) {
			logger.info("is msd set file exist:" + isFileKeyExist);
		} else {
			logger.info("is msd ind file exist:" + isFileKeyExist);
		}
		
		System.out.print("\n");
		
		try {
			fileContent = fe.getMSDFileContentByFileKey(fileName);
		} catch (FSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		logger.info("\n"); 
		
		if (isSet) {
			logger.info("msd set file content:\n" + fileContent);
		} else {
			logger.info("msd ind file content:\n" + fileContent);
		}
		
		return fileContent;
	}

	protected String getPiaContent(FileEngineItf fe, String fileName) {
		String fileContent = "";
		
		try {
			fileContent = fe.getFileContentByFileKey(fileName);// 从HBase读取filekey文件内容
		} catch (FSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.print("\n"); 
		
		logger.info("pia file content:\n" + fileContent);
		
		return fileContent;
	}
}
