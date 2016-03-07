/**
 * @name Miriam Lee
 * @date Mar 6, 2016
 * @purpose
 * @howto
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

/**
 * 2d-tree implementation. Mutable data type that uses a 2d-tree to implement
 * the same API (but replace PointSET with KdTree). A 2d-tree is a
 * generalization of a BST to two-dimensional keys. The idea is to build a BST
 * with points in the nodes, using the x- and y-coordinates of the points as
 * keys in strictly alternating sequence.
 *
 * @author mimi
 *
 */
public class KdTree {
  /**
   * construct an empty set of points
   */
  public KdTree() {
  }

  /**
   * is the set empty?
   *
   * @return
   */
  public boolean isEmpty() {
  }

  /**
   * number of points in the set
   *
   * @return
   */
  public int size() {
  }

  /**
   * add the point to the set (if it is not already in the set)
   *
   * @param p
   * @throws NullPointerException
   *           if any argument is null
   */
  public void insert(Point2D p) {
  }

  /**
   * does the set contain point p?
   *
   * @param p
   * @return
   * @throws NullPointerException
   *           if any argument is null
   */
  public boolean contains(Point2D p) {
  }

  /**
   * draw all points to standard draw
   */
  public void draw() {
  }

  /**
   * all points that are inside the rectangle
   *
   * @param rect
   * @return
   * @throws NullPointerException
   *           if any argument is null
   */
  public Iterable<Point2D> range(RectHV rect) {
  }

  /**
   * a nearest neighbor in the set to point p; null if the set is empty
   *
   * @param p
   * @return
   * @throws NullPointerException
   *           if any argument is null
   */
  public Point2D nearest(Point2D p) {
  }

  /**
   * unit testing of the methods (optional)
   *
   * @param args
   */
  public static void main(String[] args) {
  }
}
