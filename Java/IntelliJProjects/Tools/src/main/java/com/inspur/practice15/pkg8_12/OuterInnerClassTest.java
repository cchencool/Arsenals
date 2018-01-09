package com.inspur.practice15.pkg8_12;

public class OuterInnerClassTest {
    private static int sizeStatic = 3;
    private final int sizeFinal = 2;
    private int size = 1;

    public int getSize() {
        return size;
    }

    public void doStuff() {
        System.out.println("Outer.doStuff called");
    }

	/* 
	 * 匿名内部类测试有问题
	public void anonymousInnerClassTest()
	{
		new AnonymousInnerClass ()	//此处报错 
		{
			public void anonymousSay() 
			{
				System.out.println("AnonymousInnerClass says hello ");
			}
		}.anonymousSay();
	}
	*/

    public static class InnerStaticClass {
        public void doStuff() {
            // 无法访问到size和sizeFinal
            sizeStatic++;
            System.out.println("InnerStatic.doStuff called, sizeStatic: " + sizeStatic);
        }
    }

    public class InnerClass {
        private int counter = 20;
        private int size = 2 + sizeFinal;

        public void doStuff() {
            System.out.println("Inner.doStuff called ");
            System.out.println("Inner size = " + size);
            OuterInnerClassTest.this.size += (getSize() + counter);    //内部类可以访问外部类的变量和方法
            System.out.println("Inner OuterInnerClassTest.this.size = " + OuterInnerClassTest.this.size);

        }

        public int getCounter() {
            return counter;
        }
    }
}
