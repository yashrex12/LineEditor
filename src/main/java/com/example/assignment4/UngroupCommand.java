package com.example.assignment4;

import java.util.List;

public class UngroupCommand implements DCommand{
    LineModel model;
    InteractionModel iModel;
    List<Groupable> ungroupedItems;
    Groupable group;

    public UngroupCommand(LineModel model, InteractionModel iModel, Groupable group) {
        this.model = model;
        this.iModel = iModel;
        this.group = group;
    }
    @Override
    public void doIt() {
        ungroupedItems = model.ungroup(group);
        iModel.selectGroup(ungroupedItems);
    }

    @Override
    public void undo() {
        Groupable group = model.group(ungroupedItems);
        iModel.clearSelectedGroups();
        iModel.selectItems(group);
    }
}
