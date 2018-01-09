package com.inspur.practice15.pkg8_10;

public class ClassRuninngOrder {
    private String name;    // 4
    private int age;        // 5

    public ClassRuninngOrder()    // 3
    {
        // TODO Auto-generated constructor stub
        this.name = "Tom";        // 6
        this.age = 22;            // 7
    }                        // 8

    public void printClassInfo()    // 10
    {
        System.out.println(name + "'s age is " + age);    // 11
    }    // 12
}
