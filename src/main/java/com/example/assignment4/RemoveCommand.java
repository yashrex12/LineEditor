package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class RemoveCommand implements DCommand {
    List<Groupable> lines;
    LineModel model;

    public RemoveCommand(LineModel model, List<Groupable> items) {
        this.model = model;
        lines = new ArrayList<>(items);
    }
    @Override
    public void doIt() {
        model.removeLines(lines);
    }

    @Override
    public void undo() {
        model.addItems(lines);
    }
}
