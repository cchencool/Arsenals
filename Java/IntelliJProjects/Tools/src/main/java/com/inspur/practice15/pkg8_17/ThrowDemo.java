package com.inspur.practice15.pkg8_17;

public class ThrowDemo {
    static void demoProc() throws RuntimeException    // 等效于下面被注释的再向外抛出异常的语句
    {
        try {
            throw new RuntimeException("demo");
        } catch (RuntimeException exception) {
            System.out.println("Caught inside demoProc.");
//			throw exception;
        }
    }

    public static void main(String[] args) {
        try {
            demoProc();
        } catch (RuntimeException e) {
            System.out.println("Recaught: " + e);
        }
    }

}
