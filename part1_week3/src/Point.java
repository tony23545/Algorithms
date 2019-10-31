import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    // constructor
    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    // draws this point
    public void draw(){
        StdDraw.point(x, y);
    }

    // draws the line segment from this point to that point
    public void drawTo(Point that){
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // string representation
    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    // compare two points by y-coordinates, breaking ties by x-coordinates
    public int compareTo(Point that){
        if(this.y > that.y)
            return 1;
        else if(this.y == that.y){
            if(this.x > that.x)
                return 1;
            else if(this.x == that.x)
                return 0;
            else
                return -1;
        }
        else
            return -1;
    }

    // the slope between this point and that point
    public double slopeTo(Point that){
        if(this.x == that.x){
            if(this.y == that.y)
                // degenerate line
                return Double.NEGATIVE_INFINITY;
            else
                // vertical line
                return Double.POSITIVE_INFINITY;
        }
        else{
            if(this.y == that.y)
                return 0;
            else
                return 1.0 * (that.y - this.y) / (that.x - this.x);
        }
    }

    private class slopeOrderCom implements Comparator<Point>{
        public int compare(Point P1, Point P2){
            double s1 = slopeTo(P1);
            double s2 = slopeTo(P2);
            if(s1 > s2)
                return 1;
            else if(s1 == s2)
                return 0;
            else
                return -1;
        }
    }

    // compare two points by slopes they make with this point
    public Comparator<Point> slopeOrder(){
        return new slopeOrderCom();
    }

    public static void main(String[] args){
        Point p1 = new Point(1234, 5678);
        Point p2 = new Point(14000, 10000);
        Point p3 = new Point(18000, 10000);
        double s1 = p1.slopeTo(p2);
        double s2 = p1.slopeTo(p3);
        System.out.println(s1);
        System.out.println(s2);
    }
}