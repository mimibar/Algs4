/**
 * @name Miriam Lee
 * @date Feb 8, 2016
 * @purpose
 * @howto
 */
package quiz;

import java.util.Scanner;

class QuickFindUF {// extends edu.princeton.cs.algs4.QuickFindUF {
  private int[] id; // id[i] = component identifier of i
  private int count; // number of components
  private int size;

  public QuickFindUF(int N) {
    size = N;
    count = N;
    id = new int[N];
    for (int i = 0; i < N; i++)
      id[i] = i;
  }

  public int count() {
    return count;
  }

  public int find(int p) {
    validate(p);
    return id[p];
  }

  // validate that p is a valid index
  private void validate(int p) {
    int N = id.length;
    if (p < 0 || p >= N) {
      throw new IndexOutOfBoundsException(
          "index " + p + " is not between 0 and " + (N - 1));
    }
  }

  public boolean connected(int p, int q) {
    validate(p);
    validate(q);
    return id[p] == id[q];
  }

  public void union(int p, int q) {
    int pID = id[p]; // needed for correctness
    int qID = id[q]; // to reduce the number of array accesses

    // p and q are already in the same component
    if (pID == qID)
      return;

    for (int i = 0; i < id.length; i++)
      if (id[i] == pID)
        id[i] = qID;
    count--;
  }

  /*
   * (non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(id[i] + " ");
    }
    return sb.toString();

  }
}

/**
 * @author mimi
 *
 */
public class QuickFind {

  /**
   * Give the id[] array that results from the following sequence of 6 union
   * operations on a set of 10 items using the quick-find algorithm.
   *
   * 8-5 9-0 1-0 6-0 0-4 2-8
   *
   * Your answer should be a sequence of 10 integers, separated by whitespace.
   *
   * Recall: our quick-find convention for the union operation p-q is to change
   * id[p] (and perhaps some other entries) but not id[q].
   *
   * @param args
   */
  public static void main(String[] args) {
    QuickFindUF qf = new QuickFindUF(10);

    Scanner in = new Scanner(System.in);
    in.useDelimiter("[^0-9]");

    int p;
    int q;
    for (int i = 0; i < 6; i++) {
      p = Integer.parseInt(in.next("[0-9]*"));
      q = Integer.parseInt(in.next("[0-9]*"));
      qf.union(p, q);
      // qf.union((in.nextInt()), in.nextInt());
      // qf.union((in.next()), in.next());
      if (i == 0)
        System.out.println(
            "Here is the id[] array after each union operation:\n     0 1 2 3 4 5 6 7 8 9");
      System.out.println(p + "-" + q + ": " + qf);
    }

  }

}
