package com.example.assignment4;

public class InteractionModel {
    private DLine selectedLine;

    public InteractionModel(){
        selectedLine = null;
    }
    public DLine getSelectedLine() {
        return selectedLine;
    }
    public void setSelectedLine(DLine line) {
        selectedLine = line;
    }
    public void clearSelection(){
        selectedLine = null;
    }
}
