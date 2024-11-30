package com.example.assignment4;

import java.io.Serializable;
import java.util.ArrayList;

public class DGroup implements Groupable {
    ArrayList<Groupable> items;
    public DGroup() {
        items = new ArrayList<>();
    }
    public void addItem(Groupable item) {
        items.add(item);
    }
    public void move(double dx, double dy) {
        items.forEach(item -> item.move(dx, dy));
    }

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
