/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import java.util.HashMap;
import java.util.LinkedList;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

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
   * To make the data type SAP immutable, save the associated digraph in an
   * instance variable. Because our Digraph data type is mutable, you must first
   * make a defensive copy by calling the copy constructor.
   */
  private final Digraph dg;

  /**
   * HashMap&ltv, HashMap&ltw, {len,anc}&gt&gt
   */
  private final HashMap<Integer, HashMap<Integer, int[]>> cache;

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
   * @throws NullPointerException
   *           if any argument is null
   */
  public SAP(Digraph G) {
    isNotNull(G);
    dg = new Digraph(G);
    cache = new HashMap<>();
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

    if (visited(v, w)) // use cached length
      return len(v, w);

    BreadthFirstDirectedPaths bfDpV = new BreadthFirstDirectedPaths(dg, v);
    BreadthFirstDirectedPaths bfDpW = new BreadthFirstDirectedPaths(dg, w);

    int min = Integer.MAX_VALUE, ancestor = -1;
    for (int i = 0; i < dg.V(); i++) {
      // Test 1: test length() and ancestor() on fixed digraphs
      // Test 3: check length() and ancestor() on directed paths
      // Test 4: check length() and ancestor() on directed cycles
      // Test 5: check length() and ancestor() on complete graphs
      // Test 6: check length() and ancestor() on tournament digraphs
      // Test 7: check length() and ancestor() on complete binary trees
      // Test 8: check length() and ancestor() on random DAGs
      // Test 9: check length() and ancestor() on random rooted-in DAGs
      // Test 10: check length() and ancestor() on random rooted-out DAGs
      // Test 11: check length() and ancestor() on random rooted-in trees
      // Test 12: check length() and ancestor() on random rooted-out trees
      // Test 13: check length() and ancestor() on random simple digraphs
      // Test 14: check whether two SAP objects can be created at the same time

      if (bfDpV.hasPathTo(i) && bfDpW.hasPathTo(i)
          && bfDpV.distTo(i) + bfDpW.distTo(i) < min) {
        min = bfDpV.distTo(i) + bfDpW.distTo(i);
        ancestor = i;
      }

    }
    if (min == Integer.MAX_VALUE)
      min = -1;

    addCache(v, w, min, ancestor);

    return min;
  }

  private void addCache(int v, int w, int len, int ancestor) {
    int[] vTow = null;
    HashMap<Integer, int[]> vc;

    if (cache.containsKey(v))
      vc = cache.get(v);
    else
      vc = new HashMap<>();

    if (vc.containsKey(w))
      vTow = vc.get(w); // len && anc
    else
      vTow = new int[2];

    vTow[0] = len;
    vTow[1] = ancestor;
    vc.put(w, vTow);

    cache.put(v, vc);
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

    if (!visited(v, w))
      length(v, w);

    return anc(v, w);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param v
   * @param w
   * @return
   */
  private int anc(int v, int w) {

    return cache.get(v).get(w)[1];
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param v
   * @return
   */
  private boolean visited(int v, int w) {
    return cache.containsKey(v) && cache.get(v).containsKey(w);
  }

  /**
   * length of shortest ancestral path between any vertex in v and any vertex in
   * w; -1 if no such path</br>
   * The key is using the constructor in {@link BreadthFirstDirectedPaths} that
   * takes an iterable of sources instead of using a single source.
   *
   * @ @param
   *     v
   * @param w
   * @return length of shortest ancestral path between any vertex in v and any
   *         vertex in w; -1 if no such path
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    // Test 3a-c: time length() and ancestor() with random sets of 5
    // vertices
    // Test 17: test length() and ancestor() with Iterable arguments
    // * 100 random subsets of 3 and 11 vertices in digraph-wordnet.txt
    // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // OperationCountLimitExceededException
    // Number of primitive operations in Digraph exceeds limit: 1000000000
    // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    isNotNull(v);
    isNotNull(w);

    for (int i : v)
      isVertexValid(i);
    for (int i : w)
      isVertexValid(i);

    // BFS to ancestor from v and w

    BreadthFirstDirectedPaths bfDpV = new BreadthFirstDirectedPaths(dg, v);
    BreadthFirstDirectedPaths bfDpW = new BreadthFirstDirectedPaths(dg, w);

    int ancestor = -1;

    int min = Integer.MAX_VALUE, d;
    for (int i = 0; i < dg.V(); i++) {
      if (bfDpV.hasPathTo(i) && bfDpW.hasPathTo(i)) {
        d = bfDpV.distTo(i) + bfDpW.distTo(i);
        if (d < min) {
          min = d;
          ancestor = i;
        }
      }
    }

    if (min == Integer.MAX_VALUE)
      min = -1;
    else {
      addCache((int) ((Stack<Integer>) bfDpV.pathTo(ancestor)).peek(),
          (int) ((Stack<Integer>) bfDpW.pathTo(ancestor)).peek(), min, ancestor);
    }
    return min;

  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param i
   * @param j
   * @return
   */
  private int len(int i, int j) {

    return cache.get(i).get(j)[0];
  }

  /**
   * @param ob
   * @return true if argument not null
   * @throws NullPointerException
   *           if any argument is null.
   */
  private static boolean isNotNull(Object ob) {
    if (ob == null)
      throw new NullPointerException();
    return true;
  }

  /**
   * @param v
   * @return true if vertex is between 0 and G.V() - 1
   * @throws IndexOutOfBoundsException
   *           if any argument vertex is invalid—not between 0 and G.V() - 1.
   */
  private void isVertexValid(int v) {
    if (v < 0 || v > dg.V() - 1)
      throw new IndexOutOfBoundsException();
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
    // Test 3a-c: time length() and ancestor() with random sets of 5
    // vertices
    // Test 17: test length() and ancestor() with Iterable arguments

    isNotNull(v);
    isNotNull(w);
    for (int i : v)
      isVertexValid(i);
    for (int i : w)
      isVertexValid(i);
    int ancestor = -1;

    int min = length(v, w);
    for (int i : v) {
      for (int j : w) {
        if (visited(i, j) && len(i, j) == min) {
          ancestor = anc(i, j);
          break;
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
    // TODO Test 17: test length() and ancestor() with Iterable arguments
    // * 100 random subsets of 3 and 11 vertices in digraph-wordnet.txt
    // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    // OperationCountLimitExceededException
    // Number of primitive operations in Digraph exceeds limit: 1000000000
    // ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    for (int i = 0; i <= 100; i++) {
      LinkedList<Integer> w = new LinkedList<>();
      LinkedList<Integer> v = new LinkedList<>();
      for (int j = 0; j < 3; j++)
        v.add((int) (StdRandom.uniform() * G.V()));
      for (int j = 0; j < 11; j++)
        w.add((int) (StdRandom.uniform() * G.V()));

      System.out.println(sap.ancestor(v, w));
      System.out.println(sap.length(v, w));
    }
  }
}
