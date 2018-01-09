package cchen.w2e.pojo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;

public class TableRecord {
	
	/**
	 * The header of this record
	 */
	private TableHeader tableHeader;
	
	
	/**
	 * The index of All records of this record
	 */
	private Integer recordIndex;
	
	
	/**
	 * Map for key name to value
	 */
	private Map<String, String> key2Value;
	
	
	/**
	 * Map for key name to valueTyep. Default CellType.String
	 */
	private Map<String, CellType> key2ValueType;
	
	
	/**
	 * Construct by tableHeader & record index, default all values are empty string.
	 * @param tableHeader
	 * @param recordIndex
	 */
	public TableRecord(TableHeader tableHeader, Integer recordIndex) {
		this.tableHeader = tableHeader;
		this.recordIndex = recordIndex;
		this.key2Value = new HashMap<String, String>();
		this.key2ValueType = new HashMap<String, CellType>();
		for (String key : tableHeader.getHeaderKeys()) {
			key2Value.put(key, "");
			key2ValueType.put(key, CellType.STRING);
		}
	}
	
	
	/**
	 * Construct by tableHeader & values & record index.
	 * @param tableHeader
	 * @param key2Value
	 * @param recordIndex
	 */
	public TableRecord(TableHeader tableHeader, Map<String, String> key2Value, Integer recordIndex) {
		this.tableHeader = tableHeader;
		this.recordIndex = recordIndex;
		this.key2Value = new HashMap<String, String>();
		this.key2ValueType = new HashMap<String, CellType>();
		for (String key : key2Value.keySet()) {
			this.key2Value.put(key, key2Value.get(key));
			this.key2ValueType.put(key, CellType.STRING);
		}
	}
	
	
	/**
	 * Extract self columns by input tableHeader
	 * @param tableHeader
	 * @return
	 */
	public TableRecord extractSelfByTableHeader(TableHeader tableHeader) {
		List<String> newHeaders = tableHeader.getHeaderKeys();
		List<String> headers = this.tableHeader.getHeaderKeys();
		for (String headerKey : newHeaders) {
			if(!headers.contains(headerKey)){	// performance problem may be here.
				headers.remove(headerKey);
				key2Value.remove(headerKey);
				key2ValueType.remove(headerKey);
			}
		}
		return this;
	}

	public void setValueForKey(String key, String value) {
		key2Value.put(key, value);
	}
	
	public String getValueByKey(String key) {
		return key2Value.get(key);
	}
	
	public void setValueTypeForKey(String key, CellType type) {
		key2ValueType.put(key, type);
	}
	
	public CellType getValueTypeByKey(String key) {
		return key2ValueType.get(key);
	}
	
	public Integer getRecordIndex() {
		return recordIndex;
	}
	
	public void changeRecordIndex(int index) {
		this.recordIndex = index;
	}
	
	public TableHeader getTableHeader(){
		return tableHeader;
	}
	
}
