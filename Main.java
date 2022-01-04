import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = getLine();
		int max = Integer.MAX_VALUE;
		int v = toi(line[0]), e = toi(line[1]);
		int[][] dis =  new int[v][v];
		int[][] floyd = new int[v][v];
		for(int i = 0; i < v; i++) {
			Arrays.fill(floyd[i], max);
			floyd[i][i] = 0;
		}

		for(int i = 0; i < e; i++) {
			line = getLine();
			int a = toi(line[0]) - 1, b = toi(line[1]) - 1, c = toi(line[2]);
			dis[a][b] = c;
			floyd[a][b] = Math.min(floyd[a][b], c);
		}

		for(int mid = 0; mid < v; mid++) {
			for(int from = 0; from < v; from ++) {
				for(int to = 0; to < v; to++) {
					if(floyd[from][mid] != max && floyd[mid][to] != max 
					&& floyd[from][to] > floyd[from][mid] + floyd[mid][to]) floyd[from][to] = floyd[from][mid] + floyd[mid][to];
				}
			}
		}
		
		int ans = max;
		for(int i = 0; i < v; i++) {
			for(int j = 0; j < v; j++) {
				if(i == j) continue;
				if(floyd[i][j] != max && floyd[j][i] != max) ans = Math.min(ans, floyd[i][j] + floyd[j][i]);
			}
		}

		print(ans == max ? -1 : ans);
	}

	static class Edge{
		int from;
		int to;
		int dis;
		
		public Edge(int from, int to, int dis) {
			this.from = from;
			this.to = to;
			this.dis = dis;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
}