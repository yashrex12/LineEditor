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
    public void adjustLine(DLine line, double x2, double y2) {
        line.adjust(x2, y2);
        notifySubscribers();
    }
    public void addSubscriber(Subscriber subscriber) {
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }

}
