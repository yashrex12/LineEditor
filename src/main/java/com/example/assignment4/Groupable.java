package com.example.assignment4;

import java.util.ArrayList;

// enable items to be grouped together
public interface Groupable {
    void move(double x, double y);
    boolean contains(double x, double y);
    double getLeft();
    double getRight();
    double getTop();
    double getBottom();
    boolean hasChildren();
    ArrayList<Groupable> getChildren();
}
