package com.example.assignment4;

public class Rubberband {
    double left, top, right, bottom;
    double startX, startY;
    double width, height;

    public Rubberband(double x, double y) {
        left = x;
        top = y;
        startX = x;
        startY = y;
        right = x;
        bottom = y;
        width = 0;
        height = 0;
    }
    public boolean contains(double x, double y) {
        return x >= left && x <= right && y >= top && y <= bottom;
    }
    public void adjust(double x2, double y2) {
        left = Math.min(x2, startX);
        right = Math.max(x2, startX);
        top = Math.min(y2, startY);
        bottom = Math.max(y2, startY);
        width = right - left;
        height = bottom - top;

    }
    public boolean containsLine(DLine line){
        return contains(line.getX1(), line.getY1()) && contains(line.getX2(), line.getY2());
    }
    public boolean containsGroupable(Groupable item){
        if(item instanceof DLine line){
            return contains(line.getX1(), line.getY1()) && contains(line.getX2(), line.getY2());
        } else if(item instanceof DGroup group){
            for(Groupable child: group.getChildren()){
                if(!containsGroupable(child)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
