package cchen.w2e.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableIterator;
import org.apache.poi.hwpf.usermodel.TableRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;

import cchen.w2e.pojo.TableHeader;
import cchen.w2e.pojo.TableRecord;
import cchen.w2e.pojo.WordScanModel;
import cchen.w2e.utils.BeanUtils;

public class WordTableService {

//	private static String PATH = PropertiesUtil.getKeyValue("PATH");
	
//	private static List<String> KEYWORDS = Arrays.asList(PropertiesUtil.getKeyValue("KEYWORDS").split("\\|"));
//	
//	private static Map<String, String> index2TagsMap = new HashMap<String, String>();
//	
//	private static Map<String, String> tag2indexesMap = new HashMap<String, String>();

	/**
	 * 获取word文档里面所有表格
	 * 
	 * @param docx word2007+
	 *            Document 对象
	 * @throws IOException 
	 */
	public static void scanTemplateDocx(String FileName) throws IOException {
		int index = 1; 
		List<String> headers = new ArrayList<String>();
		FileInputStream in = new FileInputStream(FileName);// 载入文档
		XWPFDocument docx = new XWPFDocument(in);
//		XWPFDocument docx = hwpf.getXWPFDocument();
		Iterator<XWPFTable> tableIt = docx.getTablesIterator();
		while (tableIt.hasNext()) {
			XWPFTable table = tableIt.next();
//			String rowInfo = "";
			for (int i = 0; i < table.getRows().size(); i++) {
				List<XWPFTableCell> cells = table.getRow(i).getTableCells(); // 获得所有列
				for (int j = 0; j < cells.size(); j++) {
					XWPFTableCell cell = cells.get(j); 
//					rowInfo += cells.get(k).getText().trim() + ";";
					List<XWPFParagraph> paragraphs = cell.getParagraphs();
					for (int k = 0; k < paragraphs.size(); k++) {
						XWPFParagraph paragraph = paragraphs.get(k);
						String s = paragraph.getText();
						saveMatchedKeyWords(index, s, headers);
						index++;
					}
				}
			}
//			System.out.println(rowInfo);
		}
		docx.close();
	}
	
	/**
	 * 获取word文档里面所有表格
	 * 
	 * @param doc word2003-
	 *            Document 对象
	 */
	public static TableHeader scanTemplateDoc(String FileName) {
		TableHeader tableHeader = null;
		try {
			int index = 1;
			List<String> headers = new ArrayList<String>();
			FileInputStream in = new FileInputStream(FileName);// 载入文档
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(range);
			// 迭代文档中的表格
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				// 迭代行，默认从0开始
				for (int i = 0; i < tb.numRows(); i++) {
					TableRow tr = tb.getRow(i);
					// 迭代列，默认从0开始
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);// 取得单元格
						// 取得单元格的内容
//						for (int k = 0; k < td.numParagraphs(); k++) {
//							Paragraph para = td.getParagraph(k);
//							String s = para.text();	
						String s = td.text();
						saveMatchedKeyWords(index, s, headers);
							
//							if(s.matches(".*(\\$\\{.[^\\}]*\\}).*")){
//								System.out.println("Find Tag - " + s + " ; index - " + index );
////								index2TagsMap.put(String.valueOf(index), s);
////								tag2indexesMap.put(s, String.valueOf(index));
//							}
//							if(s.matches("^\\$\\{.[^\\}]*\\}\\-#\\{.[^\\}]*\\}$")){
//								System.out.println("Find TagSeq - " + s + " ; index - " + index );
////								index2TagsMap.put(String.valueOf(index), s);
////								tag2indexesMap.put(s, String.valueOf(index));
//							}
						index++;
//						}
					}
				}
			}
			tableHeader = new TableHeader(headers);
//			FileOutputStream fOut = new FileOutputStream(FileName.replace(".doc", "") + "_template.doc");
//			hwpf.write(fOut);
//			fOut.flush();
//			fOut.close();
			hwpf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableHeader;
	}

	
	public static TableRecord scanDocFile(String FileName, int fileIndex, TableHeader tableHeader) {
		TableRecord tableRecord = null;
		try {
			Map<String, String> key2Value = new HashMap<String, String>();
			WordScanModel wordScanModel = WordScanModel.getInstance();
			int index = 1;
			FileInputStream in = new FileInputStream(FileName);// 载入文档
			POIFSFileSystem pfs = new POIFSFileSystem(in);
			HWPFDocument hwpf = new HWPFDocument(pfs);
			Range range = hwpf.getRange();// 得到文档的读取范围
			TableIterator it = new TableIterator(range);
			// 迭代文档中的表格
			while (it.hasNext()) {
				Table tb = (Table) it.next();
				// 迭代行，默认从0开始
				for (int i = 0; i < tb.numRows(); i++) {
					TableRow tr = tb.getRow(i);
					// 迭代列，默认从0开始
					for (int j = 0; j < tr.numCells(); j++) {
						TableCell td = tr.getCell(j);// 取得单元格
						// 取得单元格的内容
//						for (int k = 0; k < td.numParagraphs(); k++) {
							
						if(wordScanModel.getKeySet().contains(index)){
//								Paragraph para = td.getParagraph(k);
//								String value = para.text();
							String value = td.text();
							String keyword = wordScanModel.getKeywordByIndex(index);
							key2Value.put(keyword, value.trim());
							System.out.println("Find value matched " + keyword + " - " + value.trim());
						}
						
						index++;
//						}
					}
				}
			}
			tableRecord = new TableRecord(tableHeader, key2Value, fileIndex);
			hwpf.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tableRecord;
	}
	
	
	private static boolean saveMatchedKeyWords(int index, String str, List<String> keywordList) {
		
		boolean isMatched = false;
		if(str != null){
			str = str.trim();
		}
		//匹配${key}或者${key}-#{seq}-&{enum}
		String regex = "(\\$\\{(.[^\\}]*)\\})(\\-#\\{(.[^\\}]*)\\}){0,1}(\\-&\\{(.[^\\}]*)\\}){0,1}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		
		while (matcher.find()) {
			String key = BeanUtils.isEmpty(matcher.group(2)) ? "" : matcher.group(2);
			String seq = BeanUtils.isEmpty(matcher.group(4)) ? "" : matcher.group(4);
			String[] enumValues = BeanUtils.isEmpty(matcher.group(5)) ? null : matcher.group(5).split("|"); 
			
			System.out.println("Find TagSeq - " + str + " ; index - " + index );
			System.out.println("Match string key - " + key);
			System.out.println("Match string seq - " + seq);
			System.out.println("Match string enum - " + (BeanUtils.isEmpty(enumValues) ? null : Arrays.asList(enumValues)));
			
			String keyword = BeanUtils.isEmpty(seq) ? key : key + "-" + seq;
			WordScanModel.getInstance().putNewMap(index, keyword);
			keywordList.add(keyword);
			isMatched = true;
		}
		return isMatched;
	}
	
//	public static void main(String[] args) {
//
//		File file = new File(PATH);
//		File[] tempList = file.listFiles();
//		System.out.println("Files amount in dir：" + tempList.length);
//		for (int i = 0; i < tempList.length; i++) {
//			if (tempList[i].isFile()) {
//				String fileName = tempList[i].getName();
//				System.out.println("File Name：" + fileName);
//				if (!".DS_Store".equals(fileName) && fileName.endsWith(".doc") && fileName.equals("template.doc")) {
//					scanTemplateDoc(PATH + fileName);
//				}
//			}
//			if (tempList[i].isDirectory()) {
//				System.out.println("Dir  Name：" + tempList[i]);
//			}
//		}
//
//	}
}

