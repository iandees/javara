package com.yellowbkpk.antfarm.game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.yellowbkpk.antfarm.farm.Farm;

public class AntGame {

    private Farm farm;
    private int time;
    private Timer timer;
    private TimerTask tTask;
    private ArrayList<TickListener> listeners;
    
    public AntGame(Farm f, int stepTime) {
        farm = f;
        time = stepTime;
        
        listeners = new ArrayList<TickListener>();
        
        initTimer();
    }
    
    private void initTimer() {
        timer = new Timer();
        tTask = new TimerTask() {
        
            public void run() {
                farm.tick();
                notifyTickListeners();
            }
        
        };
    }

    public void addTickListener(TickListener listener) {
        listeners.add(listener);
    }
    
    protected void notifyTickListeners() {
        for (TickListener listener : listeners) {
            listener.tickHappened();
        }
    }

    public void start() {
        timer.scheduleAtFixedRate(tTask, 0, time);
    }

    public void stop() {
        System.err.println("Game stop.");
        
        tTask.cancel();
        timer.cancel();
    }

    public Farm getFarm() {
        return farm;
    }

}
