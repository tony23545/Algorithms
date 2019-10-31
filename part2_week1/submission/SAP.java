import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.ArrayList;

public class SAP {
    private Digraph graph;

    private int minDis;
    private int minAnc;

    // BFS to calculate the distance from the source vertex
    private class BreadthFirstSearchDis{
        private boolean[] marked;
        private int[] dis;

        public BreadthFirstSearchDis(Digraph G, Iterable<Integer> v){
            marked = new boolean[G.V()];
            dis = new int[G.V()];
            for(int i = 0; i < G.V(); i++){
                marked[i] = false;
                dis[i] = -1;
            }

            Queue<Integer> helperQ = new Queue<>();

            for(int i : v){
                marked[i] = true;
                dis[i] = 0;
                helperQ.enqueue(i);
            }

            while(!helperQ.isEmpty()){
                int curV = helperQ.dequeue();
                for(int w : G.adj(curV)){
                    if(!marked[w]){
                        marked[w] = true;
                        dis[w] = dis[curV] + 1;
                        helperQ.enqueue(w);
                    }
                }
            }
        }

        public int disTo(int w){
            return dis[w];
        }
    }

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if(G == null)
            throw new IllegalArgumentException("null graph!");

        this.graph = new Digraph(G);;
    }

    private void query(Iterable<Integer> v, Iterable<Integer> w){
        BreadthFirstSearchDis vBFS = new BreadthFirstSearchDis(graph, v);
        BreadthFirstSearchDis wBFS = new BreadthFirstSearchDis(graph, w);

        minDis = -1;
        minAnc = -1;

        for(int i = 0; i < graph.V(); i++) {
            // if current vertex is reachable to both sources
            if (vBFS.disTo(i) >= 0 && wBFS.disTo(i) >= 0) {
                int tmpDis = vBFS.disTo(i) + wBFS.disTo(i);
                if (tmpDis < minDis || minDis < 0) {
                    minAnc = i;
                    minDis = tmpDis;
                }
            }
        }
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        if(v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
            throw new IllegalArgumentException("illegal input!");

        // breadth first search with v as source vertex
        ArrayList<Integer> vArg = new ArrayList<>();
        vArg.add(v);

        // breadth first search with w as source vertex
        ArrayList<Integer> wArg = new ArrayList<>();
        wArg.add(w);

        query(vArg, wArg);

        return minDis;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w){
        if(v < 0 || v >= graph.V() || w < 0 || w >= graph.V())
            throw new IllegalArgumentException("illegal input!");

        // breadth first search with v as source vertex
        ArrayList<Integer> vArg = new ArrayList<>();
        vArg.add(v);

        // breadth first search with w as source vertex
        ArrayList<Integer> wArg = new ArrayList<>();
        wArg.add(w);

       query(vArg, wArg);

       return minAnc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null)
            throw new IllegalArgumentException("illegal input!");
        try{
            for(int i : v) {
                if (i < 0 || i >= graph.V())
                    throw new IllegalArgumentException("illegal input!");
            }

            for(int i : w){
                if (i < 0 || i >= graph.V())
                    throw new IllegalArgumentException("illegal input!");
            }
        }catch(NullPointerException e){
            throw new IllegalArgumentException();
        }

        query(v, w);
        return minDis;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        if(v == null || w == null)
            throw new IllegalArgumentException("illegal input!");
        try{
            for(int i : v) {
                if (i < 0 || i >= graph.V())
                    throw new IllegalArgumentException("illegal input!");
            }

            for(int i : w){
                if (i < 0 || i >= graph.V())
                    throw new IllegalArgumentException("illegal input!");
            }
        }catch(NullPointerException e){
            throw new IllegalArgumentException();
        }

        query(v, w);
        return minAnc;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        ArrayList<Integer> v = new ArrayList<>();
        v.add(null);
        v.add(5);
        ArrayList<Integer> w = new ArrayList<>();
        w.add(4);
        sap.ancestor(v, w);
        /*while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }*/
    }
}