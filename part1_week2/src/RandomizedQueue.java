import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] rqArray;
    private int s;

    // construct an empty randomized queue
    public RandomizedQueue(){
        s = 0;
        rqArray = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return s == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return s;
    }

    private void resize(int cap){
        Item[] copy = (Item[]) new Object[cap];
        for(int i = 0; i < s; i++)
            copy[i] = rqArray[i];
        rqArray = copy;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null)
            throw new IllegalArgumentException("illegal argument!");
        //double the size of the queue if necessary
        if(s == rqArray.length)
            resize(2*rqArray.length);
        rqArray[s++] = item;
    }

    // remove and return a random item
    public Item dequeue(){
        if(isEmpty())
            throw new NoSuchElementException("queue empty!");
        int rand = StdRandom.uniform(s);
        Item tmp = rqArray[rand];
        //swap the rand th item and the last item, keep the array compact.
        rqArray[rand] = rqArray[--s];
        rqArray[s] = null;
        if (s > 0 && s == rqArray.length / 4)
            resize(rqArray.length / 2);
        return tmp;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty())
            throw new NoSuchElementException("queue empty!");
        int rand = StdRandom.uniform(s);
        return rqArray[rand];
    }

    // return an independent iterator over item in random order
    public Iterator<Item> iterator(){
        return new rqIterator();
    }

    private class rqIterator implements Iterator<Item> {
        private Item[] itArray;
        private int r;
        public rqIterator(){
            r = s;
            itArray = (Item[])new Object[s];
            for(int i = 0; i < s; i++)
                itArray[i] = rqArray[i];
        }
        public boolean hasNext(){return r > 0;}
        public void remove(){
            throw new UnsupportedOperationException("remove is not support!");
        }
        public Item next(){
            if(r == 0)
                throw new NoSuchElementException("queue empty!");
            int rand = StdRandom.uniform(r);
            Item tmp = itArray[rand];
            itArray[rand] = itArray[--r];
            return tmp;
        }
    }

    // unit testing
    public static void main(String[] args){
        RandomizedQueue<Integer> q = new RandomizedQueue<>();
        q.enqueue(6);
        q.enqueue(7);
        q.enqueue(3);
        q.enqueue(9);

        Iterator<Integer> a = q.iterator();
        Iterator<Integer> b = q.iterator();

        while(a.hasNext()){
            System.out.printf("%d, %d\n", a.next(), b.next());
        }
    }
}