import edu.princeton.cs.algs4.StdRandom;

public class ShuffleSort {
    public static void shuffle(Comparable[] a){
        int n = a.length;
        for(int i = 0; i < n; i++){
            int rand = StdRandom.uniform(i+1);
            exch(a, i, rand);
        }
    }

    public static void exch(Comparable[] a, int v, int w){
        Comparable tmp = a[v];
        a[v] = a[w];
        a[w] = tmp;
    }

    public static void main(String[] args){
        Comparable[] a = {4,6,3,2,76,4};
        shuffle(a);
        for(int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }
}
