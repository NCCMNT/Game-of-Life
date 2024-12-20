package org.game.simulation;

import org.game.model.GridMap;

public class Simulation implements Runnable{
    private boolean running = true;
    private final GridMap gridMap;

    public Simulation(GridMap gridMap) {
        this.gridMap = gridMap;
    }

    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            gridMap.executeTurn();
        }
    }

    public void stop(){
        running = false;
    }
}
