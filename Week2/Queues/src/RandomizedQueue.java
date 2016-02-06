/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.StdRandom;
import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Randomized queue. A randomized queue is similar to a stack or queue, except
 * that the item removed is chosen uniformly at random from items in the data
 * structure.
 *
 * @param <Item>
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
  /**
   * construct an empty randomized queue
   */
  public RandomizedQueue() {

  }

  /**
   * is the queue empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return false;
  }

  /**
   * return the number of items on the queue
   *
   * @return
   */
  public int size() {
    return 0;
  }

  /**
   * add the item
   *
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void enqueue(Item item) {
  }

  /**
   * remove and return a random item
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to sample or dequeue an item from an empty
   *           randomized queue
   */
  public Item dequeue() {
    return null;
  }

  /**
   * return (but do not remove) a random item
   *
   * @return
   */
  public Item sample() {
    return null;
  }

  /**
   * return an independent iterator over items in random order
   *
   * @throws java.lang.UnsupportedOperationException
   *           if the client calls the remove() method in the iterator
   * @throws java.util.NoSuchElementException
   *           if the client calls the next() method in the iterator and there
   *           are no more items to return.
   * @see java.lang.Iterable#iterator()
   */
  public Iterator<Item> iterator() {
    return null;
  }

  /**
   * unit testing
   *
   * @param args
   */
  public static void main(String[] args) {
  }

}
