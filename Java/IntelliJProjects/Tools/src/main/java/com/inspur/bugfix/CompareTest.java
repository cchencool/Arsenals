package com.inspur.bugfix;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompareTest extends Thread {

    private static String lock = "1";

//	private List<String> anotherlist = null;
    private List<String> list = null;

    public CompareTest(List<String> list) { //, List<String> anotherlist) {
        this.list = list;
//		this.anotherlist = anotherlist;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        List<String> list_A = new ArrayList<String>();
        List<String> list_B = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            String aString = "";
            char c = (char) ('a' + ((Math.random() * 100) / 4) % 26);
            char d = (char) ('a' + ((Math.random() * 100) / 4) % 26);
            aString += c;
            aString += d;
            list.add(aString);
            list_A.add(aString);
            list_B.add(aString);
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);
        executor.execute(new CompareTest(list));
        executor.execute(new CompareTest(list));
//		executor.execute(new CompareTest(list_A));
//		executor.execute(new CompareTest(list_B));
    }

    @Override
    public void run() {
        System.out.println(this.getName() + " run start.");

        synchronized (this.list)//(lock)
        {
            Collections.sort(list, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });

//			Collections.sort(anotherlist, new Comparator<String>(){
//				public int compare(String o1, String o2)
//				{
//					return o1.compareTo(o2);
//				}
//			});

        }

//		System.out.println( this.getName() + " sort list end.");
//
//		System.out.println( this.getName() + " sort anotherlist end.");

        System.out.println(this.getName() + " run end.");
//		System.out.println( this.getName() + ":" +  list);
    }
}
