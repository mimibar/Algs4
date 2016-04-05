/**
 * @name Miriam Lee
 * @date Feb 22, 2016
 * @purpose
 * @howto
 */
package quiz;

import edu.princeton.cs.algs4.StdIn;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author mimi
 *
 */
public class QuisckSortDijkstra3way {

  // This class should not be instantiated.
  private QuisckSortDijkstra3way() {
  }

  /**
   * Rearranges the array in ascending order, using the natural order.
   *
   * @param a
   *          the array to be sorted
   */
  public static void sort(Comparable[] a) {
    // StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
    assert isSorted(a);
  }

  // quicksort the subarray a[lo .. hi] using 3-way partitioning
  private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo) return;
    int lt = lo, gt = hi;
    Comparable v = a[lo];
    int i = lo;
    while (i <= gt) {
      int cmp = a[i].compareTo(v);
      if (cmp < 0)
        exch(a, lt++, i++);
      else if (cmp > 0)
        exch(a, i, gt--);
      else
        i++;
    }
    show(a);
    // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
    sort(a, lo, lt - 1);
    sort(a, gt + 1, hi);
    assert isSorted(a, lo, hi);
  }

  /***************************************************************************
   * Helper sorting functions.
   ***************************************************************************/

  // is v < w ?
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  // does v == w ?
  private static boolean eq(Comparable v, Comparable w) {
    return v.compareTo(w) == 0;
  }

  // exchange a[i] and a[j]
  private static void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  /***************************************************************************
   * Check if array is sorted - useful for debugging.
   ***************************************************************************/
  private static boolean isSorted(Comparable[] a) {
    return isSorted(a, 0, a.length - 1);
  }

  private static boolean isSorted(Comparable[] a, int lo, int hi) {
    for (int i = lo + 1; i <= hi; i++)
      if (less(a[i], a[i - 1])) return false;
    return true;
  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.print(" " + a[i]);
    }
    StdOut.println();
  }

  /**
   * Reads in a sequence of strings from standard input; 3-way quicksorts them;
   * and prints them to standard output in ascending order.
   */
  public static void main(String[] args) {
    String[] a = StdIn.readAllStrings();
    sort(a);
    show(a);
  }

}
