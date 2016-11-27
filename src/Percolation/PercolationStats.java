package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] statistics;
    private int trials;

    // Perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        this.statistics = new double[trials];
        this.trials = trials;

        // Perform Percolation tests
        for (int i = 0; i < trials; i++) {
            // Declare new object and current open sites to 0
            Percolation pc = new Percolation(n);
            int openSites = 0;

            // We loop while not Percolates and keep a counter for track
            while (!pc.percolates()) {
                // Generate random row and col based on size of grid
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);

                // If not already open, make it open
                if (!pc.isOpen(row, col)) {
                    pc.open(row, col);
                    openSites++;
                }
            }

            // Set statistic, we don't want integer division o_O
            statistics[i] = (double) openSites / (n * n);
        }
    }

    // Sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(statistics);
    }

    // Sample SD of percolation threshold
    public double stddev() {
        return StdStats.stddev(statistics);
    }

    // Low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // High endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(trials));
    }

    // Test client
    public static void main(String[] args) {
        if (args.length != 2)
            System.out.println("Must contain 2 arguments");

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = "
                + ps.confidenceLo() + ", " + ps.confidenceHi());
    }
}
