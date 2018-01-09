package com.inspur.practice15.pkg8_17;

import java.util.Calendar;

public class CalendarTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -213);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);    //  *month : start from 0
        int day = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("result: " + year + "年" + (month + 1) + "月" + day + "日");

    }

}