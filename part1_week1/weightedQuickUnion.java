class weightedQuickUnionUF{
	private int[] id;
	private int[] sz;

	public weightedQuickUnionUF(int N){
		id = new int[N];
		sz = new int[N];
		for(int i = 0.; i < N; i++){
			id[i] = i;
			sz[i] = 1;
		}
	}

	private int root(int i){
		while(i != id[i])
			i = id[i];
		return i;
	}

	public boolean connected(int p, int q){
		return root(p) == root(q);
	}

	public void union(int p, int q){
		int i = root(p);
		int j = root(q);
		//if p is the larger tree root, connect to p's root
		if(i == j) return;
		if(sz[i] > sz[j]){
			id[j] = i;
			sz[i] += sz[j];
		}
		else{
			id[i] = j;
			sz[j] = i;
		}
	} 

}