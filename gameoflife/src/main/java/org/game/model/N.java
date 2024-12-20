package org.game.model;

public enum N {
    TOP,
    TOP_RIGHT,
    RIGHT,
    BOTTOM_RIGHT,
    BOTTOM,
    BOTTOM_LEFT,
    LEFT,
    TOP_LEFT;

    public Position toPosition() {
        return switch(this){
            case TOP -> new Position(0,1);
            case TOP_RIGHT -> new Position(1,1);
            case RIGHT -> new Position(1,0);
            case BOTTOM_RIGHT -> new Position(1,-1);
            case BOTTOM -> new Position(0,-1);
            case BOTTOM_LEFT -> new Position(-1,-1);
            case LEFT -> new Position(-1,0);
            case TOP_LEFT -> new Position(-1,1);
        };
    }
}
