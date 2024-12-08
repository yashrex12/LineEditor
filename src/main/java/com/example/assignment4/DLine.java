package com.example.assignment4;

import java.util.ArrayList;

// Class for Line object to be created
public class DLine implements Groupable{
    private double x1, y1, x2, y2;
    private double left, top, right, bottom;

    public DLine(double x1, double y1, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    // getters and setters for Line elements
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

    // resize a line by its endpoint
    public void adjust(double x2, double y2) {
        this.x2 = x2;
        this.y2 = y2;
    }

    // move line around
    public void move(double dX, double dY){
        x1 += dX;
        y1 += dY;
        x2 += dX;
        y2 += dY;
        left += dX;
        top += dY;
    }

    // rotate X1 & X2 values of the line
    public double rotateX(double x, double y, double cx, double cy, double angle){
        return cx + (x-cx) * Math.cos(angle) - (y-cy) * Math.sin(angle);
    }
    //rotate Y1 & Y2 values of the line
    public double rotateY(double x, double y, double cx, double cy, double angle){
        return cy + (x-cx) * Math.sin(angle) + (y-cy) * Math.cos(angle);
    }
    // scale X1 & X2 values of the line
    public double scaleX(double x, double y, double cx, double cy, double scale){
        return cx + (x-cx) * scale;
    }
    // scale Y1 & Y2 values of the line
    public double scaleY(double x, double y, double cx, double cy, double scale){
        return cy + (y-cy) * scale;
    }

    // check only the endpoint(x2, y2) of the line
    public boolean checkEndpoint(double x, double y) {
        double dX = x - this.getX2();
        double dY = y - this.getY2();
        return Math.sqrt(dX * dX + dY * dY) <= 5;
    }
    // check if the cursor is within 5 pixels of a line
    public boolean contains(double x, double y) {
        // calculate perpendicular distance from a point
        double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) /
                Math.hypot(x2 - x1, y2 - y1);
        if(distance > 5) {
            return false;
        }
        // check only when cursor is near the line
        double minX = Math.min(x1, x2);
        double minY = Math.min(y1, y2);
        double maxX = Math.max(x1, x2);
        double maxY = Math.max(y1, y2);
        return (x>= minX && x<= maxX && y>= minY && y<= maxY);
    }

    @Override
    public double getLeft(){
        return Math.min(x1, x2);        // nearest starting point for the group bounding box (X)
    }

    @Override
    public double getRight() {
        return Math.max(x1, x2);        // farthest endpoint for the group bounding box     (Width)
    }

    @Override
    public double getTop() {            // nearest starting point for the group bounding box (Y)
        return Math.min(y1, y2);
    }

    @Override
    public double getBottom() {         // farthest endpoint for the group bounding box     (Height)
        return Math.max(y1, y2);
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public ArrayList<Groupable> getChildren() {
        return null;
    }
}
