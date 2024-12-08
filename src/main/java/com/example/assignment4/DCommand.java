package com.example.assignment4;

// allows undo and redo of a command
public interface DCommand {
    void doIt();
    void undo();
}
