/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.TopologicalX;

/**
 * immutable data type WordNet
 *
 * @author mimi
 *
 */
public class WordNet {

  /**
   * A synset can consist of exactly one noun. Moreover, there can be several
   * different synsets that consist of the same noun.
   */
  private HashMap<Integer, LinkedList<String>> synsets;
  /**
   * A noun can appear in more than one synset
   */
  private HashSet<String> nouns = new HashSet<>();
  private Digraph dg;

  /**
   * constructor takes the name of the two input files@param synsets
   *
   * @performance should take time linearithmic (or better) in the input size
   * @param synsets
   *          each vertex v is an integer that represents a synset
   * @param hypernyms
   *          each directed edge v→w represents that w is a hypernym of v
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IllegalArgumentException
   *           if the input does not correspond to a rooted DAG
   */
  public WordNet(String synsets, String hypernyms) {
    if (synsets == null || hypernyms == null)
      throw new NullPointerException("argument is null");

    // Synsets
    this.synsets = new HashMap<Integer, LinkedList<String>>();

    Scanner in = null;
    try {
      in = new Scanner(new File(synsets));
      in.useDelimiter(",");
      LinkedList<String> n;
      while (in.hasNextLine()) {
        int key = Integer.parseInt(in.next());
        n = new LinkedList<>(Arrays.asList(in.next().split("\\s")));
        this.synsets.put(key, n);
        nouns.addAll(n);
        in.nextLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
    }

    // Hypernyms
    try {
      in = new Scanner(new File(hypernyms));
      in.useDelimiter("[,\n]");

      dg = new Digraph(this.synsets.size());

      while (in.hasNext()) {

        int v = Integer.parseInt(in.next());

        String s = in.nextLine();

        for (String w : s.split(",")) {
          if (!w.isEmpty())
            dg.addEdge(v, Integer.parseInt(w));
        }

      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
    }

    if (!isRooted(dg) || !(new Topological(dg).hasOrder()))
      throw new IllegalArgumentException(
          "the input does not correspond to a rooted DAG");
  }

  /**
   * a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor
   * of every other vertex.
   *
   * Reachable vertex in a DAG. A linear-time algorithm to determine whether a
   * DAG has a vertex that is reachable from every other vertex. Compute the
   * outdegree of each vertex. If the DAG has exactly one vertex v with
   * outdegree 0, then it is reachable from every other vertex.
   *
   * A vertex with outdegree of zero would be the root at least of its subgraph.
   * And if there are no cycles, then a singular vertex with outdegree of zero
   * would imply it can be reached from all other vertices. That would make the
   * graph a rooted DAG. We still need to make sure that the graph has no
   * cycles.
   *
   * @see <a href=
   *      "https://class.coursera.org/algs4partII-007/forum/thread?thread_id=52">
   *      Rooted DAG and cycle detection</a>
   * @see <a href=
   *      "https://class.coursera.org/algs4partII-007/forum/thread?thread_id=37#comment-42">
   *      Multiple root detection<a/>
   * @param dg2
   * @return
   */
  private boolean isRooted(Digraph dg2) {
    boolean rootFound = false;
    for (int i = 0; i < dg.V(); i++) {
      if (dg2.outdegree(i) == 0)
        if (rootFound)
        return false;
        else
        rootFound = true;
    }
    return rootFound;
  }

  //
  /**
   * returns all WordNet nouns
   *
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   */
  public Iterable<String> nouns() {
    return nouns;
  }

  /**
   * is the word a WordNet noun?
   *
   * @performance should run in time logarithmic (or better) in the number of
   *              nouns.
   * @param word
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   */
  public boolean isNoun(String word) {
    if (word == null)
      throw new NullPointerException();

    return nouns.contains(word);
  }

  /**
   * distance between nounA and nounB (defined below)@param nounA
   *
   * @param nounB
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   * @throws ArgumentException
   *           unless both of the noun arguments are WordNet nouns.
   */
  public int distance(String nounA, String nounB) {
  }

  /**
   * a synset (second field of synsets.txt) that is the common ancestor of nounA
   * and nounB in a shortest ancestral path (defined below)
   *
   * @param nounA
   * @param nounB
   * @return
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IllegalArgumentException
   *           unless both of the noun arguments are WordNet nouns.
   */
  public String sap(String nounA, String nounB) {
  }

  /**
   * do unit testing of this class
   *
   * @param args
   */
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
  }

}
