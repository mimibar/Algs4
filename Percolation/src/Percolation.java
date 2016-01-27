
/**
 * @author Miriam Lee
 * @date
 * @purpose 
 * @howto
 */

import java.util.Arrays;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
  private final int N;
  private boolean[][] open;
  private WeightedQuickUnionUF uf;

  /**
   * create N-by- N grid, with all sites blocked
   * 
   * @param N
   * @throws java.lang.IllegalArgumentException
   *           if N ≤ 0
   */
  public Percolation(int N) {
    if (N <= 0)
      throw new IllegalArgumentException();

    this.N = N;

    open = new boolean[N + 2][N + 2];
    Arrays.fill(open[0], true); // top open
    Arrays.fill(open[N + 1], true); // bottom open

    // which sites are connected to which other sites. The last of these is
    // exactly what the union-find data structure is designed for.
    this.uf = new WeightedQuickUnionUF((N+2) * (N + 2)); // plus two extra rows
                                                       // (bottom + top)

    for (int i = 0; i <= N; i++) { // top
//      if (i < N)
        uf.union(1, i + 1);
//      uf.union(i, N + i);
    }
    for (int i = 0; i <= N; i++) { // bottom
//      if (i < N)
        uf.union(xyTo1D(N + 1, i), xyTo1D(N + 1, i + 1));
//      uf.union(xyTo1D(N + 1, i), xyTo1D(N, i));
    }
  }

  /**
   * mapping 2D coordinates to 1D coordinates.
   * 
   * Map from a 2-dimensional (row, column) pair (Base 1) to a 1-dimensional
   * union find object index (Base 1)
   * 
   * @param x
   * @param y
   * @return
   */
  private int xyTo1D(int x, int y) {
    return x * (N) + y;
  }

  private boolean validateIndices(int i, int j) throws IndexOutOfBoundsException {
    if (i <= 0 || i > N)
      throw new IndexOutOfBoundsException("row index i out of bounds");
    if (j <= 0 || j > N)
      throw new IndexOutOfBoundsException("column index j out of bounds");

    return true;
  }

  /**
   * open site (row i, column j) if it is not open already
   * 
   * @param i
   * @param j
   * @throws java.lang.IndexOutOfBoundsException
   */
  public void open(int i, int j) {
    // First, it should validate the indices of the site that it receives.
    if (validateIndices(i, j)) {

      // Second, it should somehow mark the site as open.
      open[i][j] = true;
      
      // Third, it should perform some sequence of WeightedQuickUnionUF
      // operations that links the
      // site in question to its open neighbors.
      if (i + 1 <= N+1 && open[i + 1][j]) {
//        if (i+1 != N+1 || uf.connected(1, xyTo1D(i, j))) //FOR BackWash
          uf.union(xyTo1D(i, j), xyTo1D(i + 1, j));
      }
      if (j + 1 <= N && open[i][j + 1])
        uf.union(xyTo1D(i, j), xyTo1D(i, j + 1));

      if (i - 1 >= 0 && open[i - 1][j])
        uf.union(xyTo1D(i, j), xyTo1D(i - 1, j));

      if (j - 1 > 0 && open[i][j - 1])
        uf.union(xyTo1D(i, j), xyTo1D(i, j - 1));
    }
  }

  /**
   * is site (row i, column j) open?
   * 
   * @param i
   * @param j
   * @return
   * @throws java.lang.IndexOutOfBoundsException
   */
  public boolean isOpen(int i, int j) {
    return open[i][j];
  }

  /**
   * is site (row i, column j) full?
   * 
   * A full site is an open site that can be connected to an open site in the
   * top row via a chain of neighboring (left, right, up, down) open sites.
   * 
   * @param i
   * @param j
   * @return
   * @throws java.lang.IndexOutOfBoundsException
   */
  public boolean isFull(int i, int j) {
    return uf.connected(1, xyTo1D(i, j));
  }

  /**
   * does the system percolate?
   * 
   * @return
   */
  public boolean percolates() {
    return uf.connected(1, (N+1) * (N+1)-1 ); // xyTo1D(N+1, N));
  }

  /**
   * test client (optional)
   * 
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
