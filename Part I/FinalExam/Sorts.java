/**
 * @name Miriam Lee
 * @date Feb 9, 2016
 * @purpose
 * @howto
 */

import java.util.Arrays;
// import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * @author mimi
 *
 */
public class Sorts {
  private static int N;
  private static String[] all;

  public static void clear(int length) {
    N = length;
    all = new String[length];
    Arrays.fill(all, "");
  }

  private static boolean isSorted(Comparable[] a) {
    return isSorted(a, 0, a.length - 1);
  }

  private static boolean isSorted(Comparable[] a, int lo, int hi) {
    for (int i = lo + 1; i <= hi; i++)
      if (less(a[i], a[i - 1])) return false;
    return true;
  }

  // print array to standard output
  private static void show() {
    for (int i = 0; i < all.length; i++) {
      StdOut.println(all[i]);
    }
  }

  private static void showA(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      all[i] += " " + a[i];
    }
  }

  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  } // exchange a[i] and a[j]

  private static void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  static class Insertion {// extends edu.princeton.cs.algs4.Insertion {

    public static void sort(Comparable[] a) {
      for (int i = 0; i < N; i++) {
        for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
          exch(a, j, j - 1);
          showA(a);
        }

        assert isSorted(a, 0, i);
      }
      assert isSorted(a);

    }

  }

  private static class Selection {

    public static void sort(Comparable[] a) {

      for (int i = 0; i < N; i++) {
        int min = i;
        for (int j = i + 1; j < N; j++) {
          if (less(a[j], a[min])) min = j;
        }
        exch(a, i, min);
        showA(a);
        assert isSorted(a, 0, i);
      }
      assert isSorted(a);

    }

  }

  private static class Merge {
    private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
      if (hi <= lo) return;
      int mid = lo + (hi - lo) / 2;
      sort(a, aux, lo, mid);
      showA(a);
      sort(a, aux, mid + 1, hi);
      showA(a);
      merge(a, aux, lo, mid, hi);
      showA(a);

    }

    public static void sort(Comparable[] a) {
      Comparable[] aux = new Comparable[a.length];
      sort(a, aux, 0, a.length - 1);
      assert isSorted(a);

    }

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
  }

  private static class Quick {
    public static void sort(Comparable[] a) {
//      StdRandom.shuffle(a);
      sort(a, 0, a.length - 1);
      assert isSorted(a);
    }

    // quicksort the subarray from a[lo] to a[hi]
    private static void sort(Comparable[] a, int lo, int hi) {
      if (hi <= lo) return;
      int j = partition(a, lo, hi);
      sort(a, lo, j - 1);
      showA(a);
      sort(a, j + 1, hi);
      showA(a);
      assert isSorted(a, lo, hi);
    }

    // partition the subarray a[lo..hi] so that a[lo..j-1] <= a[j] <= a[j+1..hi]
    // and return the index j.
    private static int partition(Comparable[] a, int lo, int hi) {
      int i = lo;
      int j = hi + 1;
      Comparable v = a[lo];
      while (true) {

        // find item on lo to swap
        while (less(a[++i], v))
          if (i == hi) break;

        // find item on hi to swap
        while (less(v, a[--j]))
          if (j == lo) break; // redundant since a[lo] acts as sentinel

        // check if pointers cross
        if (i >= j) break;

        exch(a, i, j);
        showA(a);
      }

      // put partitioning item v at a[j]
      exch(a, lo, j);
      showA(a);

      // now, a[lo .. j-1] <= a[j] <= a[j+1 .. hi]
      return j;
    }

    /**
     * Rearranges the array so that a[k] contains the kth smallest key; a[0]
     * through a[k-1] are less than (or equal to) a[k]; and a[k+1] through
     * a[N-1] are greater than (or equal to) a[k].
     *
     * @param a
     *          the array
     * @param k
     *          find the kth smallest
     */
    public static Comparable select(Comparable[] a, int k) {
      if (k < 0 || k >= a.length) {
        throw new IndexOutOfBoundsException("Selected element out of bounds");
      }
      StdRandom.shuffle(a);
      int lo = 0, hi = a.length - 1;
      while (hi > lo) {
        int i = partition(a, lo, hi);
        if (i > k)
          hi = i - 1;
        else if (i < k)
          lo = i + 1;
        else
          return a[i];
      }
      return a[lo];
    }
  }

  private static class Heap {
    private static void exch(Object[] pq, int i, int j) {
      Object swap = pq[i - 1];
      pq[i - 1] = pq[j - 1];
      pq[j - 1] = swap;
    }

    public static void sort(Comparable[] pq) {
      int N = pq.length;
      for (int k = N / 2; k >= 1; k--) {
        sink(pq, k, N);
        showA(pq);
      }
      while (N > 1) {
        exch(pq, 1, N--);
        showA(pq);
        sink(pq, 1, N);
        showA(pq);
      }
    }

    /***************************************************************************
     * Helper functions to restore the heap invariant.
     ***************************************************************************/

    private static void sink(Comparable[] pq, int k, int N) {
      while (2 * k <= N) {
        int j = 2 * k;
        if (j < N && less(pq, j, j + 1)) j++;
        if (!less(pq, k, j)) break;
        exch(pq, k, j);
        showA(pq);
        k = j;
      }
    }

    private static boolean less(Comparable[] pq, int i, int j) {
      return pq[i - 1].compareTo(pq[j - 1]) < 0;
    }
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param args
   *          heap lifo path swim hash loop next type fifo sort push flow prim
   *          byte trie node
   */
  public static void main(String[] args) {
    // String[] a = StdIn.readAllStrings();
    // Insertion.main(new String[] { " heap", "lifo", "path", "swim", "hash",
    // "loop", "next", "type", "fifo", "sort", "push", "flow", "prim", "byte",
    // "trie", "node" });
    final String[] a = new String[] { "dusk", "bark", "silk", "teal", "coal",
        "gray", "lust", "pear", "sand", "leaf", "onyx", "iris", "rust", "navy",
        "ecru", "dust", "cyan", "plum", "ceil", "lime", "bone", "cafe", "sage",
        "corn" };

    System.out.println("************************Selection");
    clear(a.length);
    Selection.sort(a.clone());

    show();

    System.out.println("************************INSERTION");
    clear(a.length);
    Insertion.sort(a.clone());
    show();

    System.out.println("\n*********************Merge");
    clear(a.length);
    Merge.sort(a.clone());
    show();

    System.out.println("\n*********************Quicksort ");
    clear(a.length);
    Quick.sort(a.clone());
    show();

    System.out.println("\n*********************Heapsort");
    clear(a.length);
    Heap.sort(a.clone());
    show();

  }

}
