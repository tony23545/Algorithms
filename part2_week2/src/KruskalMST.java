import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.UF;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class KruskalMST {
    private Queue<Edge> mstQ;

    public KruskalMST(EdgeWeightedGraph G){
        mstQ = new Queue<>();
        MinPQ<Edge> edgePQ = new MinPQ<>();

        for(Edge e : G.edges())
            edgePQ.insert(e);

        UF uf = new UF(G.V());

        while(!edgePQ.isEmpty() && mstQ.size() < G.V() - 1){
            Edge next = edgePQ.delMin();
            int v = next.either();
            int w = next.other(v);
            if(!uf.connected(v, w)){
                mstQ.enqueue(next);
                uf.union(v, w);
            }
        }
    }

    public Iterable<Edge> mst() {
        return mstQ;
    }

    public static void main(String args[]){
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);

    }
}
