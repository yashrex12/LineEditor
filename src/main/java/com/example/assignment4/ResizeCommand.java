package com.example.assignment4;

// command for dragging the endpoint of a line
public class ResizeCommand implements DCommand{
    LineModel model;
    DLine line;
    double startX, startY, dx, dy;

    public ResizeCommand(LineModel model, DLine line, double sx, double sy) {
        this.model = model;
        this.line = line;
        startX = sx;        // starting location
        startY = sy;        // starting location
        dx = 0;
        dy = 0;
    }
    public void finishResize(double x, double y) {
        // calculate by how much the line was adjusted
        dx = x - startX;
        dy = y - startY;
    }
    @Override
    public void doIt() {
        // adjust the line size
        model.adjustLine(line, dx, dy);
    }

    @Override
    public void undo() {
        // adjust back the line size by changed amount
        model.adjustLine(line, startX, startY);
    }
}
