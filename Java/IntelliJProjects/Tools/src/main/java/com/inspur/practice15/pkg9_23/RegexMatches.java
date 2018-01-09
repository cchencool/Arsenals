package com.inspur.practice15.pkg9_23;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatches {
    private static final String REGEX = "[1-9]|([1-2]\\d)|(3[0-1])";//1~31
    private static final String INPUT = "11";
    private static Pattern pattern;
    private static Matcher matcher;

    public static void main(String args[]) {
        pattern = Pattern.compile(REGEX);
        matcher = pattern.matcher(INPUT);

        System.out.println("Current REGEX is: " + REGEX);
        System.out.println("Current INPUT is: " + INPUT);

        System.out.println("lookingAt(): " + matcher.lookingAt());
        System.out.println("matches(): " + matcher.matches());
    }
}
