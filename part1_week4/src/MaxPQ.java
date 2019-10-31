public class MaxPQ<Key extends Comparable<Key>>{
    private Key[] PQ;
    private int n;

    public MaxPQ(int capacity){
        n = 0;
        PQ = (Key[]) new Comparable[capacity+1];
    }

    public boolean isEmpty(){
        return n == 0;
    }

    public int size(){
        return n;
    }

    public void insert(Key key){
        PQ[++n] = key;
        swim(n);
        if(n == PQ.length)
            resize(PQ.length * 2);
    }

    public Key delMax(){
        Key t = PQ[1];
        exch(1, n--);
        sink(1);
        PQ[n+1] = null;
        if(n>0 && n < PQ.length/4)
            resize(PQ.length / 2);
        return t;
    }

    private void resize(int cap){

        Key[] tmp = (Key[]) new Comparable[cap];
        for(int i = 0; i < n; i++)
            tmp[i] = PQ[i];
        PQ = tmp;
    }

    private void swim(int k){
        if(k > n || k < 1)
            throw new IllegalArgumentException("PQ illegal arg!");
        while(k>1 && less(k/2, k)){//lgn compares
            exch(k/2, k);//lgn exchange
            k = k / 2;
        }
    }

    private void sink(int k){
        if(k > n || k < 1)
            throw new IllegalArgumentException("PQ illegal arg!");
        while(k*2 <= n){ //if k has at least 1 child
            int j = k*2;
            //2lgn compare
            if(j < n && less(j, j+1)) j++;
            if(less(j, k)) break; //if child is less than parent
            exch(k, j);//lgn exchange
            k = j;
        }
    }

    private boolean less(int i, int j){
        return PQ[i].compareTo(PQ[j]) < 0;
    }

    private void exch(int i, int j){
        Key t = PQ[i];
        PQ[i] = PQ[j];
        PQ[j] = t;
    }

    public static void main(String[] args){
        MaxPQ<Integer> pq = new MaxPQ<>(10);
        pq.insert(5);
        pq.insert(7);
        pq.insert(10);
        pq.insert(59);
        pq.insert(4);
        pq.insert(58);
        System.out.println(pq.size());
        System.out.println(pq.delMax());
        System.out.println(pq.delMax());
        System.out.println(pq.delMax());
        System.out.println(pq.delMax());
        System.out.println(pq.size());
    }
}