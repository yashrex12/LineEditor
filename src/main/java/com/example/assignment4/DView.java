package com.example.assignment4;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DView extends StackPane implements Subscriber {
    private LineModel model;
    private InteractionModel iModel;
    private GraphicsContext gc;
    private Canvas canvas;

    public DView() {
        canvas = new Canvas(1000, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
        Platform.runLater(canvas::requestFocus);
    }
    public void setModel(LineModel model) {
        this.model = model;
        model.addSubscriber(this);
    }
    public void setInteractionModel(InteractionModel im) {
        this.iModel = im;
        iModel.addSubscriber(this);
    }
    public void setupEvents(AppController controller){
        canvas.setOnMousePressed(controller::handlePressed);
        canvas.setOnMouseDragged(controller::handleDragged);
        canvas.setOnMouseReleased(controller::handleReleased);
        canvas.setOnKeyPressed(controller::handleKeyPressed);
        canvas.setOnMouseMoved(controller::handleMouseMoved);
    }

    public void draw(){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i=0; i<1000; i++){
            gc.setStroke(Color.rgb(200, 200, 200, 0.7));
            gc.strokeLine(0, i*20, 10000, i*20);
            gc.strokeLine(i*20, 0, i*20, 10000);
        }
        if(iModel.hasRubRect()){
            Rubberband rect = iModel.getRubRect();
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.setLineDashes(10, 5);
            gc.strokeRect(rect.left, rect.top, rect.right-rect.left, rect.bottom-rect.top);
            gc.setLineDashes(0);
        }
        model.getLines().forEach(dl ->{
            if(iModel.isHoveredLine(dl)){
                gc.setStroke(Color.LIGHTGRAY);
                gc.setLineWidth(10);
                gc.strokeLine(dl.getX1(), dl.getY1(), dl.getX2(), dl.getY2());
            }

            if(iModel.getSelectedLines().contains(dl)){
                gc.setStroke(Color.PINK);
                gc.setLineWidth(2);
                gc.strokeLine(dl.getX1(), dl.getY1(), dl.getX2(), dl.getY2());
                gc.setFill(Color.GREEN);
                gc.fillOval(dl.getX1()-4, dl.getY1()-4, 8, 8);          //handle with radius 4
                gc.fillOval(dl.getX2()-4, dl.getY2()-4, 8, 8);
            }else {
                gc.setStroke(Color.PURPLE);
                gc.setLineWidth(1);
                gc.strokeLine(dl.getX1(), dl.getY1(), dl.getX2(), dl.getY2());
            }
        });
    }

    @Override
    public void modelChanged(){
        draw();
    }
}
