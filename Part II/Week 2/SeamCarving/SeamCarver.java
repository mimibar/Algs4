/**
 * @name Miriam Lee
 * @date May 14, 2016
 * @purpose Create a data type that resizes a W-by-H image using the
 *          seam-carving technique
 * @howto
 */
import java.awt.Color;

import edu.princeton.cs.algs4.Picture;

/**
 * mutable data type
 *
 * @author mimi
 *
 */
public class SeamCarver {
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
  }

  /**
   * current picture
   *
   * @return
   */
  public Picture picture() {
  }

  /**
   * width of current picture
   *
   * @return
   * @performance should take constant time in the worst case
   */
  public int width() {
  }

  /**
   * height of current picture
   *
   * @return
   * @performance should take constant time in the worst case
   */
  public int height() {
  }

  /**
   * energy of pixel at column x and row y
   *
   * @param x
   * @param y
   * @return
   * @throws IndexOutOfBoundsException
   *           if either x or y is outside its prescribed range: By convention,
   *           the indices x and y are integers between 0 and W − 1 and between
   *           0 and H − 1 respectively, where W is the width of the current
   *           image and H is the height.
   * @performance should take constant time in the worst case
   */
  public double energy(int x, int y) {
  }

  /**
   * sequence of indices for horizontal seam
   *
   * @return
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public int[] findHorizontalSeam() {
  }

  /**
   * sequence of indices for vertical seam
   *
   * @return
   * @performance should run in time at most proportional to W H in the worst
   *              case
   */
  public int[] findVerticalSeam() {
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
  }
}
