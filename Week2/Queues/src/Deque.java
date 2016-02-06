/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose The goal of this assignment is to implement elementary data
 *          structures using arrays and linked lists, and to introduce you to
 *          generics and iterators.
 * @howto
 */

import java.lang.NullPointerException;
import java.lang.UnsupportedOperationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Dequeue. A double-ended queue or deque (pronounced "deck") is a
 * generalization of a stack and a queue that supports adding and removing items
 * from either the front or the back of the data structure.
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {
  /**
   * construct an empty deque
   */
  public Deque() {
  }

  /**
   * is the deque empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return false;
  }

  /**
   * return the number of items on the deque
   *
   * @return
   */
  public int size() {
    return 0;
  }

  /**
   * add the item to the front
   *
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addFirst(Item item) {
  }

  /**
   * add the item to the end
   *
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addLast(Item item) {
  }

  /**
   * remove and return the item from the front
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeFirst() {
    return null;
  }

  /**
   * remove and return the item from the end
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeLast() {
    return null;
  }

  /**
   * return an iterator over items in order from front to end
   *
   * @see java.lang.Iterable#iterator()
   * @throws java.lang.UnsupportedOperationException
   *           if the client calls the remove() method in the iterator
   * @throws java.util.NoSuchElementException
   *           if the client calls the next() method in the iterator and there
   *           are no more items to return.
   */
  public Iterator<Item> iterator() {
    return null;
  } //

  /**
   * unit testing
   *
   * @param args
   */
  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

}
