package com.inspur.practice15.exam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class sumExample {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(new File("sum.in"));
            Writer writer = new FileWriter("sum.out");

            int n = scanner.nextInt();
            int s = 0;
//			System.out.println(scanner.nextLine());
//			System.out.println(scanner.nextLine());
            for (int i = 0; i < n; i++) {
                int a = scanner.nextInt();
                s += a;
                System.out.println(a);
            }

            writer.write(String.valueOf(s));

            scanner.close();
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

}
