package com.example.assignment4;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.List;

// class to handle user interactions and make changes to the model
public class AppController {
    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    double prevX, prevY;


    public AppController(){
        currentState = ready;
    }
    public void setModel(LineModel model){
        this.model = model;
    }
    public void setInteractionModel(InteractionModel iModel){
        this.iModel = iModel;
    }
    public void handlePressed(MouseEvent event){
        currentState.handlePressed(event);
    }
    public void handleReleased(MouseEvent event){
        currentState.handleReleased(event);
    }
    public void handleDragged(MouseEvent event){
        currentState.handleDragged(event);
    }

    // detect hover over the line
    public void handleMouseMoved(MouseEvent event){
        Groupable line = model.whichItem(event.getX(), event.getY());
        if(line != null){
            iModel.setHoveredLine(line);
        }else {
            iModel.clearHoveredLine();
        }
    }
    public void handleKeyPressed(KeyEvent event){
        // delete an item (group or a line)
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            iModel.addRemoveCommand(model, iModel.getSelectedGroups());
            iModel.getSelectedGroups().forEach(item ->{
                model.removeItem(item);
            });
            iModel.clearSelectedGroups();
        }
        // rotate an item anti-clockwise
        if(event.getCode() == KeyCode.LEFT){
            iModel.addRotateCommand(model, iModel.getSelectedGroups(), -Math.PI/15);
            iModel.getSelectedGroups().forEach(item ->{
                if(item instanceof DLine line){
                    model.rotateLine(line, -Math.PI/15);
                }else if(item instanceof DGroup group){
                    model.rotateGroup(group, -Math.PI/15);
                }
            });
        }
        // rotate an item clockwise
        if(event.getCode() == KeyCode.RIGHT){
            iModel.addRotateCommand(model, iModel.getSelectedGroups(), Math.PI/15);
            iModel.getSelectedGroups().forEach(item ->{
                if(item instanceof DLine line){
                    model.rotateLine(line, Math.PI/15);
                }else if(item instanceof DGroup group){
                    model.rotateGroup(group, Math.PI/15);
                }
            });
        }
        // scale up an item
        if(event.getCode() == KeyCode.UP){
            iModel.addScaleCommand(model, iModel.getSelectedGroups(), 1.2);
            iModel.getSelectedGroups().forEach(item ->{
                if(item instanceof DLine line){
                    model.scaleLine(line, 1.2);
                }else if(item instanceof DGroup group){
                    model.scaleGroup(group, 1.2);
                }
            });
        }
        // scale down an item
        if(event.getCode() == KeyCode.DOWN){
            iModel.addScaleCommand(model, iModel.getSelectedGroups(), 0.8);
            iModel.getSelectedGroups().forEach(item ->{
                if(item instanceof DLine line){
                    model.scaleLine(line, 0.8);
                }else if(item instanceof DGroup group){
                    model.scaleGroup(group, 0.8);
                }
            });
        }
        // group items together
        if(event.getCode() == KeyCode.G){
            iModel.addGroupCommand(model, iModel.selection);
            Groupable newGroup = model.group(iModel.getSelectedGroups());
            iModel.clearSelectedGroups();
            iModel.selectItems(newGroup);

        }
        // ungroup the group or first child of a nested group
        if(event.getCode() == KeyCode.U){
            iModel.addUngroupCommand(model, iModel.selection.getFirst());
            if(iModel.selection.size() == 1 && iModel.selection.getFirst().hasChildren()){
                List<Groupable> items = model.ungroup(iModel.selection.getFirst());
                iModel.selectGroup(items);

            }
        }
        // undo a command
        if(event.getCode() == KeyCode.Z){
            iModel.undo();
        }
        // redo a command
        if(event.getCode() == KeyCode.R){
            iModel.redo();
        }
    }


    private abstract class ControllerState {
        void handlePressed(MouseEvent event){}
        void handleReleased(MouseEvent event){}
        void handleDragged(MouseEvent event){}
    }

    ControllerState ready = new ControllerState() {
        @Override
        public void handlePressed(MouseEvent event) {
            prevX = event.getX();
            prevY = event.getY();
            double snapX = Math.round(prevX/20) * 20;       //snap to nearest grid point
            double snapY = Math.round(prevY/20) * 20;       //snap to nearest grid point
            if (event.isShiftDown()){
                // create a line
                Groupable line = model.addLine(snapX, snapY, event.getX(), event.getY());
                iModel.addCreateCommand(model, line);       // add it on to the stacks
                iModel.clearSelectedGroups();
                iModel.selectItems(line);
                currentState = creating;
            }
            if(event.isControlDown()){
                Groupable g= model.whichItem(event.getX(), event.getY());
                if(g!=null){
                    // enable multiple selection by clicking on items
                    iModel.selectItems(g);
                }else{
                    // start a rubberband rectangle
                    iModel.startSelection(event.getX(), event.getY());
                    currentState = rubberband;
                }
            }
            else {
                Groupable line = model.whichItem(event.getX(), event.getY());
                DLine epLine = model.whichLineEndpoint(event.getX(), event.getY());
                if(epLine != null){
                    // allow resizing of a line
                    iModel.setSelectedLine(epLine);
                    currentState = dragEndpoint;
                    iModel.startResizeCommand(model, prevX, prevY);
                }
                else if(line!=null){
                    iModel.clearSelectedGroups();
                    iModel.selectItems(line);
                    currentState = dragging;
                    iModel.startDragCommand(model, prevX, prevY);
                }
                else {
                    // click on background unselects
                    iModel.clearSelectedGroups();
                    currentState = ready;
                }
            }
        }
        public void handleReleased(MouseEvent event){
            currentState = ready;
        }
    };
    ControllerState creating = new ControllerState() {
        public void handleDragged(MouseEvent event){
            model.adjustLine(iModel.getSelectedLine(), event.getX(), event.getY());
        }
        public void handleReleased(MouseEvent event){
            double snapX = Math.round(event.getX()/20) * 20;
            double snapY = Math.round(event.getY()/20) * 20;
            model.adjustLine(iModel.getSelectedLine(), snapX, snapY);   // snap to the nearest grid point
            currentState = ready;
        }
    };
    ControllerState dragEndpoint = new ControllerState() {
        public void handleDragged(MouseEvent event){
            model.adjustLine(iModel.getSelectedLine(), event.getX(), event.getY());
        }
        public void handleReleased(MouseEvent event){
            double snapX = Math.round(iModel.getSelectedLine().getX2()/20) * 20;
            double snapY = Math.round(iModel.getSelectedLine().getY2()/20) * 20;
            model.adjustLine(iModel.getSelectedLine(), snapX, snapY);       //snap to the nearest grid point
            iModel.finishResizeCommand(event.getX(), event.getY());
            currentState = ready;
        }
    };
    ControllerState dragging = new ControllerState() {
        public void handleDragged(MouseEvent event){
            double dX = event.getX() - prevX;
            double dY = event.getY() - prevY;
            prevX = event.getX();
            prevY = event.getY();
            model.moveItems(iModel.getSelectedGroups(), dX, dY);
        }
        public void handleReleased(MouseEvent event){
            // snap the line to nearest grid points after dragging
            double snapX1 = Math.round(iModel.getSelectedLine().getX1()/20) * 20;
            double snapY1 = Math.round(iModel.getSelectedLine().getY1()/20) * 20;
            double snapX2 = Math.round(iModel.getSelectedLine().getX2()/20) * 20;
            double snapY2 = Math.round(iModel.getSelectedLine().getY2()/20) * 20;
            iModel.getSelectedLine().setX1(snapX1);
            iModel.getSelectedLine().setY1(snapY1);
            model.adjustLine(iModel.getSelectedLine(), snapX2, snapY2);
            iModel.finishDragCommand(event.getX(), event.getY());
            currentState = ready;

        }
    };
    ControllerState rubberband = new ControllerState() {
        public void handleDragged(MouseEvent event){
            if(iModel.hasRubRect()){
                // keep dragging the rectangle
                iModel.continueSelection(event.getX(), event.getY());
            }
        }
        public void handleReleased(MouseEvent event){
            // select or unselect items in the rectangle
            List<Groupable> selectedItems = model.containsItem(iModel.rubRect);
            if(!selectedItems.isEmpty()){
                iModel.selectGroup(selectedItems);
            }
            iModel.removeRubRect();     // clears the rectangle after click release
            currentState = ready;
        }
    };
}
