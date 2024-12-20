package org.game.model;

public record Position(int x, int y) {
    public Position add(Position other) {
        return new Position(this.x + other.x(), this.y + other.y());
    }
}
