/**
 * @name Miriam Lee
 * @date Feb 25, 2016
 * @purpose
 * @howto
 */

/**
 * Board and Solver data types. An immutable data type Board
 *
 * @author mimi
 *
 */
public class Board {

  /**
   * construct a board from an N-by-N array of blocks (where blocks[i][j] =
   * block in row i, column j)
   *
   * @param blocks
   *          an N-by-N array containing the N2 integers between 0 and N2 − 1,
   *          where 0 represents the blank square.
   */
  public Board(int[][] blocks) {
  }

  /**
   * board dimension N
   *
   * @return
   */
  public int dimension() {
  }

  /**
   * number of blocks out of place
   *
   * @return
   */
  public int hamming() {
  }

  /**
   * sum of Manhattan distances between blocks and goal
   *
   * @return
   */
  public int manhattan() {
  }

  /**
   * is this board the goal board?
   *
   * @return
   */
  public boolean isGoal() {
  }

  /**
   * a board that is obtained by exchanging any pair of blocks
   *
   * @return
   */
  public Board twin() {
  }

  /**
   * does this board equal y?
   *
   * (non-Javadoc)
   *
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object y) {
  }

  /**
   * all neighboring boards
   *
   * @return
   */
  public Iterable<Board> neighbors() {
  }

  /**
   * string representation of this board (in the output format specified
   * below)(non-Javadoc)
   *
   * @see java.lang.Object#toString()
   */
  public String toString() {
  }

  /**
   * unit tests (not graded)
   *
   * @param args
   */
  public static void main(String[] args) {

  }

}
