package com.inspur.practice15.pkg8_17;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
    public static void main(String[] args) {
        DateFormat dF = new SimpleDateFormat("YYYY-MM-dd HH:MM:SS SSS");    // 注意大小写
        Date d = new Date();
        String str = dF.format(d);
        System.out.println("Now: " + str);

        try {
            Date d2 = dF.parse("2012-07-27 08:08:08 888");
            System.out.println("伦敦奥运会开幕时间是:" + d2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
