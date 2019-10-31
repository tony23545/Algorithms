import edu.princeton.cs.algs4.Stack;

public class DepthFirstSearch {
    private boolean[] marked;
    private int[] from;

    public DepthFirstSearch(UndirectedGraph G, int v){
        Stack<Integer> helperStack = new Stack<>();
        marked = new boolean[G.V()];
        for(int i = 0; i < marked.length; i++)
            marked[i] = false;
        from = new int[G.V()];

        helperStack.push(v);
        marked[v] = true;
        from[v] = -1;

        while(!helperStack.isEmpty()){
            int currentV = helperStack.pop();
            marked[currentV] = true;
            for(int w : G.adj(currentV)){
                if(!marked[w]) {
                    from[w] = currentV;
                    helperStack.push(w);
                }
            }
        }
    }

    public boolean hasPathTo(int w){
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
