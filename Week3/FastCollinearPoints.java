
/**
 * @name Miriam Lee
 * @date Feb 14, 2016
 * @purpose
 * @howto
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author mimi
 *
 */
public class FastCollinearPoints {
  private ArrayList<LineSegment> segments;
  /**
   * Line Segments to avoid duplicates
   *
   * <endpoint index, slope>
   */
  private HashMap<Point, ArrayList<Double>> ls;

  /**
   * finds all line segments containing 4 or more points
   *
   * @param points
   * @throws NullPointerException
   *           either the argument to the constructor is null or if any point in
   *           the array is null.
   * @throws IllegalArgumentException
   *           if the argument to the constructor contains a repeated point.
   */
  public FastCollinearPoints(Point[] points) {
    segments = new ArrayList<LineSegment>();

    if (points == null) throw new NullPointerException();
    // Test 14: Check that data type does not mutate the constructor argument
    final Point[] p = points.clone();

    Arrays.sort(p); // will throw exception if nulls
    Point[] s = null;
    ls = new HashMap<Point, ArrayList<Double>>();

    int b = -1;
    int count = 0;

    assertNotNull(p[0]);
    if (p.length > 1) assertNotNull(p[1]);

    // line segment containing 4 (or more) points
    // Test 17: Check that the constructor throws an exception if duplicate
    // points
    for (int i = 0; i < p.length; i++) {

      if (p.length > 1 && i < p.length - 1) {
        s = p.clone();
        // Sort the points according to the slopes they makes with p.
        b = i + 1;
        Arrays.sort(s, b, p.length, p[i].slopeOrder());
        assertNotRepeated(p, i, b); // possible bug here...

        count = 2;
      }
      for (int j = i + 2; j < p.length; j++) {
        assertNotNull(p[j]);
        assertNotRepeated(p, i, j);
        // same slope to i, meaning same line segment, collinear
        if (p[i].slopeTo(s[b]) == p[i].slopeTo(s[j])) {

          if (s[j].compareTo(s[b]) > 0) b = j;
          count++;
        }
        else {
          if (count > 3) addSegment(s, i, b);
          b = j;
          count = 2;
        }
      }
      if (count > 3) {
        addSegment(s, i, b);
        count = 0;
      }
    }
  }

  /**
   * Try only adding a segment if the point your sorting with respect to is the
   * minimum point on the line (remember we have two sort methods).
   */
  private void addSegment(Point[] p, int a, int b) {
    double s = p[a].slopeTo(p[b]);

    if (!(ls.containsKey(p[b]) && ls.get(p[b]).contains(s))) {
      segments.add(new LineSegment(p[a], p[b]));

      if (!ls.containsKey(p[b])) {
        ls.put(p[b], new ArrayList<Double>(Arrays.asList(s)));
      }
      else {
        ls.get(p[b]).add(s);
      }

    }
  }

  /**
   * @param points
   * @param a
   * @param b
   * @throws IllegalArgumentException
   *           if the array contains a repeated point in index b.
   */
  private void assertNotRepeated(Point[] p, int a, int b) {
    if (p[a].compareTo(p[b]) == 0) throw new IllegalArgumentException();
  }

  /**
   * the number of line segments
   *
   * @return
   */
  public int numberOfSegments() {
    return segments.size();
  }

  /**
   * the line segments. The method segments() should include each maximal line
   * segment containing 4 (or more) points exactly once. For example, if 5
   * points appear on a line segment in the order p→q→r→s→t, then do not include
   * the subsegments p→s or q→t.
   *
   * @throws NullPointerException
   *           either the argument to the constructor is null or if any point in
   *           the array is null
   * @throws a
   *           IllegalArgumentException if the argument to the constructor
   *           contains a repeated point.
   *
   * @PerformanceRequirement The order of growth of the running time of your
   *                         program should be N2 log N in the worst case and it
   *                         should use space proportional to N plus the number
   *                         of line segments returned. FastCollinearPoints
   *                         should work properly even if the input has 5 or
   *                         more collinear points.
   *
   * @return
   */
  public LineSegment[] segments() {
    return (LineSegment[]) segments.toArray(new LineSegment[segments.size()]);
  }

  /**
   * @param point
   * @throws NullPointerException
   *           if null
   */
  private void assertNotNull(Point point) {
    if (point == null) throw new NullPointerException();
  }
}
