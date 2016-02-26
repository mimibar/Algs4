package quiz;
/**
 * @name Miriam Lee
 * @date Feb 22, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author mimi
 *
 */
public class MergeSort {
  private static int call=0;

  private static boolean isSorted(Comparable[] a, int lo, int hi) {
    for (int i = lo + 1; i <= hi; i++)
      if (less(a[i], a[i - 1])) return false;
    return true;
  }

  // is v < w ?
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  // stably merge a[lo .. mid] with a[mid+1 ..hi] using aux[lo .. hi]
  private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid,
      int hi) {
    // precondition: a[lo .. mid] and a[mid+1 .. hi] are sorted subarrays
    assert isSorted(a, lo, mid);
    assert isSorted(a, mid + 1, hi);

    // copy to aux[]
    for (int k = lo; k <= hi; k++) {
      aux[k] = a[k];
    }

    // merge back to a[]
    int i = lo, j = mid + 1;
    for (int k = lo; k <= hi; k++) {
      if (i > mid)
        a[k] = aux[j++];
      else if (j > hi)
        a[k] = aux[i++];
      else if (less(aux[j], aux[i]))
        a[k] = aux[j++];
      else
        a[k] = aux[i++];
    }

    // postcondition: a[lo .. hi] is sorted
    assert isSorted(a, lo, hi);

  }

  // mergesort a[lo..hi] using auxiliary array aux[lo..hi]
  private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
    if (hi <= lo) return;
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, lo, mid, hi);
    show(lo, mid, hi, a);
  }

  /***************************************************************************
   * Check if array is sorted - useful for debugging.
   ***************************************************************************/
  private static boolean isSorted(Comparable[] a) {
    return isSorted(a, 0, a.length - 1);
  }

  /**
   * Rearranges the array in ascending order, using the natural order.
   *
   * @param a
   *          the array to be sorted
   */
  public static void sort(Comparable[] a) {
    Comparable[] aux = new Comparable[a.length];
    sort(a, aux, 0, a.length - 1);
    assert isSorted(a);
  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.print(" " + a[i]);
    }
    StdOut.println();

  }

  private static void show(int lo, int mid, int hi, Comparable[] a) {
    StdOut.printf("(%2d) %2d %2d %2d:", ++call, lo, mid, hi);
    show(a);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param args
   */
  public static void main(String[] args) {
    String[] a = StdIn.readAllStrings();

    sort(a);
    show(a);
  }

}
