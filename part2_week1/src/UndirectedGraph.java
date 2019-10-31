import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Iterator;

public class UndirectedGraph {
    private Bag<Integer>[] adj;
    private int V;
    private int E;

    public UndirectedGraph(int V){
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for(int i = 0; i < V; i++)
            adj[i] = new Bag<>();
    }

    public UndirectedGraph(In in){
        this.V = in.readInt();
        int e = in.readInt();
        adj = (Bag<Integer>[]) new Bag[V];
        for(int i = 0; i < V; i++)
            adj[i] = new Bag<>();

        for(int i = 0; i < e; i++)
            addEdge(in.readInt(), in.readInt());
    }

    // add an edge to the graph
    public void addEdge(int v, int w){
        adj[v].add(w);
        adj[w].add(v);
        E++;
    }

    public Iterable<Integer> adj(int v){
        return adj[v];
    }

    public int V(){
        return this.V;
    }

    public int E() {
        return this.E;
    }

    // get the degree of a vertex
    public int degree(int v){
        if(v >= V) return -1; //return -1 if out of range
        int degree = 0;
        for (int w : adj(v)) degree++;
        return degree;
    }

    // get the max degree of the graph
    public int maxDegree(int v){
        int max = -1;
        for(int i = 0; i < V; i++){
            int d = degree(i);
            if(d > max) max = d;
        }
        return max;
    }

    // average degree
    public double averageDegree(){
        return 2.0 * E / V;
    }

    // count self-loop
    public int numOfSelfLoop(){
        int count = 1;
        for(int i = 0; i < V; i++)
            for(int w: adj(i))
                if(i == w) count++;
        return count;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder("All edges in this graph \n");
        for(int v = 0; v < V; v++)
            for(int w : adj(v)) {
                sb.append(v); sb.append("->"); sb.append(w); sb.append("\n");
            }
        return sb.toString();
    }

    public static void main(String[] args){
        UndirectedGraph G = new UndirectedGraph(5);

        In in = new In(args[0]);


        UndirectedGraph myGraph = new UndirectedGraph(in);
        StdOut.println(myGraph.toString());

        DepthFirstSearch dfs = new DepthFirstSearch(myGraph, 0);
        Iterable<Integer> path = dfs.pathTo(6);
        for(int w : path)
            StdOut.printf("%d->", w);

        BreadthFirstSearch bfs = new BreadthFirstSearch(myGraph, 0);
        Iterable<Integer> path1 = bfs.pathTo(6);
        for(int w : path1)
            StdOut.printf("%d->", w);
    }


}
