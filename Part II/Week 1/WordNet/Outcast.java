/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * immutable data type Outcast</br>
 *
 * Given a list of wordnet nouns, which noun is the least related to the others?
 * To identify an outcast, compute the sum of the distances between each noun
 * and every other one and return a noun At for which dt is maximum.
 *
 * @author mimi
 *
 */
public class Outcast {

  private final WordNet wordnet;

  /**
   * constructor takes a WordNet object
   *
   * @param wordnet
   *          Assume contains only valid wordnet nouns (and that it contains at
   *          least two such nouns).
   */
  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  /**
   * given an array of WordNet nouns, return an outcast
   *
   * @param nouns
   * @return
   */
  public String outcast(String[] nouns) {
    String outString = null;
    int max = Integer.MIN_VALUE;

    for (String a : nouns) {
      int dt = 0;
      for (String b : nouns) {
        if (a.equals(b))
          continue;
        dt += wordnet.distance(a, b);

      }
      if (dt > max) {
        max = dt;
        outString = a;
      }
    }
    return outString;
  }

  /**
   * test client
   *
   * @param args
   */
  public static void main(String[] args) {

    WordNet wordnet = new WordNet(args[0], args[1]);
    Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; t++) {
      In in = new In(args[t]);
      String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}
