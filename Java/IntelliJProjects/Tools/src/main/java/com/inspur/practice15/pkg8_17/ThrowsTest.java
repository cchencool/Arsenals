package com.inspur.practice15.pkg8_17;

public class ThrowsTest {
    public static void exceptionExample() throws ExampleException, LookupException {
        throw new ExampleException("Throw me", 1);
    }

    public static void main(String[] args) // throws ExampleException, LookupException // 要么catch全部、要么就throws
    {
        try {
            ThrowsTest.exceptionExample();
        } catch (ExampleException e) {
            System.out.println(e.getMessage());
        } catch (LookupException e) {
            e.printStackTrace();
        }
    }
}
