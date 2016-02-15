import java.util.ArrayList;
import java.util.Arrays;

/**
 * @name Miriam Lee
 * @date Feb 14, 2016
 * @purpose
 * @howto
 */

/**
 * @PerformanceRequirement. The order of growth of the running time of your
 * program should be N4 in the worst case and it should use space proportional
 * to N plus the number of line segments returned. *
 *
 * @author mimi
 *
 */
public class BruteCollinearPoints {
  private ArrayList<LineSegment> segments;

  /**
   * finds all line segments containing 4 points
   *
   * @param points
   * @throws NullPointerException
   *           either the argument to the constructor is null or if any point in
   *           the array is null
   * @throws IllegalArgumentException
   *           if the argument to the constructor contains a repeated point.
   */
  public BruteCollinearPoints(Point[] points) {
    if (points == null) throw new NullPointerException();
    Arrays.sort(points);

    segments = new ArrayList<LineSegment>();

    for (int i = 0; i < points.length; i++) {
      isValid(points, i); // Test 14: Check that the constructor throws an
                          // exception if duplicate points

      for (int j = i + 1; j < points.length - 2; j++) {

        for (int k = j + 1; k < points.length - 1; k++) {
          if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {

            for (int l = k + 1; l < points.length; l++) {
              if (points[i].slopeTo(points[j]) == points[i]
                  .slopeTo(points[l])) {
                segments.add(new LineSegment(points[i], points[l]));
              }

            }
          }
        }
      }
    }
  }

  /**
   * Checks if not null and if not teh same as the previous
   *
   * @param points
   * @param i
   * @throws IllegalArgumentException
   *           if repeated
   */
  private void isValid(Point[] points, int i) {

    assertNotNull(points[i]);
    // Test 14: Check that the constructor throws an exception if duplicate
    // points
    if (i > 0 && points[i].compareTo(points[i - 1]) == 0)
      throw new IllegalArgumentException();
  }

  /**
   * @param point
   * @throws NullPointerException
   *           if null
   */
  private void assertNotNull(Point point) {
    if (point == null) throw new NullPointerException();
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
   * the line segments. The method segments() should include each line segment
   * containing 4 points exactly once. If 4 points appear on a line segment in
   * the order p→q→r→s, then you should include either the line segment p→s or
   * s→p (but not both) and you should not include subsegments such as p→r or
   * q→r.
   *
   * @return
   */
  public LineSegment[] segments() {
    return (LineSegment[]) segments
        .toArray(new LineSegment[numberOfSegments()]);
  }
}
