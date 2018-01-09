package cchen.w2e.pojo;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordScanModel {

	private static WordScanModel wordScanModel = new WordScanModel();
	
	private Map<Integer, String> index2Keywords;

	private WordScanModel() {
		index2Keywords = new HashMap<Integer, String>();
	}
	
	public static WordScanModel getInstance() {
		return wordScanModel;
	}
	
	public void putNewMap(Integer index, String keyword) {
		index2Keywords.put(index, keyword);
	}
	
	public Set<Integer> getKeySet() {
		return index2Keywords.keySet();
	}
	
	public String getKeywordByIndex(Integer index) {
		return index2Keywords.get(index);
	}
}
