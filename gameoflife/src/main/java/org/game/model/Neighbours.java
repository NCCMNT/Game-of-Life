package org.game.model;

import java.util.ArrayList;
import java.util.List;

public class Neighbours {
    private final List<Neighbour> neighbourCells = new ArrayList<>();

    public Neighbours() {
        this.neighbourCells.add(new Neighbour(-1, 1));
        this.neighbourCells.add(new Neighbour(0, 1));
        this.neighbourCells.add(new Neighbour(1, 1));
        this.neighbourCells.add(new Neighbour(1, 0));
        this.neighbourCells.add(new Neighbour(1, -1));
        this.neighbourCells.add(new Neighbour(0, -1));
        this.neighbourCells.add(new Neighbour(-1, -1));
        this.neighbourCells.add(new Neighbour(-1, 0));
    }

    public List<Neighbour> getNeighbours() {
        return neighbourCells;
    }
}
