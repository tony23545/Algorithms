import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Iterator;

public class PointSET {
    private TreeSet<Point2D> tree;

    // construct an empty set of points
    public PointSET(){
        tree = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty(){
        return tree.isEmpty();
    }

    // number of points in the set
    public int size(){
        return tree.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        tree.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        return tree.contains(p);
    }

    // draw all points to standard draw
    public void draw(){
        for(Iterator<Point2D> it = tree.iterator(); it.hasNext();){
            it.next().draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)
            throw new IllegalArgumentException("null input!");
        if(tree.isEmpty())
            return null;
        ArrayList<Point2D> rangeList = new ArrayList<>();
        for(Iterator<Point2D> it = tree.iterator(); it.hasNext();){
            Point2D tmp = it.next();
            if(rect.xmin() <= tmp.x() && rect.xmax() >= tmp.x() && rect.ymin() <= tmp.y() && rect.ymax() >= tmp.y())
                rangeList.add(tmp);
        }
        return rangeList;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        if(tree.isEmpty())
            return null;
        Point2D nearest = tree.first();
        double nearestDistance = nearest.distanceTo(p);
        for(Iterator<Point2D> it = tree.iterator(); it.hasNext();){
            Point2D tmp = it.next();
            double d = tmp.distanceTo(p);
            if(d < nearestDistance){
                nearestDistance = d;
                nearest = tmp;
            }
        }
        return nearest;
    }

    public static void main(String[] args){
        PointSET s = new PointSET();
        s.insert(new Point2D(0.7, 0.6));
        s.insert(new Point2D(0.4, 0.2));
        s.insert(new Point2D(0.5, 0.5));
        StdOut.println(s.tree.first().toString());
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        s.draw();
        StdDraw.show();
        Point2D nearest = s.nearest(new Point2D(1, 1));
        StdOut.println(nearest.toString());
        StdOut.println(s.tree.first().toString());
        //StdDraw.show();
    }
}