package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

// command for grouping elements
public class GroupCommand implements DCommand{
    LineModel model;
    InteractionModel iModel;
    List<Groupable> items;
    Groupable group;

    public GroupCommand(LineModel model, InteractionModel iModel, List<Groupable> groupItems) {
        this.model = model;
        this.iModel = iModel;
        items = new ArrayList<>(groupItems);
    }

    @Override
    public void doIt() {
        // group selected items
        group = model.group(items);
        iModel.clearSelectedGroups();
        iModel.selectItems(group);
    }

    @Override
    public void undo() {
        // ungroup the last grouped items
        model.ungroup(group);
        iModel.clearSelectedGroups();
        iModel.selectGroup(items);
    }
}
