package com.inspur.practice15.pkg8_17;

import java.util.Enumeration;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Properties props = System.getProperties();
        Enumeration propNames = props.propertyNames();
        while (propNames.hasMoreElements()) {
            String propName = (String) propNames.nextElement();
            String property = props.getProperty(propName);
            System.out.println("property '" + propName + "' is '" + property + "' ");
        }
    }

}
