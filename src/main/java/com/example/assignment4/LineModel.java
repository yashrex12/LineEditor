package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class LineModel {
    private List<DLine> lines;
    private List<Subscriber> subs;

    public LineModel() {
        lines = new ArrayList<>();
        subs = new ArrayList<>();
    }
    public List<DLine> getLines() {
        return lines;
    }
    public DLine addLine(double x1, double y1, double x2, double y2) {
        DLine line = new DLine(x1, y1, x2, y2);
        lines.add(line);
        notifySubscribers();
        return line;
    }
    public void removeLine(DLine line) {
        lines.remove(line);
        notifySubscribers();
    }
    public void moveLine(DLine line, double x, double y){
        line.move(x, y);
        notifySubscribers();
    }
    public void rotateLine(DLine line, double angle){
        double cx = (line.getX1() + line.getX2()) / 2;
        double cy = (line.getY1() + line.getY2()) / 2;
        double newX1 = line.rotateX(line.getX1(), line.getY1(), cx, cy, angle);
        double newY1 = line.rotateY(line.getX1(), line.getY1(), cx, cy, angle);
        double newX2 = line.rotateX(line.getX2(), line.getY2(), cx, cy, angle);
        double newY2 = line.rotateY(line.getX2(), line.getY2(), cx, cy, angle);
        line.setX1(newX1);
        line.setY1(newY1);
        line.setX2(newX2);
        line.setY2(newY2);
        notifySubscribers();
    }
    public void scaleLine(DLine line, double scale){
        double cx = (line.getX1() + line.getX2()) / 2;
        double cy = (line.getY1() + line.getY2()) / 2;
        line.setX1(line.scaleX(line.getX1(), line.getY1(), cx, cy, scale));
        line.setY1(line.scaleY(line.getX1(), line.getY1(), cx, cy, scale));
        line.setX2(line.scaleX(line.getX2(), line.getY2(), cx, cy, scale));
        line.setY2(line.scaleY(line.getX2(), line.getY2(), cx, cy, scale));
        notifySubscribers();
    }
    //detect and return which line was clicked
    public DLine whichLine(double x, double y){
        return lines.stream()
                .filter(e -> e.contains(x, y))
                .findFirst()
                .orElse(null);
    }
    // detect and return which line's endpoint was clicked
    public DLine whichLineEndpoint(double x, double y){
        for (DLine line : lines) {
            if (line.checkEndpoint(x, y)) {
                return line;
            }
        } return null;
    }
    public void adjustLine(DLine line, double x2, double y2) {
        line.adjust(x2, y2);
        notifySubscribers();
    }
    public List<DLine> containsLine(Rubberband rect){
        ArrayList<DLine> rectLines = new ArrayList<>();
        lines.forEach(dl ->{
            if(rect.containsLine(dl)){
                rectLines.add(dl);
            }
        });
        return rectLines;
    }
    public void addSubscriber(Subscriber subscriber) {
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }

}
