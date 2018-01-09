package com.inspur.practice15.pkg8_17;

import java.util.Random;

public class HandleError {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int a = 0, b = 0, c = 0;
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            try {
                b = r.nextInt();
                c = r.nextInt();
                a = 1000 / (b / c);
            } catch (ArithmeticException e) {
                // TODO: handle exception
                System.out.println("Division by zero.");
                a = 0;
            }
            System.out.println("a: " + a);
        }
    }

}
