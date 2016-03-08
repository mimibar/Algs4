/**
 * @name Miriam Lee
 * @date Mar 6, 2016
 * @purpose
 * @howto
 */
import java.util.LinkedList;
import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

/**
 * Brute-force implementation. Mutable data type that represents a set of points
 * in the unit square.API by using a red-black BST (using either SET from
 * algs4.jar or java.util.TreeSet).
 *
 * @author mimi
 *
 */
public class PointSET {
  private TreeSet<Point2D> tree;

  /**
   * construct an empty set of points
   */
  public PointSET() {
    tree = new TreeSet<Point2D>();
  }

  /**
   * is the set empty?
   *
   * @return
   */
  public boolean isEmpty() {
    return tree.isEmpty();
  }

  /**
   * number of points in the set
   *
   * @return
   */
  public int size() {
    return tree.size();
  }

  /**
   * add the point to the set (if it is not already in the set)
   *
   * @param p
   * @throws NullPointerException
   *           if any argument is null
   */
  public void insert(Point2D p) {
    if (p == null) throw new NullPointerException();

    tree.add(p);
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
    if (p == null) throw new NullPointerException();

    return tree.contains(p);
  }

  /**
   * draw all points to standard draw
   */
  public void draw() {
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    for (Point2D p : tree) {
      StdDraw.point(p.x(), p.y());
    }
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
    if (rect == null) throw new NullPointerException();

    LinkedList<Point2D> r = new LinkedList<Point2D>();

    for (Point2D p : tree) {
      if (rect.contains(p)) r.add(p);
    }
    return r;
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
    if (p == null) throw new NullPointerException();

    Point2D nearest = null;

    for (Point2D q : tree) {
      if (nearest == null
          || p.distanceSquaredTo(q) < p.distanceSquaredTo(nearest)) {
        nearest = q;
      }
    }
    return nearest;
  }

  /**
   * unit testing of the methods (optional)
   *
   * @param args
   */
  public static void main(String[] args) {
  }
}
