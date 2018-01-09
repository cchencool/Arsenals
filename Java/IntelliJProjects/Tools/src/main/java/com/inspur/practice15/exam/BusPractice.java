package com.inspur.practice15.exam;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

//import java.util.List;

public class BusPractice {
    static final int NOT_IN_CONSIDERED_BORDER = 0;
    static final int LEFT_BORDER = 1;
    static final int BOTTOM_BORDER = 2;
    static int T = 0;

    static int x = 1, y = 1;

    public static void main(String[] args) {
        Writer writer = null;
        try {
            long start = System.currentTimeMillis();

            writer = new FileWriter("bus.out");
            // 得到每个站点的信息。
            MartixStoreObject stations = readPassengersInStationFromFile(new File("bus.in"));
            stations.setIncludeAxises(false);
//			System.out.println(stations.toString());
            //遍历每个节点
            Traversal(stations, (stations.getRowCount() < stations.getColumnCount()));
//			System.out.println(stations.toString());

            Station finalStation = (Station) stations.getObjectInCoordinate(stations.getColumnCount(), stations.getRowCount());
            Bus finalBus = finalStation.getStopedBus();
            Integer maxTakePassenger = finalBus.getPassengerCount();
            System.out.println("能达到的最大载客量: " + maxTakePassenger);
            writer.write(maxTakePassenger.toString());

            long end = System.currentTimeMillis();

//			List<Point> route = finalBus.getRoute();
//			String routeToString = "";
//			int i = 0;
//			for (Point point : route)
//			{
//				if (i != route.size() - 1)
//				{
//					routeToString += "(" + point.x + "," + point.y + ")" + "-->";
//				}else {
//					routeToString += "(" + point.x + "," + point.y + ")";
//				}
//				i ++;
//			}
//			System.out.println(routeToString);
//			
            System.out.println("运行时间：" + (end - start) + "mm");
            System.out.println("calculate " + T + " times");


        } catch (IOException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                System.out.println("writer exception: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    static MartixStoreObject readPassengersInStationFromFile(File fileName) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(fileName);
            int columnCount = scanner.nextInt();
            int rowCount = scanner.nextInt();
            MartixStoreObject stations = new MartixStoreObject(rowCount, columnCount);
            int stationsCountWithPassenger = scanner.nextInt();
            int xC, yR;
            for (int iR = 0; iR < rowCount; iR++) {
                for (int jC = 0; jC < columnCount; jC++) {
                    stations.setObjectInCoordinate(jC, iR, new Station(jC + 1, iR + 1, 0));
                }
            }
            for (int i = 0; i < stationsCountWithPassenger; i++) {
                xC = scanner.nextInt();
                yR = scanner.nextInt();
                stations.setObjectInCoordinate(xC - 1, yR - 1, new Station(xC, yR, scanner.nextInt()));
            }
            return stations;
        } catch (FileNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
        return null;
    }

    static void Traversal(MartixStoreObject stations, boolean isHorizontalStart) {
        Station station;
        int topBorder = stations.getRowCount();
        int rightBorder = stations.getColumnCount();
        boolean hasMiddle = !(topBorder == rightBorder - 1 || rightBorder == topBorder - 1 || topBorder == rightBorder);
        // do left bottom
        do {
            if ((isHorizontalStart ? x : y) == (min(topBorder, rightBorder) + 1))    // 超出了较小的行或列的范围
            {
                break;
            }
            station = (Station) stations.getObjectInCoordinate(x, y);
//			System.out.println(station.toString());
            calculate(station, stations);    //得到当前车站可获得的最大乘客的公交车
            if (1 == (isHorizontalStart ? x : y))// 1 == lefBorder == bottomBorder
            {
                changeLine(isHorizontalStart, hasMiddle);
                continue;
            }
            moveNext(isHorizontalStart);
        } while (true);
//		System.out.println("left bottom done!");
        // do middle
        do {
            if (!hasMiddle)    // 此时不处理
            {
                break;
            }
            if (x + y == (isHorizontalStart ? rightBorder + 1 : topBorder + 1))    // 超出了较小的行或列的范围
            {
                break;
            }
            station = (Station) stations.getObjectInCoordinate(x, y);
//			System.out.println(station.toString());
            calculate(station, stations);    //得到当前车站可获得的最大乘客的公交车
            if ((isHorizontalStart ? topBorder : rightBorder) == (isHorizontalStart ? y : x))// 1 == lefBorder == bottomBorder
            {
                changeLine(isHorizontalStart, hasMiddle);
                continue;
            }
            moveNext(isHorizontalStart);
        } while (true);
//		System.out.println("middle done!");
        // do right top
        if (rightBorder == topBorder)    // 为正方形修正
        {
            y--;
            x++;
        }
        do {
            if (x > rightBorder || y > topBorder) {
                break;
            }
            station = (Station) stations.getObjectInCoordinate(x, y);
//			System.out.println(station.toString());
            calculate(station, stations);
            if ((isHorizontalStart ? topBorder : rightBorder) == (isHorizontalStart ? y : x)) {
                if (hasMiddle) {
                    if (isHorizontalStart) {
                        y = x + y - rightBorder + 1;
                        x = rightBorder;
                    } else {
                        x = x + y - topBorder + 1;
                        y = topBorder;
                    }
                } else {
                    changeLine(isHorizontalStart, hasMiddle);
                    if (rightBorder == topBorder)    // 为正方形修正
                    {
                        y--;
                        x++;
                    }
                }
                continue;
            }
            moveNext(isHorizontalStart);
        } while (true);
//		System.out.println("right top done!");
        System.out.println("Traversal Done!\n");
    }

    //得到当前车站可获得的最大乘客的公交车
    static void calculate(Station station, MartixStoreObject stations) {
        T++;
        int leftBorder = 1;
        int bottomBorder = 1;
        if (station.getLocation().equals(new Point(1, 1))) {
            Bus bus = new Bus(station.getLocation(), station.getWaitingPassengerCount());
            bus.addToRoute(station.getLocation());
            bus.stopInStation(station);
            return;
        }
        switch (inOneBorderOrInSide(station, leftBorder, bottomBorder)) {
            case NOT_IN_CONSIDERED_BORDER:
                Station lefStation = (Station) stations.getObjectInCoordinate(station.getLocation().x - 1, station.getLocation().y);
                Bus busFromLeft = lefStation.getStopedBus();
                Bus thisStationBusFL = new Bus(station.getLocation(), busFromLeft.getPassengerCount());
                Station downStation = (Station) stations.getObjectInCoordinate(station.getLocation().x, station.getLocation().y - 1);
                Bus busFromDown = downStation.getStopedBus();
                Bus thisStationBusFD = new Bus(station.getLocation(), busFromDown.getPassengerCount());
                thisStationBusFL.takePassengers(station.getWaitingPassengerCount());    //接上过来公交车上的人
                thisStationBusFD.takePassengers(station.getWaitingPassengerCount());
                if (thisStationBusFL.getPassengerCount() > thisStationBusFD.getPassengerCount()) {
                    thisStationBusFL.setRoute(busFromLeft.getRoute());    //记录历史轨迹
                    thisStationBusFL.addToRoute(station.getLocation());    //增加轨迹
                    thisStationBusFL.stopInStation(station);        // 入站
                } else {
                    thisStationBusFD.setRoute(busFromDown.getRoute());
                    thisStationBusFD.addToRoute(station.getLocation());
                    thisStationBusFD.stopInStation(station);
                }
                break;
            case LEFT_BORDER:
                Station downNearByStation = (Station) stations.getObjectInCoordinate(station.getLocation().x, station.getLocation().y - 1);
                Bus busOnlyFromDown = downNearByStation.getStopedBus();
                Bus thisStationBusL = new Bus(station.getLocation(), busOnlyFromDown.getPassengerCount());
                thisStationBusL.takePassengers(station.getWaitingPassengerCount());
                thisStationBusL.setRoute(busOnlyFromDown.getRoute());    //记录历史轨迹
                thisStationBusL.addToRoute(station.getLocation());    //增加轨迹
                thisStationBusL.stopInStation(station);
                break;
            case BOTTOM_BORDER:
                Station leftNearByStation = (Station) stations.getObjectInCoordinate(station.getLocation().x - 1, station.getLocation().y);
                Bus busOnlyFromLeft = leftNearByStation.getStopedBus();
                Bus thisStationBusB = new Bus(station.getLocation(), busOnlyFromLeft.getPassengerCount());
                thisStationBusB.takePassengers(station.getWaitingPassengerCount());
                thisStationBusB.setRoute(busOnlyFromLeft.getRoute());    //记录历史轨迹
                thisStationBusB.addToRoute(station.getLocation());    //增加轨迹
                thisStationBusB.stopInStation(station);
                break;
            default:
                break;
        }
//		System.out.println(station.getLocation());
    }

    static int inOneBorderOrInSide(Station station, int leftBorder, int bottomBorder) {
        return station.getLocation().x == leftBorder ? LEFT_BORDER :
                station.getLocation().y == bottomBorder ? BOTTOM_BORDER : NOT_IN_CONSIDERED_BORDER;
    }

    static void moveNext(Boolean isHorizontalStart) {
        if (isHorizontalStart) {
            x--;
            y++;
        } else {
            y--;
            x++;
        }
    }

    static void changeLine(boolean isHorizontalStart, boolean hasMiddle) //move to next line
    {
        if (hasMiddle) {
            if (isHorizontalStart) {
                x += y;
                y = 1;
            } else {
                y += x;
                x = 1;
            }
        } else {
            exchange();
            if (isHorizontalStart) {
                x++;
            } else {
                y++;
            }
        }
    }

    static void exchange() {
        int t = x;
        x = y;
        y = t;
    }

    static int max(int a, int b) {
        return a > b ? a : b;
    }

    static int min(int a, int b) {
        return a < b ? a : b;
    }
}