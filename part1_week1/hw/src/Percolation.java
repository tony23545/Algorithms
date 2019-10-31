import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n_;
    private WeightedQuickUnionUF weightedQF1;
    private WeightedQuickUnionUF weightedQF2;
    private boolean[][] status;
    private int numOfOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("illegal n!");
        n_ = n;
        //n*n objects plus 2 virtual objects
        weightedQF1 = new WeightedQuickUnionUF(n_ * n_ + 2);
        weightedQF2 = new WeightedQuickUnionUF(n_ * n_ + 1);

        status = new boolean[n_][n_];
        for (int i = 0; i < n_; i++) {
            for (int j = 0; j < n_; j++) {
                status[i][j] = false;
            }
        }
        numOfOpen = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if(row < 1 || row > n_)
            throw new IllegalArgumentException("illegal row!");
        if(col < 1 || col > n_)
            throw new IllegalArgumentException("illegal col!");

        if(!isOpen(row, col)) {
            numOfOpen += 1;
            status[row - 1][col - 1] = true;

            int cur_id = n_ * (row - 1) + col;
            //left object
            if (col > 1) {//has left neighbor
                if (isOpen(row, col - 1)) {
                    weightedQF1.union(cur_id, cur_id - 1);
                    weightedQF2.union(cur_id, cur_id - 1);
                }
            }
            if (col < n_) {//has right neighbor
                if (isOpen(row, col + 1)) {
                    weightedQF1.union(cur_id, cur_id + 1);
                    weightedQF2.union(cur_id, cur_id + 1);
                }
            }
            if (row > 1) {//has up neighbor
                if (isOpen(row - 1, col)) {
                    weightedQF1.union(cur_id, cur_id - n_);
                    weightedQF2.union(cur_id, cur_id - n_);
                }
            }
            if (row < n_) {//has down neighbor
                if (isOpen(row + 1, col)) {
                    weightedQF1.union(cur_id, cur_id + n_);
                    weightedQF2.union(cur_id, cur_id + n_);
                }
            }
            if (row == 1) {
                weightedQF1.union(0, cur_id);
                weightedQF2.union(0, cur_id);
            }
            if (row == n_) {
                weightedQF1.union(n_ * n_ + 1, cur_id);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if(row < 1 || row > n_)
            throw new IllegalArgumentException("illegal row!");
        if(col < 1 || col > n_)
            throw new IllegalArgumentException("illegal col!");
        return status[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if(row < 1 || row > n_)
            throw new IllegalArgumentException("illegal row!");
        if(col < 1 || col > n_)
            throw new IllegalArgumentException("illegal col!");

       return weightedQF2.connected(0, (row-1) * n_ + col) && isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return numOfOpen;
    }

    // does the system percolate?
    public boolean percolates(){
        return weightedQF1.connected(0, n_*n_+1);
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation P = new Percolation(3);
        P.open(1,1);
        P.open(2,2);
        P.open(2,1);

        //for(int i = 1; i < 1; i++)
        //    P.open(i, 1);

        if(P.percolates()){
            System.out.println("connected!");
        }else {
            System.out.println("no!");
        }
        System.out.println(P.isOpen(2,2));
        System.out.println(P.isFull(2,2));
    }
}