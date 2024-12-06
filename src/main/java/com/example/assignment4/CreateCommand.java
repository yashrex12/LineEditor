package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class CreateCommand implements DCommand {
    List<Groupable> items;
    LineModel model;

    public CreateCommand(LineModel model, Groupable line) {
        this.model = model;
        items = new ArrayList<>();
        items.add(line);
    }
//    public CreateCommand(LineModel model, List<Groupable> lineGroups) {
//        this.model = model;
//        items = new ArrayList<>(lineGroups);
//    }
    @Override
    public void doIt() {
        model.addItems(items);
    }

    @Override
    public void undo() {
        model.removeLines(items);
    }
}
