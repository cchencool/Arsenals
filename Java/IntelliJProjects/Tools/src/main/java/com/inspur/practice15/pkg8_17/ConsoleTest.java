package com.inspur.practice15.pkg8_17;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleTest {

    public static void main(String[] args) {
        String str = "";
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        System.out.println("Ctrl + z to exit");
        try {
            str = bufferedReader.readLine();
            while (str != null) {
                System.out.println("Read: " + str);
                str = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
