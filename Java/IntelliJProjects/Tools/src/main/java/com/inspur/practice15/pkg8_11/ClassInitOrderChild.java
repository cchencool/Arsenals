package com.inspur.practice15.pkg8_11;

public class ClassInitOrderChild extends ClassInitOrderParent {
    private String name;    // 7
    private int age;    // 8

    public ClassInitOrderChild()    // 6
    {
        System.out.println("正在初始化子类...");    // 9
        name = "Tom";    // 10
        age = 20;    // 11
    }    // 12

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

}
