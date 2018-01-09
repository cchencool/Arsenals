package com.inspur.practice15.pkg8_12;

public class StaticTest {

    public static int aStaticInt = 0;
    private int aNotStaticInt = 0;

    public static void staticMethodTest() {
        // this  static方法此处无法调用this
        aStaticInt++;
        System.out.println("StaticMethodTest has been called!\n"
                + "aStaticInt plused one ");
    }
}
