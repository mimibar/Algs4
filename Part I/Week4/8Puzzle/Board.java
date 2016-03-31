/**
 * @name Miriam Lee
 * @date Feb 25, 2016
 * @purpose
 * @howto
 */
import java.util.Arrays;

import edu.princeton.cs.algs4.Stack;

/**
 * Board and Solver data types. An immutable data type Board
 *
 * @author mimi
 *
 */
public class Board {
  // TODO To save memory, consider using an N-by-N char[][] array or a length
  // N^2 char[] array.
  private int[][] tiles;
  private int N;
  // Cache
  private int ham = -1, man = -1;

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param blocks
   *          an N-by-N array containing the N2 integers between 0 and N2 − 1,
   *          where 0 represents the blank square.
   */
  public Board(int[][] blocks) {
    N = blocks.length;
    tiles = new int[N][N];
    for (int i = 0; i < N; i++)
      tiles[i] = Arrays.copyOf(blocks[i], N);
  }

  /**
   * board dimension N
   *
   * @return
   */
  public int dimension() {
    return N;
  }

  /**
   * number of blocks out of place
   *
   * @return
   */
  public int hamming() {

    if (ham == -1) {
      ham = 0;
      for (int m = 0; m < N; m++) {
        for (int n = 0; n < N; n++) {
          if (tiles[m][n] != 0 && tiles[m][n] != n + 1 + (m * (N)))

            ham++;
        }
      }
    }
    return ham;

  }

  /**
   * sum of Manhattan distances between blocks and goal
   *
   * @return
   */
  public int manhattan() {
    if (man == -1) {
      man = 0;
      for (int m = 0; m < N; m++) {
        for (int n = 0; n < N; n++) {
          if (tiles[m][n] != 0 && tiles[m][n] != n + 1 + (m * (N))) {

            man += abs(m - ((tiles[m][n] - 1) / N))
                + abs(n - ((tiles[m][n] - 1) % N)); // real minus ideal
          }
        }
      }
    }
    return man;
  }

  private int abs(int i) {
    if (i < 0) return i * (-1);
    return i;
  }

  /**
   * is this board the goal board?
   *
   * @return
   */
  public boolean isGoal() {
    for (int m = 0; m < N; m++) {
      for (int n = 0; n < N; n++) {
        if (m != N - 1 || n != N - 1) {

          if (tiles[m][n] != n + 1 + (m * (N))) return false;
        }
      }
    }
    return true;
  }

  /**
   * a board that is obtained by exchanging any pair of blocks
   *
   * @return
   */
  public Board twin() {
    Board b = new Board(tiles);

    int i = 0, j = 0, x = 0, y = 0;
    while (b.tiles[i][j] == 0) {
      j++;
    }
    if (j < N - 1 && b.tiles[i][j + 1] != 0) {
      x = i;
      y = j + 1;
    }
    else {
      x = i + 1;
      y = j;
    }
    exch(b.tiles, i, j, x, y);
    return b;
  }

  /**
   * does this board equal y?
   *
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object y) {
    if (y == null) return false;
    if (y == this) return true;
    if (this.getClass() != y.getClass()) return false;

    Board b = (Board) y;
    if (this.dimension() != b.dimension()) return false;

    for (int m = 0; m < N; m++) {
      for (int n = 0; n < N; n++) {
        if (tiles[m][n] != b.tiles[m][n]) return false;
      }
    }
    return true;
  }

  /**
   * all neighboring boards.
   *
   * those that can be reached in one move from the dequeued search node
   *
   * @return
   */
  public Iterable<Board> neighbors() {
    Stack<Board> neighbors = new Stack<Board>();

    // search blank square
    int m = 0, n = 0;

    found:

    for (m = 0; m < N; m++) {
      for (n = 0; n < N; n++) {
        if (tiles[m][n] == 0) break found; // found!
      }
    }

    if (n < N - 1) neighbors.push(newNeighbor(m, n, m, n + 1));
    if (n > 0) neighbors.push(newNeighbor(m, n, m, n - 1));
    if (m < N - 1) neighbors.push(newNeighbor(m, n, m + 1, n));
    if (m > 0) neighbors.push(newNeighbor(m, n, m - 1, n));

    return neighbors;
  }

  /**
   * @param m
   *          i of blank tile
   * @param n
   *          j of blank tile
   * @param m2
   *          i of block to move
   * @param n2
   *          j of block to move
   * @return a new neighbor
   */
  private Board newNeighbor(int m, int n, int m2, int n2) {
    Board b = new Board(this.tiles);
    exch(b.tiles, m, n, m2, n2);
    return b;
  }

  /**
   * Exchanges two tiles in a board
   *
   * @param t
   * @param m
   * @param n
   * @param m2
   * @param n2
   */
  private void exch(int[][] t, int m, int n, int m2, int n2) {
    int temp = t[m][n];
    t[m][n] = t[m2][n2];
    t[m2][n2] = temp;
  }

  /**
   * string representation of this board (in the output format specified
   * below)(non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(N + "\n");
    for (int i = 0; i < N; i++) {
      for (int j = 0; j < N; j++) {
        s.append(String.format("%2d ", tiles[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  /**
   * unit tests (not graded)
   *
   * @param args
   */
  public static void main(String[] args) {

  }

}
