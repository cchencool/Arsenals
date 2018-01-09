//Martix
package com.inspur.practice15.exam;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

public class MartixPractice {
    //	public static Scanner scanner;
    public static Writer writer;

    public static void main(String[] args) {
        try {
            Martix martix = new Martix(new File("matrix.in"));
            writer = new FileWriter("matrix.out");

            System.out.println(martix);
            //先算出每行每列最多的连续1个个数，得到的数组为处理后行数组和处理后列数组
            List<Integer> continuousOneCountInRowList = martix.getContinuousXCountInRowOrColumn(true, false, 1);
            List<Integer> continuousOneCountInColumnList = martix.getContinuousXCountInRowOrColumn(false, false, 1);
            System.out.println("每行最大连续1的个数：" + continuousOneCountInRowList);
            System.out.println("每列最大连续1的个数：" + continuousOneCountInColumnList);

            Integer maxEffectiveX = 0, maxEffectiveY = 0,    //处理后RC数组中最大的有效值，有效值指（此值的大小）不小于（（不小于此值的数）的个数）的值。
                    xCount = 0, yCount = 0,    //处理后数组的当前值的个数
                    maxXInC = null, minXInC = null,    //处理后列中的最大、最小数字
                    maxYInR = null, minYInR = null;        //处理后行中的最大、最小数字

            Collections.sort(continuousOneCountInRowList);
            Collections.sort(continuousOneCountInColumnList);

            maxXInC = continuousOneCountInColumnList.get(continuousOneCountInColumnList.size() - 1);
            minXInC = continuousOneCountInColumnList.get(0);
            maxYInR = continuousOneCountInRowList.get(continuousOneCountInRowList.size() - 1);
            minYInR = continuousOneCountInRowList.get(0);

            for (Integer integer = minXInC; integer <= maxXInC; integer++) {
                xCount = (new Martix(continuousOneCountInRowList, true)).
                        getContinuousXCountInRowOrColumn(true, true, integer).get(0);
                if (integer <= xCount) {
                    maxEffectiveX = maxEffectiveX > integer ? maxEffectiveX : integer;
                }
            }
            for (Integer integer = minYInR; integer <= maxYInR; integer++) {
                yCount = (new Martix(continuousOneCountInColumnList, true)).
                        getContinuousXCountInRowOrColumn(true, true, integer).get(0);
                if (integer <= yCount) {
                    maxEffectiveY = maxEffectiveY > integer ? maxEffectiveY : integer;
                }
            }
            System.out.println("行最大有效值：" + maxEffectiveX + "\n" + "列最大有效值：" + maxEffectiveY);
            int a = (maxEffectiveX > maxEffectiveY ? maxEffectiveY : maxEffectiveX);
            Integer square = a * a;//取较小数计算平方
            System.out.println("最大面积为： " + square);

            writer.write(square.toString());
        } catch (IOException e) {
            System.out.println("main() exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("writer.close() exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

//public class Martix
//{
//	protected Integer rowCount; //行数
//	protected Integer columnCount;	//列数
//	protected boolean isIncludeAxises = true;
//	private Integer[][] data;	//data[rowCount][columnCount]
//	private Scanner scanner;
//	
//	public Martix()
//	{
//		
//	}
//	
//	public Martix(Integer rowCount,Integer columnCount)
//	{	
//		this.columnCount = columnCount;
//		this.rowCount = rowCount;
//		Integer[][] tempData = new Integer[rowCount][columnCount];
//		for (int i = 0; i < rowCount; i++)
//		{
//			for (int j = 0; j < columnCount; j++)
//			{
//				tempData[i][j] = 0;
//			}
//		}
//		this.data = tempData;
//	}
//	public Martix(Integer[][] data)
//	{
//		this.data = data;
//		this.rowCount = data.length;
//		this.columnCount = data[0].length;
//	}
//	public Martix(File martixInFile)
//	{
//		try
//		{
//			scanner = new Scanner(martixInFile);
//			this.rowCount = scanner.nextInt();
//			this.columnCount = scanner.nextInt();
//			scanner.nextLine();
//			Integer[][] martixData = new Integer[this.rowCount][this.columnCount];
//			List<String> dataString = new ArrayList<String>();
//			for (int i = 0; i < this.rowCount; i++)
//			{
//				dataString.clear();
//				Collections.addAll(dataString, scanner.nextLine().split(" "));
//				int j = 0;
//				for (String data : dataString)
//				{
//					martixData[i][j] = Integer.parseInt(data);
//					j++;
//				}
//			}
//			this.data = martixData;
//		} catch (NumberFormatException e)
//		{
//			System.out.println("Martix(File martixInFile) exception: " + e.getMessage());
//			e.printStackTrace();
//		} catch (FileNotFoundException e)
//		{
//			System.out.println("Martix(File martixInFile) exception: " + e.getMessage());
//			e.printStackTrace();
//		} finally {
//			scanner.close();
//		}
//	}
//	public Martix(List<Integer> list, boolean isRow)
//	{
//		if (isRow)
//		{
//			this.columnCount = list.size();
//			this.rowCount = 1;
//			Integer[][] tempData = new Integer[rowCount][columnCount];
//			for (int i = 0; i < rowCount; i++)
//			{
//				for (int j = 0; j < columnCount; j++)
//				{
//					tempData[i][j] = list.get(j);
//				}
//			}
//			this.data = tempData;
//		}else {
//			this.columnCount = 1;
//			this.rowCount = list.size();
//			Integer[][] tempData = new Integer[rowCount][columnCount];
//			for (int i = 0; i < rowCount; i++)
//			{
//				for (int j = 0; j < columnCount; j++)
//				{
//					tempData[i][j] = list.get(j);
//				}
//			}
//			this.data = tempData;
//		}
//	}
//	
//	public Integer getRowCount()
//	{
//		return rowCount;
//	}
//	public Integer getColumnCount()
//	{
//		return columnCount;
//	}
//	public void setIncludeAxises(boolean isIncludeAxises)
//	{
//		this.isIncludeAxises = isIncludeAxises;
//	}
//	public Integer[][] getData()
//	{
//		return data;
//	}
//	public void setData(Integer[][] data)
//	{
//		this.data = data;
//	}
//	//include Axis
//	public boolean setValueInCoordinate(int rowIndex, int cloumnIndex,Integer data)
//	{
//		if (isIncludeAxises)
//		{
//			if (cloumnIndex < getColumnCount() && 
//					rowIndex < getRowCount() && 
//					rowIndex >= 0 && 
//					cloumnIndex >= 0	)
//			{
//				this.data[rowIndex][cloumnIndex] = data;
//				return true;
//			}else {
//				return false;
//			}
//		}else {
//			if (cloumnIndex <= getColumnCount() && 
//					rowIndex <= getRowCount() && 
//					rowIndex >= 1 && 
//					cloumnIndex >= 1 )
//			{
//				this.data[rowIndex-1][cloumnIndex-1] = data;
//				return true;
//			}else {
//				return false;
//			}
//		}
//	}
//	//获得指定点的值
//	public Integer getValueInCoordinate(int rowIndex, int cloumnIndex)
//	{
//		return isIncludeAxises ? data[rowIndex][cloumnIndex] : data[rowIndex-1][cloumnIndex-1];
//	}
//	//获得列
//	public List<Integer> getColumnInIndex(Integer index)
//	{
//		List<Integer> thisColumn = new ArrayList<Integer>();
//		for (int i = 0; i < this.rowCount; i++)
//		{
//			thisColumn.add(data[i][index-1]);
//		}
//		return thisColumn;
//	}
//	//获得行
//	public List<Integer> getRowInIndex(Integer index)
//	{
//		List<Integer> thisRow = new ArrayList<Integer>();
//		Collections.addAll(thisRow, data[index-1]);
//		return thisRow;
//	}
//
//	//获得所有行或列的和
//	public List<Integer> getRowOrColumnSum(boolean isRow)
//	{
//		List<Integer> thisBunchSum = new ArrayList<Integer>();
//		for (int i = 0; i < rowCount; i++)
//		{
//			int j = 0;
//			for (Integer integer : (isRow ? getRowInIndex(i+1) : getColumnInIndex(i+1)))
//			{
//				if (thisBunchSum.size() < (isRow ? columnCount : rowCount))
//				{
//					thisBunchSum.add(j, integer);
//				}else {
//					thisBunchSum.set(j, thisBunchSum.get(j)+integer);
//				}
//				j++;				
//			}
//		}
//		return thisBunchSum;
//	}
//	//返回每行或列中连续的数字X的个数，返回值为一个行或列list
//	public List<Integer> getContinuousXCountInRowOrColumn(boolean isRetrunInRow, boolean isGreatThanOrEqual, Integer xInteger)
//	{
//		List<Integer> thisBunchContinuousX = new ArrayList<Integer>();
//		//计算每行或列中最大的连续的X个数
//		for (int i = 0; i < (isRetrunInRow ? rowCount : columnCount); i++)
//		{	//计算此列或行的
//			Integer lastElement; 
//			Integer continueXCountNow = 0;
//			Integer continueXCountMax = 0;
//			int j =0;
//			for (Integer integer : (isRetrunInRow ? getRowInIndex(i+1) : getColumnInIndex(i+1)))
//			{
//				if (j > 0)
//				{	
//					lastElement = (isRetrunInRow ? getValueInCoordinate(i, j-1) : getValueInCoordinate(j-1, i));
//					if (isGreatThanOrEqual ? xInteger <= lastElement : xInteger == lastElement)
//					{
//						if (isGreatThanOrEqual ? xInteger <= integer : xInteger == integer)
//						{
//							continueXCountNow ++;
//							continueXCountMax = (continueXCountMax > continueXCountNow ? continueXCountMax : continueXCountNow);
//						}else {
//							continueXCountMax = (continueXCountMax > continueXCountNow ? continueXCountMax : continueXCountNow);
//							continueXCountNow = 0;
//						}
//					}
//				}
//				j++;
//			}
//			thisBunchContinuousX.add((0==continueXCountMax ? continueXCountMax :continueXCountMax+1));
//		}
//		return thisBunchContinuousX;
//	}
//	
//	@Override
//	public String toString()
//	{
//		String martixString = "";
//		for (int i = 0; i < rowCount; i++)
//		{
//			for (int j = 0; j < columnCount; j++)
//			{
//				martixString += (data[i][j] + " ");
//			}
//			martixString += "\n";
//		}
//		return martixString;
//	}
//}