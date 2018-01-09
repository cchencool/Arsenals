package com.inspur.practice15.pkg8_13;

public class Test4_9 {
    public static void main(String[] args) {
        double num = 7.4;
        int a = (int) Math.abs(num + 0.5); //截取
        int b = (int) Math.ceil(num + 0.5); //四舍五入后截取
        int c = (int) Math.floor(num + 0.5); //截取
        int d = (int) Math.round(num + 0.5); //四舍五入后截取
        int e = (int) Math.round(num - 0.5); //同上
        int f = (int) Math.floor(num - 0.5); //截取
        int g = (int) Math.ceil(num - 0.5); //四舍五入后截取
        int h = (int) Math.abs(num - 0.5); //截取
        System.out.println("a=" + a);
        System.out.println("b=" + b);
        System.out.println("c=" + c);
        System.out.println("d=" + d);
        System.out.println("e=" + e);
        System.out.println("f=" + f);
        System.out.println("g=" + g);
        System.out.println("h=" + h);
    }
}