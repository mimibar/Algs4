/**
 * @name Miriam Lee
 * @date Feb 8, 2016
 * @purpose
 * @howto
 */
package quiz;

import java.util.Scanner;

class WeightedQuickUnionUF extends edu.princeton.cs.algs4.WeightedQuickUnionUF {
  private int[] parent; // parent[i] = parent of i
  private int[] size; // size[i] = number of sites in subtree rooted at i
  private int count; // number of components
  private int comp;

  /**
   * Initializes an empty union-find data structure with <tt>N</tt> sites
   * <tt>0</tt> through <tt>N-1</tt>. Each site is initially in its own
   * component.
   *
   * @param N
   *          the number of sites
   * @throws IllegalArgumentException
   *           if <tt>N &lt; 0</tt>
   */
  public WeightedQuickUnionUF(int N) {
    super(N);
    count = N;
    comp = N;
    parent = new int[N];
    size = new int[N];
    for (int i = 0; i < N; i++) {
      parent[i] = i;
      size[i] = 1;
    }
  }

  /**
   * Returns the number of components.
   *
   * @return the number of components (between <tt>1</tt> and <tt>N</tt>)
   */
  public int count() {
    return count;
  }

  /**
   * Returns the component identifier for the component containing site
   * <tt>p</tt>.
   *
   * @param p
   *          the integer representing one object
   * @return the component identifier for the component containing site
   *         <tt>p</tt>
   * @throws IndexOutOfBoundsException
   *           unless <tt>0 &le; p &lt; N</tt>
   */
  public int find(int p) {
    validate(p);
    while (p != parent[p])
      p = parent[p];
    return p;
  }

  // validate that p is a valid index
  private void validate(int p) {
    int N = parent.length;
    if (p < 0 || p >= N) {
      throw new IndexOutOfBoundsException(
          "index " + p + " is not between 0 and " + (N - 1));
    }
  }

  /**
   * Returns true if the the two sites are in the same component.
   *
   * @param p
   *          the integer representing one site
   * @param q
   *          the integer representing the other site
   * @return <tt>true</tt> if the two sites <tt>p</tt> and <tt>q</tt> are in the
   *         same component; <tt>false</tt> otherwise
   * @throws IndexOutOfBoundsException
   *           unless both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
   */
  public boolean connected(int p, int q) {
    return find(p) == find(q);
  }

  /**
   * Merges the component containing site <tt>p</tt> with the the component
   * containing site <tt>q</tt>.
   *
   * @param p
   *          the integer representing one site
   * @param q
   *          the integer representing the other site
   * @throws IndexOutOfBoundsException
   *           unless both <tt>0 &le; p &lt; N</tt> and <tt>0 &le; q &lt; N</tt>
   */
  public void union(int p, int q) {
    int rootP = find(p);
    int rootQ = find(q);
    if (rootP == rootQ)
      return;

    // make smaller root point to larger one
    if (size[rootP] < size[rootQ]) {
      parent[rootP] = rootQ;
      size[rootQ] += size[rootP];
    } else {
      parent[rootQ] = rootP;
      size[rootP] += size[rootQ];
    }
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
    for (int i = 0; i < comp; i++) {
      sb.append(parent[i] + " ");
    }
    return sb.toString();

  }
}

/**
 * @author mimi
 *
 */
public class WeightedQuickUnion {

  /**
   * Give the id[] array that results from the following sequence of 9 union
   * operations on a set of 10 items using the weighted quick-union algorithm
   * from lecture.
   *
   * 9-2 5-6 1-0 0-3 3-7 1-4 2-5 0-9 9-8
   *
   * Your answer should be a sequence of 10 integers, separated by whitespace.
   *
   * Recall: when joining two trees of equal size, our weighted quick union
   * convention is to make the root of the second tree point to the root of the
   * first tree. Also, our weighted quick union algorithm performs union by size
   * (number of nodes) - not union by height - and does not do path compression.
   *
   * @param args
   */
  public static void main(String[] args) {
    WeightedQuickUnionUF wqu = new WeightedQuickUnionUF(10);

    Scanner in = new Scanner(System.in);
    in.useDelimiter("[^0-9]");

    int p;
    int q;
    for (int i = 0; i < 9; i++) {
      p = Integer.parseInt(in.next("[0-9]*"));
      q = Integer.parseInt(in.next("[0-9]*"));
      wqu.union(p, q);
      if (i == 0)
        System.out.println(
            "Here is the id[] array after each union operation:\n     0 1 2 3 4 5 6 7 8 9");
      System.out.println(p + "-" + q + ": " + wqu);
    }
  }
}
