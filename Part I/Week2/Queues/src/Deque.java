/**
 * @name Miriam Lee
 * @date Feb 5, 2016
 * @purpose The goal of this assignment is to implement elementary data
 *          structures using arrays and linked lists, and to introduce you to
 *          generics and iterators.
 * @howto
 */

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

  // helper linked list class
  /**
   * @author mimi
   *
   */
  private class Node {
    /**
     * @TODO Include a comment describing every instance variable.
     */
    private Item itm;
    /**
     * @TODO Include a comment describing every instance variable.
     */
    private Node next;
    /**
     * @TODO Include a comment describing every instance variable.
     */
    private Node prev;

    /**
     * @param item
     */
    private Node(final Item item) {
      itm = item;
    }

    /**
     * @param item
     * @param nex
     * @param pre
     */
    private Node(final Item item, Node pre, Node nex) {
      this(item);
      next = nex;
      prev = pre;
    }

  }

  /**
   * @TODO Include a comment describing every instance variable.
   */
  private Node head;
  /**
   * @TODO Include a comment describing every instance variable.
   */
  private Node tail;
  /**
   * @TODO Include a comment describing every instance variable.
   */
  private int size;

  /**
   * construct an empty deque.
   */
  public Deque() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * is the deque empty?
   *
   * @return if size is 0
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * return the number of items on the deque.
   *
   * @return the number of items on the deque
   */
  public int size() {
    return size;
  }

  /**
   * add the item to the front.
   *
   * @param itm
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addFirst(Item item) {
    if (item == null)
      throw new NullPointerException();

    Node old = this.head;
    head = new Node(item, null, old);

    if (isEmpty()) {
      tail = head;
    } else // if (old != null)
      old.prev = head;

    size++;
    assert check();
  }

  /**
   * Enqueue: add the item to the end
   *
   * @param itm
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addLast(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }

    Node old = this.tail;
    tail = new Node(item, old, null);

    if (isEmpty()) {
      head = tail;
    } else {
      old.next = tail;
    }

    size++;
    assert check();
  }

  /**
   * dequeue: remove and return the item from the front
   *
   * @return the item from the front
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeFirst() {
    if (size == 0) {
      throw new NoSuchElementException();
    }

    Item item = head.itm;
    size--;

    if (isEmpty()) {
      head = null;
      tail = null;
    } else {
      head = head.next;
      head.prev = null;
    }
    assert check();
    return item;
  }

  /**
   * remove and return the item from the end.
   *
   * @return the item from the end
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeLast() {
    if (size == 0) {
      throw new NoSuchElementException();
    }

    Item item = tail.itm;
    size--;

    if (isEmpty()) {
      head = null;
      tail = null;
    } else {
      tail = tail.prev;
      tail.next = null;
    }
    // assert check();
    return item;
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
    return new DequeIterator();
  }

  /**
   * an iterator, doesn't implement remove() since it's optional
   */
  private class DequeIterator implements Iterator<Item> {
    /**
     * @TODO Include a comment describing every instance variable.
     */
    private Node current = head;

    /**
     * (non-Javadoc).
     *
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
      return current != null;
    }

    /**
     * (non-Javadoc).
     *
     * @see java.util.Iterator#remove()
     */
    public void remove() {
      throw new UnsupportedOperationException();
    }

    /**
     * (non-Javadoc).
     *
     * @see java.util.Iterator#next()
     */
    public Item next() {
      if (!hasNext()) {
        throw new NoSuchElementException();
      }
      Item item = current.itm;
      current = current.next;
      return item;
    }
  }

  /**
   * check internal invariants.
   *
   * @return
   */
  private boolean check() {

    // check a few properties of instance variable 'first'
    if (size < 0) {
      return false;
    }

    switch (size) {
    case 0:
      if (head != null) {
        return false;
      }
      break;
    case 1:
      if (head == null || head.next != null) {
        return false;
      }
      break;
    default:
      if (head == null || head.next == null) {
        return false;
      }
      break;
    }

    // check internal consistency of instance variable N
    int numberOfNodes = 0;
    for (Node x = head; x != null && numberOfNodes <= size; x = x.next) {
      numberOfNodes++;
    }
    if (numberOfNodes != size) {
      return false;
    }

    return true;

  }

  /**
   * We recommend that you create a client class with a name like TestDeque,
   * where each unit test is a method in this class.
   */
  private class TestDeque extends Deque<Item> {

    // private void testaddFirst(Item im) {
    // addFirst(im);
    // assert super.head.itm == im;
    // testSize();
    // }
    //
    // private void testaddLast(Item im) {
    // addLast(im);
    // assert super.tail.itm == im;
    // testSize();
    // }
    //
    // private Item testremoveFirst() {
    // Item i = super.head.itm;
    // Item ft = removeFirst();
    // assert ft == i;
    // testSize();
    // return ft;
    // }
    //
    // private Item testremoveLast() {
    // Item im = super.tail.itm;
    // Item lt = removeLast();
    // assert lt == im;
    // testSize();
    // return lt;
    // }
    //
    // /**
    // * check internal consistency of instance variable size.
    // */
    // private void testSize() { //
    // int numberOfNodes = 0;
    // for (Node x = super.head; x != null
    // && numberOfNodes <= super.size; x = x.next) {
    // numberOfNodes++;
    // }
    // assert numberOfNodes == super.size;
    // }
    //
    // /**
    // * Don't forget to test your iterator.
    // */
    // private void testIterator() {
    // Iterator<Item> it = super.iterator();
    //
    // assert it.hasNext() == (super.size > 0);
    // Node t = super.head;
    // Item i;
    //
    // while (it.hasNext()) {
    // i = it.next();
    // assert t.itm.equals(i);
    // t = t.next;
    // }
    // }
    //
    // // private TestDeque() {
    // // super();
    // // }
    //
    // /**
    // * call addFirst() with the numbers 1 through N in ascending order, then
    // * call removeLast() N times, you should see the numbers 1 through N in
    // * ascending order.
    // */
    // private void testN(final int N) {
    // for (int i = 1; i <= N; i++) {
    // addFirst((Item) (Integer.valueOf(i)));
    // }
    // testIterator();
    // for (int i = 1; i <= N; i++) {
    // assert (removeLast().equals((Item) (Integer.valueOf(i))));
    // }
    // }
  }

  /**
   * unit testing.
   *
   * @param args
   */
  public static void main(String[] args) {
    //
    // // 1 2 3 4 5 - - - - -
    // // 1 2 5 - 3 4 - - - -
    // // 5 - 1 2 3 - 4 - - -
    // // 5 - 4 - 3 - 2 - 1 -
    // Deque.TestDeque s = new Deque<Integer>().new TestDeque();
    // s.testN(10);
    //
    // s = new Deque<String>().new TestDeque();
    //
    // while (!StdIn.isEmpty()) {
    // String item = StdIn.readString();
    // if (!item.equals("-")) {
    // s.testaddFirst(item);
    // } else {
    // if (!s.isEmpty()) {
    // StdOut.print(s.testremoveFirst() + " ");
    // }
    // }
    // }
    //
    // StdOut.println("(" + s.size() + " left on stack)");

  }

}
