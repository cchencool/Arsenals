package com.inspur.pmv6.vo;

import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang.yi on 12/26/2017.
 */
public class HiveVO implements Serializable {

    private List<String> structFieldNames;
    private List<Object> fieldValues;

    public HiveVO(List<String> structFieldNames, List<Object> fieldValues) {
        this.structFieldNames = structFieldNames;
        this.fieldValues = fieldValues;
    }

    public List<String> getStructFieldNames() {
        return structFieldNames;
    }

    public void setStructFieldNames(List<String> structFieldNames) {
        this.structFieldNames = structFieldNames;
    }


    public List<Object> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<Object> fieldValues) {
        this.fieldValues = fieldValues;
    }
}
