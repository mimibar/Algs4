import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
    Comparator<Point> so;

    for (int i = 0; i < points.length - 3; i++) {
      assertNotNull(points[i]);
      so = points[i].slopeOrder();
      for (int j = i + 1; j < points.length - 2; j++) {
        isValid(points, j);

        for (int k = j + 1; k < points.length - 1; k++) {
          isValid(points, k);

          // if (so.compare(points[j], points[k]) == 0) {
          if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) {

            for (int l = k + 1; l < points.length; l++) {
              isValid(points, l);

              // if (so.compare(points[k], points[l]) == 0) {
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
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param points
   * @param k
   */
  private void isValid(Point[] points, int i) {
    assertNotNull(points[i]);
    if (points[i].compareTo(points[i - 1]) == 0)
      throw new IllegalArgumentException();
  }

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param point
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
