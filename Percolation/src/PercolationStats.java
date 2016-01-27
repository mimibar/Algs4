
/**
 * @author Miriam Lee
 * @date
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
  private double[] x;
  private double T;
  // private int N;

  /**
   * perform T independent experiments on an N-by-N grid
   * 
   * @param N
   * @param T
   * @throws java.lang.IllegalArgumentException
   *           if either N ≤ 0 or T ≤ 0
   */
  public PercolationStats(int N, int T) throws IllegalArgumentException {

    if (N <= 0 || T <= 0)
      throw new IllegalArgumentException();

    this.T = T;
    // this.N = N;
    x = new double[T];
    Percolation p;

    // performs T independent computational experiments (discussed above) on
    // an N-by-N grid
    int i, j, opened;

    while (T-- > 0) {
      p = new Percolation(N);
      opened = 0;
      // Repeat the following until the system percolates:
      while (opened < (N * N) && !p.percolates()) {

        do { // Choose a site (row i, column j) uniformly at random among all
             // blocked sites.
          i = StdRandom.uniform(1, N + 1);
          j = StdRandom.uniform(1, N + 1);
          // System.out.println(i + " " + j);
        } while (p.isOpen(i, j));

        // Open the site (row i, column j).
        p.open(i, j);
        opened++;
        // PercolationVisualizer.draw(p, N);
        // StdDraw.show(0);
      }
      // The fraction of sites that are opened when the system percolates
      // provides an estimate of the percolation threshold.
      x[T] = (double) opened / (N * N);
    }
  }

  /**
   * sample mean of percolation threshold
   * 
   * @return
   */
  public double mean() {
    return StdStats.mean(x);
  }

  /**
   * sample standard deviation of percolation threshold
   * 
   * @return
   */
  public double stddev() {
    return StdStats.stddev(x);
  }

  /**
   * low endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceLo() {
    return mean() - (1.96 * stddev()) / Math.sqrt(T);
  }

  /**
   * high endpoint of 95% confidence interval
   * 
   * @return
   */
  public double confidenceHi() {
    return mean() + (1.96 * stddev()) / Math.sqrt(T);
  } //

  /**
   * test client (described below)
   * 
   * @param args
   */
  public static void main(String[] args) {
    // turn on animation mode
    // StdDraw.show(0);

    // takes two command-line arguments N and T,
    PercolationStats ps = new PercolationStats((int) Integer.parseInt(args[0]),
        (int) Integer.parseInt(args[1]));

    // prints the mean, standard deviation, and the 95% confidence interval for
    // the percolation threshold.
    StdOut.printf("mean                    = %.16f\n", ps.mean());
    StdOut.printf("stddev                  = %.17f\n", ps.stddev());
    StdOut.printf("95%% confidence interval = %.16f, %.16f\n", ps.confidenceLo(), ps.confidenceHi());

  }

}
