public class TernarySearchTrie<Value> {
    private Node root;

    private class Node{
        private Value val;
        private char c;
        private Node left, middle, right;
    }

    public void put(String key, Value val){
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, Value val, int d){
        char c = key.charAt(d);
        if(x == null){
            x = new Node();
            x.c = c;
        }

        if(c < x.c) x.left = put(x.left, key, val, d+1);
        else if(c > x.c) x.right = put(x.right, key, val, d+1);
        else if(d < (key.length()-1)) x.middle = put(x.middle, key, val, d+1);
        else x.val = val;

        return x;
    }

    public boolean contain(String key){
        return get(key) != null;
    }

    public Value get(String key){
         Node x = get(root, key, 0);
         if(x == null)
             return null;
         return x.val;
    }

    private Node get(Node x, String key, int d){
        if(x == null) return null;
        char c = key.charAt(d);
        if(c < x.c) return get(x.left, key, d+1);
        else if(c > x.c) return get(x.right, key, d+1);
        else if(d < (key.length()-1)) return get(x.middle, key, d+1);
        else return x;
    }

    public static void main(){

    }
}