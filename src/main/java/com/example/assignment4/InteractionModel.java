package com.example.assignment4;

import java.util.ArrayList;

public class InteractionModel {
    private DLine selectedLine;
    private DLine hoveredLine;
    private ArrayList<Subscriber> subs;

    public InteractionModel(){
        selectedLine = null;
        subs = new ArrayList<>();
    }
    public DLine getSelectedLine() {
        return selectedLine;
    }
    public void setSelectedLine(DLine line) {
        selectedLine = line;
        notifySubscribers();
    }
    public boolean isSelectedLine(DLine line) {
        return selectedLine == line;
    }
    public void clearSelectedLine(){
        selectedLine = null;
        notifySubscribers();
    }

    public DLine getHoveredLine() {
        return hoveredLine;
    }
    public void setHoveredLine(DLine line) {
        hoveredLine = line;
        notifySubscribers();
    }
    public boolean isHoveredLine(DLine line) {
        return hoveredLine == line;
    }
    public void clearHoveredLine(){
        hoveredLine = null;
        notifySubscribers();
    }

    public void addSubscriber(Subscriber subscriber){
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }
}
