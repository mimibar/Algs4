/**
 * @name Miriam Lee
 * @date Mar 6, 2016
 * @purpose
 * @howto
 */

import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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
  private Node root = null;

  private static class Node {
    /**
     * the point
     */
    private Point2D p;
    /**
     * The axis-aligned rectangle corresponding to this node.
     *
     * Each node corresponds to an axis-aligned rectangle in the unit square,
     * which encloses all of the points in its subtree. The root corresponds to
     * the unit square; the left and right children of the root corresponds to
     * the two rectangles split by the x-coordinate of the point at the root;
     * and so forth.
     */
    private RectHV rect;
    /**
     * the left/bottom subtree
     */
    private Node lb;
    /**
     * the right/top subtree
     */
    private Node rt;
  }

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
    return size() == 0;
  }

  /**
   * number of points in the set
   *
   * @return
   */
  public int size() {
    if (root == null) return 0;

    return size(root);
  }

  private int size(Node p) {
    if (p == null) return 0;

    return size(p.lb) + size(p.rt) + 1;
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

    root = insert(root, p, false);
    if (root.rect == null) {
      root.rect = new RectHV(0, 0, 1, 1);

      root.lb = new Node();
      root.lb.rect = getRect(root, false, true);

      root.rt = new Node();
      root.rt.rect = getRect(root, false, false);
    }
  }

  private RectHV getRect(Node n, boolean hor, boolean left) {
    if (hor) {
      if (left)
        return new RectHV(n.rect.xmin(), n.rect.ymin(), n.rect.xmax(), n.p.y());
      else
        return new RectHV(n.rect.xmin(), n.p.y(), n.rect.xmax(), n.rect.ymax());
    }
    // ver
    if (left)// bottom
      return new RectHV(n.rect.xmin(), n.rect.ymin(), n.p.x(), n.rect.ymax());
    else
      return new RectHV(n.p.x(), n.rect.ymin(), n.rect.xmax(), n.rect.ymax());

  }

  private int xycompare(boolean hor, Point2D o1, Point2D o2) {
    if (!hor)
      return Point2D.X_ORDER.compare(o1, o2);
    else
      return Point2D.Y_ORDER.compare(o1, o2);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param r
   * @param pt
   * @param hor
   *          orientation. 1 - horizontal, 0 - vertical
   */
  private Node insert(Node r, Point2D pt, boolean hor) {
    if (r == null) r = new Node();
    if (r.p == null) {
      r.p = pt;
    }
    else {
      int cmp = xycompare(hor, pt, r.p);

      if (cmp < 0) {
        r.lb = insert(r.lb, pt, !hor);
        if (r.lb.lb == null) { // point was inserted..

          r.lb.lb = new Node();
          r.lb.lb.rect = getRect(r.lb, !hor, true);

          r.lb.rt = new Node();
          r.lb.rt.rect = getRect(r.lb, !hor, false);
        }
      }
      else // What should I do if a point has the same x-coordinate as the point
           // in a node when inserting/searching in a 2d-tree? Go the right
           // subtree as specified.
      if (cmp >= 0) {
        r.rt = insert(r.rt, pt, !hor);
        if (r.rt.lb == null) {
          r.rt.lb = new Node();
          r.rt.lb.rect = getRect(r.rt, !hor, true);

          r.rt.rt = new Node();
          r.rt.rt.rect = getRect(r.rt, !hor, false);
        }
      }

    }
    return r;

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
    if (p == null)
      throw new NullPointerException("argument to contains() is null");
    return contains(root, p, false);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param r
   * @param p
   * @return
   */
  private boolean contains(Node r, Point2D p, boolean hor) {
    if (p == null) return false;

    // int cmp = p.compareTo(r.p);
    int cmp = xycompare(hor, p, r.p); // TODO x-axis compare
    if (cmp < 0)
      return contains(r.lb, p, !hor);
    else if (cmp > 0)
      return contains(r.rt, p, !hor);

    else
      return true;
  }

  /**
   * draw all points to standard draw
   */
  public void draw() {
    draw(root, false);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param n
   * @param hor
   */
  private void draw(Node n, boolean hor) {
    if (n == null || n.p == null) return;

    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.setPenRadius(.01);
    StdDraw.point(n.p.x(), n.p.y());

    StdDraw.setPenRadius();
    if (hor) { // blue (for horizontal splits)
      StdDraw.setPenColor(StdDraw.BLUE);
      StdDraw.line(n.lb.rect.xmin(), n.lb.rect.ymax(), n.lb.rect.xmax(),
          n.lb.rect.ymax());
    }
    else { // red (for vertical splits)
      StdDraw.setPenColor(StdDraw.RED);
      StdDraw.line(n.lb.rect.xmax(), n.lb.rect.ymin(), n.lb.rect.xmax(),
          n.lb.rect.ymax());
    }

    draw(n.lb, !hor);
    draw(n.rt, !hor);
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

    LinkedList<Point2D> q = new LinkedList<Point2D>();
    addInRange(rect, root, q);

    return q;
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param root2
   * @param q
   */
  private void addInRange(RectHV rect, Node n, LinkedList<Point2D> q) {
    if (n.p == null) return;

    if (rect.contains(n.p)) q.add(n.p);

    // pruning rule: if the query rectangle does not intersect the rectangle
    // corresponding to a node, there is no need to explore that node (or its
    // subtrees). A subtree is searched only if it might contain a point
    // contained in the query rectangle.
    if (n.rect.intersects(rect)) {
      addInRange(rect, n.lb, q);
      addInRange(rect, n.rt, q);
    }
    // TODO Instead of checking whether the query rectangle intersects the
    // rectangle corresponding to a node, it suffices to check only whether the
    // query rectangle intersects the splitting line segment: if it does, then
    // recursively search both subtrees; otherwise, recursively search the one
    // subtree where points intersecting the query rectangle could be.
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
    return nearest(root, p, null);
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param root2
   * @param p
   * @param dist
   * @return
   */
  private Point2D nearest(Node r, Point2D p, Point2D nearest) {
    // pruning rule: if the closest point discovered so far is closer than the
    // distance between the query point and the rectangle corresponding to a
    // node, there is no need to explore that node (or its subtrees). That is, a
    // node is searched only if it might contain a point that is closer than the
    // best one found so far. The effectiveness of the pruning rule depends on
    // quickly finding a nearby point.
    if (r.p == null) return nearest;

    if (nearest == null
        || r.p.distanceSquaredTo(p) < nearest.distanceSquaredTo(p)) {
      nearest = r.p;
    }

    double min = nearest.distanceSquaredTo(p);
    // TODO To do this, organize your recursive method so that when there are
    // two possible subtrees to go down, you always choose the subtree that is
    // on the same side of the splitting line as the query point as the first
    // subtree to explore—the closest point found while exploring the first
    // subtree may enable pruning of the second subtree.
    if (r.lb.rect.distanceSquaredTo(p) < min) {
      nearest = nearest(r.lb, p, nearest);
      min = nearest.distanceSquaredTo(p);
    }
    if (r.rt.rect.distanceSquaredTo(p) < min)
      nearest = nearest(r.rt, p, nearest);

    return nearest;

  }

  /**
   * unit testing of the methods (optional)
   *
   * @param args
   */
  public static void main(String[] args) {
    StdDraw.show(0);

    KdTree kdtree = new KdTree();
    Point2D p;
    if (args.length > 0) {

      String filename = args[0];
      In in = new In(filename);

      // initialize the data structures with N points from standard input
      while (!in.isEmpty()) {
        double x = in.readDouble();
        double y = in.readDouble();
        p = new Point2D(x, y);
        kdtree.insert(p);

      }
      kdtree.draw();
      StdDraw.show();
      return;
    }

    p = new Point2D(.7, .2);
    kdtree.insert(p);
    kdtree.draw();
    StdDraw.show();

    p = new Point2D(.5, .4);
    kdtree.insert(p);
    kdtree.draw();
    StdDraw.show();

    p = new Point2D(.2, .3);
    kdtree.insert(p);
    kdtree.draw();
    StdDraw.show();

    p = new Point2D(.4, .7);
    kdtree.insert(p);
    kdtree.draw();
    StdDraw.show();

    p = new Point2D(.9, .6);
    kdtree.insert(p);
    kdtree.draw();
    StdDraw.show();

  }
}
