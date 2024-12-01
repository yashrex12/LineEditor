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

    public void draw() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int i = 0; i < 1000; i++) {
            gc.setStroke(Color.rgb(200, 200, 200, 0.7));
            gc.strokeLine(0, i * 20, 10000, i * 20);
            gc.strokeLine(i * 20, 0, i * 20, 10000);
        }
        if (iModel.hasRubRect()) {
            Rubberband rect = iModel.getRubRect();
            gc.setStroke(Color.RED);
            gc.setLineWidth(1);
            gc.setLineDashes(10, 5);
            gc.strokeRect(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
            gc.setLineDashes(0);
        }
        model.getItems().forEach(this::drawItem);
    }

    private void drawItem(Groupable g){
        if(g.hasChildren()){
            gc.setStroke(Color.HOTPINK);
            gc.setLineWidth(1);
            gc.strokeRect(g.getLeft(), g.getTop(), g.getRight() - g.getLeft(), g.getBottom() - g.getTop());
            g.getChildren().forEach(this::drawItem);
        }
        else if(g instanceof DLine line){
            if (iModel.isHoveredLine(line)){
                gc.setStroke(Color.LIGHTGRAY);
                gc.setLineWidth(10);
                gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
            if (iModel.getSelectedGroups().contains(g)){
                gc.setStroke(Color.PINK);
                gc.setLineWidth(2);
                gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
                gc.setFill(Color.GREEN);
                gc.fillOval(line.getX1()-4, line.getY1()-4, 8, 8);          //handle with radius 4
                gc.fillOval(line.getX2()-4, line.getY2()-4, 8, 8);
            }else {
                gc.setStroke(Color.PURPLE);
                gc.setLineWidth(1);
                gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
            }
        }
    }

    @Override
    public void modelChanged(){
        draw();
    }
}
