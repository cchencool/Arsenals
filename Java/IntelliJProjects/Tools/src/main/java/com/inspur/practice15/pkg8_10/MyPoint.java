package com.inspur.practice15.pkg8_10;

public class MyPoint {
    private int x;    //横坐标
    private int y;    //纵坐标

    public int getX() {
        return this.x;
    }

    public void setX(int num) //封装
    {
        this.x = num;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int num) {
        this.y = num;
    }

    public String toString() {
        String str = "(" + x + "," + y + ")";
        return str;
    }

	/*
	public static void main(String[] args) 
	{	
		MyPoint myPoint = new MyPoint();
		if (args.length == 0) {
			System.out.println("(0,0)");
		}
		else
		{
			System.out.println(myPoint.toString());
		}
		
	}
	*/
}
