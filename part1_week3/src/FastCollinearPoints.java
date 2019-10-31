import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private int segNum;
    private ArrayList<LineSegment> segList = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points){
        if(points == null)
            throw new IllegalArgumentException("null point array!");
        int n = points.length;

        //check for null point
        for(int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("found one null point!");
        }

        //sort the point array
        Point[] pArray = new Point[n];
        for(int i = 0; i < n; i++)
            pArray[i] = points[i];
        Arrays.sort(pArray);

        //check for repeated points
        //n^2 to n! for sorted array
        for(int i = 0; i < n-1; i++){
            if(pArray[i].compareTo(pArray[i+1]) == 0)
                throw new IllegalArgumentException("found repeated points!");
        }

        segNum = 0;

        for(int i = 0; i < n-3; i++){
            double[] slopeListTmp = new double[i];
            Point[] pointListTmp = new Point[n-i-1];

            //copy the rest of the points
            for(int j = 0; j < i; j++)
                slopeListTmp[j] = pArray[i].slopeTo(pArray[j]);

            Arrays.sort(slopeListTmp);

            for(int j = i+1; j < n; j++)
                pointListTmp[j-i-1] = pArray[j];

            // sort according to the slope to pArray[i]
            Arrays.sort(pointListTmp, pArray[i].slopeOrder());

            // find points on a line
            int count = 1;
            double currentSlope, lastSlope;

            lastSlope = pArray[i].slopeTo(pointListTmp[0]);
            for(int j = 1; j < pointListTmp.length; j++){
                currentSlope = pArray[i].slopeTo(pointListTmp[j]);
                if(currentSlope == lastSlope)
                    count++;
                else{
                    if(count >= 3 && !subSegment(lastSlope, slopeListTmp)) {
                        segNum++;
                        segList.add(new LineSegment(pArray[i], pointListTmp[j - 1]));
                    }
                    count = 1;
                }
                lastSlope = currentSlope;
            }
            if(count >= 3 && !subSegment(lastSlope, slopeListTmp)){
                segNum++;
                segList.add(new LineSegment(pArray[i], pointListTmp[pointListTmp.length-1]));
            }

        }
    }

    private boolean subSegment(double s, double[] sList){
        int lo = 0;
        int hi = sList.length-1;
        int mi = (lo + hi) / 2;
        while(lo <= hi){
            if(s > sList[mi]) lo = mi+1;
            else if(s < sList[mi]) hi = mi-1;
            else return true;
            mi = (lo + hi) / 2;
        }
        return false;
    }

    // the number of line segments
    public int numberOfSegments(){
        return segNum;
    }

    // the line segments
    public LineSegment[] segments(){
        LineSegment[] l = new LineSegment[segNum];
        for(int i = 0; i < segNum; i++)
            l[i] = segList.get(i);
        return l;
    }

    public static void main(String[] args){
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
        }
        StdDraw.show();
    }
}