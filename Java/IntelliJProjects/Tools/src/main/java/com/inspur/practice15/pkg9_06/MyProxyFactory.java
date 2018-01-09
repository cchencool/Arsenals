package com.inspur.practice15.pkg9_06;

import java.lang.reflect.Proxy;

public class MyProxyFactory {
    static Object getProxy(Object target) throws Exception {
        MyInvocationHandler handler = new MyInvocationHandler();
        handler.setTarget(target);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), handler);
    }
}
