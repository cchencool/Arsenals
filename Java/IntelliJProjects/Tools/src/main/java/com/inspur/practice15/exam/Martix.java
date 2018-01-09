package com.inspur.practice15.exam;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Martix {
    protected Integer rowCount; //行数
    protected Integer columnCount;    //列数
    protected boolean isIncludeAxises = true;
    private Integer[][] data;    //data[rowCount][columnCount]
    private Scanner scanner;

    public Martix() {

    }

    public Martix(Integer rowCount, Integer columnCount) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        Integer[][] tempData = new Integer[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tempData[i][j] = 0;
            }
        }
        this.data = tempData;
    }

    public Martix(Integer[][] data) {
        this.data = data;
        this.rowCount = data.length;
        this.columnCount = data[0].length;
    }

    public Martix(File martixInFile) {
        try {
            scanner = new Scanner(martixInFile);
            this.rowCount = scanner.nextInt();
            this.columnCount = scanner.nextInt();
            scanner.nextLine();
            Integer[][] martixData = new Integer[this.rowCount][this.columnCount];
            List<String> dataString = new ArrayList<String>();
            for (int i = 0; i < this.rowCount; i++) {
                dataString.clear();
                Collections.addAll(dataString, scanner.nextLine().split(" "));
                int j = 0;
                for (String data : dataString) {
                    martixData[i][j] = Integer.parseInt(data);
                    j++;
                }
            }
            this.data = martixData;
        } catch (NumberFormatException e) {
            System.out.println("Martix(File martixInFile) exception: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.out.println("Martix(File martixInFile) exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public Martix(List<Integer> list, boolean isRow) {
        if (isRow) {
            this.columnCount = list.size();
            this.rowCount = 1;
            Integer[][] tempData = new Integer[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    tempData[i][j] = list.get(j);
                }
            }
            this.data = tempData;
        } else {
            this.columnCount = 1;
            this.rowCount = list.size();
            Integer[][] tempData = new Integer[rowCount][columnCount];
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < columnCount; j++) {
                    tempData[i][j] = list.get(j);
                }
            }
            this.data = tempData;
        }
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public Integer getColumnCount() {
        return columnCount;
    }

    public void setIncludeAxises(boolean isIncludeAxises) {
        this.isIncludeAxises = isIncludeAxises;
    }

    public Integer[][] getData() {
        return data;
    }

    public void setData(Integer[][] data) {
        this.data = data;
    }

    //include Axis
    public boolean setValueInCoordinate(int rowIndex, int cloumnIndex, Integer data) {
        if (isIncludeAxises) {
            if (cloumnIndex < getColumnCount() &&
                    rowIndex < getRowCount() &&
                    rowIndex >= 0 &&
                    cloumnIndex >= 0) {
                this.data[rowIndex][cloumnIndex] = data;
                return true;
            } else {
                return false;
            }
        } else {
            if (cloumnIndex <= getColumnCount() &&
                    rowIndex <= getRowCount() &&
                    rowIndex >= 1 &&
                    cloumnIndex >= 1) {
                this.data[rowIndex - 1][cloumnIndex - 1] = data;
                return true;
            } else {
                return false;
            }
        }
    }

    //获得指定点的值
    public Integer getValueInCoordinate(int rowIndex, int cloumnIndex) {
        return isIncludeAxises ? data[rowIndex][cloumnIndex] : data[rowIndex - 1][cloumnIndex - 1];
    }

    //获得列
    public List<Integer> getColumnInIndex(Integer index) {
        List<Integer> thisColumn = new ArrayList<Integer>();
        for (int i = 0; i < this.rowCount; i++) {
            thisColumn.add(data[i][index - 1]);
        }
        return thisColumn;
    }

    //获得行
    public List<Integer> getRowInIndex(Integer index) {
        List<Integer> thisRow = new ArrayList<Integer>();
        Collections.addAll(thisRow, data[index - 1]);
        return thisRow;
    }

    //获得所有行或列的和
    public List<Integer> getRowOrColumnSum(boolean isRow) {
        List<Integer> thisBunchSum = new ArrayList<Integer>();
        for (int i = 0; i < rowCount; i++) {
            int j = 0;
            for (Integer integer : (isRow ? getRowInIndex(i + 1) : getColumnInIndex(i + 1))) {
                if (thisBunchSum.size() < (isRow ? columnCount : rowCount)) {
                    thisBunchSum.add(j, integer);
                } else {
                    thisBunchSum.set(j, thisBunchSum.get(j) + integer);
                }
                j++;
            }
        }
        return thisBunchSum;
    }

    //返回每行或列中连续的数字X的个数，返回值为一个行或列list
    public List<Integer> getContinuousXCountInRowOrColumn(boolean isRetrunInRow, boolean isGreatThanOrEqual, Integer xInteger) {
        List<Integer> thisBunchContinuousX = new ArrayList<Integer>();
        //计算每行或列中最大的连续的X个数
        for (int i = 0; i < (isRetrunInRow ? rowCount : columnCount); i++) {    //计算此列或行的
            Integer lastElement;
            Integer continueXCountNow = 0;
            Integer continueXCountMax = 0;
            int j = 0;
            for (Integer integer : (isRetrunInRow ? getRowInIndex(i + 1) : getColumnInIndex(i + 1))) {
                if (j > 0) {
                    lastElement = (isRetrunInRow ? getValueInCoordinate(i, j - 1) : getValueInCoordinate(j - 1, i));
                    if (isGreatThanOrEqual ? xInteger <= lastElement : xInteger == lastElement) {
                        if (isGreatThanOrEqual ? xInteger <= integer : xInteger == integer) {
                            continueXCountNow++;
                            continueXCountMax = (continueXCountMax > continueXCountNow ? continueXCountMax : continueXCountNow);
                        } else {
                            continueXCountMax = (continueXCountMax > continueXCountNow ? continueXCountMax : continueXCountNow);
                            continueXCountNow = 0;
                        }
                    }
                }
                j++;
            }
            thisBunchContinuousX.add((0 == continueXCountMax ? continueXCountMax : continueXCountMax + 1));
        }
        return thisBunchContinuousX;
    }

    @Override
    public String toString() {
        String martixString = "";
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                martixString += (data[i][j] + " ");
            }
            martixString += "\n";
        }
        return martixString;
    }
}
