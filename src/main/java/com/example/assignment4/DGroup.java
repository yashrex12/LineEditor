package com.example.assignment4;

import java.io.Serializable;
import java.util.ArrayList;

// Group for group objects
public class DGroup implements Groupable {
    private ArrayList<Groupable> items;

    public DGroup() {
        items = new ArrayList<>();
    }

    // move the group and its children
    public void move(double dx, double dy) {
        items.forEach(item -> item.move(dx, dy));
    }

    // check any of the clicked item are contained within the group
    @Override
    public boolean contains(double x, double y) {
        return items.stream().anyMatch(item -> item.contains(x, y));
    }

    @Override
    public double getLeft() {
        return items.stream().mapToDouble(Groupable::getLeft).min().getAsDouble();
    }

    @Override
    public double getRight() {
        return items.stream().mapToDouble(Groupable::getRight).max().getAsDouble();
    }

    @Override
    public double getTop() {
        return items.stream().mapToDouble(Groupable::getTop).min().getAsDouble();
    }

    @Override
    public double getBottom() {
        return items.stream().mapToDouble(Groupable::getBottom).max().getAsDouble();
    }

    @Override
    public boolean hasChildren() {
        return true;
    }

    @Override
    public ArrayList<Groupable> getChildren() {
        return items;
    }
}
