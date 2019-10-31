import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Board {
    private final int n;
    private int[][] board;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public  Board(int[][] tiles){
        if(tiles == null)
            throw new IllegalArgumentException("empty tiles!");

        n = tiles.length;
        board = new int[n][n];
        for(int i = 0; i < tiles.length; i++)
            for(int j = 0; j < tiles[i].length; j++)
                board[i][j] = tiles[i][j];
    }

    // string representation of this board
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++){
                sb.append(board[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }


    // board dimension n
    public int dimension(){
        return n;
    }

    // number of tiles out of place
    public int hamming(){
        int distance = 0;
        for(int i = 0; i < n; i++)
            for(int j = 0; j < n; j++)
                if(board[i][j] != n*i+j+1)
                    distance++;
        distance--;//for the 0 piece
        return distance;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int distance = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                int t = board[i][j] - 1;
                if(t >= 0) {
                    int row = t / n;
                    int col = t % n;
                    distance += Math.abs(row - i) + Math.abs(col - j);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if(y == null)
            return false;
        if(y == this)
            return true;
        if(y.getClass().isInstance(this)){
            Board yb = (Board) y;
            if(n != yb.n)
                return false;
            for(int i = 0; i < n; i++){
                for(int j = 0; j < n; j++) {
                    if (board[i][j] != yb.board[i][j])
                        return false;
                }
            }
            return true;
        }else
            return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        ArrayList<Board> neighb = new ArrayList<>();
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++){
                if(board[i][j] == 0){
                    if(i > 0){
                        Board nei1 = new Board(board);
                        nei1.swap(i, j, i-1, j);
                        neighb.add(nei1);
                    }
                    if(i < n-1){
                        Board nei2 = new Board(board);
                        nei2.swap(i, j, i+1, j);
                        neighb.add(nei2);
                    }
                    if(j > 0){
                        Board nei3 = new Board(board);
                        nei3.swap(i, j, i, j-1);
                        neighb.add(nei3);
                    }
                    if(j < n-1){
                        Board nei4 = new Board(board);
                        nei4.swap(i, j, i, j+1);
                        neighb.add(nei4);
                    }
                    break;
                }
            }
        }
        return neighb;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board result = new Board(board);
        if(n == 1)
            return this;
        if(board[0][0] != 0 && board[n-1][n-1] != 0)
            result.swap(0, 0, n-1, n-1);
        else
            result.swap(0, 1, n-1, n-2);
        return result;

    }

    private void swap(int aRow, int aCol, int bRow, int bCol){
        int tmp = board[aRow][aCol];
        board[aRow][aCol] = board[bRow][bCol];
        board[bRow][bCol] = tmp;

    }

    public static void main(String[] args){
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        StdOut.println(initial.toString());
        StdOut.println("hamming");
        StdOut.println(initial.hamming());
        StdOut.println("mah");
        StdOut.println(initial.manhattan());

        StdOut.println("neighbour");
        Iterable<Board> t = initial.neighbors();
        for(Board i: t){
            StdOut.println(i.toString());
        }

        StdOut.println("twin");
        StdOut.println(initial.twin());


    }
}