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
    public void setX1(double x1) {
        this.x1 = x1;
    }
    public void setY1(double y1) {
        this.y1 = y1;
    }
    public void setX2(double x2) {
        this.x2 = x2;
    }
    public void setY2(double y2) {
        this.y2 = y2;
    }
    public void adjust(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
    }
    public void move(double dX, double dY){
        x1 += dX;
        y1 += dY;
        x2 += dX;
        y2 += dY;
    }
    public Endpoint whichEndpoint(double x, double y) {
        if(Math.hypot(x - x1, y - y1) < 10){
            return new Endpoint(x1, y1);
        }else {
            return new Endpoint(x2, y2);
        }
    }
    public boolean checkEndpoint(double x, double y) {
        double dX = x - this.getX2();
        double dY = y - this.getY2();
        return Math.sqrt(dX * dX + dY * dY) <= 5;
    }
    public boolean contains(double x, double y) {
        double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) /
                Math.hypot(x2 - x1, y2 - y1);
        return distance < 5;
    }
}
