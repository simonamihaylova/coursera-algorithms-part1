import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private ArrayList<Board> solutionPath;
    private final int moves;
    private final boolean isSolvable;

    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final SearchNode previous;

        private final int manhattan;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
            this.manhattan = board.manhattan(); // cache the Manhattan distance to avoid recomputing it each time during
                                                // various priority queue operations
        }

        public int getPriority() {
            return manhattan + moves;
        }

        @Override
        public int compareTo(Solver.SearchNode s) {
            if (this.getPriority() > s.getPriority()) {
                return 1;
            } else if (this.getPriority() < s.getPriority()) {
                return -1;
            }
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) {
            throw new IllegalArgumentException();
        }

        SearchNode lastNode = aStarSearch(initial);

        if (lastNode != null) {
            isSolvable = true;
            moves = lastNode.moves;
            saveSolutionPath(lastNode);

        } else {
            isSolvable = false;
            moves = -1;
            solutionPath = null;
        }

    }

    // A* algorithm. If the initial board is solvable it will lead to the solution,
    // otherwise the twin board will. If the initial board is solvable,
    // return the last explored node, otherwise return null
    private SearchNode aStarSearch(Board initial) {

        MinPQ<SearchNode> initialNodes = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinNodes = new MinPQ<SearchNode>();

        initialNodes.insert(new SearchNode(initial, 0, null));
        twinNodes.insert(new SearchNode(initial.twin(), 0, null));

        while (!initialNodes.min().board.isGoal() && !twinNodes.min().board.isGoal()) {

            SearchNode currInitNode = initialNodes.delMin();
            SearchNode currTwinNode = twinNodes.delMin();

            for (Board neighbor : currInitNode.board.neighbors()) {
                if (currInitNode.previous == null || !neighbor.equals(currInitNode.previous.board)) {
                    initialNodes.insert(new SearchNode(neighbor, currInitNode.moves + 1, currInitNode));
                }
            }

            for (Board neighbor : currTwinNode.board.neighbors()) {
                if (currTwinNode.previous == null || !neighbor.equals(currTwinNode.previous.board)) {
                    twinNodes.insert(new SearchNode(neighbor, currTwinNode.moves + 1, currTwinNode));
                }
            }
        }

        if (initialNodes.min().board.isGoal()) {
            return initialNodes.min();
        }

        return null;
    }

    // reconstruct the solution path and save it
    private void saveSolutionPath(SearchNode node) {
        solutionPath = new ArrayList<Board>();

        while (node != null) {
            solutionPath.add(0, node.board);
            node = node.previous;
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solutionPath;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    }

}