/**
 * @name Miriam Lee
 * @date May 14, 2016
 * @purpose Create a data type that resizes a W-by-H image using the
 *          seam-carving technique
 * @howto
 */
import java.awt.Color;
import java.util.Arrays;

import edu.princeton.cs.algs4.Picture;

/**
 * mutable data type
 *
 * @author mimi
 *
 */
public class SeamCarver {
  private Picture pic;
  private Color[][] color;
  private int H, W;
  /**
   * [width][height]= [x][y]
   */
  private double[][] energy;
  private boolean transposed;

  /**
   * create a seam carver object based on the given picture
   *
   * @param picture
   *          The data type may not mutate the Picture argument to the
   *          constructor.
   *
   * @throws NullPointerException
   *           if is called with a null argument.
   */
  public SeamCarver(Picture picture) {
    isNotNull(picture);
    pic = new Picture(picture);
    H = pic.height();
    W = pic.width();
    // We already use an array to store the colors
    color = new Color[H][W];
    for (int x = 0; x < W; x++) {
      for (int y = 0; y < H; y++) {
        color[y][x] = pic.get(x, y);
      }
    }

    setEnergies();

  }

  /**
   * and another array to store the energy levels of the pixels. construct a 2d
   * energy array using the energy() method that you have already written.
   *
   */
  private void setEnergies() {

    energy = new double[H][W];
    for (int x = 0; x < W; x++) {
      for (int y = 0; y < H; y++) {
        energy[y][x] = energyt(x, y);
      }
    }
  }

  /**
   * @param o
   * @throws NullPointerException
   *           if is called with a null argument.
   */
  private void isNotNull(Object o) {
    if (o == null) throw new NullPointerException();
  }

  /**
   * current picture
   *
   * @return
   */
  public Picture picture() {
    if (transposed) {
      // transpose the image
      color = transposeColor(color);
      setEnergies(); // recalculate energies
      // energy = transposeColor(energy);
      transposed = false;
    }
    // TODO There is no need to create a new Picture object after removing a
    // seam —instead, you can maintain the color information in a 2D array of
    // integers. That is, you can defer creating a Picture object until required
    // to do (when the client calls picture()). Since Picture objects are
    // relatively expensive, this will speed things up.
    Picture p = new Picture(W, H);
    for (int x = 0; x < W; x++)
      for (int y = 0; y < H; y++)
        p.set(x, y, color[y][x]);
    pic = p;
    return pic;
  }

  /**
   *
   *
   * @return width of current picture
   * @performance should take constant time in the worst case
   */
  public int width() {
    return W;
  }

  /**
   *
   * @return height of current picture
   * @performance should take constant time in the worst case
   */
  public int height() {
    return H;
  }

  /**
   *
   *
   * @param x
   * @param y
   * @return energy of pixel at column x and row y
   * @throws IndexOutOfBoundsException
   *           if either x or y is outside its prescribed range: By convention,
   *           the indices x and y are integers between 0 and W − 1 and between
   *           0 and H − 1 respectively, where W is the width of the current
   *           image and H is the height.
   * @performance should take constant time in the worst case
   */
  public double energy(int x, int y) {
    if (x < 0 || x >= W || y < 0 || y >= H) throw new IndexOutOfBoundsException();

    if (transposed) color = transposeColor(color);
    return energyt(x, y);

  }

  private double energyt(int x, int y) {
    if (x < 0 || x >= W || y < 0 || y >= H) throw new IndexOutOfBoundsException();

    if (x > 0 && x < W - 1 && y > 0 && y < H - 1) {
      // neg, pos
      Color n, p;
      int r, g, b;
      double e = 0.0;
      // x-gradient
      n = color[y][x - 1]; // left
      p = color[y][x + 1]; // right
      r = n.getRed() - p.getRed();
      g = n.getGreen() - p.getGreen();
      b = n.getBlue() - p.getBlue();
      e = (double) (r * r + g * g + b * b);

      // y-gradient
      n = color[y - 1][x]; // up
      p = color[y + 1][x]; // down
      r = n.getRed() - p.getRed();
      g = n.getGreen() - p.getGreen();
      b = n.getBlue() - p.getBlue();
      e += (double) (r * r + g * g + b * b);

      return Math.sqrt(e);
    }
    // We define the energy of a pixel at the border of the image to be
    // 1000, so that it is strictly larger than the energy of any interior
    // pixel.
    return 1000;

  }

  /**
   * sequence of indices for horizontal seam
   *
   * @return
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public int[] findHorizontalSeam() {
    // TODO Don't explicitly transpose the Picture until you need to do so. For
    // example, if you perform a sequence of 50 consecutive horizontal seam
    // removals, you should need only two transposes (not 100).
    if (!transposed) {
      // transpose the image
      color = transposeColor(color);
      setEnergies(); // recalculate energies
      // energy = transposeColor(energy);

    }

    // call findVerticalSeam(),
    return findVerticalSeam();
    // TODO and transpose it back.
  }

  /**
   * @return
   * @see {@link http://introcs.cs.princeton.edu/java/14array/Transpose.java}
   */
  private Color[][] transposeColor(Color[][] a) {

    Color[][] t = new Color[a[0].length][a.length];
    for (int i = 0; i < a.length; i++)
      for (int j = 0; j < a[0].length; j++)
        t[j][i] = a[i][j];
    W = a[0].length;
    H = a.length;
    transposed = !transposed;
    return t;
  }

