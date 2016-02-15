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

    int a, b, count;

    // line segment containing 4 (or more) points
    for (int i = 0; i < points.length - 3; i++) {
      assertNotNull(points[i]);

      // Sort the points according to the slopes they makes with p.
      Arrays.sort(points, i + 1, points.length, points[i].slopeOrder());
      assertNotRepeated(points, i, i + 1); // possible bug here...

      if (points[i].compareTo(points[i + 1]) > 0) {
        a = i + 1;
        b = i;
      }
      else {
        a = i;
        b = i + 1;
      }

      count = 2;

      for (int j = i + 2; j < points.length; j++) {
        assertNotRepeated(points, i, j);
        // same slope meaning same line segment, collinear
        if (points[a].slopeTo(points[b]) == points[a].slopeTo(points[j])) {
          // store points not indices
          if (points[j].compareTo(points[b]) > 0)
            b = j;
          else if (points[j].compareTo(points[a]) < 0) a = j;
          count++;
        }
        else {
          if (count > 3) {
            segments.add(new LineSegment(points[a], points[b]));
          }
          if (points[i].compareTo(points[j]) > 0) {
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
        segments.add(new LineSegment(points[a], points[b]));
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
