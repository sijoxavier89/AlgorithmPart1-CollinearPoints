/* *****************************************************************************
 *  Name: Sijo Xavier
 *  Date: 07-Dec-2019
 *  Description: Fast alogorith to find collinear poins
 *  from the given set of cordinates
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int numSegments = 0;

    public FastCollinearPoints(
            Point[] points)     // finds all line segments containing 4 or more points
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
        Point origin;
        Point[] pointsToCompare = new Point[inputSize - 1];
        int indexOfOrigin;

        // iterate for all the elements
        for (int i = 0; i < inputSize; i++) {
            origin = points[i];
            indexOfOrigin = i;

            // Create new array of points except origin point
            int indexOfPtoC = 0;
            for (int j = 0; j < inputSize; j++) {
                if (j != indexOfOrigin) {

                    pointsToCompare[indexOfPtoC] = points[j];
                    indexOfPtoC++;
                }
            }

            // sort based on the slop order
            Arrays.sort(pointsToCompare, origin.slopeOrder());

            // check pointsToCompare array have 3 or adjacent points
            // which have equal slops wrt to origin
            findCollinearPoints(pointsToCompare, origin);

        }

    }

    // check for null entry
    private void checkForNullCase(Point[] input) {
        for (Point p : input) {
            if (p == null) {
                throw new java.lang.IllegalArgumentException();
            }
        }
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


    //  check wether the given array have
    // collinear points
    private void findCollinearPoints(Point[] pointsToCompare, Point origin) {

        int arraySize = pointsToCompare.length;
        int startOfCollinear = 0;
        int endOfCollinear = 0;
        int numCollinears = 1;

        for (int k = 0; k < arraySize - 1; k++) {

            if (origin.slopeTo(pointsToCompare[k]) == origin.slopeTo(pointsToCompare[k + 1])) {
                numCollinears++;
                endOfCollinear = k + 1;
            }
            else {
                if (numCollinears >= 3) {
                    endOfCollinear = k;

                    addPointsToLineSegment(pointsToCompare, startOfCollinear, endOfCollinear,
                                           origin);
                }
                numCollinears = 1;
                startOfCollinear = k + 1;
            }

            // if lines are collinear till the last index
            if (k + 1 == arraySize - 1) {
                if (numCollinears >= 3) {
                    addPointsToLineSegment(pointsToCompare, startOfCollinear, endOfCollinear,
                                           origin);
                }
            }
        }

    }

    // add collinear points to line segment array
    // method sort the collinear array to find end points
    private void addPointsToLineSegment(Point[] pointsToCompare, int start, int end, Point origin) {

        // form new collinear array
        int arraySize = (end - start) + 2;
        Point[] collinearArray = new Point[arraySize];

        int collinearIndex = 0;
        for (int i = start; i <= end; i++) {
            collinearArray[collinearIndex] = pointsToCompare[i];
            collinearIndex++;
        }

        // add origin to the collinear array
        collinearArray[arraySize - 1] = origin;

        // sort array in natural order
        Arrays.sort(collinearArray);

        Point startOfLine = collinearArray[0];
        Point endOfLine = collinearArray[arraySize - 1];

        // avoid duplicates by checking the origin is and end point
        // or not
        if (origin.compareTo(startOfLine) == 0) {
            LineSegment line = new LineSegment(startOfLine, endOfLine);
            lineSegments[numSegments] = line;
            numSegments++;
        }
    }

    public int numberOfSegments()        // the number of line segments
    {
        return numSegments;
    }

    public LineSegment[] segments() {

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();

            StdDraw.show();
        }


    }
}
