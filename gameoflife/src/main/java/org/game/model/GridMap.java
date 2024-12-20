package org.game.model;


import org.game.utils.GridMapObserver;
import org.game.utils.MapObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GridMap {
    private final int rows;
    private final int columns;
    private final HashMap<Position, Boolean> cellsStates;
    private final List<Position> neighbours = new Neighbours().getNeighbours();
    private List<Position> cellsToBeChanged;
    private List<MapObserver> observers = new ArrayList<>();

    public GridMap(int rows, int columns, HashMap<Position, Boolean> cellsStates) {
        this.rows = rows;
        this.columns = columns;
        this.cellsStates = cellsStates;
    }

    public int getRows() { return rows; }

    public int getColumns() { return columns; }

    public void executeTurn(){
        this.cellsToBeChanged = new ArrayList<>();
        for (Position position : cellsStates.keySet()) {
            final int cln = countLivingNeighbours(position);
            final boolean cellState = cellsStates.get(position);
            if ((cellState && cln < 2) || (cellState && cln > 3) || (!cellState && cln == 3)) {
                cellsToBeChanged.add(position);
            }
        }
        this.notifyObservers(cellsToBeChanged);
    }

    private int countLivingNeighbours(Position position) {
        int count = 0;
        for (Position neighbour : this.neighbours){
            Position current_position = position.add(neighbour);
            if ((current_position.x() < columns) && (current_position.y() < rows)
            && (current_position.x() >= 0) && (current_position.y() >= 0)
            && cellsStates.get(current_position)) {
                count++;
            }
        }
        return count;
    }

    public void addObserver(MapObserver observer) { observers.add(observer); }
    public void removeObserver(MapObserver observer) { observers.remove(observer); }
    public void notifyObservers(List<Position> positions) {
        for (MapObserver observer : observers) {
            observer.cellsToBeChanged(this, positions);
        }
    }
}
