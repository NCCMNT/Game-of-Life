package org.game.utils;

import org.game.model.GridMap;
import org.game.model.Position;

import java.util.List;

public interface MapObserver {
    /**
     *
     */
    void cellsToBeChanged( GridMap gridMap , List<Position> positions);
}
