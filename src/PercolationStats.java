

import java.lang.IllegalArgumentException;

public class PercolationStats {
   
    private int N; // N x N percolation grid
    private int T; // Number of experiments
    private double [] ratio; // ratio[i] =(number cells open for grid to percolate)/N
                             // in the ith iteration where 0 <= i < T
   
    // 0 <= iter < T
    private void runExperiment(int iter) {
        int i, j;
        int numOpen = 0;
        Percolation perc = new Percolation(this.N);
        while(!perc.percolates()) {
            i = StdRandom.uniform(1, this.N+1);
            j = StdRandom.uniform(1, this.N+1);
            if(!perc.isOpen(i, j)) {
                perc.open(i, j);
                numOpen++;
            }
        }
        ratio[iter] =(double)numOpen/(double)(this.N*this.N);
    }
   
    public PercolationStats(int N, int T) {
        int i;
       
        if(N <= 0) throw new IllegalArgumentException("Value of N = " + N);
        if(T <= 0) throw new IllegalArgumentException("Value of T = " + T);

        this.N = N;
        this.T = T;
        ratio = new double[T];
        for(i = 0; i < T; i++) runExperiment(i);
    }
   
    public double mean() {
        return StdStats.mean(ratio);
    }

    public double stddev() {
        if(this.T == 1) return 0;
        else return StdStats.stddev(ratio);
    }
   
    public double confidenceLo() {
        return(mean() -((1.96*stddev())/java.lang.Math.sqrt(this.T)));
    }

    public double confidenceHi() {
        return(mean() +((1.96*stddev())/java.lang.Math.sqrt(this.T)));
    }
   
    /*
     * main() method that takes two command-line arguments N and T, 
     * performs T independent computational experiments on an N-by-N grid, 
     * and prints out the mean, standard deviation, and the 95% confidence 
     * interval for the percolation threshold.
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]); 
        int T = Integer.parseInt(args[1]);
       
        PercolationStats percStats = new PercolationStats(N, T);
        StdOut.print("mean                    = ");
        StdOut.println(percStats.mean());
        StdOut.print("stddev                  = ");
        StdOut.println(percStats.stddev());       
        StdOut.print("95% confidence interval = ");
        StdOut.print(percStats.confidenceLo());   
        StdOut.print(", ");
        StdOut.println(percStats.confidenceHi());   
    }
}


