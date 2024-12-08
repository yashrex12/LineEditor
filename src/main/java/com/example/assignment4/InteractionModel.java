package com.example.assignment4;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// store temporary changes and selections
public class InteractionModel {
    private DLine selectedLine;
    private Groupable hoveredLine;
    private ArrayList<Subscriber> subs;
    private List<DLine> selectedLines;
    List<Groupable> selection;
    Rubberband rubRect;
    DragCommand dragCommand;
    ResizeCommand resizeCommand;
    Stack<DCommand> undoStack, redoStack;

    public InteractionModel(){
        selectedLine = null;
        rubRect = null;
        subs = new ArrayList<>();
        selectedLines = new ArrayList<>();
        selection = new ArrayList<>();
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }
    public DLine getSelectedLine() {
        return selectedLine;
    }
    public void setSelectedLine(DLine line) {
        selectedLines.clear();
        if(line != null) {
            selectedLines.add(line);
        }
        selectedLine = line;
        notifySubscribers();
    }
    public void setHoveredLine(Groupable line) {
        hoveredLine = line;
        notifySubscribers();
    }
    public boolean isHoveredLine(DLine line) {
        return hoveredLine == line;
    }
    public void clearHoveredLine(){
        hoveredLine = null;
        notifySubscribers();
    }

    // start rubber band rectangle
    public void startSelection(double x, double y){
        rubRect = new Rubberband(x, y);
    }
    public void continueSelection(double x, double y){
        rubRect.adjust(x, y);
        notifySubscribers();
    }
    public boolean hasRubRect(){
        return rubRect != null;
    }
    public Rubberband getRubRect() {
        return rubRect;
    }
    public void removeRubRect(){
        rubRect = null;
        notifySubscribers();
    }

    // multiple selection
    // select a line or a group
    public void selectItems(Groupable g){
        addSelectedGroup(g);
        notifySubscribers();
    }
    // select multiple items
    public void selectGroup(List<Groupable> items){
        items.forEach(this::addSelectedGroup);
        notifySubscribers();
    }
    // add if unselected and remove if selected.
    public void addSelectedGroup(Groupable g){
        if(selection.contains(g)){
            selection.remove(g);
        }else {
            selection.add(g);
        }
    }
    public List<Groupable> getSelectedGroups() {
        return selection;
    }
    public void clearSelectedGroups() {
        selection.clear();
        notifySubscribers();
    }

    // commands for undo and redo
    // command for moving an item (line or a group)
    public void startDragCommand(LineModel model, double x, double y){
        dragCommand = new DragCommand(model, selection, x, y);
    }
    // adjusting endpoint of a line
    public void startResizeCommand(LineModel model, double x, double y){
        resizeCommand  = new ResizeCommand(model, selectedLine, x, y);
    }
    // finish commands if changed by at least 1 pixel
    public void finishDragCommand(double x, double y){
        dragCommand.finishMove(x, y);
        if(dragCommand.dx > 0 || dragCommand.dy > 0){
            addUndo(dragCommand);
        }
    }
    public void finishResizeCommand(double x, double y){
        resizeCommand.finishResize(x, y);
        if(resizeCommand.dx > 0 || resizeCommand.dy > 0){
            addUndo(resizeCommand);
        }
    }
    // push a command onto the undo stack
    public void addUndo(DCommand command){
        redoStack.clear();
        undoStack.push(command);
        notifySubscribers();
    }
    public void addCreateCommand(LineModel model, Groupable line){
        CreateCommand createCommand = new CreateCommand(model, line);
        addUndo(createCommand);
    }
    public void addRemoveCommand(LineModel model, List<Groupable> lineGroups){
        RemoveCommand removeCommand = new RemoveCommand(model, lineGroups);
        addUndo(removeCommand);
    }
    public void addRotateCommand(LineModel model, List<Groupable> lineGroups, double angle){
        RotateCommand rotateCommand = new RotateCommand(model, lineGroups, angle);
        addUndo(rotateCommand);
    }
    public void addScaleCommand(LineModel model, List<Groupable> lineGroups, double scale){
        ScaleCommand scaleCommand = new ScaleCommand(model, lineGroups, scale);
        addUndo(scaleCommand);
    }
    public void addGroupCommand(LineModel model, List<Groupable> lineGroups){
        GroupCommand groupCommand = new GroupCommand(model, this, lineGroups);
        groupCommand.doIt();
        addUndo(groupCommand);
    }
    public void addUngroupCommand(LineModel model, Groupable itemsToUngroup){
        UngroupCommand ungroupCommand = new UngroupCommand(model, this, itemsToUngroup);
        ungroupCommand.doIt();
        addUndo(ungroupCommand);
    }

    // Undo the last used command and push it onto redo stack
    public void undo(){
        if(undoStack.isEmpty()){
            System.out.println("Nothing to undo");
        }else {
            DCommand command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
        notifySubscribers();
    }
    // Redo the last undone command and push it onto the undo stack
    public void redo(){
        if(redoStack.isEmpty()){
            System.out.println("Nothing to redo");
        }else{
            DCommand command = redoStack.pop();
            command.doIt();
            undoStack.push(command);
        }
        notifySubscribers();
    }

    public void addSubscriber(Subscriber subscriber){
        subs.add(subscriber);
    }
    public void notifySubscribers(){
        subs.forEach(Subscriber::modelChanged);
    }
}
