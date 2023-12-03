package hw4.puzzle;

import java.util.ArrayList;
import java.util.List;

public class Board implements WorldState{
    private static final int BLANK = 0;
    private int[][] tiles;
    private int size;
    private int estimateDist;

    /**
     * 
     */
    public Board(int[][] tiles) {
        if (tiles == null || tiles[0] == null || tiles.length != tiles[0].length) {
            throw new IllegalArgumentException();
        }
        this.size = tiles.length;
        this.tiles = new int[size][size];
        estimateDist = - 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    /**
     * 
     */
    public int tileAt(int i, int j) {
        if (!inBounds(i) || !inBounds(j)) {
            throw new IllegalArgumentException("tileAt() is out of bounds");
        }
        return tiles[i][j];
    }
    private boolean inBounds (int index) {
        return index >= 0 && index < tiles.length;
    }

    public int size() {
        return size;
    }

    /**
     * @Scource: Jush Hug online code
     */
    @Override
    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new ArrayList<>();
        int blankx = -1, blanky = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == BLANK) {
                    blankx = i;
                    blanky = j;
                }
            }
        }

        int[][] temp = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                temp[i][j] = tiles[i][j];
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (Math.abs(i - blankx) + Math.abs(j - blanky) - 1 == 0) {
                    temp[blankx][blanky] = temp[i][j];
                    temp[i][j] = BLANK;
                    Board neighbor = new Board(temp);
                    neighbors.add(neighbor);
                    temp[i][j] = tiles[i][j];
                    temp[blankx][blanky] = BLANK;
                }
            }
        }
        return neighbors;
    }

    /**
     * 
     */
    public int hamming() {
        int sum = size * size - 1;
        int legal = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != BLANK && tiles[i][j] == size * (i - 1) + j) {
                    legal++;
                }
            }
        }
        return sum - legal;

    }

    /**
     * 
     */
    public int manhattan() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != BLANK) {
                    sum += Math.abs((tiles[i][j] - 1) % size - j);
                    sum += Math.abs((tiles[i][j] - 1) / size - i);
                }

            }
        }
        return sum;

    }

    /**
     * 
     */
    @Override
    public int estimatedDistanceToGoal() {
        if (estimateDist == -1) {
            return manhattan();
        }
        return estimateDist;
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }
        Board other = (Board) y;
        if (size != other.size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != other.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;

    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
