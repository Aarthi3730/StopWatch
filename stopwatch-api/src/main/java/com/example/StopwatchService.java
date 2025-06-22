package com.example.stopwatch;

import org.springframework.stereotype.Service;

@Service
public class StopwatchService {

    private long startTime;
    private long elapsedTime;
    private boolean running;

    public void start() {
        if (!running) {
            startTime = System.currentTimeMillis();
            running = true;
        }
    }

    public void stop() {
        if (!running) {
            throw new IllegalStateException("Cannot stop. Stopwatch was not started.");
        }
        elapsedTime += System.currentTimeMillis() - startTime;
        running = false;
    }

   public void reset() {
    if (!running && elapsedTime == 0) {
        System.out.println("Stopwatch is already reset.");
        return;
    }
    startTime = 0;
    elapsedTime = 0;
    running = false;
}


    public long getElapsedTime() {
        if (running) {
            return elapsedTime + (System.currentTimeMillis() - startTime);
        } else {
            return elapsedTime;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
