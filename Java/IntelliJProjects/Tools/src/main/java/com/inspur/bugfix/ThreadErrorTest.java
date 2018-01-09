package com.inspur.bugfix;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadErrorTest extends Thread {


    public static void main(String[] args) {
        System.out.println((new Date()).getTime() + " " + "system startup.");
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new ThreadErrorTest());
        executor.execute(new ThreadErrorTest());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println((new Date()).getTime() + " " + "system shutdown.");
    }

    @Override
    public void run() {
        System.out.println((new Date()).getTime() + " " + this.getName() + " run start.");
        try {
//			throw new Exception();
            Thread.sleep(900);
            System.out.println((new Date()).getTime() + " " + this.getName() + "throw error.");
            throw new Error();
        } catch (Exception e) {
            System.out.println((new Date()).getTime() + " " + this.getName() + " catch exception.");
        } finally {
            System.out.println((new Date()).getTime() + " " + this.getName() + " finally.");
        }
        System.out.println((new Date()).getTime() + " " + this.getName() + " run end.");
    }

}
