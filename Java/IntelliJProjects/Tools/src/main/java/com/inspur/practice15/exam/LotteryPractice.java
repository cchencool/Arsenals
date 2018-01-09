package com.inspur.practice15.exam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class LotteryPractice {
    public static Scanner scanner;
    public static Writer writer;

    public static Integer checkWinLevel(List<String> winNumbers, List<String> lotteryNumbers) {
        Integer winLevel = 8;
        for (String lotteryNumber : lotteryNumbers) {
            for (String winNumber : winNumbers) {
                if (lotteryNumber.equals(winNumber)) {
                    winLevel--;
                }
            }
        }
        if (winLevel == 8) {
            winLevel = null;
        }
        return winLevel;
    }

    public static void main(String[] args) {
        try {
            scanner = new Scanner(new File("lottery.in"));
            writer = new FileWriter("lottery.out");
            Integer[] winLotterisCountForEachLevel = {0, 0, 0, 0, 0, 0, 0};
            //获得票张数
            int lotteryCount = scanner.nextInt();
            //跳过第一行，读出中奖号码
            scanner.nextLine();
            List<String> winNumbers = new ArrayList<String>();
            Collections.addAll(winNumbers, scanner.nextLine().split(" "));

            List<String> thisLottery = new ArrayList<String>();
            for (int i = 0; i < lotteryCount; i++) {
                thisLottery.clear();
                Collections.addAll(thisLottery, scanner.nextLine().split(" "));
                System.out.println(thisLottery);
                Integer winLevel = checkWinLevel(winNumbers, thisLottery);
                if (null != winLevel) {
                    winLotterisCountForEachLevel[winLevel - 1]++;
                }
            }
            //format output
            String eachLevelWins = "" +
                    winLotterisCountForEachLevel[0] + " " +
                    winLotterisCountForEachLevel[1] + " " +
                    winLotterisCountForEachLevel[2] + " " +
                    winLotterisCountForEachLevel[3] + " " +
                    winLotterisCountForEachLevel[4] + " " +
                    winLotterisCountForEachLevel[5] + " " +
                    winLotterisCountForEachLevel[6];

            System.out.println(eachLevelWins);

            writer.write(eachLevelWins);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                System.out.println("writeException:" + e.getMessage());
            }
        }
    }

}
