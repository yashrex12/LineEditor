package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

// command for deletion
public class RemoveCommand implements DCommand {
    List<Groupable> lines;
    LineModel model;

    public RemoveCommand(LineModel model, List<Groupable> items) {
        this.model = model;
        lines = new ArrayList<>(items);
    }
    @Override
    public void doIt() {
        model.removeLines(lines);       // delete a line
    }

    @Override
    public void undo() {
        model.addItems(lines);          // add last deleted item back to selection
    }
}
