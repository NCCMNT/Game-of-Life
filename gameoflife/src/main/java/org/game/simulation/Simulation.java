package org.game.simulation;

import org.game.model.GridMap;

public class Simulation implements Runnable{
    private boolean running = true;
    private final GridMap gridMap;
    private int speed = 500;

    public Simulation(GridMap gridMap, int speed) {
        this.gridMap = gridMap;
        this.speed = speed;
    }

    public void setSpeed(int speed){
        this.speed = speed;
    }

    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(this.speed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gridMap.executeTurn();
        }
    }

    public void stop(){
        running = false;
    }
    public void resume() { running = true; }
}
