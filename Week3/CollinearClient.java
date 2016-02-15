
/**
 * @name Miriam Lee
 * @date Feb 14, 2016
 * @purpose
 * @howto
 */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * This client program takes the name of an input file as a command-line
 * argument; read the input file (in the format specified below); prints to
 * standard output the line segments that your program discovers, one per line;
 * and draws to standard draw the line segments.
 *
 * @author mimi
 *
 */
public class CollinearClient {

  /**
   * @TODO Include a bold (or Javadoc) comment describing every method.
   * @param args
   */
  public static void main(String[] args) {

    // read the N points from a file
    In in = new In(args[0]);
    int N = in.readInt();
    Point[] points = new Point[N];
    // draw the points
    StdDraw.show();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.setPenColor(StdDraw.RED);
    StdDraw.setPenRadius(0.01);
    for (int i = 0; i < N; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
      points[i].draw();
//      StdDraw.textLeft(x + 300, y + 30, x + ",\n" + y);
    }

    StdDraw.show();
    StdDraw.setPenColor(StdDraw.BLUE);

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    StdOut.println("Line Segments");
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
//      segment.draw();
    }
    StdDraw.text(16384, 32038, args[0]);

    StdDraw.show();
    StdDraw.setPenColor(StdDraw.GREEN);

    // print and draw the line segments
    FastCollinearPoints fast = new FastCollinearPoints(points);
    for (LineSegment segment : fast.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.text(16384, 32038, args[0]);

  }

}
