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
  @SuppressWarnings("unchecked")
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
    @SuppressWarnings("unchecked")
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
    int r = N > 1 ? StdRandom.uniform(N - 1) : 0;
    Item item = rqueue[r];
    rqueue[r] = null; // to avoid loitering

    for (int i = r; i < N - 1; i++) {
      rqueue[i] = rqueue[i + 1];
    }

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
    int r = StdRandom.uniform(N - 1);
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
     * Given an array, how can I rearrange the entries in random order? Use
     * StdRandom.shuffle()—it implements the Knuth shuffle discussed in lecture
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
      StdRandom.shuffle(b);
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

      int n = a.length;
      @SuppressWarnings("unchecked")
      Item[] b = (Item[]) new Object[n];
      for (int i = 0; i < n; i++) {
        b[i] = a[i];
      }
      return b;
    }

    /**
     *
     */
    private RandomizedQueueIterator() {
      it = 0;
      sh = shuffle(rqueue);
    }

    /**
     * If current less than N.
     *
     * @return it < N
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
      return it < N;
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
      return sh[it++];
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
