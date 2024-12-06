package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class DragCommand implements DCommand {
    List<Groupable> items;
    LineModel model;
    double startX, startY, dx, dy;

    public DragCommand(LineModel model, List<Groupable> newItems, double sx, double sy) {
        this.model = model;
        items = new ArrayList<>(newItems);
        startX = sx;
        startY = sy;
        dx = 0;
        dy = 0;
    }
    public void finishMove(double x, double y){
        dx = x - startX;
        dy = y - startY;
    }
    @Override
    public void doIt() {
        model.moveItems(items, dx, dy);
    }

    @Override
    public void undo() {
        model.moveItems(items, -dx, -dy);
    }
}
