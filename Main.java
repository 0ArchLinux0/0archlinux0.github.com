import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line;
		int n = toi(br.readLine()), w = toi(br.readLine());
		int[][] dp = new int[w + 1][w + 1]; // 0 ~ w
		// for(int[] ar: dp) Arrays.fill(ar, Integer.MAX_VALUE);
		ArrayList<int[]> works = new ArrayList<>();
		works.add(new int[] { 1, 1 });

		for(int i = 1; i <= w; i++) {
			line = getLine();
			int ii = toi(line[0]), jj = toi(line[1]);
			works.add(new int[] { ii, jj } );
		}


		int[] tempt = works.get(0);

		sb.append(getMax(dp, 0, 0, w, works, n)).append("\n");

		int ii1 = 0, jj1 = 0, ii2 = 0, jj2 = 0;
		int ii = 0, jj = 0;
		int p1i = 1, p1j = 1, p2i = n, p2j = n;
		for(int i = 1; i <= w; i++) {
			int toI = works.get(i)[0], toJ = works.get(i)[1];
			int disJ = jj == 0 ? dis(toI, toJ, n, n) : dis(toI, toJ, works.get(jj)[0], works.get(jj)[1]);
			if(dp[ii][jj] == dp[ii][i] + disJ) {
				jj = i;
				sb.append("2\n");
			} else {
				ii = i;
				sb.append("1\n"); 
			}
		}
		print(sb);
	}

	static int getMax(int[][] dp, int a, int b, int maxLen, ArrayList<int[]> works, int n) {
		if(a == maxLen || b == maxLen) return 0;
		if(dp[a][b] != 0) return dp[a][b];
		int nextStep = Math.max(a, b) + 1;
		int ii = works.get(nextStep)[0], jj = works.get(nextStep)[1];
		int dis2 = b == 0 ? dis(n, n, ii, jj) : dis(works.get(b)[0], works.get(b)[1], ii, jj);
		return dp[a][b] = Math.min(
			getMax(dp, a, nextStep, maxLen, works, n) + dis2,
			getMax(dp, nextStep, b, maxLen, works, n) + dis(works.get(a)[0], works.get(a)[1], ii, jj)
		);
	}

	static int dis(int i1, int j1, int i2, int j2) {
		return Math.abs(i1 - i2) + Math.abs(j1 - j2);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}