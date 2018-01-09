package com.inspur.practice15.pkg8_11;

import java.sql.Date;

public class Employee {
    protected String name;
    protected Date hireDate;
    protected Date dateOfBirth;
    protected String jobTitle;
    protected int salary;

    public Employee() {
        this.name = null;
    }

    public Employee(String name)    //参数列表不同 重载
    {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return "Name: " + name + "\nSalary: " + salary;
    }
}
