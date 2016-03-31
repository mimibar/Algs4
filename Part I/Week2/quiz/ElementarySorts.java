/**
 * @name Miriam Lee
 * @date Feb 9, 2016
 * @purpose
 * @howto
 */
package quiz;

import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.Insertion;
import edu.princeton.cs.algs4.Shell;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author mimi
 *
 */
public class ElementarySorts {
  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  } // exchange a[i] and a[j]

  private static void exch(Object[] a, int i, int j) {
    Object swap = a[i];
    a[i] = a[j];
    a[j] = swap;
  }

  static class Insertion {// extends edu.princeton.cs.algs4.Insertion {

    // is v < w ?

    // is the array sorted from a[lo] to a[hi]
    private static boolean isSorted(Comparable[] a, int lo, int hi) {
      for (int i = lo + 1; i <= hi; i++)
        if (less(a[i], a[i - 1]))
          return false;
      return true;
    }

    /***************************************************************************
     * Check if array is sorted - useful for debugging.
     ***************************************************************************/
    private static boolean isSorted(Comparable[] a) {
      return isSorted(a, 0, a.length - 1);
    }

    public static String[] sort(Comparable[] a) {
      int N = a.length;
      String[] all = new String[a.length];
      Arrays.fill(all, "");
      for (int i = 0; i < N; i++) {
        for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
          exch(a, j, j - 1);
          showA(a, all);
        }

        assert isSorted(a, 0, i);
      }
      assert isSorted(a);
      return all;
    }

  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.println(a[i] + "  ");
    }
  }

  private static void showA(Comparable[] a, String[] all) {
    for (int i = 0; i < a.length; i++) {
      all[i] += "  " + a[i];
    }
  }

  private static class Selection {

    public static Comparable[] sort(Comparable[] a) {
      int N = a.length;
      String[] all = new String[a.length];
      Arrays.fill(all, "");

      for (int i = 0; i < N; i++) {
        int min = i;
        for (int j = i + 1; j < N; j++) {
          if (less(a[j], a[min]))
            min = j;
        }
        exch(a, i, min);
        showA(a, all);
        assert isSorted(a, 0, i);
      }
      assert isSorted(a);
      return all;
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
      for (int i = lo + 1; i <= hi; i++)
        if (less(a[i], a[i - 1]))
          return false;
      return true;
    }

    private static boolean isSorted(Comparable[] a) {
      return isSorted(a, 0, a.length - 1);
    }
  }

  static class Shell {
    public static String[] sort(Comparable[] a) {
      int N = a.length;
      String[] all = new String[N];
      Arrays.fill(all, "");
      showA(a, all);

      // 3x+1 increment sequence: 1, 4, 13, 40, 121, 364, 1093, ...
      int h = 1;
      while (h < N / 3)
        h = 3 * h + 1;

      while (h >= 1) {
        // h-sort the array
        for (int i = h; i < N; i++) {
          for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
            exch(a, j, j - h);
            showA(a, all);
          }

        }
        assert isHsorted(a, h);
        h /= 3;
      }
      assert isSorted(a);
      return all;
    }

    private static boolean isHsorted(Comparable[] a, int h) {
      for (int i = h; i < a.length; i++)
        if (less(a[i], a[i - h]))
          return false;
      return true;
    }

    private static boolean isSorted(Comparable[] a) {
      for (int i = 1; i < a.length; i++)
        if (less(a[i], a[i - 1]))
          return false;
      return true;
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
    final String[] a = new String[] { "heap", "lifo", "path", "swim", "hash",
        "loop", "next", "type", "fifo", "sort", "push", "flow", "prim", "byte",
        "trie", "node" };
    System.out.println("************************Selection");

    show(Selection.sort(a.clone()));
    System.out.println("************************INSERTION");

    show(Insertion.sort(a.clone()));

    System.out.println("\n*********************SHELL");

    show(Shell.sort(a.clone()));
  }

}
