/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * immutable data type Outcast
 *
 * @author mimi
 *
 */
public class Outcast {

  /**
   * constructor takes a WordNet object@param wordnet
   *
   * @param wordnet
   *          Assume contains only valid wordnet nouns (and that it contains at
   *          least two such nouns).
   */
  public Outcast(WordNet wordnet) {
  } //

  /**
   * given an array of WordNet nouns, return an outcast
   *
   * @param nouns
   * @return
   */
  public String outcast(String[] nouns) {
  } //

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
