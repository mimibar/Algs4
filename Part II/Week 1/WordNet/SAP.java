/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Immutable data type SAP.
 *
 * An ancestral path between two vertices v and w in a digraph is a directed
 * path from v to a common ancestor x, together with a directed path from w to
 * the same ancestor x. A shortest ancestral path is an ancestral path of
 * minimum total length.
 *
 * @author mimi
 *
 */
public class SAP {

  /**
   * constructor takes a digraph (not necessarily a DAG)
   *
   * @param G
   *          a digraph (not necessarily a DAG)
   */
  public SAP(Digraph G) {
  }

  /**
   * length of shortest ancestral path between v and w; -1 if no such path
   *
   * @param v
   * @param w
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  public int length(int v, int w) {
  }

  /**
   * a common ancestor of v and w that participates in a shortest ancestral
   * path; -1 if no such path
   *
   * @param v
   * @param w
   * @return a common ancestor of v and w that participates in a shortest
   *         ancestral path; -1 if no such path
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  public int ancestor(int v, int w) {
  }

  /**
   * length of shortest ancestral path between any vertex in v and any vertex in
   * w; -1 if no such path
   *
   * @param v
   * @param w
   * @return length of shortest ancestral path between any vertex in v and any
   *         vertex in w; -1 if no such path
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
  }

  /**
   * a common ancestor that participates in shortest ancestral path; -1 if no //
   * such path
   *
   * @param v
   * @param w
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
  }

  /**
   * do unit testing of this class
   *
   * @param args
   */
  public static void main(String[] args) {

    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}
