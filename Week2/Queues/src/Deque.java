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

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Dequeue. A double-ended queue or deque (pronounced "deck") is a
 * generalization of a stack and a queue that supports adding and removing items
 * from either the front or the back of the data structure.
 *
 * @param <Item>
 */
public class Deque<Item> implements Iterable<Item> {

  // helper linked list class
  private class Node {
    private Item item;
    private Node next;
    private Node prev;

    /**
     * @param item
     */
    public Node(Item item) {
      this.item = item;
    }

    /**
     * @param item
     * @param next
     * @param prev
     */
    public Node(Item item, Node prev, Node next) {
      this(item);
      this.next = next;
      this.prev = prev;
    }

  }

  private Node head;
  private Node tail;
  private int size;

  /**
   * construct an empty deque
   */
  public Deque() {
    head = null;
    tail = null;
    size = 0;
  }

  /**
   * is the deque empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * return the number of items on the deque
   *
   * @return
   */
  public int size() {
    return size;
  }

  /**
   * add the item to the front
   *
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addFirst(Item im) {
    if (im == null)
      throw new NullPointerException();

    Node old = this.head;
    head = new Node(im, null, old);

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
   * @param item
   * @throws java.lang.NullPointerException
   *           if the client attempts to add a null item
   */
  public void addLast(Item im) {
    if (im == null)
      throw new NullPointerException();

    Node old = this.tail;
    tail = new Node(im, old, null);

    if (isEmpty())
      head = tail;
    else
      old.next = tail;

    size++;
    assert check();
  }

  /**
   * dequeue: remove and return the item from the front
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeFirst() {
    if (size == 0)
      throw new NoSuchElementException();

    Item item = head.item;
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
   * remove and return the item from the end
   *
   * @return
   * @throws java.util.NoSuchElementException
   *           if the client attempts to remove an item from an empty deque
   */
  public Item removeLast() {
    if (size == 0)
      throw new NoSuchElementException();

    Item item = tail.item;
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
    private Node current = head;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException();
    }

    public Item next() {
      if (!hasNext())
        throw new NoSuchElementException();
      Item item = current.item;
      current = current.next;
      return item;
    }
  }

  // check internal invariants
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
    if (numberOfNodes != size)
      return false;

    return true;

  }

  private class TestDeque extends Deque<Item> {

    private void testaddFirst(Item im) {
      addFirst(im);
      assert super.head.item == im;
      testSize();
    }

    private void testaddLast(Item im) {
      addLast(im);
      assert super.tail.item == im;
      testSize();
    }

    private Item testremoveFirst() {
      Item i = super.head.item;
      Item ft = removeFirst();
      assert ft == i;
      testSize();
      return ft;
    }

    private Item testremoveLast() {
      Item im = super.tail.item;
      Item lt = removeLast();
      assert lt == im;
      testSize();
      return lt;
    }

    /**
     * check internal consistency of instance variable size
     */
    private void testSize() { //
      int numberOfNodes = 0;
      for (Node x = super.head; x != null
          && numberOfNodes <= super.size; x = x.next) {
        numberOfNodes++;
      }
      assert numberOfNodes == super.size;
    }

    private void testIterator() {
      Iterator<Item> it = super.iterator();

      assert it.hasNext() == (super.size > 0);
      Node t = super.head;
      Item i;

      while (it.hasNext()) {
        i = it.next();
        assert t.item.equals(i);
        t = t.next;
      }
    }

    private TestDeque() {
      super();
    }

    /**
     * call addFirst() with the numbers 1 through N in ascending order, then
     * call removeLast() N times, you should see the numbers 1 through N in
     * ascending order.
     */
    private void N(int N) {
      for (int i = 1; i <= N; i++) {
        addFirst((Item) (Integer.valueOf(i)));
      }
      testIterator();
      for (int i = 1; i <= N; i++) {
        assert (removeLast().equals((Item) (Integer.valueOf(i))));
      }
    }
  }

  /**
   * unit testing
   *
   * @param args
   */
  public static void main(String[] args) {

    // 1 2 3 4 5 - - - - -
    // 1 2 5 - 3 4 - - - -
    // 5 - 1 2 3 - 4 - - -
    // 5 - 4 - 3 - 2 - 1 -
    Deque.TestDeque s = new Deque<Integer>().new TestDeque();
    s.N(10);

    s = new Deque<String>().new TestDeque();

    while (!StdIn.isEmpty()) {
      String item = StdIn.readString();
      if (!item.equals("-"))
        s.testaddFirst(item);
      else {
        if (!s.isEmpty())
          StdOut.print(s.testremoveFirst() + " ");
      }
    }

    StdOut.println("(" + s.size() + " left on stack)");

  }

}
