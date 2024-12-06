package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class RotateCommand implements DCommand{
    List<Groupable> items;
    LineModel model;
    double angle;

    public RotateCommand(LineModel model, List<Groupable> groupItems, double angle) {
        this.model = model;
        items = new ArrayList<>(groupItems);
        this.angle = angle;
    }
    @Override
    public void doIt() {
        for(Groupable item : items) {
            if(item instanceof DLine line){
                model.rotateLine(line, angle);
            }else if(item instanceof DGroup group){
                model.rotateGroup(group, angle);
            }
        }
    }

    @Override
    public void undo() {
        for(Groupable item : items) {
            if(item instanceof DLine line){
                model.rotateLine(line, -angle);
            }else if(item instanceof DGroup group){
                model.rotateGroup(group, -angle);
            }
        }
    }
}
