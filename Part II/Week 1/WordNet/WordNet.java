/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
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
   * A synset can consist of exactly one noun. Moreover, there can be several different synsets that
   * consist of the same noun, i.e. a noun can appear in more than one synset.
   */
  private final HashMap<String, List<Integer>> nounSynsets = new HashMap<>();

  /**
   * Number of synsets
   */
  private int syn = 0;

  private final SAP sap;

  private final ArrayList<String> synsets = new ArrayList<>();

  /**
   * constructor takes the name of the two input files
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
    String line;
    String[] lines;
    In in = null;
    ////////////////////////// Synsets

    in = new In(synsets);

    String[] nouns;
    List<Integer> ss;
    while (in.hasNextLine()) {
      line = in.readLine();
      lines = line.split(",");
      int key = Integer.parseInt(lines[0]);
      String value = lines[1];
      this.synsets.add(key, value);

      nouns = value.split("\\s");
      for (String n : nouns) {
        if (nounSynsets.containsKey(n))
          ss = nounSynsets.get(n);
        else
          ss = new LinkedList<>();
        ss.add(key);
        nounSynsets.put(n, ss);

      }
      syn++;
      // in.readLine();
    }
    in.close();

    ////////////////////////// Hypernyms
    // scanner.useDelimiter("[,\n]");
    in = new In(hypernyms);
    Digraph dg = new Digraph(syn);

    while (in.hasNextLine()) {
      line = in.readLine();
      lines = line.split(",");
      int i = 0;
      int v = Integer.parseInt(lines[i]);

      while (i < lines.length - 1) {
        String w = lines[++i];
        if (!w.isEmpty()) dg.addEdge(v, Integer.parseInt(w));
      }
    }
    if (!isRooted(dg) || !(new Topological(dg).hasOrder()))
      throw new IllegalArgumentException("the input does not correspond to a rooted DAG");

    sap = new SAP(dg);
    in.close();

  }

  /**
   * @param ob
   * @return true if argument not null
   * @throws NullPointerException
   *           if any argument is null.
   */
  private static boolean isNotNull(Object ob) {
    if (ob == null) throw new NullPointerException();
    return true;
  }

  /**
   * a rooted DAG: it is acyclic and has one vertex—the root—that is an ancestor of every other
   * vertex.</br>
   *
   * Reachable vertex in a DAG. A linear-time algorithm to determine whether a DAG has a vertex that
   * is reachable from every other vertex. Compute the outdegree of each vertex. If the DAG has
   * exactly one vertex v with outdegree 0, then it is reachable from every other vertex.
   *
   * A vertex with outdegree of zero would be the root at least of its subgraph. And if there are no
   * cycles, then a singular vertex with outdegree of zero would imply it can be reached from all
   * other vertices. That would make the graph a rooted DAG. We still need to make sure that the
   * graph has no cycles.
   *
   * @see <a href= "https://class.coursera.org/algs4partII-007/forum/thread?thread_id=52"> Rooted
   *      DAG and cycle detection</a>
   * @see <a href=
   *      "https://class.coursera.org/algs4partII-007/forum/thread?thread_id=37#comment-42">
   *      Multiple root detection<a/>
   * @param dg2
   * @return
   */
  private static boolean isRooted(Digraph g) {
    boolean rootFound = false;
    for (int i = 0; i < g.V(); i++) {
      if (g.outdegree(i) == 0) if (rootFound)
        return false;
      else
        rootFound = true;
    }
    return rootFound;
  }

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
   * @performance should run in time logarithmic (or better) in the number of nouns.
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
    // Test 3a-c: timing sap() and distance() with random nouns

    isNotNull(nounA);
    isNotNull(nounB);
    if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
    return sap.length(nounSynsets.get(nounA), nounSynsets.get(nounB));
  }

  /**
   * a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB in a
   * shortest ancestral path (defined below)
   *
   * @param nounA
   * @param nounB
   * @return The individual nouns that comprise a synset are separated by spaces (and a synset
   *         element is not permitted to contain a space).
   * @throws NullPointerException
   *           if any argument is null.
   * @throws IllegalArgumentException
   *           unless both of the noun arguments are WordNet nouns.
   */
  public String sap(String nounA, String nounB) {
    // Test 3a-c: timing sap() and distance() with random nouns
    isNotNull(nounA);
    isNotNull(nounB);

    if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();

    int anc = sap.ancestor(nounSynsets.get(nounA), nounSynsets.get(nounB));

    // Test 4: test sap() of random noun pairs,
    // Test 6: test sap() of random noun pairs,
    // Test 14: random calls to isNoun(), distance(), and sap(), with
    return synsets.get(anc);

  }

  /**
   * do unit testing of this class
   *
   * @param args
   */
  public static void main(String[] args) {
    WordNet wn = new WordNet(args[0], args[1]);

    System.out.println(wn.isNoun("Abruzzi"));
    System.out.println(wn.distance("Abruzzi", "Abutilon"));

    // Test 4: test sap() of random noun pairs
    System.out.println(wn.sap("phenylacetamide", "Constantine_the_Great"));
    // Test 14: random calls to isNoun(), distance(), and sap(), with
    // probabilities p1, p2, and p3, respectively
    System.out.println(wn.sap("nuclear_engineering", "Osteoglossiformes"));
    System.out.println(wn.sap("trope", "conditional_probability"));
    System.out.println(wn.sap("genus_Majorana", "XII"));

    // Test 6: test sap() of random noun pairs
    wn = new WordNet("testing/synsets100-subgraph.txt", "testing/hypernyms100-subgraph.txt");
    System.out.println(wn.sap("Christmas_factor", "supermolecule"));

    wn = new WordNet("testing/synsets500-subgraph.txt", "testing/hypernyms500-subgraph.txt");
    System.out.println(wn.sap("butyl", "uranyl"));
    wn = new WordNet("testing/synsets500-subgraph.txt", "testing/hypernyms500-subgraph.txt");
    System.out.println(wn.sap("gondang_wax", "ketone_group"));
  }

}
