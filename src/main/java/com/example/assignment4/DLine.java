package com.example.assignment4;

public class DLine {
    private double x1, y1, x2, y2;

    public DLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public double getX1() {
        return x1;
    }
    public double getY1() {
        return y1;
    }
    public double getX2() {
        return x2;
    }
    public double getY2() {
        return y2;
    }
    public void adjust(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
    }
}
