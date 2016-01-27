
/**
 * @author Miriam Lee
 * @date
 * @purpose
 * @howto
 */

import java.util.Arrays;

// import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * @author mimi
 *
 */
public class Percolation {
  /**
   * the grid size.
   */
  private final int N;
  /**
   * which sites are open.
   */
  private boolean[][] open;
  /**
   * which sites are connected to which other sites.
   */
  private WeightedQuickUnionUF uf;

  /**
   * create N-by- N grid, with all sites blocked.
   *
   * @param N
   * @throws java.lang.IllegalArgumentException
   *           if N ≤ 0
   */
  public Percolation(final int N) {
    if (N <= 0) {
      throw new IllegalArgumentException();
    }

    this.N = N;

    open = new boolean[N + 2][N + 2];
    Arrays.fill(open[0], true); // top open
    Arrays.fill(open[N + 1], true); // bottom open

    // which sites are connected to which other sites. The last of these is
    // exactly what the union-find data structure is designed for.
    this.uf = new WeightedQuickUnionUF((N + 2) * (N + 2)); // plus two extra
                                                           // rows (bottom +
                                                           // top)

    for (int i = 0; i <= N; i++) { // top
      uf.union(1, i + 1);
    }
    for (int i = 0; i <= N; i++) { // bottom
      // if (i < N)
      uf.union(xyTo1D(N + 1, i), xyTo1D(N + 1, i + 1));
      // uf.union(xyTo1D(N + 1, i), xyTo1D(N, i));
    }
  }

  /**
   * mapping 2D coordinates to 1D coordinates.
   *
   * Map from a 2-dimensional (row, column) pair (Base 1) to a 1-dimensional
   *
   * @param x
   *          i
   * @param y
   *          j
   * @return 1D coordinates
   */
  private int xyTo1D(final int x, final int y) {
    return x * (N + 2) + y;
  }

  /**
   * The API specifies that valid row and column indices are between 1 and N.
   *
   * @param i
   *          row
   * @param j
   *          column
   * @return true if indices are valid
   */
  private boolean validateIndices(final int i, final int j) {
    if (i <= 0 || i > N) {
      throw new IndexOutOfBoundsException("row index i out of bounds");
    }
    if (j <= 0 || j > N) {
      throw new IndexOutOfBoundsException("column index j out of bounds");
    }

    return true;
  }

  /**
   * open site (row i, column j) if it is not open already.
   *
   * @param i
   *          row
   * @param j
   *          column
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void open(final int i, final int j) {
    // First, it should validate the indices of the site that it receives.
    if (validateIndices(i, j)) {

      // Second, it should somehow mark the site as open.
      open[i][j] = true;

      // Third, it should perform some sequence of WeightedQuickUnionUF
      // operations that links the
      // site in question to its open neighbors.
      if (i + 1 <= N + 1 && open[i + 1][j]) {
        // if (i+1 != N+1 || uf.connected(1, xyTo1D(i, j))) //FOR BackWash
        uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
      }
      if (j + 1 <= N && open[i][j + 1]) {
        uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));
      }

      if (i - 1 >= 0 && open[i - 1][j]) {
        uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));
      }

      if (j - 1 > 0 && open[i][j - 1]) {
        uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
      }
    }
  }

  /**
   * is site (row i, column j) open?
   *
   * @param i
   *          row
   * @param j
   *          column
   * @return true if open
   * @throws java.lang.IndexOutOfBoundsException
   */
  public boolean isOpen(final int i, final int j) {
    validateIndices(i, j);
    return open[i][j];
  }

  /**
   * is site (row i, column j) full?
   *
   * A full site is an open site that can be connected to an open site in the
   * top row via a chain of neighboring (left, right, up, down) open sites.
   *
   * @param i
   *          row
   * @param j
   *          column
   * @return true if full
   * @throws java.lang.IndexOutOfBoundsException
   */
  public boolean isFull(final int i, final int j) {
    validateIndices(i, j);
    return uf.connected(1, xyTo1D(i, j));
  }

  /**
   * does the system percolate?
   *
   * @return the result of connected
   */
  public boolean percolates() {
    return uf.connected(1, xyTo1D(N + 1, N)); // (N+1) * (N+1)-1);
  }

  /**
   * test client (optional).
   *
   * @param args
   *          nothing
   */
  public static void main(String[] args) {

    // StdOut
    // .println("Test 1: Open predetermined list of sites using file inputs");
    // test1("/input6.txt");
    // test1("/input8.txt");
    // test1("/input8-no.txt");
    // test1("/input10-no.txt");
    // test1("/greeting57.txt");
    // test1("/heart25.txt");
    //
  }
  //
  // static void test1(final String txt) {
  // In in = new In(txt); // input file
  // int N = in.readInt(); // N-by-N percolation system Percolation p;
  //
  // // repeatedly read in sites to open and draw resulting system
  // Percolation perc = new Percolation(N);
  //
  // // turn on animation mode
  // StdDraw.show(0);
  // int DELAY = 0;
  // PercolationVisualizer.draw(perc, N);
  //
  // StdDraw.show(DELAY);
  // StdOut.println("filename = " + txt);
  // StdOut.println("isFull(1, 1) = " + perc.isFull(1, 1));
  //
  // while (!in.isEmpty()) {
  // int i = in.readInt();
  // int j = in.readInt();
  // perc.open(i, j);
  // PercolationVisualizer.draw(perc, N);
  // StdDraw.show(DELAY);
  // }
}
