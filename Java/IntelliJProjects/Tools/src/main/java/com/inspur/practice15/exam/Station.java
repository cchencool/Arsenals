package com.inspur.practice15.exam;

import java.awt.*;

public class Station {
    private Bus stopedBus;    // the bus which got the most passengers in this point
    private Point location;
    private Integer waitingPassengerCount;
    private int inBorder;

    public Station(int x, int y, int waitingPassengerCount) {
        stopedBus = new Bus();
        location = new Point(x, y);
        this.waitingPassengerCount = waitingPassengerCount;
    }

    public void busIn(Bus busIn) {
        stopedBus = busIn;
    }

    public Point getLocation() {
        return location;
    }

    public int getInBorder() {
        return inBorder;
    }

    public void setInBorder(Integer inBorder) {
        this.inBorder = inBorder;
    }

    public Integer getWaitingPassengerCount() {
        return waitingPassengerCount;
    }

    public Bus getStopedBus() {
        return stopedBus;
    }

    public String toStringWaitingPassengerDetail() {
        return "S(" + location.x + "," + location.y + "):" + waitingPassengerCount.toString() + "P ";
    }

    public String toStringBusesPassengerDetail() {
        return "SpkB(" + location.x + "," + location.y + "):" + stopedBus.getPassengerCount() + "P ";
    }

    @Override
    public String toString() {
        return "S(" + location.x + "," + location.y + "):" + waitingPassengerCount.toString() + "p.B:" + stopedBus.getPassengerCount() + "p";
    }
}
