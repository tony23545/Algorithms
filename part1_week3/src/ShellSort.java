public class ShellSort {
    public static void sort(Comparable[] a){
        int n = a.length;

        int h = 1;
        while(h < n / 3)
            h = 3 * h + 1;

        while(h >= 1){
            for(int i = h; i < n; i++){
                for(int j = i; j >= h; j-=h)
                    if(isLess(a[j], a[j-h]))
                        exch(a, j, j-h);
                    else
                        break;
            }
            h = h / 3;
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

    public static void main(String[] args){
        Comparable[] a = {4,6,3,2,76,4};
        sort(a);
        for(int i = 0; i < a.length; i++)
            System.out.println(a[i]);
    }
}