  /**
   * sequence of indices for vertical seam
   *
   * @return
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public int[] findVerticalSeam() {
    // Your algorithm can traverse the 2d energy array/matrix treating some
    // select entries as reachable from (x, y) to calculate where the seam is
    // located.

    // We can use yet a third array to store the cumulative weights of these
    // pixels
    double[][] weight = new double[W][H];
    double min = Integer.MAX_VALUE;
    int seamX = 0, seamY = 0;
    int[][] pathTo = new int[W][H];

    // Because the pixel relationships follow a regular pattern, instead of
    // considering how each pixel points to three pixels in the row below, it is
    // more efficient to consider how each pixel is pointed to by three pixels
    // in the row above.
    for (int y = 0; y < H; y++) {
      for (int x = 0; x < W; x++) {
        // So, for the weight at pixel position (x, y), we simply
        // choose the smallest weight of pixels at (x-1, y-1), (x, y-1), and
        // (x+1, y-1) and add it to the energy at position (x, y). In addition,
        // record this choice in the pathTo array. Do this for all pixels in
        // topological order, being careful to handle corner conditions at the
        // borders.
        double l = Integer.MAX_VALUE, r = Integer.MAX_VALUE, t = Integer.MAX_VALUE;

        if (y > 0) {
          if (x > 0) // not left corner
            l = weight[x - 1][y - 1];
          if (x < W - 1)// not right corner
            r = weight[x + 1][y - 1];
          // not top corner
          t = weight[x][y - 1];
        }
        else {
          l = 0;
          r = 0;
          t = 0;
        }

        if (l <= r) {
          if (l <= t)
            pathTo[x][y] = x - 1; // l
          else if (t < Integer.MAX_VALUE) pathTo[x][y] = x; // t
        }
        else {
          if (r < t)
            pathTo[x][y] = x + 1; // r
          else if (t < Integer.MAX_VALUE) pathTo[x][y] = x; // t
        }
        weight[x][y] = energy[y][x] + Math.min(l, Math.min(r, t));

        if (y == H - 1 && weight[x][y] < min) {
          min = weight[x][y];
          seamX = x;
        }
      }

    }
    int[] vSeam = new int[H];
    // and a fourth array to store the paths along which these values are
    // computed. Following the topological orderings of the pixels, we can
    // compute one row of data from the row of data above it. Progressing in
    // this manner, we can process the entire picture efficiently in one single
    // pass.
    seamY = H - 1;
    vSeam[seamY] = seamX;
    while (seamY > 0) {
      seamX = pathTo[seamX][seamY];
      seamY--;
      vSeam[seamY] = seamX;
    }
    return vSeam;
  }

  /**
   * remove horizontal seam from current picture
   *
   * @param seam
   * @throws NullPointerException
   *           if is called with a null argument.
   * @throws IllegalArgumentException
   *           if is called with an array of the wrong length or if the array is
   *           not a valid seam (i.e., either an entry is outside its prescribed
   *           range or two adjacent entries differ by more than 1).
   * @throws IllegalArgumentException
   *           if is called when the height of the picture is less than or equal
   *           to 1.
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public void removeHorizontalSeam(int[] seam) {
  }

  /**
   * remove vertical seam from current picture
   *
   * @param seam
   * @throws NullPointerException
   *           if is called with a null argument.
   * @throws IllegalArgumentException
   *           if is called with an array of the wrong length or if the array is
   *           not a valid seam (i.e., either an entry is outside its prescribed
   *           range or two adjacent entries differ by more than 1).
   * @throws IllegalArgumentException
   *           if is called when the width of the picture is less than or equal
   *           to 1
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public void removeVerticalSeam(int[] seam) {
    if (seam == null) throw new NullPointerException();

    if (transposed) color = transposeColor(color);

    if (seam.length != H) throw new IllegalArgumentException();
    if (W <= 1) throw new IllegalArgumentException();
    // Consider using System.arraycopy() to shift elements within an array.
    // Reuse the energy array and shift array elements to plug the holes left
    // from the seam that was just removed. You will need to recalculate the
    // energies for the pixels along the seam that was just removed, but no
    // other energies will change.
    Color[][] c2 = new Color[color.length][color[0].length - 1]; // yx
    double[][] e2 = new double[energy.length][energy[0].length - 1];

    for (int y = 0; y < H; y++) {
      if (seam[y] < 0 || seam[y] >= W) throw new IllegalArgumentException();
      if (y > 0 && seam[y] - seam[y - 1] > 1) throw new IllegalArgumentException();
      if (y < H - 1 && seam[y + 1] - seam[y] > 1)
        throw new IllegalArgumentException();

      System.arraycopy(color[y], 0, c2[y], 0, seam[y]);
      System.arraycopy(color[y], seam[y] + 1, c2[y], seam[y], W - seam[y] - 1);

      System.arraycopy(energy[y], 0, e2[y], 0, seam[y]);
      System.arraycopy(energy[y], seam[y] + 1, e2[y], seam[y], W - seam[y] - 1);

      if (seam[y] < color[0].length - 1) e2[y][seam[y]] = energyt(seam[y], y);
    }
    color = c2;
    energy = e2;

    W--;
  }

  public static void main(String[] args) {

  }
}
