import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int s;

    private class Node{
        Item item;
        Node next;
        Node previous;
    }

    // construct an empty deque
    public Deque(){
        s = 0;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return s == 0;
    }

    // return the number of items on the deque
    public int size(){
        return s;
    }

    // add the item to the front
    public void addFirst(Item item){
        if(item == null)
            throw new IllegalArgumentException("illegal item!");
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        first.next = oldFirst;
        first.previous = null;
        if(isEmpty())
            last = first;
        else
            oldFirst.previous = first;
        s++;
    }

    // add the item to the back
    public void addLast(Item item){
        if(item == null)
            throw new IllegalArgumentException("illegal item!");
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.previous = oldLast;
        if(isEmpty())
            first = last;
        else
            oldLast.next = last;
        s++;
    }

    // remove and return the items from the front
    public Item removeFirst(){
        if(isEmpty())
           throw new NoSuchElementException("queue empty!");
        Item item = first.item;
        first = first.next;
        if(first != null)
            first.previous = null;
        else
            last = null;
        s--;
        return item;
    }

    // remove and return the items from the back
    public Item removeLast(){
        if(isEmpty())
            throw new NoSuchElementException("queue empty!");
        Item item = last.item;
        last = last.previous;
        if(last != null)
            last.next = null;
        else
            first = null;
        s--;
        return item;
    }

    // return a random item (but do not remove it)
    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){return current != null;}
        public void remove(){
            throw new UnsupportedOperationException("remove is not support!");
        }
        public Item next(){
            if(current == null)
                throw new NoSuchElementException("current empty!");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args){
        Deque<Integer> q = new Deque<>();
        q.addFirst(1);
        System.out.println(q.removeLast());
        System.out.println(q.size());
        Iterator<Integer> it = q.iterator();
        System.out.println(it.hasNext());
    }
}