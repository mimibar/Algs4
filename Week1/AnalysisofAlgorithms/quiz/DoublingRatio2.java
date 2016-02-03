/**
 * @name Miriam Lee
 * @date Feb 2, 2016
 */
package quiz;

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
public class DoublingRatio2 {
  private static final int MAXIMUM_INTEGER = 1000000;

  // This class should not be instantiated.
  private DoublingRatio2() {
  }

  /**
   * What is the order of growth of the worst case running time of the following
   * code fragment as a function of N?
   *
   * @seed 260368
   * @param N
   * @return
   */
  public static double sequence(double N) {

    double sum = 0;
    for (double i = 3 * N * N * N; i > 1; i = i / 2)
      sum++;

    return sum;
  }

  /**
   * Prints table of running times to call <tt>ThreeSum.count()</tt> for arrays
   * of size 250, 500, 1000, 2000, and so forth, along with ratios of running
   * times between successive array sizes.
   */
  public static void main(String[] args) {
    StdOut.printf(
        "N\tseconds\t\tratio\t\tlog_2 ratio\n---------------------------------------\n");
    int N = 2048;// 256;// 2048;// 32;
    double b = 0, ratio;
    double prev = sequence(N);
    StdOut.printf("%8d\t%8.3f\t-\t-\n", N, prev);
    for (N += N; N <= 33554432/* 262144 */ /* 33554432 */ /* 4097 */; N += N) {
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
    StdOut.printf("The empirical order-of-growth is N ^ %.2f\n", b / 11);
  }
}
