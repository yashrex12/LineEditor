package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

// command to scale an item
public class ScaleCommand implements DCommand{
    List<Groupable> items;
    LineModel model;
    double scaleFactor;

    public ScaleCommand(LineModel model, List<Groupable> groupItems, double scaleFactor) {
        this.model = model;
        items = new ArrayList<>(groupItems);
        this.scaleFactor = scaleFactor;
    }

    @Override
    public void doIt(){
        // scale up an object
        for (Groupable item : items) {
            if(item instanceof DLine line){
                model.scaleLine(line, scaleFactor);
            }else if(item instanceof DGroup group){
                model.scaleGroup(group, scaleFactor);
            }
        }
    }

    @Override
    public void undo(){
        // scale down an object
        for (Groupable item : items) {
            if(item instanceof DLine line){
                model.scaleLine(line, 1/scaleFactor);
            }else if(item instanceof DGroup group){
                model.scaleGroup(group, 1/scaleFactor);
            }
        }
    }
}
