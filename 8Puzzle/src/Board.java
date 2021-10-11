
import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int[][] tiles;

    /*
     * create a board from an n-by-n array of tiles, where tiles[row][col] = tile at
     * (row, col)
     */
    public Board(int[][] tiles) {
        this.tiles = copyArray(tiles);
    }

    // a copy of the given array
    private int[][] copyArray(int[][] arr) {
        int[][] copy = new int[arr.length][arr.length];

        for (int row = 0; row < arr.length; row++) {
            for (int col = 0; col < arr.length; col++) {
                copy[row][col] = arr[row][col];
            }
        }

        return copy;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Board other = (Board) obj;
        if (!Arrays.deepEquals(tiles, other.tiles))
            return false;
        return true;
    }

    // string representation of this board
    public String toString() {
        StringBuilder tilesString = new StringBuilder(tiles.length + "\n");

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                tilesString.append(String.format("%2d ", tiles[row][col]));
            }
            tilesString.append("\n");
        }

        return tilesString.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int distance = 0;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != 0 && tiles[row][col] != (row * tiles.length) + col + 1) {
                    distance += 1;
                }
            }
        }

        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {
                if (tiles[row][col] != 0 && tiles[row][col] != (row * tiles.length) + col + 1) {
                    distance += calculateManhattan(row, col, tiles[row][col]);
                }
            }
        }

        return distance;
    }

    // calculates the Manhattan distance for the given tile
    private int calculateManhattan(int row, int col, int tile) {
        int realRow = (tile - 1) / tiles.length;
        int realCol = (tile - 1) % tiles.length;
        return Math.abs(realRow - row) + Math.abs(realCol - col);
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[][] neighborDirections = { { 0, 1 }, { 0, -1 }, { 1, 0 }, { -1, 0 } };
        ArrayList<Board> neighbors = new ArrayList<Board>();

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles.length; col++) {

                if (tiles[row][col] == 0) {
                    for (int[] direction : neighborDirections) {
                        int neighborRow = row + direction[0];
                        int neighborCol = col + direction[1];

                        if (isInBoundaries(neighborRow, neighborCol)) {
                            neighbors.add(new Board(swapTiles(row, col, neighborRow, neighborCol)));
                        }
                    }
                }
            }
        }

        return neighbors;
    }

    /*
     * check if the given row and column positions are in the boundaries of the
     * tiles
     */
    private boolean isInBoundaries(int row, int col) {
        return row >= 0 && row < tiles.length && col >= 0 && col < tiles.length;
    }

    /*
     * return a copy of the tiles with exchanged values for the given pairs of tiles
     */
    private int[][] swapTiles(int row1, int col1, int row2, int col2) {
        int[][] swappedTiles = copyArray(tiles);

        int temp = swappedTiles[row1][col1];
        swappedTiles[row1][col1] = swappedTiles[row2][col2];
        swappedTiles[row2][col2] = temp;

        return swappedTiles;
    }

    /*
     * a board that is obtained by exchanging any pair of tiles. Exchange the first
     * 2 elements in the first row if neither of them is zero, otherwise exchange
     * the first 2 elements of the second row. We know that the length of the tiles
     * is >= 2 and only one of the elements is zero.
     */

    public Board twin() {

        if (tiles[0][0] != 0 && tiles[0][1] != 0) {
            return new Board(swapTiles(0, 0, 0, 1));
        }

        return new Board(swapTiles(1, 0, 1, 1));
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] board = { { 8, 1, 3 }, { 4, 0, 2 }, { 7, 6, 5 } };

        Board b = new Board(board);
        System.out.println(b);
        System.out.println(b.hamming());
        System.out.println(b.manhattan());
        System.out.println(b.neighbors());
        System.out.println(b.twin());

    }

}