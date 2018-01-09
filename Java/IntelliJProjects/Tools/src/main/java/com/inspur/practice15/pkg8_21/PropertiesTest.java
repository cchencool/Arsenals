package com.inspur.practice15.pkg8_21;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) throws IOException {
        int a, b;
        a = Integer.parseInt("100");
        b = Integer.parseInt("20");
        int c = a / b;
        System.out.println(c);
        Properties props = System.getProperties();

        InputStream in = PropertiesTest.class.getResourceAsStream("config.properties");
        props.load(in);
        in.close();

//		Enumeration propNames = props.propertyNames();
//		while (propNames.hasMoreElements())
//		{
//			String prop_name = (String) propNames.nextElement();
//			String property = props.getProperty(prop_name);
//			System.out.println("Property '" + prop_name + "' is '" + property + "' ");
//		}

        String key = "Driver";
        String value = props.getProperty(key);
        System.out.println(key + ": " + value);

        System.out.println(System.getProperty("java.version"));
    }

}
