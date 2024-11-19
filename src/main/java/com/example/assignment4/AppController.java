package com.example.assignment4;

import javafx.scene.input.MouseEvent;

public class AppController {
    private LineModel model;
    private InteractionModel iModel;
    private ControllerState currentState;
    private double snapX, snapY;

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
    public void handleKeyPressed(MouseEvent event){
        currentState.handleKeyPressed(event);
    }
    private abstract class ControllerState {
        void handlePressed(MouseEvent event){}
        void handleReleased(MouseEvent event){}
        void handleDragged(MouseEvent event){}
        void handleKeyPressed(MouseEvent event){}
    }

    ControllerState ready = new ControllerState() {
        @Override
        public void handlePressed(MouseEvent event) {
            if (event.isShiftDown()){
                DLine line = model.addLine(event.getX(), event.getY(), event.getX(), event.getY());
                iModel.setSelectedLine(line);
                currentState = creating;
            }
        }
    };
    ControllerState creating = new ControllerState() {
        public void handleDragged(MouseEvent event){
            model.adjustLine(iModel.getSelectedLine(), event.getX(), event.getY());
        }
    };
}
