package com.inspur.practice15.pkg8_14;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

;

public class ArrayTest {
    public String str = "ArrayTest";

    public static void main(String[] args) {
        char[] s = new char[6];
        char[] copyS = new char[10];
        int[] nums = {3, 4, 1, 5, 2};

        //char[] Init & copy
        for (int i = 0; i < s.length; i++) {
            s[i] = (char) ('a' + i);
            System.out.println("s[" + i + "] = " + s[i]);
        }
        System.arraycopy(s, 0, copyS, 0, s.length);
        for (char c : copyS) {
            System.out.println("copyS: " + c);
        }

        //Object[]
        ArrayTest[] arrayTest = {
                new ArrayTest(),
                new ArrayTest(),
                new ArrayTest()
        };
        for (ArrayTest iArrayTest : arrayTest) {
            System.out.println(iArrayTest.str);
        }

        Color[] palette = {
                Color.blue,
                Color.red,
                Color.white
        };

        //int[] sort
        for (int i : nums) {
            System.out.println("Before Sort: " + i);
        }
        Arrays.sort(nums);
        for (int i : nums) {
            System.out.println("After Sort: " + i);
        }

        //List
        List<Integer> list = Arrays.asList(2, 4, 6, 7, 9, 10, 20);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i) + ",");
        }

        //Arrays.binarySearch()使用前要确保数组已排序
        int[] arr = {1, 3, 5, 7, 9, 11, 65, 87, 98};
        System.out.println(Arrays.binarySearch(arr, 7));
    }
}
