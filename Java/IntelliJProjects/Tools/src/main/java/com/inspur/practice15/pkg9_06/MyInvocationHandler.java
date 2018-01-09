package com.inspur.practice15.pkg9_06;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Object target;

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        DogUtil dU = new DogUtil();

        dU.method1();
        Object result = method.invoke(target, args);    //this line call method in GunDog
        dU.method2();

        return result;
    }

}
