package cchen.w2e.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cchen.w2e.utils.BeanUtils;

public class TableHeader {
	
	/**
	 * keys
	 */
	private List<String> keys;
	
	
	/**
	 * Map for key to index
	 */
	private Map<String, Integer> key2Index;
	
	
	/**
	 * Map for index 2 key
	 */
	private Map<Integer, String> index2Key;
	
	
	/**
	 * Construct by exist object
	 * @param tableHeader
	 */
	public TableHeader(TableHeader tableHeader) {
		this.keys = tableHeader.getHeaderKeys();
		this.key2Index = tableHeader.getHeaderKey2Index();
		this.index2Key = tableHeader.getHeaderIndex2Key();
	}
	
	
	/**
	 * Construct by key list. Generate headers in order start from 0.
	 * @param keys
	 */
	public TableHeader(List<String> keys) {
		this.keys = keys;
		this.key2Index = new HashMap<String, Integer>();
		this.index2Key = new HashMap<Integer, String>();
		for (int i = 0; i < keys.size(); i++) {
			key2Index.put(keys.get(i), i);
			index2Key.put(i, keys.get(i));
		}
	}
	
	
	/**
	 * Construct by index2Key.
	 * @param index2Key
	 */
	public TableHeader(Map<Integer, String> index2Key) {
		this.index2Key = index2Key;
		this.keys = new ArrayList<String>();
		this.key2Index = new HashMap<String, Integer>();
		Set<Integer> indexs = index2Key.keySet();
		for (Integer index : indexs) {
			key2Index.put(index2Key.get(index), index);
		}
		for (int i = 0; i < index2Key.size(); i++) {
			keys.add(index2Key.get(i));
		}
	}
	
	
	/**
	 * Get header name by index
	 * @param index
	 * @return
	 */
	public String getHeaderKeyByIndex(Integer index) {
		return index2Key.get(index);
	}
	
	
	/**
	 * Get header index by header name
	 * @param key
	 * @return
	 */
	public Integer getHeaderIndexByKey(String key) {
		return key2Index.get(key);
	}

	
	/**
	 * Compare tableHeaders is same in all headers or not
	 * @param tableHeader
	 * @return
	 */
	public boolean isSame(TableHeader tableHeader){
		boolean result = false;
		if(!BeanUtils.isEmpty(tableHeader)){
			result = true;
			Set<Integer> indexs = index2Key.keySet();
			for (Integer index : indexs) {
				if(!this.index2Key.get(index).equals(tableHeader.getHeaderKeyByIndex(index))){
					result = false;
					break;
				}
			}
		}
		return result;
	}
	
	public Map<String, Integer> getHeaderKey2Index() {
		return this.key2Index;
	}
	
	public Map<Integer, String> getHeaderIndex2Key(){
		return this.index2Key;
	}
	
	public List<String> getHeaderKeys() {
		return keys;
	}

	public void setHeaderKeys(List<String> keys) {
		this.keys = keys;
	}
	
	public int getHeaderCount(){
		return keys.size();
	}
	
	public Set<Integer> getHeaderIndexSet(){
		return index2Key.keySet();
	}
	
	
}
