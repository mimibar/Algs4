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
   * Marker of elements roots linked to bottom.
   */
  private boolean[] bottom;

  /**
   * which sites are connected to which other sites.
   */
  private WeightedQuickUnionUF uf;

  /**
   * create N-by- N grid, with all sites blocked.
   *
   * @param N size of grid
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
    bottom = new boolean[(N + 2) * (N + 2)];

    // which sites are connected to which other sites. The last of these is
    // exactly what the union-find data structure is designed for.
    this.uf = new WeightedQuickUnionUF((N + 2) * (N + 2)); // plus two extra
                                                           // rows (bottom +
                                                           // top)

    for (int i = 0; i <= N; i++) { // top
      uf.union(1, i + 1);
    }
    for (int i = 0; i <= N; i++) { // bottom
      uf.union(xyTo1D(N + 1, i), xyTo1D(N + 1, i + 1));
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
      // operations that links the site in question to its open neighbors.

      connect(i, j, i, j + 1);
      connect(i, j, i, j - 1);
      connect(i, j, i - 1, j);

      if (open[i + 1][j]) {
        if (i == N) { // if opening bottom row, set root to link to bottom
          bottom[uf.find(xyTo1D(i, j))] = true;
          // if it is connected to top, percolate
          if (uf.connected(1, xyTo1D(i, j))) {
            unite(i, j, i + 1, j);
          }
        } else {
          connect(i, j, i + 1, j);
          // unite(i, j, i + 1, j);

        }
      }
    }

  }

  /**
   * Connect site. Given isOpen(i,j)==true, if any of the sites' parents connect
   * to bottom, then percolate.
   *
   * @param i
   * @param j
   * @param i2
   * @param j2
   */
  private void connect(int i, int j, int i2, int j2) {
    if (open[i2][j2]) {
      // FOR BackWash
      boolean con = bottom[uf.find(xyTo1D(i2, j2))]
          || bottom[uf.find(xyTo1D(i, j))];
      unite(i, j, i2, j2);
      // update root parent if connected to bottom
      bottom[uf.find(xyTo1D(i, j))] = con;
      // if coming from the top (and connecting to the bottom)
      if (con && uf.connected(1, xyTo1D(i, j))) {
        unite(i, j, N + 1, N);
      }
    }

  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param xyTo1D
   * @param xyTo1D2
   */
  private void unite(int i1, int j1, int i2, int j2) {
    uf.union(xyTo1D(i1, j1), xyTo1D(i2, j2));
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
    // boolean conn = uf.connected(xyTo1D(i, j), xyTo1D(N + 1, N));//backwash

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
