/**
 * @name Miriam Lee
 * @date Feb 2, 2016
 */
package quiz;

import java.util.HashMap;

import edu.princeton.cs.algs4.StdOut;

/**
 * The <tt>DoublingRatio</tt> class provides a client for measuring the running
 * time of a method using a doubling ratio test.
 * <p>
 * For additional documentation, see
 * <a href="http://algs4.cs.princeton.edu/14analysis">Section 1.4</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 *
 * @author Robert Sedgewick
 * @author Kevin Wayne
 */
public class DoublingRatio {
  private static final int MAXIMUM_INTEGER = 1000000;

  // This class should not be instantiated.
  private DoublingRatio() {
  }

  /**
   * Returns the amount of time to call <tt>ThreeSum.count()</tt> with
   * <em>N</em> random 6-digit integers.
   *
   * @param N
   *          the number of integers
   * @return amount of time (in seconds) to call <tt>ThreeSum.count()</tt> with
   *         <em>N</em> random 6-digit integers
   */
  public static double sequence(int N) {
    HashMap<Integer, Double> h = new HashMap<>(8);
    h.put(32, 0.0);
    h.put(64, 0.004);
    h.put(128, 0.041);
    h.put(256, 0.473);
    h.put(512, 5.562);
    h.put(1024, 64.693);
    h.put(2048, 742.645);
    h.put(4096, 8760.418);

    return h.get(N);
  }

  /**
   * Prints table of running times to call <tt>ThreeSum.count()</tt> for arrays
   * of size 250, 500, 1000, 2000, and so forth, along with ratios of running
   * times between successive array sizes.
   */
  public static void main(String[] args) {
    StdOut.printf(
        "N\tseconds\t\tratio\t\tlog_2 ratio\n---------------------------------------\n");
    int N = 32;
    double b = 0, ratio;
    double prev = sequence(N);
    StdOut.printf("%8d\t%8.3f\t-\t-\n", N, prev);
    for (N += N; N <= 4096; N += N) {
      double time = sequence(N);
      ratio = Math.log(time / prev) / Math.log(2);

      if (!Double.isInfinite(ratio) && !Double.isNaN(ratio)) {
        b += ratio;
        StdOut.printf("%8d\t%8.3f\t%5.2f\t%5.2f\n", N, time, time / prev,
            ratio);
      } else {
        StdOut.printf("%8d\t%8.3f\t-\t-\n", N, time);
      }
      prev = time;

    }
    StdOut.printf("The empirical order-of-growth is N ^ %.2f\n", b / 6);
  }
}
