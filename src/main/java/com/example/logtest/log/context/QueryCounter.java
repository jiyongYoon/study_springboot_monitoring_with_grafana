package com.example.logtest.log.context;

public class QueryCounter {

    private int count;

    public void increase() {
        count++;
    }

    public int count() {
        return count;
    }
}
