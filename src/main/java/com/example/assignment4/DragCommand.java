package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

// command for moving an item around
public class DragCommand implements DCommand {
    List<Groupable> items;
    LineModel model;
    double startX, startY, dx, dy;

    public DragCommand(LineModel model, List<Groupable> newItems, double sx, double sy) {
        this.model = model;
        items = new ArrayList<>(newItems);
        startX = sx;        // start location of the object
        startY = sy;        // start location of the object
        dx = 0;
        dy = 0;
    }
    public void finishMove(double x, double y){
        // calculate by how much the object has moved
        dx = x - startX;
        dy = y - startY;
    }
    @Override
    public void doIt() {
        model.moveItems(items, dx, dy);     // move to the new location
    }

    @Override
    public void undo() {
        model.moveItems(items, -dx, -dy);   // move back by the changed amount
    }
}
