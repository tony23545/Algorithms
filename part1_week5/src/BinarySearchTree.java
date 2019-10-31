import edu.princeton.cs.algs4.Queue;

public class BinarySearchTree <Key extends Comparable<Key>, Value> {
    private Node root;

    //node class
    private class Node{
        private Key key;
        private Value value;
        private Node left, right;
        private int count;

        public Node(Key k, Value v, int c){
            key = k;
            value = v;
            count = c;
        }
    }

    public BinarySearchTree(){
        root = null;
    }

    public Value get(Key k){
        Node r = root;
        while(r != null){
            int cmp = r.key.compareTo(k);
            if(cmp > 0) r = r.left;
            else if(cmp < 0) r = r.right;
            else if(cmp == 0) return r.value;
        }
        return null;
    }

    public void put(Key k, Value v){
        root = put(root, k, v);
    }

    //for the convenience of the following red black tree?
    private Node put(Node r, Key k, Value v){
        if(r == null)
            return new Node(k, v, 1);
        int cmp = r.key.compareTo(k);
        if(cmp > 0) r.left = put(r.left, k, v);
        else if(cmp < 0) r.right = put(r.right, k, v);
        else if(cmp == 0) r.value = v;

        //update size of this node
        r.count = 1 + size(r.left) + size(r.right);
        return r;
    }

    public Key floor(Key k){
        Node x = floor(root, k);
        if(x == null) return null;
        else return x.key;
    }

    private Node floor(Node r, Key k){
        if(r == null) return null;
        int cmp = r.key.compareTo(k);

        // if go left, return whatever you get
        if(cmp > 0) return floor(r.left, k);
        else if(cmp == 0) return r;

        // if cmp < 0
        Node t = floor(r.right, k);

        if(t == null) return r;
        else return t;
    }

    public Key ceil(Key k){
        Node x = ceil(root, k);
        if(x == null) return null;
        else return x.key;
    }

    private Node ceil(Node r, Key k){
        if(r == null) return null;
        int cmp = r.key.compareTo(k);
        if(cmp < 0) return ceil(r.right, k);
        else if(cmp == 0) return r;

        Node t = ceil(r.left, k);
        if(t == null) return r;
        else return t;
    }

    // recursive implementation
    public Key min(){
        return min(root).key;
    }

    private Node min(Node r){
        if(r == null) return null;
        Node t = min(r.left);
        if(t == null) return r;
        else return t;
    }

    // non recursive implementation
    public Key max(){
        if(root == null) return null;
        Node x = root;
        while(x.right != null){
            x = x.right;
        }
        return x.key;
    }

    // size of subtree
    public int size(){
        return size(root);
    }

    private int size(Node r){
        if(r == null) return 0;
        else return r.count;
    }

    //return rank of given k in current tree
    public int rank(Key k){
        return rank(root, k);
    }

    private int rank(Node r, Key k){
        if(r == null) return 0;
        int cmp = r.key.compareTo(k);

        if(cmp == 0) return size(r.left);
        else if(cmp > 0) return rank(r.left, k);
        else return 1 + size(r.left) + rank(r.right, k);
    }

    // Iterator
    public Iterable<Key> iterator(){
        Queue<Key> q = new Queue<Key> ();
        inorder(root, q);
        return q;

    }

    private void inorder(Node r, Queue<Key> q){
        if(r == null) return;
        inorder(r.left, q);
        q.enqueue(r.key);
        inorder(r.right, q);
    }

    // delete the min element in the tree
    public void delMin(){
        root = delMin(root);
    }

    private Node delMin(Node r){
        if(r.left == null) return r.right;
        r.left = delMin(r.left);
        r.count = 1 + size(r.left) + size(r.right);
        return r;
    }

    // deletion operation
    public void deletion(Key k){
        root = deletion(root, k);
    }

    private Node deletion(Node r, Key k){
        if(r == null) return null;
        int cmp = r.key.compareTo(k);

        if(cmp > 0) r.left = deletion(r.left, k);
        else if(cmp < 0) r.right = deletion(r.right, k);
        else{
            if(r.left == null) return r.right;
            if(r.right == null) return r.left;

            // node r has two non null children
            Node t = r;
            r = min(r.right);
            r.left = t.left;
            r.right = delMin(t.right);
        }
        r.count = 1 + size(r.left) + size(r.right);
        return r;
    }

    public static void main(String[] args){

    }
}