package com.example.assignment4;

// establish a publish-subscriber mechanism for any reported changes
public interface Subscriber {
    void modelChanged();
}
