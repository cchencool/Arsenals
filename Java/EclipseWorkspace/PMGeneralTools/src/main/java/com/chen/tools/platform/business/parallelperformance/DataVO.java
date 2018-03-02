package com.chen.tools.platform.business.parallelperformance;

import java.util.concurrent.*;

public class DataVO
{
    private String id;
    private ConcurrentHashMap<String, Integer> valueMap;
    
    public DataVO(final String id, final ConcurrentHashMap<String, Integer> map) {
        this.id = id;
        this.valueMap = map;
    }
    
    public String getId() {
        return this.id;
    }
    
    public void setId(final String id) {
        this.id = id;
    }
    
    public ConcurrentHashMap<String, Integer> getValueMap() {
        return this.valueMap;
    }
    
    public void setValueMap(final ConcurrentHashMap<String, Integer> valueMap) {
        this.valueMap = valueMap;
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("id: " + this.id);
        sb.append("\n");
        sb.append("val: " + this.valueMap);
        sb.append("\n");
        return sb.toString();
    }
}
