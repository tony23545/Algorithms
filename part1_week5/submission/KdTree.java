import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private Node root;

    private class Node{
        private Point2D point;
        private boolean use_x;
        private Node right, left;
        private int count;
        RectHV rect;

        public Node(Point2D p, boolean ux, RectHV r){
            point = p;
            use_x = ux;
            count = 1;
            rect = r;
        }

        public boolean use_x(){
            return use_x;
        }
    }

    // construct an empty set of points
    public KdTree(){
        root = null;
    }

    // is the set empty?
    public boolean isEmpty(){
        return root == null;
    }

    // number of points in the set
    public int size(){
        if(isEmpty())
            return 0;
        else
            return root.count;
    }

    public void insert(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        root = insert(root, p);
    }

    private Node insert(Node r, Point2D p){
        if(r == null)
            return new Node(p, true, new RectHV(0.0, 0.0, 1.0, 1.1));
        if(r.point.equals(p))
            return r;

        if(r.use_x()){
            if(r.point.x() > p.x()){
                if(r.left == null)
                    r.left = new Node(p, !r.use_x(), new RectHV(r.rect.xmin(), r.rect.ymin(), r.point.x(), r.rect.ymax()));
                else r.left = insert(r.left, p);
            }else{
                if(r.right == null)
                    r.right = new Node(p, !r.use_x(), new RectHV(r.point.x(), r.rect.ymin(), r.rect.xmax(), r.rect.ymax()));
                else r.right = insert(r.right, p);
            }
        }else{
            if(r.point.y() > p.y()){
                if(r.left == null)
                    r.left = new Node(p, !r.use_x(), new RectHV(r.rect.xmin(), r.rect.ymin(), r.rect.xmax(), r.point.y()));
                else r.left = insert(r.left, p);
            }else{
                if(r.right == null)
                    r.right = new Node(p, !r.use_x(), new RectHV(r.rect.xmin(), r.point.y(), r.rect.xmax(), r.rect.ymax()));
                else r.right = insert(r.right, p);
            }
        }
        r.count = 1 + size(r.left) + size(r.right);
        return r;
    }



    private int size(Node r){
        if(r == null) return 0;
        else return r.count;
    }

    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        return contains(root, p);
    }

    private boolean contains(Node r, Point2D p){
        if(r == null)
            return false;

        if(r.point.equals(p))
            return true;

        if(r.use_x){
            if(r.point.x() > p.x()) return contains(r.left, p);
            else return contains(r.right, p);
        }
        else{
            if(r.point.y() > p.y()) return contains(r.left, p);
            else return contains(r.right, p);
        }
    }

    // draw all points to standard draw
    public void draw(){
        draw(root);
    }

    private void draw(Node r){
        if(r == null)
            return;
        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.BLACK);
        r.point.draw();
        if(r.use_x){
            StdDraw.setPenRadius(0.002);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(r.point.x(), r.rect.ymin(), r.point.x(), r.rect.ymax());
        }
        else{
            StdDraw.setPenRadius(0.002);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(r.rect.xmin(), r.point.y(), r.rect.xmax(), r.point.y());
        }
        draw(r.left);
        draw(r.right);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null)
            throw new IllegalArgumentException("null input!");
        if(root == null)
            return new ArrayList<Point2D>();
        else
            return range(root, rect);
    }

    private ArrayList<Point2D> range(Node r, RectHV rect){
        ArrayList<Point2D> points = new ArrayList<>();
        if(r == null || !r.rect.intersects(rect))
            return points;
        if(rect.contains(r.point))
            points.add(r.point);
        points.addAll(range(r.left, rect));
        points.addAll(range(r.right, rect));
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p == null)
            throw new IllegalArgumentException("null input!");
        if(root == null)
            return null;
        Point2D nearestP = root.point;
        return nearest(root, p, nearestP);
    }

    private Point2D nearest(Node r, Point2D p, Point2D currentNearest){
        double currentDis = currentNearest.distanceTo(p);
        if(r == null || r.rect.distanceTo(p) >= currentDis)
            return currentNearest;

        currentNearest = nearer(p, currentNearest, r.point);

        if(r.use_x()){
            if(p.x() < r.point.x()){
                currentNearest = nearer(p, currentNearest, nearest(r.left, p, currentNearest));
                currentNearest = nearer(p, currentNearest, nearest(r.right, p, currentNearest));
            }else{
                currentNearest = nearer(p, currentNearest, nearest(r.right, p, currentNearest));
                currentNearest = nearer(p, currentNearest, nearest(r.left, p, currentNearest));
            }
        }else{
            if(p.y() < r.point.y()){
                currentNearest = nearer(p, currentNearest, nearest(r.left, p, currentNearest));
                currentNearest = nearer(p, currentNearest, nearest(r.right, p, currentNearest));
            }else{
                currentNearest = nearer(p, currentNearest, nearest(r.right, p, currentNearest));
                currentNearest = nearer(p, currentNearest, nearest(r.left, p, currentNearest));
            }
        }
        return currentNearest;
    }

    private Point2D nearer(Point2D p, Point2D c1, Point2D c2){
        if(c1 == null)
            return c2;
        if(c2 == null)
            return c1;
        if(c1.distanceTo(p) < c2.distanceTo(p))
            return c1;
        else return c2;
    }

    public static void main(String[] args){
        KdTree tree = new KdTree();
        tree.insert(new Point2D(0.7, 0.6));
        tree.insert(new Point2D(0.4, 0.2));
        tree.insert(new Point2D(0.5, 0.5));
        tree.insert(new Point2D(0.7, 0.8));
        tree.insert(new Point2D(0.1, 0.6));
        tree.insert(new Point2D(0.6, 0.3));
        tree.draw();
        StdDraw.show();
    }
}