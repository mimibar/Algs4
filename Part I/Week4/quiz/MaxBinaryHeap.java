/**
 * @name Miriam Lee
 * @date Feb 25, 2016
 * @purpose
 * @howto
 */
package quiz;

import java.util.Iterator;

import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * @author mimi
 *
 */
public class MaxBinaryHeap {

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param args
   */
  public static void main(String[] args) {
//    edu.princeton.cs.algs4.MaxPQ.main(null);

    MaxPQ<String> pq = new MaxPQ<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-"))
        pq.insert(item);
      else if (!pq.isEmpty()) StdOut.print(pq.delMax() + " ");
    }
    StdOut.println("(" + pq.size() + " left on pq)");
    Iterator<String> it = pq.iterator();
    while (it.hasNext()) {
      System.out.print(it.next() + " ");
    }
  }

}
