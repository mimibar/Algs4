import java.util.ArrayList;
import java.util.Arrays;

/**
 * @name Miriam Lee
 * @date Feb 14, 2016
 * @purpose
 * @howto
 */

/**
 * @author mimi
 *
 */
public class FastCollinearPoints {
  private ArrayList<LineSegment> segments;

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

    Point[] p = points.clone(); // Test 14: Check that data type does not mutate
                                // the constructor argument

    int a, b, count;

    // line segment containing 4 (or more) points
    for (int i = 0; i < p.length - 3; i++) {
      assertNotNull(p[i]);

      // Sort the points according to the slopes they makes with p.
      Arrays.sort(p, i + 1, p.length, p[i].slopeOrder());
      assertNotRepeated(p, i, i + 1); // possible bug here...

      if (p[i].compareTo(p[i + 1]) > 0) {
        a = i + 1;
        b = i;
      }
      else {
        a = i;
        b = i + 1;
      }
      count = 2;

      for (int j = i + 2; j < p.length; j++) {
        assertNotRepeated(p, i, j);
        // same slope meaning same line segment, collinear
        if (p[a].slopeTo(p[b]) == p[a].slopeTo(p[j])) {
          // store points not indices
          if (p[j].compareTo(p[b]) > 0)
            b = j;
          else if (p[j].compareTo(p[a]) < 0) a = j;
          count++;
        }
        else {
          if (count > 3) {
            segments.add(new LineSegment(p[a], p[b]));
          }
          if (points[i].compareTo(points[j]) > 0) {
          if (p[i].compareTo(p[j]) > 0) {
            a = j;
            b = i;
          }
          else {
            a = i;
            b = j;
          }
          count = 2;
        }
      }
      if (count > 3) {
        // Try only adding a segment if the point your sorting with respect to
        // is the minimum point on the line (remember we have two sort methods).
        segments.add(new LineSegment(p[a], p[b]));
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
