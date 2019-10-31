import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int segNum;
    private ArrayList<LineSegment> segList = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
        if(points == null)
            throw new IllegalArgumentException("null point array!");
        int n = points.length;

        //check for null point
        for(int i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("found one null point!");
        }

        /*
        //check for repeated points
        for(int i = 0; i < n-1; i++){
            for(int j = i+1; j < n; j++)
                if(points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("found repeated points!");
        }*/

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

        //find segments
        for(int i = 0; i < n-3; i++){
            for(int j = i+1; j < n-2; j++){
                double s1 = pArray[i].slopeTo(pArray[j]);
                for(int k = j+1; k < n-1; k++){
                    double s2 = pArray[i].slopeTo(pArray[k]);
                    if(s1 != s2)
                        continue;;
                    for(int l = k+1; l < n; l++){
                        double s3 = pArray[i].slopeTo(pArray[l]);
                        if(s1 == s3){
                            //4 points on a line
                            segNum++;
                            segList.add(new LineSegment(pArray[i], pArray[l]));
                        }
                    }
                }
            }
        }
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
