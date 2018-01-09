package com.inspur.practice17.pkg09_25;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PiEstimation {

    public final static int count = 10;

    public static void main(String[] args) {
        int c = count;
        switch (args.length) {
            case 1:
                c = (args.length >= 1 && args[0] != null) ? Integer.parseInt(args[0]) : count;
                doList(c);
                break;
            case 2:
                if ("-one".equals(args[0])) {
                    c = (args.length >= 1 && args[0] != null) ? Integer.parseInt(args[1]) : count;
                }
                doOne(c);
                break;
            default:
                doList(c);
                break;
        }

    }

    public static void doList(int c) {
        for (int i = 1000, j = 0; j < c; i = i * 10, j++) {
            System.out.println("Round " + j + ": base count - " + i);
            getPi(i);
            System.out.println("\n");
        }
    }

    public static void doOne(int c) {
        int i = (int) Math.pow(10, c);
        System.out.println("base count - " + i);
        getPi(i);
        System.out.println("\n");
    }

    public static double getPi(int NUM_SAMPLES) {
        long insertTaskStartTime = System.currentTimeMillis();
        List<Integer> list = new ArrayList<Integer>(Collections.nCopies(NUM_SAMPLES, 1));
        System.out.println("cost: " + (System.currentTimeMillis() - insertTaskStartTime));

        insertTaskStartTime = System.currentTimeMillis();
        long count = list.parallelStream().filter(i -> {
            double x = Math.random();
            double y = Math.random();
            return x * x + y * y < 1;
        }).count();
        System.out.println("cost: " + (System.currentTimeMillis() - insertTaskStartTime));

        double pi = 4.0 * count / NUM_SAMPLES;

        System.out.println("Pi is roughly " + pi);


        return pi;
    }

    public static String getCurrentTimeMillisec() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
