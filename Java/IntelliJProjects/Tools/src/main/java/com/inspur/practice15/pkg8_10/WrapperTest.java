package com.inspur.practice15.pkg8_10;

public class WrapperTest {
    private int intPrivate = 0;

    public void setIntPrivate(int num) {
        intPrivate = num;
    }

    public void display() {
        System.out.println("call WrapperTest dispaly() method!...\n"
                + "intPrivate =" + intPrivate);
    }
}
