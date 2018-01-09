package com.inspur.practice15.exam;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bus {
    private Point positionNow;
    private Integer passengerCount;
    private List<Point> route;

    public Bus() {
        passengerCount = 0;
        route = new ArrayList<Point>();
    }

    public Bus(Point positionNow, Integer passengerIn) {
        this.positionNow = positionNow;
        this.passengerCount = passengerIn;
        this.route = new ArrayList<Point>();
    }

    public Integer getPassengerCount() {
        return passengerCount;
    }

    public Point getPositionNow() {
        return positionNow;
    }

    public void setPositionNow(Point positionNow) {
        this.positionNow = positionNow;
    }

    public List<Point> getRoute() {
        return route;
    }

    public void setRoute(List<Point> route) {
        this.route.addAll(route);
    }

    public void addToRoute(Point p) {
        this.route.add(p);
    }

    public void takePassengers(Integer passengerIn) {
        passengerCount += passengerIn;
    }

    public void stopInStation(Station station) {
        station.busIn(this);
        positionNow = station.getLocation();
    }

    @Override
    public String toString() {
        return passengerCount.toString();
    }


}
