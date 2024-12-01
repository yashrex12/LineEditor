package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class InteractionModel {
    private DLine selectedLine;
    private Groupable hoveredLine;
    private ArrayList<Subscriber> subs;
    private List<DLine> selectedLines;
    List<Groupable> selection;
    Rubberband rubRect;

    public InteractionModel(){
        selectedLine = null;
        rubRect = null;
        subs = new ArrayList<>();
        selectedLines = new ArrayList<>();
        selection = new ArrayList<>();
    }
    public DLine getSelectedLine() {
        return selectedLine;
    }
    public void setSelectedLine(DLine line) {
        selectedLines.clear();
        if(line != null) {
            selectedLines.add(line);
        }
        selectedLine = line;
        notifySubscribers();
    }
    public boolean isSelectedLine(DLine line) {
        return selectedLine == line;
    }
    public void clearSelectedLine(){
        selectedLine = null;
        selectedLines.clear();
        notifySubscribers();
    }
    public List<DLine> getSelectedLines() {
        return selectedLines;
    }
    public void setSelectedLines(List<DLine> newSelectedLines) {
        selectedLines.clear();
        selectedLines.addAll(newSelectedLines);
        selectedLine = selectedLines.isEmpty() ? null : selectedLines.getFirst();
        notifySubscribers();
    }
    public void clearSelectedLines() {
        selectedLines.clear();
        selectedLine = null;
        notifySubscribers();
    }
//    public DLine getHoveredLine() {
//        return hoveredLine;
//    }
    public void setHoveredLine(Groupable line) {
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
            if(selectedLine == line){
                selectedLine = selectedLines.isEmpty() ? null : selectedLines.getFirst();
            }
        }else{
            selectedLines.add(line);
            selectedLine = line;
        }
        notifySubscribers();
    }
    public void addSelectedLines(List<DLine> lines){
        lines.forEach(this::addSelectedLine);
        selectedLine = selectedLines.isEmpty() ? null : selectedLines.getFirst();
        notifySubscribers();
    }
    public void startSelection(double x, double y){
        rubRect = new Rubberband(x, y);
    }
    public void continueSelection(double x, double y){
        rubRect.adjust(x, y);
        notifySubscribers();
    }
    public boolean hasRubRect(){
        return rubRect != null;
    }
    public Rubberband getRubRect() {
        return rubRect;
    }
    public void removeRubRect(){
        rubRect = null;
        notifySubscribers();
    }

    public void selectItems(Groupable g){
        addSelectedGroup(g);
        notifySubscribers();
    }
    public void selectGroup(List<Groupable> items){
        items.forEach(this::addSelectedGroup);
        notifySubscribers();
    }
    public void addSelectedGroup(Groupable g){
        if(selection.contains(g)){
            selection.remove(g);
        }else {
            selection.add(g);
        }
    }
    public boolean isSelectedGroup(Groupable g){
        return selection.contains(g);
    }
    public List<Groupable> getSelectedGroups() {
        return selection;
    }
    public void clearSelectedGroups() {
        selection.clear();
        notifySubscribers();
    }

    public void addSubscriber(Subscriber subscriber){
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }
}
