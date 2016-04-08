/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
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

  private Digraph dg;
  private Integer[][] len;
  private Integer[][] anc;

  /**
   * constructor takes a digraph (not necessarily a DAG).</br>
   *
   * 'The most straightforward way to find a shortest ancestral path is to run
   * two breadth-first searches in G starting from x and from y, thereby
   * computing the distance of each synset from x and from y. Then, for each
   * synset reachable from x and from y, sum the two distances, Finally, take
   * the minimum of these sums and subtract two. (Or, instead of subtracting
   * two, in each distance computation avoid counting the first edge.) An
   * improvement is to run both searches concurrently, visiting vertices at
   * distance one from either x or y, then vertices at distance two from x or y,
   * and so on. Once some vertex is reached from both x and y, the sum of its
   * distances from x and y gives an upper bound on the distance beyond which
   * the searches need not go. The upper bound can be reduced each time a
   * shorter pair of paths is found. This allows early termination, especially
   * in the case of nearby words. </br>
   * If W is acyclic, an additional heuristic can be used to try to further
   * limit the searches ... Do a preprocessing step to number the vertices in
   * topological order. When choosing whether to do a step of the BFS from x or
   * the one from y, visit the vertex smaller in topological order. This
   * strategy can be combined with the shortest distance strategy in various
   * ways.' <a href=
   * "http://www.cs.princeton.edu/courses/archive/fall10/cos226/precepts/WordNet-Tarjan.pdf">
   * WordNet-Tarjan.pdf</a>
   *
   * @param G
   *          a digraph (not necessarily a DAG)
   */
  public SAP(Digraph G) {
    dg = new Digraph(G);
    len = new Integer[dg.V()][dg.V()];
    anc = new Integer[dg.V()][dg.V()];
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
    isNotNull(v);
    isNotNull(w);
    isVertexValid(v);
    isVertexValid(w);

    if (len[v][w] != null)// use cached length
      return len[v][w];

    BreadthFirstDirectedPaths bfDpV = new BreadthFirstDirectedPaths(dg, v);
    BreadthFirstDirectedPaths bfDpW = new BreadthFirstDirectedPaths(dg, w);

    int min = Integer.MAX_VALUE, ancestor = -1;
    for (int i = 0; i < dg.V(); i++) {
      if (i == v)
        continue;

      if (bfDpV.hasPathTo(i) && bfDpW.hasPathTo(i)
          && bfDpV.distTo(i) + bfDpW.distTo(i) < min) {
        min = bfDpV.distTo(i) + bfDpW.distTo(i);
        ancestor = i;
      }

    }
    if (min == Integer.MAX_VALUE)
      min = -1;

    len[v][w] = min;
    anc[v][w] = ancestor;

    return min;
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
    isNotNull(v);
    isNotNull(w);
    isVertexValid(v);
    isVertexValid(w);

    if (len[v][w] == null)
      length(v, w);

    return anc[v][w];
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
    isNotNull(v);
    isNotNull(w);

    int min = Integer.MAX_VALUE;

    for (int i : v) {
      isVertexValid(i);
      for (int j : w) {
        isVertexValid(j);

        if (len[i][i] == null) {
          len[i][i] = length(i, j);

          if (len[i][i] > -1 && len[i][i] < min)
            min = len[i][i];
        }
      }
    }
    if (min < Integer.MAX_VALUE)
      min -= 0;
    else
      min = -1;
    return min;
  }

  /**
   * @param o
   * @return true if argument not null
   * @throws NullPointerException
   *           if any argument is null.
   */
  private boolean isNotNull(Object o) {
    if (o == null)
      throw new NullPointerException();
    return true;
  }

  /**
   * @param v
   * @return true if vertex is between 0 and G.V() - 1
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  private boolean isVertexValid(int v) {
    if (v < 0 || v > dg.V() - 1)
      throw new IndexOutOfBoundsException();
    return true;
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

    isNotNull(v);
    isNotNull(w);
    int min = Integer.MAX_VALUE, ancestor = -1;

    for (int i : v) {
      isVertexValid(i);
      for (int j : w) {
        isVertexValid(j);

        if (len[i][j] == null)
          length(i, j);

        if (len[i][j] > -1 && len[i][j] < min) {
          min = len[i][j];
          ancestor = anc[i][j];
        }
      }
    }

    return ancestor;
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
