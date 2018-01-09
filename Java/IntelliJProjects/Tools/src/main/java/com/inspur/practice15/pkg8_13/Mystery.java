package com.inspur.practice15.pkg8_13;

public class Mystery {
    String s = "a";

    public static void main(String[] args) {
        Mystery m = new Mystery();
        m.go();
        m.Mystery();
    }

    void Mystery()    //有void前缀并非构造函数
    {
        this.s = "constructor";
        System.out.println(s);
    }

    void go() {
        System.out.println(s);
    }

}
