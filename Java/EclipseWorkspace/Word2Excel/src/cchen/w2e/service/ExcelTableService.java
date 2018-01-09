package cchen.w2e.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cchen.w2e.pojo.Table;
import cchen.w2e.pojo.TableHeader;
import cchen.w2e.pojo.TableRecord;
import cchen.w2e.utils.BeanUtils;

public class ExcelTableService {

//	private static String PATH = PropertiesUtil.getKeyValue("PATH");

	public static void readExcelXls(String FileName) {
		HSSFWorkbook wb = null;
		POIFSFileSystem fs = null;
		try {
			// 设置要读取的文件路径
			fs = new POIFSFileSystem(new FileInputStream(FileName));
			// HSSFWorkbook相当于一个excel文件，HSSFWorkbook是解析excel2007之前的版本（xls）
			// 之后版本使用XSSFWorkbook（xlsx）
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheet = wb.getSheetAt(0);
			HSSFRow headerRow = sheet.getRow(0);
			int lastHeaderNum = headerRow.getLastCellNum();
			for(int i=0; i<lastHeaderNum; i++){
				// 获得行中的列，即单元格
				HSSFCell cell = headerRow.getCell(i);				
				String msg = cell.getStringCellValue();
				System.out.println("Head Find in .xls - " + msg);				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void readExcelXlsx(String fileName) {
		try {
			// 设置要读取的文件路径
			FileInputStream in = new FileInputStream(fileName);// 载入文档
			XSSFWorkbook wb = new XSSFWorkbook(in);
			XSSFSheet sheet = wb.getSheetAt(0);
			XSSFRow headerRow = sheet.getRow(0);
			int lastHeaderNum = headerRow.getLastCellNum();
			for(int i=0; i<lastHeaderNum; i++){
				// 获得行中的列，即单元格
				XSSFCell cell = headerRow.getCell(i);		
				String msg = cell.getStringCellValue();
				System.out.println("Head Find in .xlsx - " + msg);
				cell.setCellValue("Scaned - " + msg);
			}
			// output
//			FileOutputStream fOut = new FileOutputStream(fileName.replace(".xlsx", "") + "_debug.xlsx");
//			wb.write(fOut);
//			fOut.flush();
//			fOut.close();
			in.close();
			wb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Get headers of an Excel
	 * @param fileName
	 * @return
	 */
	public static TableHeader scanHeadersXlsx(String fileName) {
		TableHeader tableHeader = null;
		try {
			Map<Integer, String> headerMaps = new HashMap<Integer, String>();
			FileInputStream inputStream = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
			XSSFSheet sheet = workbook.getSheet("template");
			XSSFRow row = sheet.getRow(0);
			int columnCount = row.getLastCellNum();
			for (int i=0; i<columnCount; i++){
				XSSFCell cell = row.getCell(i);
				String headerName = cell.getStringCellValue();
				headerMaps.put(i, headerName);
				System.out.println("Header Find, headerName - " + headerName + " in index - " + i);
			}
			System.out.println("Finish scan table headers...");
			tableHeader = new TableHeader(headerMaps);
//			table.setTableHeader(tableHeader);
			inputStream.close();
			workbook.close();
//			return true;
		} catch (Exception e) {
			e.printStackTrace();
//			return false;
		}
		return tableHeader;
	}
	
	
	/**
	 * Write records to new file
	 * @param fileName
	 * @param tableHeader
	 */
	public static boolean writeXlsxToNewFile(String fileName, Table table) {
		try {
			TableHeader header = table.getTableHeader();
			List<TableRecord> tableRecords = table.getTableRecords();
			
			if (BeanUtils.isEmpty(header)) {
				return false;
			}
			FileOutputStream out = new FileOutputStream(fileName.replace(".xlsx", "") + "_debug.xlsx");
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			XSSFRow row = sheet.createRow(0);
			// write headers
			for (Integer index : header.getHeaderIndexSet()) {
				XSSFCell cell = row.createCell(index, CellType.STRING);
				cell.setCellValue(header.getHeaderKeyByIndex(index));
			}
			System.out.println("finish write headers...");
			// TODO write values
			for (TableRecord record : tableRecords) {
				row = sheet.createRow(record.getRecordIndex());
				List<String> headerKeys = header.getHeaderKeys();
				for (String headerKey : headerKeys) {
					int cellIndex = header.getHeaderIndexByKey(headerKey);
					CellType cellType = record.getValueTypeByKey(headerKey);
					if(!BeanUtils.isEmpty(cellIndex) && !BeanUtils.isEmpty(cellType)){						
						XSSFCell cell = row.createCell(cellIndex, cellType);
						cell.setCellValue(record.getValueByKey(headerKey));
					}
				}
			}
			System.out.println("finish write records...");
			workbook.write(out);
			out.flush();
			out.close();
			workbook.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	/**
	 * Append records to an exist file
	 * @param fileName
	 * @param tableHeader
	 */
	public static boolean appendXlsx(String fileName, Table table) {
		try {
			TableHeader header = table.getTableHeader();
			List<TableRecord> tableRecords = table.getTableRecords();
			if (BeanUtils.isEmpty(header)) {
				return false;
			}
			FileInputStream inputStream = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.getSheetAt(0);
			int recordCount = sheet.getLastRowNum(); 
			// append records
			for (TableRecord record : tableRecords) {
				XSSFRow row = sheet.createRow(recordCount + record.getRecordIndex());
				List<String> headerKeys = header.getHeaderKeys();
				for (String headerKey : headerKeys) {
					XSSFCell cell = row.createCell(header.getHeaderIndexByKey(headerKey), record.getValueTypeByKey(headerKey));
					cell.setCellValue(record.getValueByKey(headerKey));
				}
			}
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			outputStream.flush();
			outputStream.close();
			inputStream.close();
			workbook.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
//	public static void print1() throws Exception {
//		InputStream is = new FileInputStream("d:\\book1.xls");
//		HSSFWorkbook wb = new HSSFWorkbook(new POIFSFileSystem(is));
//		// A text extractor for Excel files.
//		// Returns the textual content of the file, suitable for indexing by
//		// something like Lucene,
//		// but not really intended for display to the user.
//		// 用来获得整个excel文件的内容，表示为字符串
//		ExcelExtractor extractor = new ExcelExtractor(wb);
//		// 字符串所包含的类型，详见api
//		extractor.setIncludeSheetNames(true);
//		extractor.setFormulasNotResults(false);
//		extractor.setIncludeCellComments(true);
//		// 获得字符串形式
//		String text = extractor.getText();
//		System.out.println(text);
//	}
//
//	public static void print2() throws Exception {
//		HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream("d:\\book1.xls"));
//		HSSFSheet sheet = wb.getSheetAt(0);
//		// 迭代行
//		for (Iterator<Row> iter = (Iterator<Row>) sheet.rowIterator(); iter.hasNext();) {
//			Row row = iter.next();
//			// 迭代列
//			for (Iterator<Cell> iter2 = (Iterator<Cell>) row.cellIterator(); iter2.hasNext();) {
//				Cell cell = iter2.next();
//				// 用于测试的文件就2列，第一列为数字，第二列为字符串
//				// 对于数字cell.getCellType的值为HSSFCell.CELL_TYPE_NUMERIC，为0
//				// 对于字符串cell.getCellType的值为HSSFCell.CELL_TYPE_STRING，为1
//				// 完整的类型列表请查看api
//				String content = cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue() + "" : cell.getStringCellValue();
//				System.out.println(content);
//			}
//		}
//	}

//	public static void main(String[] args) {
////		readExcelXlsx(PATH + "/template.xlsx");
//		scanHeadersXlsx(PATH + "/template.xlsx");
////		System.out.println(isScanSuccess);
////		excelTableTools.writeXlsxToNewFile(PATH + "/template.xlsx", tableHeader);
////		System.out.println('\n');
////		readExcelOfXls(PATH + "/template.xls");
//	}
}
