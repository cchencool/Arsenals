package com.inspur.threadabout;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiThreadPractices {

    public static void main(String[] args) {

        Runnable newRun = MultiThreadPractices::runThread;

        Runnable newRun2 = () -> {
            String a = "this is another thread 2. " + Thread.currentThread().getName();
            System.out.println(a);
        };

        Runnable newRun3 = new Runnable() {
            @Override
            public void run() {
                String a = "this is another thread 2. " + Thread.currentThread().getName();
                System.out.println(a);
            }
        };

        ExecutorService es = Executors.newFixedThreadPool(10);

//        Future future = es.submit(ParallelStreamTest::runThread);
//        es.submit(newRun);
        es.submit(MultiThreadPractices::runThread);
        es.submit(newRun2);
        es.submit(newRun3);

        es.shutdown();

    }


    private static void runThread() {

        System.out.println("this is another thread 1. " + Thread.currentThread().getName());

        String res = IntStream.range(1, 20).mapToObj(i -> "val: " + String.valueOf(i)).collect(Collectors.joining(", ", "[", "]"));

        System.out.println(res);

        System.gc();

        Map<Integer, Integer> collect = IntStream
                .range(1, 20)
                .mapToObj(Integer::valueOf)
                .collect(Collectors.groupingBy(i -> i % 3, Collectors.summingInt(Integer::valueOf)));


        System.out.println(collect);


        double[] values = new double[10];
        Arrays.parallelSetAll(values, i -> i * i);
        Arrays.stream(values).forEach(i -> System.out.print(i + " "));

        "asd".intern();

    }
}
