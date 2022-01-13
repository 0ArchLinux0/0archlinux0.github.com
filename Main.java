import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String line1 = br.readLine(), line2 = br.readLine();
		int l1 = line1.length(), l2 = line2.length();
		int[][] dp = new int[l1][l2]; // dp[i][j]: line1 0 ~ i line 0 ~ j
		
		for(int i = 0; i < l1; i++) {
			for(int j = 0; j < l2; j++) {
				if(line1.charAt(i) == line2.charAt(j)) {
					if(i >= 1 && j >= 1) dp[i][j] = dp[i-1][j-1] + 1;
					else dp[i][j] = 1;
				} else {
					int v1 = i > 0 ? dp[i - 1][j] : 0;
					int v2 = j > 0 ? dp[i][j - 1] : 0;
					dp[i][j] = Math.max(v1, v2);
				}
			}
		}
		println(dp[l1-1][l2-1]);
		Stack<Integer> stack = new Stack<>();
		int store = dp[l1-1][l2-1];
		int ii = l1-1, jj = l2-1;
		while(ii >= 0 && jj >=0) {
			if(dp[ii][jj] == 0) break;
			if(line1.charAt(ii) == line2.charAt(jj)) {
				sb.insert(0, line1.charAt(ii));
				ii--;
				jj--;
				continue;
			}
			int upVal = ii > 0 ? dp[ii-1][jj] : 0;
			int preVal = jj > 0 ? dp[ii][jj-1] : 0;
			int max = 0;
			boolean goUp = true;
			if(upVal > preVal) max = upVal;
			else {
				max = preVal;
				goUp = false;
			}
			if(goUp) ii--;
			else jj--;
		}
		print(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}