package com.inspur.practice17.pkg11_21;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Usage: to test Stream write.
 * Created by Chen on 18/12/2017.
 */
public class StreamWriteTest {

    public static void main(String[] args) throws IOException {

        System.out.println("output start.");

        String outPath = "/Users/Chen/Desktop/StreamWrite.txt";

        File file = new File(outPath);


        if (!file.exists()) {
            file.createNewFile();
        }

        FileWriter fw = new FileWriter(file);

        IntStream.range(0, 1000)
                .mapToObj(i -> "this is the " + i + " line.")
                .forEach((String str) -> {

                    try {

                        fw.write(str + "\n");

                        fw.flush();

                        Thread.sleep(10);

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                });

        fw.close();

        System.out.println("output end.");

    }

}
