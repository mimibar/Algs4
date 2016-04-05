/**
 * @name Miriam Lee
 * @date Mar 31, 2016
 * @purpose
 * @howto
 */

/**
 * immutable data type WordNet
 *
 * @author mimi
 *
 */
public class WordNet {

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
  }

}
