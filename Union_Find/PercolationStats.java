package algo4_lab_percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int testCnt = 0;
    private double[] test;
    private static final double x = 1.96;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Invalid");
        }
        test = new double[trials];
        testCnt = trials;
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            while (!p.percolates()) {
                int rRand = StdRandom.uniformInt(1, n + 1);
                int cRand = StdRandom.uniformInt(1, n + 1);
                if (!p.isOpen(rRand, cRand)) {
                    p.open(rRand, cRand);
                }
            }
            test[i] = (double) p.numberOfOpenSites() / (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(test);
    }

    public double stddev() {
        return StdStats.stddev(test);
    }

    public double confidenceLo() {
        return mean() - x * stddev() / Math.sqrt(testCnt);
    }

    public double confidenceHi() {
        return mean() + x * stddev() / Math.sqrt(testCnt);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, trials);
        double mean = ps.mean();
        double stddev = ps.stddev();
        double lo = ps.confidenceLo();
        double hi = ps.confidenceHi();
        StdOut.println("mean = " + mean);
        StdOut.println("stdevv = " + stddev);
        StdOut.println("95% confidence interval = " + "[" + lo + "," + hi + "]");
    }
}
