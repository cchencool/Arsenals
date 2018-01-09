package com.chen.tools.platform.business.worker;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;


public class FileKeyQuerier {

	private static final char FILEKEY_FIELD_SEPARATOR = '_';

	public static String md516(String content) 
	{
		return StringUtils.substring(DigestUtils.md5Hex(content), 8, 18);
	}

	public static String getFilekey(String period, String startDay, // NOPMD
			String startTime, String equipmentEntityId, String indicatorSetid, String sourceMotypeId,
			String targetMotypeId, String emsEntityId, String bhid) 
	{
		String startTimeStr = StringUtils.leftPad(String.valueOf(startTime), 6, '0');
		startTimeStr = StringUtils.substring(startTimeStr, 0, 4);
		String md516Digest = md516(
				indicatorSetid + sourceMotypeId + targetMotypeId + (StringUtils.isEmpty(bhid) ? "" : bhid));
		equipmentEntityId = StringUtils.leftPad(equipmentEntityId == null ? "" : equipmentEntityId, 10, '0');
		String[] keyArray = { period, startDay, startTimeStr,
				StringUtils.substring(indicatorSetid, 0, 3) + StringUtils.substring(sourceMotypeId, 0, 3)
						+ StringUtils.substring(targetMotypeId, 0, 3) + md516Digest + equipmentEntityId
						+ "_indicatorid" };
		return StringUtils.join(keyArray, FILEKEY_FIELD_SEPARATOR);
	}
	
	
	public static void main(String[] args) {
		
//		String sourceMotypeId = "27a7f2d4a10845e1b0eb8bedfd52370c";
//		String targetMotypeId = "27a7f2d4a10845e1b0eb8bedfd52370c";
//		
//		String equipmentEntityId = "5390";
//		String period = "M15";
//		String startDay = "20171221";
//		String startTime = "011500";
//		String indicatorSetid = "867b47d954734d6a840ffb97d2bc5d7d";
//		
//		String emsEntityId = "";
//		String bhid = "";
		
		String sourceMotypeId = "27a7f2d4a10845e1b0eb8bedfd52370c";
		String targetMotypeId = "27a7f2d4a10845e1b0eb8bedfd52370c";
		
		String equipmentEntityId = "5390";
		String period = "M15";
		String startDay = "20171221";
		String startTime = "011500";
		String indicatorSetid = "639decbb49164a819c840b8695839673";
		
		String emsEntityId = "";
		String bhid = "";
		
		String fileKey = getFilekey(period, startDay, startTime, equipmentEntityId, indicatorSetid, sourceMotypeId, targetMotypeId, emsEntityId, bhid);
		List<String> msdFileKey = getMSDFilekeyList(period, startDay, startTime, equipmentEntityId, indicatorSetid, sourceMotypeId, targetMotypeId, emsEntityId, bhid, null);

		
		System.out.println("pia: " + fileKey);
		
		System.out.println("msd set:" + msdFileKey.get(0));
		System.out.println("msd ind:" + msdFileKey.get(1));
	}
	
	
	public static List<String> getMSDFilekeyList(String period, String startDay, // NOPMD
            String startTime, String equipmentEntityId, String indicatorSetid,
            String sourceMotypeId, String targetMotypeId, String emsEntityId, String bhid, String missType)
    {
	    List<String> fileKeyList = new ArrayList<String>();
        String startTimeStr = StringUtils.leftPad(String.valueOf(startTime),
                6,
                '0');
        startTimeStr = StringUtils.substring(startTimeStr, 0, 4);
        String md516Digest = md516(
                indicatorSetid + sourceMotypeId + targetMotypeId + (StringUtils.isEmpty(bhid)?"":bhid));
        equipmentEntityId = StringUtils.leftPad(
                equipmentEntityId == null ? "" : equipmentEntityId, 10, '0');
        String[] keyArray = {md516Digest, period, startDay, startTimeStr,
                StringUtils.substring(indicatorSetid, 0, 3)
                        + StringUtils.substring(sourceMotypeId, 0, 3)
                        + StringUtils.substring(targetMotypeId, 0, 3)
                         , equipmentEntityId};
        fileKeyList.add(StringUtils.join(keyArray, FILEKEY_FIELD_SEPARATOR)+FILEKEY_FIELD_SEPARATOR + "0");
        fileKeyList.add(StringUtils.join(keyArray, FILEKEY_FIELD_SEPARATOR)+FILEKEY_FIELD_SEPARATOR + "1");
        return fileKeyList;
    }
}
