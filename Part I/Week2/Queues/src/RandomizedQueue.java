/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
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
   * array of items .
   */
  private Item[] rqueue;
  /**
   * number of elements on stack.
   */
  private int N;

  /**
   * construct an empty randomized queue.
   */
  public RandomizedQueue() {
    rqueue = (Item[]) new Object[2];
    N = 0;
  }

  /**
   * is the queue empty?
   *
   * @return true if this stack is empty; false otherwise
   */
  public boolean isEmpty() {
    return N == 0;
  }

  /**
   * return the number of items on the queue.
   *
   * @return the number of items in the stack
   */
  public int size() {
    return N;
  }

  /**
   * add the item.
   *
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (N == rqueue.length) {
      resize(2 * rqueue.length); // double size of array if necessary
    }
    rqueue[N++] = item; // add item
  }

  /**
   * resize the underlying array holding the elements.
   *
   * @param capacity
   *          the new size
   */
  private void resize(int capacity) {
    assert capacity >= N;
    Item[] temp = (Item[]) new Object[capacity];

    for (int i = 0; i < N; i++) {
      temp[i] = rqueue[i];
    }
    rqueue = temp;
  }

  /**
   * remove and return a random item.
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to sample or dequeue an item from an empty
   *           randomized queue
   */
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Stack underflow");
    }

    int r = 0;
    if (N > 1) {
      r = StdRandom.uniform(N); // N (exclusive)
    }

    Item item = rqueue[r];
    // Test 1a-1g: exchange last with dequeued
    rqueue[r] = rqueue[N - 1];
    rqueue[N - 1] = null;

    N--;

    // shrink size of array if necessary
    if (N > 0 && N == rqueue.length / 4) {
      resize(rqueue.length / 2);

    }
    return item;
  }

  /**
   * return (but do not remove) a random item.
   *
   * @return random item
   * @throws NoSuchElementException
   *           if the client attempts to sample or dequeue an item from an empty
   *           randomized queue
   */
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException();
    }
    int r = 0;
    if (N > 1) { // Test 6a
      r = StdRandom.uniform(N); // N (exclusive)
    }
    return rqueue[r];
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
    return new RandomizedQueueIterator();
  }

  /**
   * an iterator, doesn't implement remove() since it's optional. The iterators
   * should operate independently of one another.
   */
  private final class RandomizedQueueIterator implements Iterator<Item> {
    /**
     * the current position.
     */
    private int it;
    /**
     * the shuffled array.
     */
    private Item[] sh;

    /**
    *
    */
    private RandomizedQueueIterator() {
      it = -1;
      sh = shuffle(rqueue);
    }

    /**
     * Given an array, how can I rearrange the entries in random order? Use
     * StdRandom.shuffle() it implements the Knuth shuffle discussed in lecture
     * and runs in linear time
     *
     * @param a
     *          the array to shuffle
     * @return returns a new shuffled array
     */
    private Item[] shuffle(final Item[] a) {
      if (a == null) {
        throw new NullPointerException("argument array is null");
      }

      Item[] b = copy(a);

      // Shuffle only with existing items
      if (N > 1)
        StdRandom.shuffle(b, 0, N - 1); // Test 15: right endpoint (inclusive)
      return b;
    }

    /**
     * Returns a copy of a given array.
     *
     * @param a
     *          the array to copy
     * @return the new array
     */
    private Item[] copy(final Item[] a) {
      if (a == null) {
        throw new NullPointerException("argument array is null");
      }

      Item[] b = (Item[]) new Object[a.length];
      for (int i = 0; i < N; i++) { // only copy items, not nulls
        b[i] = a[i];
      }
      return b;
    }

    /**
     * If current less than N.
     *
     * @return it < N
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
      return it < N - 1;
    }

    /**
     * (non-Javadoc).
     *
     * @throws UnsupportedOperationException
     *           if the client calls the remove() method in the iterator
     * @see java.util.Iterator#remove()
     */
    public void remove() {
      throw new UnsupportedOperationException();
    }

    /**
     * (non-Javadoc).
     *
     * @return sh[it]
     * @throws NoSuchElementException
     *           if the client calls the next() method in the iterator and there
     *           are no more items to return.
     * @see java.util.Iterator#next()
     */
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      return sh[++it];
    }

    /**
     * We recommend that you create a client class with a name like TestDeque,
     * where each unit test is a method in this class.
     */
    private class TestDeque extends RandomizedQueue<Item> {

      // /**
      // * Don't forget to test your iterator.
      // */
      // private void testIterator() {
      // Iterator<Item> it = super.iterator();
      //
      // assert it.hasNext() == (super.N > 0);
      // Item t = super.rqueue[0];
      //
      // while (it.hasNext()) {
      // i = it.next();
      // // assert t.itm.equals(i);
      // // t = t.next;
      // }
      // }

    }
  }

  /**
   * unit testing.
   *
   * to be or not to - be - - that - - - is
   *
   * @param args
   */
  public static void main(String[] args) {

    RandomizedQueue<String> s = new RandomizedQueue<String>();
    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-")) {
        s.enqueue(item);
      } else if (!s.isEmpty()) {
        StdOut.print(s.dequeue() + " ");
      }
    }
    StdOut.println("(" + s.size() + " left on stack)");
  }

}
