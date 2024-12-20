package org.game.model;

import java.util.ArrayList;
import java.util.List;

public class Neighbours {
    private final List<Position> neighbourCells = new ArrayList<>();

    public Neighbours() {
        this.neighbourCells.add(new Position(-1, 1));
        this.neighbourCells.add(new Position(0, 1));
        this.neighbourCells.add(new Position(1, 1));
        this.neighbourCells.add(new Position(1, 0));
        this.neighbourCells.add(new Position(1, -1));
        this.neighbourCells.add(new Position(0, -1));
        this.neighbourCells.add(new Position(-1, -1));
        this.neighbourCells.add(new Position(-1, 0));
    }

    public List<Position> getNeighbours() {
        return neighbourCells;
    }
}