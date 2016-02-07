/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Takes a command-line integer k; reads in a sequence of N strings from
 * standard input using StdIn.readString(); and prints out exactly k of them,
 * uniformly at random.
 *
 */
public class Subset {

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param args
   */
  public static void main(String[] args) {
    int k = Integer.valueOf(args[0]);
    int N = 0;

    RandomizedQueue<String> s = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      s.enqueue(item);
//      StdOut.println(item);
        N++;
    }
    while (k-- > 0) {
      StdOut.println(s.dequeue());
    }
  }
}
