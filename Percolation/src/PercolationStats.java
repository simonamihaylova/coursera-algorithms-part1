import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private static final double CONFIDENCE_95 = 1.96;

	private final double[] sum;
	private double mean = -1.0;
	private double stddev = -1.0;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {

		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException("The parameters must be positive numbers");
		}

		this.sum = new double[trials];
		int completedTrials = 0;

		while (completedTrials < trials) {
			Percolation p = new Percolation(n);
			double cnt = 0;
			while (!p.percolates()) {
				int i = StdRandom.uniform(n) + 1;
				int j = StdRandom.uniform(n) + 1;

				if (!p.isOpen(i, j)) {
					p.open(i, j);
					cnt++;
				}
			}
			sum[completedTrials] = cnt / (n * n);

			completedTrials++;
		}

	}

	// sample mean of percolation threshold
	public double mean() {
		if (this.mean == -1.0) {
			this.mean = StdStats.mean(sum);
		}
		return this.mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		if (this.stddev == -1.0) {
			this.stddev = StdStats.stddev(sum);
		}
		return this.stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		double mean = mean();
		double stdDev = stddev();
		return mean - (CONFIDENCE_95 * stdDev / Math.sqrt(sum.length));
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		double mean = mean();
		double stdDev = stddev();
		return mean + (CONFIDENCE_95 * stdDev / Math.sqrt(sum.length));
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);

		PercolationStats ps = new PercolationStats(n, trials);
		System.out.printf("mean                    = %f%n", ps.mean());
		System.out.printf("stddev                  = %f%n", ps.stddev());
		System.out.printf("95%% confidence interval = [%f, %f]", ps.confidenceLo(), ps.confidenceHi());

	}
}
