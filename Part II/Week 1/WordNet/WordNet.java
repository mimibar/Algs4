/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Topological;

/**
 * immutable data type WordNet
 *
 * @author mimi
 *
 */
public class WordNet {

  /**
   * &lt;noun, synsets&gt;</br>
   * A synset can consist of exactly one noun. Moreover, there can be several
   * different synsets that consist of the same noun, i.e. a noun can appear in
   * more than one synset.
   */
  private HashMap<String, List<Integer>> nounSynsets =
      new HashMap<String, List<Integer>>();

  /**
   * Number of synsets
   */
  private int syn = 0;

  private SAP sap;

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
    isNotNull(synsets);
    isNotNull(hypernyms);

    Scanner in = null;

    ////////////////////////// Synsets

    try {
      in = new Scanner(new File(synsets));
      in.useDelimiter(",");

      String[] nouns;
      List<Integer> ss;

      while (in.hasNextLine()) {
        int key = Integer.parseInt(in.next());

        nouns = in.next().split("\\s");
        for (String n : nouns) {
          if (nounSynsets.containsKey(n))
            ss = nounSynsets.get(n);
          else
            ss = new LinkedList<>();
          ss.add(key);
          nounSynsets.put(n, ss);

        }
        syn++;
        in.nextLine();
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
    }

    ////////////////////////// Hypernyms
    try {
      in = new Scanner(new File(hypernyms));
      in.useDelimiter("[,\n]");

      Digraph dg = new Digraph(syn);

      String s;
      while (in.hasNext()) {

        int v = Integer.parseInt(in.next());

        s = in.nextLine();

        for (String w : s.split(",")) {
          if (!w.isEmpty())
            dg.addEdge(v, Integer.parseInt(w));
        }

      }
      if (!isRooted(dg) || !(new Topological(dg).hasOrder()))
        throw new IllegalArgumentException(
            "the input does not correspond to a rooted DAG");

      sap = new SAP(dg);
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } finally {
      if (in != null)
        in.close();
    }
  }

  /**
   * @param o
   * @return true if argument not null
   * @throws NullPointerException
   *           if any argument is null.
   */
  private static boolean isNotNull(Object o) {
    if (o == null)
      throw new NullPointerException();
    return true;
  }

  /**
   * a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor
   * of every other vertex.</br>
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
  private static boolean isRooted(Digraph g) {
    boolean rootFound = false;
    for (int i = 0; i < g.V(); i++) {
      if (g.outdegree(i) == 0)
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
    return nounSynsets.keySet();
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
    isNotNull(word);

    return nounSynsets.containsKey(word);
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
    isNotNull(nounA);
    isNotNull(nounB);
    if (!isNoun(nounA) || !isNoun(nounB))
      throw new IllegalArgumentException();
    return sap.length(nounSynsets.get(nounA), nounSynsets.get(nounB));
  }

  /**
   * a synset (second field of synsets.txt) that is the common ancestor of nounA
   * and nounB in a shortest ancestral path (defined below)
   *
   * @param nounA
   * @param nounB
   * @return The individual nouns that comprise a synset are separated by spaces
   *         (and a synset element is not permitted to contain a space).
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IllegalArgumentException
   *           unless both of the noun arguments are WordNet nouns.
   */
  public String sap(String nounA, String nounB) {
    isNotNull(nounA);
    isNotNull(nounB);

    if (!isNoun(nounA) || !isNoun(nounB))
      throw new IllegalArgumentException();

    StringBuilder sb = new StringBuilder();

    int anc = sap.ancestor(nounSynsets.get(nounA), nounSynsets.get(nounB));

    for (Entry<String, List<Integer>> n : nounSynsets.entrySet()) {
      if (n.getValue().contains(anc))
        sb.append(n.getKey()).append(" ");
    }

    return sb.toString();

  }

  /**
   * do unit testing of this class
   *
   * @param args
   */
  public static void main(String[] args) {
    WordNet wn = new WordNet(args[0], args[1]);
    if (wn.nouns() != null) {
      System.out.println(wn.isNoun("Abruzzi"));
      System.out.println(wn.sap("Abruzzi", "Abutilon"));
      System.out.println(wn.distance("Abruzzi", "Abutilon"));
    }
  }

}
