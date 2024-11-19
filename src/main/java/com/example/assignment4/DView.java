package com.example.assignment4;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class DView extends StackPane implements Subscriber {
    private LineModel model;
    private GraphicsContext gc;
    private Canvas canvas;

    public DView() {
        canvas = new Canvas(1000, 800);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);
    }
    public void setModel(LineModel model) {
        this.model = model;
    }
    public void setupEvents(AppController controller){
        canvas.setOnMousePressed(controller::handlePressed);
        canvas.setOnMouseDragged(controller::handleDragged);
        canvas.setOnMouseReleased(controller::handleReleased);
    }

    public void draw(){
        for (int i=0; i<1000; i++){
            gc.strokeLine(0, i*20, 10000, i*20);
            gc.strokeLine(i*20, 0, i*20, 10000);
        }
        model.getLines().forEach(dl ->{
            gc.strokeLine(dl.getX1(), dl.getY1(), dl.getX2(), dl.getY2());
        });
    }

    @Override
    public void modelChanged(){
        draw();
    }
}
