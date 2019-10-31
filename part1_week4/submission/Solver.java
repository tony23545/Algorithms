import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Solver {
    private final Stack<Board> solu;
    private final boolean sovable;

    private class SearchNode implements Comparable<SearchNode>{
        public Board b;
        public final int move;
        private final int distance;
        private final int priority;
        public SearchNode parent;

        public SearchNode(Board b_, SearchNode p){

            b = b_;
            if(p == null)
                move = 0;
            else
                move = p.move + 1;
            parent = p;
            distance = b.manhattan();
            priority = move + distance;
        }

        @Override
        public int compareTo(SearchNode that){
            if(priority < that.priority) return -1;
            else if(priority > that.priority) return 1;
            else return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial == null)
            throw new IllegalArgumentException("empty initial!");
        MinPQ<SearchNode> realPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        realPQ.insert(new SearchNode(initial, null));
        twinPQ.insert(new SearchNode(initial.twin(),null));
        SearchNode realCurrentNode, twinCurrentNode;
        solu = new Stack<>();

        boolean sovableTmp = false;

        while(true){
            realCurrentNode = realPQ.delMin();
            twinCurrentNode = twinPQ.delMin();
            // find solution for the real initial
            if(realCurrentNode.distance == 0){
                SearchNode n = realCurrentNode;
                while(n != null){
                    solu.push(n.b);
                    n = n.parent;
                }
                sovableTmp = true;
                break;
            }
            // twin find solution
            else if(twinCurrentNode.distance == 0){
                break;
            }
            // neither of the PQ find solution
            else{
                update(realPQ, realCurrentNode);
                update(twinPQ, twinCurrentNode);
            }
        }
        sovable = sovableTmp;
    }

    private void update(MinPQ<SearchNode> PQ, SearchNode currentNode){
        Iterable<Board> tmp = currentNode.b.neighbors();
        if(currentNode.parent == null)
            for(Board n : tmp)
                PQ.insert(new SearchNode(n, currentNode));
        else{
            for(Board n : tmp)
                if(!n.equals(currentNode.parent.b))
                    PQ.insert(new SearchNode(n, currentNode));
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return sovable;
    }

    // min number of moves to solve initial board
    public int moves(){
        if(sovable)
            return solu.size()-1;
        else
            return -1;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        /*if(sovable) {
            ArrayList<Board> s = new ArrayList<>();
            Stack<Board> solTmp = new Stack<>(solu.);
            for (int i = solu.size() - 1; i >= 0; i--)
                s.add(solTmp.pop());
            return s;
        }
        else
            return null;*/
        if(sovable)
            return solu;
        else
            return null;
    }

    // test client (see below)
    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}