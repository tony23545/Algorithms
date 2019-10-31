import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet){
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns){
        if(nouns == null)
            throw new IllegalArgumentException();

        int strLen = nouns.length;
        int[] dis = new int[strLen];

        for(int i = 0; i < strLen; i++)
            dis[i] = 0;

        for(int i = 0; i < strLen; i++){
            for(int j = i+1; j < strLen; j++){
                int curDis = wn.distance(nouns[i], nouns[j]);
                //StdOut.println(curDis);
                dis[i] += curDis;
                dis[j] += curDis;
            }
        }

        int maxID = 0;
        int maxDis = dis[0];
        for(int i = 0; i < strLen; i++){
            if(dis[i] > maxDis){
                maxID = i;
                maxDis = dis[i];
            }
        }
        return nouns[maxID];
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
