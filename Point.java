/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point. Formally, if the two points are
     * (x0, y0) and (x1, y1), then the slope is (y1 - y0) / (x1 - x0). For completeness, the slope
     * is defined to be +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical; and Double.NEGATIVE_INFINITY if
     * (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        double slop;
        /* Degenerate lines */
        if (this.x == that.x && this.y == that.y) {
            return Double.NEGATIVE_INFINITY;
        }
        double dx = this.x - that.x;

        /* Vertical points*/
        if (dx == 0) {
            return Double.POSITIVE_INFINITY;
        }

        double dy = this.y - that.y;

        if (dy == 0) {
            return 0;
        }
        slop = dy / dx;
        return slop;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate. Formally, the invoking
     * point (x0, y0) is less than the argument point (x1, y1) if and only if either y0 < y1 or if
     * y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument point (x0 = x1 and y0 =
     * y1); a negative integer if this point is less than the argument point; and a positive integer
     * if this point is greater than the argument point
     */
    public int compareTo(Point that) {

        double dx = this.x - that.x;
        double dy = this.y - that.y;

        if (dy == 0) {
            if (dx == 0) {
                return 0;
            }

            if (dx > 0) {
                return 1;
            }
            else if (dx < 0) {
                return -1;

            }
        }
        // check for y cordinates
        if (dy > 0) {
            return 1;
        }
        else if (dy < 0) {
            return -1;
        }
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point. The slope is defined as in the
     * slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {

        // final Comparator<Point> SLOP_ORDER = new SlopOrder();
        class SlopOrder implements Comparator<Point> {
            public int compare(Point o1, Point o2) {

                double slop1 = Point.this.slopeTo(o1);
                double slop2 = Point.this.slopeTo(o2);
                if (slop1 > slop2) {
                    return 1;
                }
                else if (slop1 < slop2) {
                    return -1;
                }
                return 0;
            }
        }

        return new SlopOrder();
    }


    /**
     * Returns a string representation of this point. This method is provide for debugging; your
     * program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p = new Point(0, 0);
        Point p1 = new Point(0, 0);
        double slop = p.slopeTo(p1);
        StdOut.println("slop is:");
        StdOut.println(slop);
        StdOut.println("slop of horizontal line is:");
        p = new Point(1, 1);
        Point p2 = new Point(2, 1);
        slop = p.slopeTo(p2);
        StdOut.println(slop);

        StdOut.println("slop of vertical line is:");
        p = new Point(1, 1);
        Point p3 = new Point(1, 2);
        slop = p.slopeTo(p3);
        StdOut.println(slop);
    }
}
