package com.inspur.practice15.pkg8_11;

public class ClassInitOrderParent {
    private int num = 1; // 3

    public ClassInitOrderParent() // 2
    {
        System.out.println("正在初始化父类..."); // 4
    }    // 5

    public void ClassInitOrderChild() {
        System.out.println("这是父类ClassInitOrderChild()方法...");
    }
}
