import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.TreeSet;

public class WordNet {
    private TreeSet<Node> allNouns;
    private ArrayList<String> synsetsList;
    private Digraph digraph;
    private SAP sap;

    private class Node implements Comparable<Node> {
        private ArrayList<Integer> id;
        private String noun;

        public Node(String str){
            id = new ArrayList<>();
            noun = str;
        }

        public void addID(int i){
            id.add(i);
        }

        public Iterable<Integer> getID(){
            return id;
        }

        @Override
        public int compareTo(Node o) {
            return noun.compareTo(o.noun);
        }
    }

    private class checkDAG{
        private boolean[] visited;
        private boolean[] finished;
        private Digraph G;
        private int rootCount;

        public checkDAG(Digraph g){
            G = g;
            rootCount = 0;
            visited = new boolean[G.V()];
            finished = new boolean[G.V()];

            for(int i = 0; i < G.V(); i++){
                visited[i] = false;
                finished[i] = false;
            }

            for(int i = 0; i < G.V(); i++){
                if(!visited[i]) dfs(i);
            }
        }

        private void dfs(int v){
            if(!G.adj(v).iterator().hasNext()) {
                if(++rootCount > 1)
                    throw new IllegalArgumentException("more than one root!");
            }

            visited[v] = true;
            for(int w : G.adj(v)){
                if(!visited[w])
                    dfs(w);
                else{
                    if(finished[w])
                        continue;
                    else
                        // find cycle
                        throw new IllegalArgumentException("not DAG!");
                }
            }
            finished[v] = true;
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if(synsets == null || hypernyms == null)
            throw new IllegalArgumentException();

        allNouns = new TreeSet<>();
        synsetsList = new ArrayList<>();
        int count = 0;

        In synsetsReader = new In(synsets);
        // BufferedReader synsetsReader = new BufferedReader(new FileReader(synsets));
        String line;

        //store all synsets in a tree
        while ((line = synsetsReader.readLine()) != null) {
            String[] parts = line.split(",");
            synsetsList.add(parts[1]);
            String[] nouns = parts[1].split(" ");
            for (String str : nouns) {
                Node node = new Node(str);
                if (allNouns.contains(node))
                    allNouns.ceiling(node).addID(Integer.parseInt(parts[0]));
                else {
                    node.addID(Integer.parseInt(parts[0]));
                    allNouns.add(node);
                }
            }
            count++;
        }

        //StdOut.printf("finish reading synsets with %d items", count);

        digraph = new Digraph(count);

        // store all hypernyms in digraph
        In hypernymsReader = new In(hypernyms);
        //BufferedReader hypernymsReader = new BufferedReader(new FileReader(hypernyms));
        while ((line = hypernymsReader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++)
                digraph.addEdge(id, Integer.parseInt(parts[i]));
        }

        // check for DAG
        checkDAG check = new checkDAG(digraph);

        sap = new SAP(digraph);
    }


    // returns all WordNet nouns
    public Iterable<String> nouns(){
        ArrayList<String> nounsList = new ArrayList<>();
        for(Node n : allNouns){
            nounsList.add(n.noun);
        }
        return nounsList;
    }


    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null)
            throw new IllegalArgumentException();
        Node n = new Node(word);
        return allNouns.contains(n);
    }


    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(nounA == null || nounB == null || !isNoun(nounA) || ! isNoun(nounB))
            throw new IllegalArgumentException();

        ArrayList<Integer> idA = (ArrayList<Integer>) allNouns.ceiling(new Node(nounA)).getID();
        ArrayList<Integer> idB = (ArrayList<Integer>) allNouns.ceiling(new Node(nounB)).getID();

        return sap.length(idA, idB);
    }


    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB){
        if(nounA == null || nounB == null || !isNoun(nounA) || ! isNoun(nounB))
            throw new IllegalArgumentException();

        ArrayList<Integer> idA = (ArrayList<Integer>) allNouns.ceiling(new Node(nounA)).getID();
        ArrayList<Integer> idB = (ArrayList<Integer>) allNouns.ceiling(new Node(nounB)).getID();

        return synsetsList.get(sap.ancestor(idA, idB));
    }

    // do unit testing of this class
    public static void main(String[] args){
        WordNet wd = new WordNet(args[0], args[1]);



    }
}