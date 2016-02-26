/**
 * @name Miriam Lee
 * @date Feb 25, 2016
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

/**
 * an immutable data type Solver
 *
 * @author mimi
 *
 */
public class Solver {

  private SearchNode sol;
  private boolean solvable;

  /**
   * We define a search node of the game to be a board, the number of moves made
   * to reach the board, and the previous search node.
   *
   * @author mimi
   *
   */
  private class SearchNode implements Comparable<SearchNode> {
    private Board b;
    private int moves; // g(n)
    private SearchNode prev;
    private int man, ham = -1;

    /**
     * @param initial
     *          the initial board, 0 moves, and a null previous search node
     */
    SearchNode(Board board) {
      b = board;
      man = b.manhattan();
    }

    /**
     * @param x
     * @param prev
     */
    SearchNode(Board x, SearchNode node) {
      this(x);
      if (node == null) throw new NullPointerException();
      prev = node;
      moves = prev.moves + 1;
      man += moves;
    }

    /**
     * When two search nodes have the same Manhattan priority, you can break
     * ties however you want, e.g., by comparing either the Hamming or Manhattan
     * distances of the two boards.
     *
     * (non-Javadoc)
     *
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(SearchNode n) {

      if (man == n.man) {
        if (ham() > n.ham()) return 1;
        if (ham() < n.ham()) return -1;
        return 0;
      }
      if (man > n.man) return 1;
      return -1;
    }

    /**
     * @TODO Include a bold (or Javadoc) comment describing every method.
     * @return
     */
    private int ham() {
      if (ham == -1) ham = b.hamming() + moves;
      return ham;
    }

  }

  /**
   * find a solution to the initial board (using the A* algorithm)
   *
   * @param initial
   * @throws NullPointerException
   *           if passed a null argument.
   */
  public Solver(Board initial) {
    if (initial == null) throw new NullPointerException();
    MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
    // The current API requires you to detect infeasiblity in Solver by using
    // two synchronized A* searches (e.g., using two priority queues)
    // TODO Use only one PQ to run the A* algorithm on the initial board and its
    // twin.
    MinPQ<SearchNode> pqt = new MinPQ<SearchNode>();

    // First, insert the initial search node into a priority queue.
    pq.insert(new SearchNode(initial));
    pqt.insert(new SearchNode(initial.twin()));

    SearchNode node = pq.delMin();
    SearchNode twin = pqt.delMin();

    // Repeat this procedure until the search node dequeued corresponds to a
    // goal board. The success of this approach hinges on the choice of priority
    // function for a search node.
    while (!node.b.isGoal() && !twin.b.isGoal()) {
      // insert onto the priority queue all neighboring search nodes (those that
      // can be reached in one move from the dequeued search node).
      for (Board x : node.b.neighbors()) {
        // critical optimization: don't enqueue a neighbor if its board is the
        // same as the board of the previous search node.
        if (node.prev == null || !x.equals(node.prev.b))

          pq.insert(new SearchNode(x, node));
      }
      for (Board x : twin.b.neighbors()) {
        if (twin.prev == null || !x.equals(twin.prev.b))
          pqt.insert(new SearchNode(x, twin));
      }

      // Then, delete from the priority queue the search node with the minimum
      // priority
      node = pq.delMin();
      twin = pqt.delMin();
    }

    if (node.b.isGoal()) {
      sol = node;
      solvable = true;
    }
    else if (twin.b.isGoal()) {
      solvable = false;
    }
  }

  /**
   * is the initial board solvable?
   *
   * @return
   */
  public boolean isSolvable() {
    return solvable;
  }

  /**
   * min number of moves to solve initial board; -1 if unsolvable
   *
   * @return
   */
  public int moves() {
    if (sol != null) return sol.moves;
    return -1;
  }

  /**
   * sequence of boards in a shortest solution; null if unsolvable
   *
   * @return
   */
  public Iterable<Board> solution() {

    if (!solvable) return null;

    // Add the items you want to a Stack<Board> or Queue<Board> and return that.
    Queue<Board> solution = new Queue<>();
    SearchNode n = sol;
    solution.enqueue(n.b);

    while (n.prev != null) {
      n = n.prev;
      solution.enqueue(n.b);
    }

    return solution;

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