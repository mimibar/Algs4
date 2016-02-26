/**
 * @name Miriam Lee
 * @date Feb 25, 2016
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


/**
 * an immutable data type Solver
 *
 * @author mimi
 *
 */
public class Solver {
  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   * @throws NullPointerException
   *           if passed a null argument.
   */
  public Solver(Board initial) {
  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable
   *
   * @return
   */
  public int moves() {
  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable
   *
   * @return
   */
  public Iterable<Board> solution() {
  }

  /**
   * solve a slider puzzle (given below)
   *
   * @param args
   */
  public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
      for (int j = 0; j < N; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }

}
