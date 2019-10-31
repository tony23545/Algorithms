import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {

    public static void main(String[] args){
        int k = 1;
        int n = 0;
        String tmp;
        if(args.length >= 1)
            k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<>();
        while (!StdIn.isEmpty()){
            n++;
            tmp = StdIn.readString();
            if(n <= k)
                q.enqueue(tmp);
            else{
                if(StdRandom.uniform(n) < k){
                    q.dequeue();
                    q.enqueue(tmp);
                }
            }
        }

        for(int i = 0; i < k; i++){
            System.out.println(q.dequeue());
        }
    }
}
