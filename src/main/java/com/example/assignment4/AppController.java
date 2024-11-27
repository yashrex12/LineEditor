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
        System.out.println("something");
        if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.DELETE){
            System.out.println("backspace");
            if(iModel.getSelectedLine()!=null){
                model.removeLine(iModel.getSelectedLine());
                iModel.clearSelectedLine();
            }
        }
        if(event.getCode() == KeyCode.LEFT){
            rotateLine(-Math.PI/15);
        }else if (event.getCode() == KeyCode.RIGHT){
            rotateLine(Math.PI/15);
        }

        if(event.getCode() == KeyCode.UP){
            scaleLine(0.5);
        }else if(event.getCode() == KeyCode.DOWN){
            scaleLine(-0.5);
        }
    }
    private void rotateLine(double angle){
        DLine line = iModel.getSelectedLine();
        if(line != null){
            double cx = (line.getX1() + line.getX2()) / 2;
            double cy = (line.getY1() + line.getY2()) / 2;
            line.setX1(rotateX(line.getX1(), line.getY1(), cx, cy, angle));
            line.setY1(rotateY(line.getX1(), line.getY1(), cx, cy, angle));
            line.setX2(rotateX(line.getX2(), line.getY2(), cx, cy, angle));
            line.setY2(rotateY(line.getX2(), line.getY2(), cx, cy, angle));
        }
    }
    private double rotateX(double x, double y, double cx, double cy, double angle){
        return cx + (x-cx) * Math.cos(angle) - (y-cy) * Math.sin(angle);
    }
    private double rotateY(double x, double y, double cx, double cy, double angle){
        return cy + (x-cx) * Math.sin(angle) + (y-cy) * Math.cos(angle);
    }

    private void scaleLine(double scale){
        DLine line = iModel.getSelectedLine();
        if(line != null){
            double cx = (line.getX1() + line.getX2()) / 2;
            double cy = (line.getY1() + line.getY2()) / 2;
            line.setX1(scaleX(line.getX1(), line.getY1(), cx, cy, scale));
            line.setY1(scaleY(line.getX1(), line.getY1(), cx, cy, scale));
            line.setX2(scaleX(line.getX2(), line.getY2(), cx, cy, scale));
            line.setY2(scaleY(line.getX2(), line.getY2(), cx, cy, scale));
        }
    }
    private double scaleX(double x, double y, double cx, double cy, double scale){
        return cx + (x-cx) * scale;
    }
    private double scaleY(double x, double y, double cx, double cy, double scale){
        return cy + (y-cy) * scale;
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
