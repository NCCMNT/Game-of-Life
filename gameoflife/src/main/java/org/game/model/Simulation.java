package org.game.model;


import java.util.List;

public class Simulation {
    private final int rows;
    private final int columns;
    private final boolean[][] grid;
    private final List<Neighbour> neighbours = new Neighbours().getNeighbours();

    public Simulation(int rows, int columns, boolean[][] grid) {
        this.rows = rows;
        this.columns = columns;
        this.grid = grid;
    }

    public int getRows() { return rows; }

    public int getColumns() { return columns; }

    public void run(){
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                final int cln = countLivingNeighbours(row, column);
                final boolean cellState = this.grid[row][column];
                if (cellState && cln < 2) {
                    //cell dies
                }
                else if (cellState && cln > 3) {
                    //cell dies
                }
                else if (!cellState && cln == 3) {
                    //cell is made alive
                }
            }
        }
    }

    private int countLivingNeighbours(int row, int column) {
        int count = 0;
        for (Neighbour neighbour : this.neighbours){
            if ((row + neighbour.x() >= 0) && (row + neighbour.x() < this.rows)
                && (column + neighbour.y() >= 0) && (column + neighbour.y() < this.columns)
                && (this.grid[row][column])) count++;
        }
        return count;
    }
}
