import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;


public class BoggleSolver {
    private final int R = 26;
    private Node root;
    private boolean[][] marked;
    private char[][] bboard;

    private int row = 0;
    private int col = 0;


    // word tires node class
    private class Node{
        private boolean isWord = false;
        private Node[] next = new Node[R];
    }

    private class adjacent{
        private int i;
        private int j;
        public adjacent(int i_, int j_){
            i = i_;
            j = j_;
        }
    }

    public void AddWord(String word){
        if(word == null)
            return;
        root = AddWord(root, word, 0);
    }

    private Node AddWord(Node x, String word, int d){
        if(x == null)
            x = new Node();

        // root have no information
        if(d == word.length()) {
            x.isWord = true;
            return x;
        }

        char c = word.charAt(d);
        x.next[c - 'A'] = AddWord(x.next[c - 'A'], word, d+1);

        return x;
    }

    public boolean contain(String word){
        Node x = get(root, word, 0);
        if(x == null)
            return false;
        else
            return x.isWord;
    }

    private Node get(Node x, String word, int d){
        if(x == null) return null;

        if(d == word.length())
            return x;
        else{
            char c = word.charAt(d);
            return get(x.next[c - 'A'], word, d+1);
        }
    }

    private Iterable<adjacent> getAdj(int i, int j){
        ArrayList<adjacent> adj = new ArrayList<adjacent>();
        if(i > 0){
            if(!marked[i-1][j])
                adj.add(new adjacent(i-1, j));
            if(j > 0 && !marked[i-1][j-1])
                adj.add(new adjacent(i-1, j-1));
            if(j < (col-1) && !marked[i-1][i+1])
                adj.add(new adjacent(i-1, j+1));

        }
        if(i < (row-1)){
            if(!marked[i+1][j])
                adj.add(new adjacent(i+1, j));
            if(j > 0 && !marked[i+1][j-1])
                adj.add(new adjacent(i+1, j-1));
            if(j < (col-1) && !marked[i+1][j+1])
                adj.add(new adjacent(i+1, j+1));
        }
        if(j > 0 && !marked[i][j-1])
            adj.add(new adjacent(i, j-1));
        if(j < (col-1) && !marked[i][j+1])
            adj.add(new adjacent(i, j+1));
        return adj;
    }

    private Iterable<String> dfs(){
        SET<String> words = new SET<>();
        for(int i = 0; i < row; i++){
            for(int j = 0; j < col; j++)
                dfs(root.next[bboard[i][j] - 'A'], new adjacent(i, j), words, "", 1);
        }
        return words;
    }

    private void dfs(Node x, adjacent adj, SET<String> words, String preStr, int d){
        if(x == null)
            return;

        marked[adj.i][adj.j] = true;

        if(d >= 3) {
            char c = bboard[adj.i][adj.j];
            if (c == 'Q')
                preStr = preStr + "QU";
            else
                preStr = preStr + c;

            if (x.isWord)
                words.add(preStr);
        }

        for(adjacent adjNex : getAdj(adj.i, adj.j)){
            char c = bboard[adjNex.i][adjNex.j];
            if(c == 'Q')
                dfs((x.next[c - 'A']).next['U' - 'A'], adjNex, words, preStr, d+2);
            else
                dfs(x.next[c - 'A'], adjNex, words, preStr, d+1);
        }

        marked[adj.i][adj.j] = false;
    }



    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){
        for(String str : dictionary)
            AddWord(str);
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        if(col != board.cols() || row != board.rows()){
            col = board.cols();
            row = board.rows();
            marked = new boolean[row][col];
            bboard = new char[row][col];
            for(int i = 0; i < row; i++){
                for(int j = 0; j < col; j++){
                    marked[i][j] = false;
                    bboard[i][j] = board.getLetter(i, j);
                }
            }
        }
        return dfs();
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        if(word == null)
            throw new IllegalArgumentException("null word");
        else if(! contain(word) || word.length() < 3)
            return 0;
        else if(word.length() < 5)
            return 1;
        else if(word.length() < 6)
            return 2;
        else if(word.length() < 7)
            return 3;
        else if(word.length() < 8)
            return 5;
        else
            return 11;
    }

    public static void main(String[] args) {
        /*In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);*/

        BoggleSolver solver = new BoggleSolver();
    }
}
