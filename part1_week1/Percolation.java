class Percolation{


	//create n*n grid, with all sites initially blocked
	public Percolation(int n){
		n_ = n;
		for(int i = 0; i < n_; i++){
			id[i] = i;
			sz[i] = 1;
		}
	}

	//opens the sites (row, col) if it is not open already
	public void open(int row, int col){

	}

	//is the site {row, col} open?
	public boolean isOpen(int row, int col){

	}

	//is the site {row, col} full?
	public boolean isFull(int row, int col){

	}

	//returns the number of open sites
	public int numberOfOpenSites(){

	}

	//does the system percolate?
	public boolean percolates(){

	}


}