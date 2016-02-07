/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.StdRandom;

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
    int k = Integer.parseInt(args[0]);
//    boolean[] kb = new boolean[N];
//
//    int kk = k, p;
//    while (kk-- > 0) {
//      do {
//        p = StdRandom.uniform(N);
//      } while (kb[p]);
//      kb[p] = true;
//      kk--;
//    }

//    int N = 0;
    RandomizedQueue<String> s = new RandomizedQueue<String>();

    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
//      if (kb[N])
        s.enqueue(item);
      // StdOut.println(item);
//      N++;
    }
    while (k-- > 0) {
      StdOut.println(s.dequeue());
    }
  }
}
