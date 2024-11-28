package com.example.assignment4;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

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
        DLine line = model.whichLine(event.getX(), event.getY());
        if(line != null){
            iModel.setHoveredLine(line);
        }else {
            iModel.clearHoveredLine();
        }
    }
    public void handleKeyPressed(KeyEvent event){
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            if(iModel.getSelectedLine()!=null){
                model.removeLine(iModel.getSelectedLine());
                iModel.clearSelectedLine();
            }
        }
        if(event.getCode() == KeyCode.LEFT){
            if(iModel.getSelectedLine()!=null){
                model.rotateLine(iModel.getSelectedLine(),-Math.PI/15);
            }
        }
        if(event.getCode() == KeyCode.RIGHT){
            if(iModel.getSelectedLine()!=null){
                model.rotateLine(iModel.getSelectedLine(),Math.PI/15);
            }
        }
        if(event.getCode() == KeyCode.UP){
            if(iModel.getSelectedLine()!=null) {
                model.scaleLine(iModel.getSelectedLine(), 1.2);
            }
        }
        if(event.getCode() == KeyCode.DOWN){
            if(iModel.getSelectedLine()!=null) {
                model.scaleLine(iModel.getSelectedLine(), 0.8);
            }
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
            double snapX = Math.round(prevX/20) * 20;
            double snapY = Math.round(prevY/20) * 20;
            if (event.isShiftDown()){
                DLine line = model.addLine(snapX, snapY, event.getX(), event.getY());
                iModel.setSelectedLine(line);
                currentState = creating;
            }else {
                DLine line = model.whichLine(event.getX(), event.getY());
                DLine epLine = model.whichLineEndpoint(event.getX(), event.getY());
                if(epLine != null){
                    iModel.setSelectedLine(epLine);
                    currentState = dragEndpoint;
                }
                else if(line!=null){
                    iModel.setSelectedLine(line);
                    currentState = dragging;
                }else {
                    iModel.clearSelectedLine();
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
            model.adjustLine(iModel.getSelectedLine(), snapX, snapY);
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
            model.adjustLine(iModel.getSelectedLine(), snapX, snapY);
            currentState = ready;
        }
    };
    ControllerState dragging = new ControllerState() {
        public void handleDragged(MouseEvent event){
            double dX = event.getX() - prevX;
            double dY = event.getY() - prevY;
            prevX = event.getX();
            prevY = event.getY();
            model.moveLine(iModel.getSelectedLine(), dX, dY);
        }
        public void handleReleased(MouseEvent event){
            double snapX1 = Math.round(iModel.getSelectedLine().getX1()/20) * 20;
            double snapY1 = Math.round(iModel.getSelectedLine().getY1()/20) * 20;
            double snapX2 = Math.round(iModel.getSelectedLine().getX2()/20) * 20;
            double snapY2 = Math.round(iModel.getSelectedLine().getY2()/20) * 20;
            iModel.getSelectedLine().setX1(snapX1);
            iModel.getSelectedLine().setY1(snapY1);
            model.adjustLine(iModel.getSelectedLine(), snapX2, snapY2);
            currentState = ready;

        }
    };
}
