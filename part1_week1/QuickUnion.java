class QuickUnionUF{
	private int[] id;

	public QuickUnionUF(int N){
		id = new int[N];
		for(int i = 0; i < N; i++)
			id[i] = i;
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
		if(p == q) return;
		//connect p's root to q's root
		id[i] = j;
	}

	public static void main(String[] args) {
		QuickUnionUF QU = new QuickUnionUF(5);
		QU.union(0, 3);
		QU.union(3, 4);
		boolean check = QU.connected(0, 4);

		if(check)
			System.out.println("connected!");
		else
			System.out.println("no");
	}
}