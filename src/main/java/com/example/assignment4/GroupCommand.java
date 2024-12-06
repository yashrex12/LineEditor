package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;

public class GroupCommand implements DCommand{
    LineModel model;
    InteractionModel iModel;
    List<Groupable> items;
    Groupable group;

    public GroupCommand(LineModel model, InteractionModel iModel, List<Groupable> groupItems) {
        this.model = model;
        this.iModel = iModel;
        this.items = groupItems;
    }

    @Override
    public void doIt() {
        group = model.group(items);
        iModel.clearSelectedGroups();
        iModel.selectItems(group);
    }

    @Override
    public void undo() {
        model.ungroup(group);
    }
}
