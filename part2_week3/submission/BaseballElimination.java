import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

import java.util.HashMap;
import java.util.ArrayList;

public class BaseballElimination {
    private int N;
    private int[] w, l, r;
    private int[][] g;
    private String[] teams;
    private HashMap<String, Integer> hashT;
    private int flow;

    // create a baseball division from given filename in format specified below
    public BaseballElimination(String filename){
        if(filename == null)
            throw new IllegalArgumentException();

        // read in data
        In in = new In(filename);
        N = in.readInt();
        flow = 0;

        w = new int[N];
        l = new int[N];
        r = new int[N];
        teams = new String[N];
        hashT = new HashMap<>();
        g = new int[N][N];

        for(int i = 0; i < N; i++){
            teams[i] = in.readString();
            hashT.put(teams[i], i);
            w[i] = in.readInt();
            l[i] = in.readInt();
            r[i] = in.readInt();
            for(int j = 0; j < N; j++)
                g[i][j] = in.readInt();
        }
    }

    // number of teams
    public int numberOfTeams(){
        return N;
    }

    // all teams
    public Iterable<String> teams(){
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i < N; i++)
            result.add(teams[i]);
        return result;
    }

    // number of wins for given team
    public int wins(String team){
        if(team == null || !hashT.containsKey(team))
            throw new IllegalArgumentException();
        int W =  w[hashT.get(team)];
        return W;
    }

    // number of losses for given team
    public int losses(String team){
        if(team == null || !hashT.containsKey(team))
            throw new IllegalArgumentException();
        int L = l[hashT.get(team)];
        return L;
    }

    // number of remaining games for given team
    public int remaining(String team){
        if(team == null || !hashT.containsKey(team))
            throw new IllegalArgumentException();
        int R = r[hashT.get(team)];
        return R;
    }

    // number of remaining games between team1 and team2
    public int against(String team1, String team2){
        if(team1 == null || team2 == null || !hashT.containsKey(team1) || !hashT.containsKey(team2))
            throw new IllegalArgumentException();
        int G = g[hashT.get(team1)][hashT.get(team2)];
        return G;
    }


    // is given team eliminated?
    public boolean isEliminated(String team){
        if(team == null || !hashT.containsKey(team))
            throw new IllegalArgumentException();

        FlowNetwork FN = constructFN(team);
        if(FN == null) {
            //StdOut.println("FN null\n");
            return true;
        }
        else{
            FordFulkerson FF = new FordFulkerson(FN, 0, FN.V()-1);
            //StdOut.printf("flow = %d, value = %f\n", flow, FF.value());
            return flow > FF.value();
        }
    }

    // subset R of teams that eliminates given team; null if not eliminated
    public Iterable<String> certificateOfElimination(String team){
        if(team == null || !hashT.containsKey(team))
            throw new IllegalArgumentException();

        FlowNetwork FN = constructFN(team);
        ArrayList<String> cert = new ArrayList<>();
        int iT = hashT.get(team);

        if(FN == null) {
            int maxPosiWin = w[iT] + r[iT];
            for(int i = 0; i < N; i++){
                if(i == iT) continue;
                if(w[i] > maxPosiWin) cert.add(teams[i]);
            }
            return cert;
        }
        else{
            FordFulkerson FF = new FordFulkerson(FN, 0, FN.V()-1);
            if(flow == FF.value()) return null;

            int indexI = (N-1) * (N-2) / 2;
            for(int i = 0; i < N; i++){
                if(i == iT) continue;
                indexI++;
                if(FF.inCut(indexI))
                    cert.add(teams[i]);
            }
            return cert;
        }
    }

    private FlowNetwork constructFN (String team){
        int iT = hashT.get(team); // interested team

        int totalTeam = N - 1;
        int totalGame = (N -1) * (N-2) / 2;
        int s = 0;
        int t = totalGame + totalTeam + 1;

        double maxPosiWin = w[iT] + r[iT];

        FlowNetwork FN = new FlowNetwork(2 + totalGame + totalTeam);

        // add edge
        int gameCount = 1;
        int indexI = totalGame;
        int indexJ = indexI;
        flow = 0;

        for(int i = 0; i < N; i++){
            if(i == iT) continue;
            indexI++;
            indexJ = indexI;


            // if definitely eliminated
            if(w[i] > maxPosiWin)
               return null;

            for(int j = i+1; j < N; j++){
                if(j == iT) continue;

                indexJ++;
                // add game edge
                FN.addEdge(new FlowEdge(s, gameCount, g[i][j]));
                flow += g[i][j]; // total possible win
                FN.addEdge(new FlowEdge(gameCount, indexI, Double.POSITIVE_INFINITY));
                FN.addEdge(new FlowEdge(gameCount, indexJ, Double.POSITIVE_INFINITY));

                gameCount++;
            }

            FN.addEdge(new FlowEdge(indexI, t, maxPosiWin - w[i]));
        }
        return FN;
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination(args[0]);

        //for(String s : division.teams())
        //    StdOut.println(s);

        //StdOut.println(division.against("Atlanta", "Philadelphia"));

        String team = "Boston";
        if (division.isEliminated(team)) {
            StdOut.print(team + " is eliminated by the subset R = { ");
            for (String t : division.certificateOfElimination(team)) {
                StdOut.print(t + " ");
            }
            StdOut.println("}");
        }
        else {
            StdOut.println(team + " is not eliminated");
        }

        /*for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            }
            else {
                StdOut.println(team + " is not eliminated");
            }
        }*/


    }
}