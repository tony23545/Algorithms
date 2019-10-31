class QuickFindUF{
	private int[] id;

	public QuickFindUF(int N){
		id = new int[N];
		for(int i = 0; i < N; i++)
			id[i] = i;
	}

	public boolean connected(int p, int q){
		return id[p] == id[q];
	}

	public void union(int p, int q){
		int pid = id[p];
		int qid = id[q];
		for(int i = 0; i < id.length; i++)
			if(id[i] == pid)
				id[i] = qid;
	}

	public static void main(String[] args) {
		QuickFindUF QF = new QuickFindUF(5);
		QF.union(0, 3);
		QF.union(3, 4);
		boolean check = QF.connected(0, 4);

		if(check)
			System.out.println("connected!");
		else
			System.out.println("no");
	}

}