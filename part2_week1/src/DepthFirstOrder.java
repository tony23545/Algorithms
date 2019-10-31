import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class DepthFirstOrder {
    private Stack<Integer> tpo;
    private boolean[] marked;

    public DepthFirstOrder(Digraph G){
        tpo = new Stack<>();

        marked = new boolean[G.V()];
        for(int i = 0; i < G.V(); i++){
            marked[i] = false;
        }

        for(int i = 0; i < G.V(); i++){
            if(!marked[i])
                dfs(G, i);
        }

    }

    private void dfs(Digraph G, int v){
        marked[v] = true;
        for(int w : G.adj(v)){
            if(!marked[w])
                dfs(G, w);
        }
        tpo.push(v);
    }

    public Iterable<Integer> topologicalOrder(){
        return tpo;
    }

    public static void main(String args[]){
        In in = new In(args[0]);
        Digraph G = new Digraph(in);

        StdOut.println(G.toString());

        DepthFirstOrder dfo = new DepthFirstOrder(G);

        StdOut.println("topological sort: ");
        StdOut.println(dfo.topologicalOrder().toString());
    }
}