package com.inspur.practice15.exam;

public class MartixStoreObject extends Martix {
    private Object[][] objectData;

    public MartixStoreObject(Integer rowCount, Integer columnCount)// ,Object initObjectModel)
    {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        Object[][] tempObjectData = new Object[rowCount][columnCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                tempObjectData[i][j] = null;//initObjectModel;
            }
        }
        this.objectData = tempObjectData;
    }

    public MartixStoreObject(Object[][] objects) {
        this.objectData = objects;
        this.rowCount = objectData.length;
        this.columnCount = objectData[0].length;
    }

    //坐标轴形式,默认包含坐标轴
    public boolean setObjectInCoordinate(int xC, int yR, Object data) {
        if (isIncludeAxises) {
            if (xC < getColumnCount() && yR < getRowCount() && xC >= 0 && yR >= 0) {
                this.objectData[yR][xC] = data;
                return true;
            } else {
                return false;
            }
        } else {
            if (xC <= getColumnCount() && yR <= getRowCount() && xC >= 1 && yR >= 1) {
                this.objectData[yR - 1][xC - 1] = data;
                return true;
            } else {
                return false;
            }
        }
    }

    //按照坐标取值
    public Object getObjectInCoordinate(int xC, int yR) {
        return this.isIncludeAxises ? objectData[yR][xC] : objectData[yR - 1][xC - 1];
    }

    //	public String toStringDetail(boolean isInStationPassenger)
//	{
//		String martixObjectString = "";
//		for (int i = rowCount-1; i >= 0; i--)	//坐标系样式输出，即Y轴自上而下递减
//		{
//			for (int j = 0; j < columnCount; j++)
//			{
//				martixObjectString += (isInStationPassenger ? ((Station) objectData[i][j]).toStringWaitingPassengerDetail() :
//					((Station) objectData[i][j]).toStringBusesPassengerDetail() + " ");
//			}
//			martixObjectString += "\n";
//		}
//		return martixObjectString;
//	}
    @Override
    public String toString() {
        String martixObjectString = "";
        for (int i = rowCount - 1; i >= 0; i--)    //坐标系样式输出，即Y轴自上而下递减
        {
            for (int j = 0; j < columnCount; j++) {
                martixObjectString += (objectData[i][j] + " ");
            }
            martixObjectString += "\n";
        }
        return martixObjectString;
    }
}
