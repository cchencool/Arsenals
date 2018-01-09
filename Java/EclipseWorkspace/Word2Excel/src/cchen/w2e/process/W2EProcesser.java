package cchen.w2e.process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cchen.w2e.pojo.Table;
import cchen.w2e.pojo.TableHeader;
import cchen.w2e.pojo.TableRecord;
import cchen.w2e.service.ExcelTableService;
import cchen.w2e.service.WordTableService;
import cchen.w2e.utils.BeanUtils;
import cchen.w2e.utils.PropertiesUtil;

public class W2EProcesser {
	
	private static String PATH = PropertiesUtil.getKeyValue("PATH");

	
	public static void main(String[] args) {
		
		try {
			if (BeanUtils.isEmpty(PATH)) {
				PATH = "./";
			}

			if (!PATH.endsWith("/")) {
				PATH += "/";
			}

			File file = new File(PATH);
			System.out.println(PATH);
			File[] tempList = file.listFiles();
			System.out.println("Files amount in dir：" + tempList.length);

			File template = new File(PATH + "template.doc");
			if (!template.exists()) {
				System.out.println("Could not find the template.doc in dir:" + PATH + " ...");
			} else {
				TableHeader tableHeader = WordTableService.scanTemplateDoc(PATH + "template.doc");
				List<TableRecord> tableRecords = new ArrayList<TableRecord>();
				int fileIndex = 1;
				for (int i = 0; i < tempList.length; i++) {
					if (tempList[i].isFile()) {
						String fileName = tempList[i].getName();
						System.out.println("File Name：" + fileName);
						if (fileName.endsWith(".doc") && !fileName.equals("template.doc")) {
							tableRecords.add(WordTableService.scanDocFile(PATH + fileName, fileIndex, tableHeader));
							fileIndex++;
						}
					}
					if (tempList[i].isDirectory()) {
						System.out.println("Dir  Name：" + tempList[i]);
					}
				}
				
				Table table = new Table(tableRecords);
				ExcelTableService.writeXlsxToNewFile(PATH + "result.xlsx", table);
			}
			
			System.out.println("\nPress Enter to exist...");
			System.in.read();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
