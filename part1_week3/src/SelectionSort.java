public class SelectionSort {
    public static void sort(Comparable[] a){
        int n = a.length;
        for(int i = 0; i < n; i++){
            int min = i;
            for(int j = i+1; j < n; j++)
                if(isLess(a[j], a[min]))
                    min = j;
            exch(a, i, min);
        }
    }

    public static boolean isLess(Comparable v, Comparable w){
        return v.compareTo(w) < 0;
    }

    public static void exch(Comparable[] a, int v, int w){
        Comparable tmp = a[v];
        a[v] = a[w];
        a[w] = tmp;
    }

    public static void main(String args[]){
        Comparable[] a = {4,6,3,2,76,4};
        sort(a);
        for(int i = 0; i < a.length; i++)
           System.out.println(a[i]);
    }
}
