package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

// Command for creation
public class CreateCommand implements DCommand {
    List<Groupable> items;
    LineModel model;

    public CreateCommand(LineModel model, Groupable line) {
        this.model = model;
        items = new ArrayList<>();
        items.add(line);
    }

    @Override
    public void doIt() {
        model.addItems(items);      // create a line
    }

    @Override
    public void undo() {
        model.removeLines(items);   // remove a line
    }
}
