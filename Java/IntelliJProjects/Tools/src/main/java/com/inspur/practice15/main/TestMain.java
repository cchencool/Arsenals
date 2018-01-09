package com.inspur.practice15.main;

public class TestMain {

    public static void main(String[] args)        // 1
    {
        //  2015/08/10
        /*
		WrapperTest warpperTest = new WrapperTest();
		warpperTest.setIntPrivate(10);
		warpperTest.display();
		int numberInt = 500;	//int
		Integer numberInteger = new Integer(numberInt);	//integer
		String numberString = numberInteger.toString();	//string
		int numberFromSting = Integer.parseInt(numberString); //int from string
		System.out.println("Different data type test:" + numberString+','+numberInt+','+numberInteger+','+numberFromSting);

		ClassRuninngOrder calssRunningOrder = new ClassRuninngOrder(); // 2
		calssRunningOrder.printClassInfo();	// 9

		MyPoint myPoint = new MyPoint();
		myPoint.setX(1);
		myPoint.setY(2);
		System.out.println(myPoint.toString());

		TestMain test = new TestMain();
		long sum = test.sumFibonacci(10);
		System.out.println(sum);
		*/

        //	2015/08/11
		/*
		ClassInitOrderChild clsIntOrder = new ClassInitOrderChild(); // 1
		System.out.println(clsIntOrder.getName() + "'s age is " + clsIntOrder.getAge()); // 13
		clsIntOrder.ClassInitOrderChild();

		Employee e = new Employee("老王");
		System.out.println(e.getDetails() + "\n");

		Employee employee = new Manager("小明", "OBD");	// 多态
		//employee.setDepartment("OBD");	//此时会报错，employee无法访问到setDepartment
		System.out.println(employee.getDetails() + "\n");

		if (employee instanceof Manager) {
			Manager manager = (Manager)employee;
			manager.setDepartment("OBD");
			System.out.println(manager.getName() + " is a manager. ");
			System.out.println(manager.getDetails() + "\n");
		}

		//Manager m = new Employee();	//此时会报错，子类类型的变量不能指向父类对象
		*/

        //	2015/08/12

//        System.out.println("StaticTest.aStaticInt = " + StaticTest.aStaticInt);
//        StaticTest.staticMethodTest();
//        System.out.println("StaticTest.aStaticInt = " + StaticTest.aStaticInt);
//
//        OuterInnerClassTest outerClass = new OuterInnerClassTest();
//        outerClass.doStuff();
//        InnerClass innerClass = outerClass.new InnerClass(); //此处必须为外部类的对象.new 创建内部类
//        innerClass.doStuff();
//        InnerStaticClass innerStaticClass = new InnerStaticClass();
//        innerStaticClass.doStuff();
//
//        System.out.println("outerClass.getSize(): " + outerClass.getSize());
//        System.out.println("innerClass.getCounter(): " + innerClass.getCounter());
//        // System.out.println(outerClass.getCounter);	//此处编译错误，外部类不能访问内部类的方法和变量


    }

    public long sumFibonacci(int count) {
        switch (count) {
            case 0:
                return 0L;
            case 1:
                return 0L;
            case 2:
                return 1L;
            default:
                break;
        }
        long sum = 1L;    //一定为long。不然位数不够
        long first = 0L;
        long second = 1;
        long third = 0L;
        for (int i = 2; i < count; i++) {
            third = first + second;
            sum = sum + third;
            first = second;
            second = third;
        }
        return sum;
    }

}
