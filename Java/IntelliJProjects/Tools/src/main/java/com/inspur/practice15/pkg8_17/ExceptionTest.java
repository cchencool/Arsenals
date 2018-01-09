package com.inspur.practice15.pkg8_17;

public class ExceptionTest {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            int d = 0;

            try {
                throw new Exception("can't be dividde by zero! ");
//				int b = 2 / d;
            } catch (ArithmeticException e2) {
                System.out.println("inner try-catch");
            } catch (Exception err) {
                err.printStackTrace();
            }

            int a = 1 / d;

            System.out.println("This will not be printed");
        } catch (ArithmeticException e1) {
            System.out.println("divison by zero, program continue run");
        } finally {
            System.out.println("Finally box");
        }
        System.out.println("After try-catch-finally ");
    }

}
