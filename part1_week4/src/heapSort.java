import java.util.Comparator;

public class heapSort {
    public heapSort(Comparable[] a){
        int n = a.length;

        //heap construction
        for(int k = n/2; k > 0; k--)
            sink(a, k, n);

        //
        while(n > 1){
            exch(a, 1, n--);
            sink(a, 1, n);
        }

        for(int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }

    private void sink(Comparable[] a, int k, int n){
        while(2*k <= n){
            int j = 2 * k;
            if(j < n && less(a, j, j+1)) j++;
            if(less(a, j, k)) break;
            exch(a, k, j);
            k = j;
        }
    }

    private void exch(Comparable[] a, int i, int j){
        Comparable t = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = t;
    }

    private boolean less(Comparable[] a, int i, int j){
        return a[i-1].compareTo(a[j-1]) < 0;
    }

    public static void main(String[] args){
        Comparable[] t = new Comparable[5];
        t[0] = 56;
        t[1] = 43;
        t[2] = 634;
        t[3] = 4;
        t[4] = 6;
        heapSort a = new heapSort(t);
    }
}