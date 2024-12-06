package com.example.assignment4;

public class ResizeCommand implements DCommand{
    LineModel model;
    DLine line;
    double startX, startY, dx, dy;

    public ResizeCommand(LineModel model, DLine line, double sx, double sy) {
        this.model = model;
        this.line = line;
        startX = sx;
        startY = sy;
        dx = 0;
        dy = 0;
    }
    public void finishResize(double x, double y) {
        dx = x - startX;
        dy = y - startY;
    }
    @Override
    public void doIt() {
        model.adjustLine(line, dx, dy);
    }

    @Override
    public void undo() {
        model.adjustLine(line, startX, startY);
    }
}
