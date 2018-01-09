package com.inspur.practice17.pkg09_25;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@FunctionalInterface
interface Converter<A, B> {
    B convert(A from);
}

public class J8Test {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return b.compareTo(a);
            }
        });
        System.out.println(names);


        Collections.sort(names, (String a, String b) -> a.compareTo(b));
        System.out.println(names);


        Converter<String, Integer> converter = (from) -> Integer.valueOf(from + "321");
        Integer converted = converter.convert("123");
        System.out.println(converted);    // 123

    }

}