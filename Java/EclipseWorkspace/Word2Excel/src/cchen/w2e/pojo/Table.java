package cchen.w2e.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cchen.w2e.utils.BeanUtils;

public class Table {
	
	private TableHeader tableHeader;
	
	private List<TableRecord> tableRecords = new ArrayList<TableRecord>();
	
	private Map<Integer, TableRecord> tableRecordMap = new HashMap<Integer, TableRecord>();
	
	public Table(TableHeader tableHeader) {
		this.setTableHeader(tableHeader);
	}
	
	public Table(List<TableRecord> tableRecords) {
		this.addTableRecord(tableRecords);
	}
	
	public TableHeader getTableHeader(){
		return this.tableHeader;
	}
	
	public void setTableHeader(TableHeader tableHeader){
		this.tableHeader = tableHeader;
	}
	
	public List<TableRecord> getTableRecords() {
		return tableRecords;
	}
	
	public void setTableRecords(List<TableRecord> tableRecords) {
		this.tableRecords = tableRecords;
	}
	
	
	/**
	 * Add single record to table content
	 * @param tableRecord
	 * @param isExtractByHeaders
	 * @return
	 */
	public boolean addTableRecord(TableRecord tableRecord, boolean isExtractByHeaders) {
		boolean isAddSuccess = false;
		if (BeanUtils.isEmpty(this.tableHeader)) {
			this.tableHeader = new TableHeader(tableRecord.getTableHeader());
		}
		if(checkRecordHeaderCorrect(tableRecord)){
			this.tableRecords.add(tableRecord);
			this.tableRecordMap.put(tableRecord.getRecordIndex(), tableRecord);
			isAddSuccess = true;
		}else{
			if(isExtractByHeaders){
				tableRecord = extractTableRecordByTableHeader(this.tableHeader, tableRecord);
				this.tableRecords.add(tableRecord);
				this.tableRecordMap.put(tableRecord.getRecordIndex(), tableRecord);
				isAddSuccess = true;
			}
			isAddSuccess = false;
		}
		return isAddSuccess;
	}
	
	
	/**
	 * Add a list of record to table content
	 * @param tableRecords
	 * @param isExtractByHeaders
	 * @return
	 */
	public boolean addTableRecord(List<TableRecord> tableRecords, boolean isExtractByHeaders) {
		boolean isAddSuccess = false;
		for (TableRecord tableRecord : tableRecords) {
			isAddSuccess = this.addTableRecord(tableRecord, isExtractByHeaders);
			if (!isAddSuccess) {
				break;
			}
		}
		return isAddSuccess;
	}
	
	
	/**
	 * default not extract
	 * @param tableRecord
	 * @return
	 */
	public boolean addTableRecord(TableRecord tableRecord) {
		return addTableRecord(tableRecord, false);
	}
	
	
	/**
	 * default not extract
	 * @param tableRecords
	 * @return
	 */
	public boolean addTableRecord(List<TableRecord> tableRecords) {
		return addTableRecord(tableRecords, false);
	}
	
	/**
	 * Delete single record of table content
	 * @param index
	 */
	public void deleteTableRecord(int index) {
		this.tableRecords.remove(index);
	}
	
	
	private boolean checkRecordHeaderCorrect(TableRecord tableRecord){
		boolean result = false;
		if(!BeanUtils.isEmpty(this.tableHeader)){
			result = this.tableHeader.isSame(tableRecord.getTableHeader());
		}
		return result;
	}
	
	
	private TableRecord extractTableRecordByTableHeader(TableHeader tableHeader, TableRecord tableRecord){
		return tableRecord.extractSelfByTableHeader(tableHeader);
	}
}
