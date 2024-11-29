package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    private DLine selectedLine;
    private DLine hoveredLine;
    private ArrayList<Subscriber> subs;
    private List<DLine> selectedLines;

    public InteractionModel(){
        selectedLine = null;
        subs = new ArrayList<>();
        selectedLines = new ArrayList<>();
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
    public List<DLine> getSelectedLines() {
        return selectedLines;
    }
    public void setSelectedLines(List<DLine> newSelectedLines) {
        selectedLines.clear();
        selectedLines.addAll(newSelectedLines);
        notifySubscribers();
    }
    public void clearSelectedLines() {
        selectedLines.clear();
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

    public void addSelectedLine(DLine line){
        if(selectedLines.contains(line)){
            selectedLines.remove(line);
        }else{
            selectedLines.add(line);
        }
        notifySubscribers();
    }

    public void addSubscriber(Subscriber subscriber){
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }
}
