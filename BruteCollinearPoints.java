/* *****************************************************************************
 *  Name:    Alan Turing
 *  NetID:   aturing
 *  Precept: P00
 *
 *  Description:  Find the collinear points using Bruteforce
 *                Algorithmz
 *
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
    private int numSegments = 0;

    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
    {
        if (points == null) {
            throw new java.lang.IllegalArgumentException();
        }
        // check if any entry is null
        checkForNullCase(points.clone());

        // check if any entry is repeated
        if (isPointRepeat(points.clone())) {
            throw new java.lang.IllegalArgumentException();
        }


        int inputSize = points.length;

        // define line segment array
        lineSegments = new LineSegment[inputSize];

        // check pointsToCompare array have 3 or more adjacent points
        // which have equal slops wrt to origin
        findCollinearPoints(inputSize, points);

    }

    private void checkForNullCase(Point[] input) {
        for (Point p : input) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    // Bruteforce algorith to find collinearity
    private void findCollinearPoints(int inputSize, Point[] pointsToCompare) {
        Point p;
        Point q;
        Point r;
        Point s;

        Point[] collinearArray = new Point[4];

        for (int i = 0; i < inputSize - 3; i++) {
            p = pointsToCompare[i];

            for (int j = i + 1; j < inputSize - 2; j++) {
                q = pointsToCompare[j];

                for (int k = j + 1; k < inputSize - 1; k++) {
                    r = pointsToCompare[k];

                    for (int m = k + 1; m < inputSize; m++) {
                        s = pointsToCompare[m];

                        if (isCollinear(p, q, r, s)) {
                            collinearArray[0] = p;
                            collinearArray[1] = q;
                            collinearArray[2] = r;
                            collinearArray[3] = s;

                            // add to line segment
                            addToLineSegment(collinearArray);
                        }
                    }
                }
            }

        }

    }


    private boolean isCollinear(Point p, Point q, Point r, Point s) {
        double sq = p.slopeTo(q);
        double sr = p.slopeTo(r);
        double ss = p.slopeTo(s);

        // check for horizontal line
        if (sq == Double.POSITIVE_INFINITY && sr == Double.POSITIVE_INFINITY
                && ss == Double.POSITIVE_INFINITY) {
            return true;
        }

        if ((Double.compare(sq, sr) == 0) && (Double.compare(sq, ss) == 0)
                && (Double.compare(ss, sr) == 0)) {
            return true;
        }

        return false;
    }

    // method check if any point is repeating
    // return true for duplicate entry
    private boolean isPointRepeat(Point[] points) {
        Arrays.sort(points);
        Point previous = null;
        for (Point p : points) {
            if (previous != null) {
                if (p.compareTo(previous) == 0) {
                    return true;
                }
            }
            previous = p;
        }
        return false;
    }


    private void addToLineSegment(Point[] colArray) {
        // sort the points in their natural order

        Arrays.sort(colArray);

        Point startOfLine = colArray[0];
        Point endOfLine = colArray[3];

        // avoid duplicates by checking the origin is and end point
        // or not


        LineSegment line = new LineSegment(startOfLine, endOfLine);
        lineSegments[numSegments] = line;
        numSegments++;

    }

    public int numberOfSegments()        // the number of line segments
    {
        return numSegments;
    }

    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] filteredSegments = new LineSegment[numSegments];

        for (int i = 0; i < numSegments; i++) {
            filteredSegments[i] = lineSegments[i];
        }
        return filteredSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println(collinear.numberOfSegments());
    }

}
