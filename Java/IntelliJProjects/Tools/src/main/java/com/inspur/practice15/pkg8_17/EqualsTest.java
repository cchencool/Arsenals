package com.inspur.practice15.pkg8_17;

public class EqualsTest {
    public int value = 0;
    public String valueString = "";
//	public boolean equals (Object obj)
//	{
//		if (this == obj)
//		{
//			return true;
//		}
//		if (obj == null)
//		{
//			return false;
//		}
//		
//		if (obj instanceof EqualsTest)
//		{
//			EqualsTest eT = (EqualsTest) obj;
//			if (this.value == eT.value)
//			{
//				return true;
//			}else {
//				return false;
//			}
//		}else {
//			return false;
//		}
//	}


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EqualsTest eT1 = new EqualsTest();
        EqualsTest eT2 = new EqualsTest();
        eT1.value = 4;
        eT1.valueString = "Hello";
        eT2.value = 4;
        eT2.valueString = "Hello";
        System.out.println("eT1 == eT2 result:" + (eT1 == eT2)); // 引用
        System.out.println("eT1 equals eT2 result: " + (eT1.equals(eT2))); // 引用
        System.out.println("eT1.intValue == eT2.intValue result:" + (eT1.value == eT2.value)); // 内容
//		System.out.println("eT1.intValue equals eT2.intValue result: " + (eT1.value.equals(eT2.value)));	// can't
        System.out.println("eT1.stringValue == eT2.stringValue result:" + (eT1.valueString == eT2.valueString));    // 内容
        System.out.println("eT1.stringValue equals eT2.stringValue result: " + (eT1.valueString.equals(eT2.valueString))); // 内容


    }

}

