package com.inspur.practice15.pkg8_11;

public class Manager extends Employee {
    protected String department;
    protected Employee[] subordinates;

    public Manager() {
        super();
        this.department = null;
    }

    public Manager(String name, String department) {
        super(name);
        this.department = department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    //	public String getDetails()	// super父类方法
//	{
//		return super.getDetails() + "\nDepartment: " + department;
//	}
    public String getDetails()    //方法重载
    {
        return "Name: " + name + "\nManager of: " + department;
    }

}
