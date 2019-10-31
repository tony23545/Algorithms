import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double mean_;
    private final double stddev_;
    private final double confidenceLo_;
    private final double confidenceHi_;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1)
            throw new IllegalArgumentException("illegal n!");
        if (trials < 1)
            throw new IllegalArgumentException("illegal trials!");

        double[] testResult = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            double count = 0;

            while (!percolation.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!percolation.isOpen(row, col)) {
                    count++;
                    percolation.open(row, col);
                }

            }
            testResult[i] = count / (n * n);
        }

        double confidenceConst = 1.96;
        mean_ = StdStats.mean(testResult);
        stddev_ = StdStats.stddev(testResult);
        confidenceHi_ = mean_ + confidenceConst * stddev_ / Math.sqrt(trials);
        confidenceLo_ = mean_ - confidenceConst * stddev_ / Math.sqrt(trials);
    }

    // sample mean of percolation threshold
    public double mean() {
        return mean_;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stddev_;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return confidenceLo_;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return confidenceHi_;
    }

    // test client (see below)
    public static void main(String[] args){
        int n = 20;
        int trials = 300;
        if(args.length > 2){
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
        }else
            System.out.println("use default setting!");

        PercolationStats PS = new PercolationStats(n, trials);
        System.out.printf("mean = %f\n", PS.mean());
        System.out.printf("stddev = %f\n", PS.stddev());
        System.out.printf("95%% confidence interval = [%f, %f]", PS.confidenceLo(), PS.confidenceHi());
    }
}