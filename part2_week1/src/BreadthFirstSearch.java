import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class BreadthFirstSearch {
    private boolean[] marked;
    private int[] from;
    Queue<Integer> helperQueue;

    public BreadthFirstSearch(UndirectedGraph G, int v){
        marked = new boolean[G.V()];
        for(int i = 0; i < marked.length; i++)
            marked[i] = false;
        from = new int[G.V()];

        helperQueue = new Queue<>();

        helperQueue.enqueue(v);
        marked[v] = true;
        from[v] = -1;

        while(!helperQueue.isEmpty()){
            int currentV = helperQueue.dequeue();
            for(int w : G.adj(currentV)){
                if(!marked[w]){
                    marked[w] = true;
                    from[w] = currentV;
                    helperQueue.enqueue(w);
                }
            }
        }
    }

    public boolean hasPathTO(int w){
        return marked[w];
    }

    public Iterable<Integer> pathTo(int w){
        Stack<Integer> path = new Stack<>();
        if(marked[w]){
            int tmp = w;
            path.push(tmp);
            while(from[tmp] != -1){
                tmp = from[tmp];
                path.push(tmp);
            }
        }
        return path;
    }
}
