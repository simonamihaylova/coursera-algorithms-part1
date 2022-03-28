import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] grid;
	private boolean[][] fullSites;

	private final WeightedQuickUnionUF quickUnion;
	private final int length;

	private final int[] neighbours = { -1, 1 };
	private final int topVertexIndex;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {

		if (n <= 0) {
			throw new IllegalArgumentException("N must be positive");
		}

		this.length = n;
		this.grid = new boolean[this.length][this.length];
		this.fullSites = new boolean[this.length][this.length];
		this.quickUnion = new WeightedQuickUnionUF(n * n + 1);
		this.topVertexIndex = n * n;
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {

		if (isOpen(row, col)) {
			return;
		}

		row = row - 1;
		col = col - 1;

		isOutsideRange(row, col);

		grid[row][col] = true;
		unionWithNeighbours(row, col);

		int index = row * this.length + col;

		if (quickUnion.connected(index, topVertexIndex)) {
			fullSites[row][col] = true;
		}
	}

	private void unionWithNeighbours(int row, int col) {
		for (int i = 0; i < neighbours.length; i++) {
			int newRow = row + neighbours[i];
			int newCol = col + neighbours[i];
			if (newRow >= 0 && newRow < this.length && isOpenRealNums(newRow, col)) {
				connect(row, col, newRow, col);
			}
			if (newCol >= 0 && newCol < this.length && isOpenRealNums(row, newCol)) {
				connect(row, col, row, newCol);
			}
		}

		if (row == 0) {
			connectToVirtualVertex(row, col);
		}

	}

	private void connectToVirtualVertex(int row, int col) {
		int index = row * this.length + col;

		quickUnion.union(index, topVertexIndex);
	}

	private void connect(int row, int col, int neighbourRow, int neighbourCol) {
		int index = row * this.length + col;
		int neighbourIndex = neighbourRow * this.length + neighbourCol;

		quickUnion.union(index, neighbourIndex);

	}

	// must be in the interval (0, n-1)
	private void isOutsideRange(int row, int col) {
		if (row < 0 || row >= this.length || col < 0 || col >= this.length) {
			throw new IllegalArgumentException("There is an argument outside of range");
		}

	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		row = row - 1;
		col = col - 1;

		isOutsideRange(row, col);
		return isOpenRealNums(row, col);
	}

	private boolean isOpenRealNums(int row, int col) {
		return this.grid[row][col] == true;
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {

		row = row - 1;
		col = col - 1;

		isOutsideRange(row, col);

		return fullSites[row][col];

	}

	// returns the number of open sites
	public int numberOfOpenSites() {

		int count = 0;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				if (grid[i][j] == true) {
					count++;
				}
			}
		}
		return count;
	}

	// does the system percolate?
	public boolean percolates() {

		for (int i = 1; i <= this.length; i++) {
			if (isFull(this.length, i)) {
				return true;
			}
		}

		return false;
	}

	public static void main(String[] args) {
		Percolation p = new Percolation(3);

		p.open(1, 3);
		System.out.println(p.isFull(1, 3));

		p.open(2, 3);
		System.out.println(p.isFull(2, 3));

		p.open(3, 3);
		System.out.println(p.isFull(3, 3));

		/*
		 * p.open(3, 1); System.out.println(p.isFull(3, 1));
		 */
		p.open(3, 1);
		System.out.println(p.isFull(3, 1));
		System.out.println(p.percolates());

	}

}
