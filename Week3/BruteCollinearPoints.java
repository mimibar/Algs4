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
  }

  /**
   * the number of line segments
   *
   * @return
   */
  public int numberOfSegments() {
    return 0;
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
    return null;
  }
}
