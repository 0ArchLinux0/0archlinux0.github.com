import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		// String[] line = getLine();
		String line1 = br.readLine(), line2 = br.readLine();
		String[][] dp = new String[line1.length()][line2.length()];
		for(String[] tmp : dp) Arrays.fill(tmp, "");
		String result = lcs(line1, line2, 0, 0, dp);
		if(result.length() == 0) sb.append("0");
		else sb.append(result.length()).append("\n").append(result);
		print(sb); 
	}

	static String lcs(String line1, String line2, int idx1, int idx2, String[][] dp) {
		if(idx1 >= line1.length() || idx2 >= line2.length()) return ""; 
		if(dp[idx1][idx2].length() != 0) return dp[idx1][idx2];
		if(line1.charAt(idx1) == line2.charAt(idx2)) 
			return dp[idx1][idx2] = line1.charAt(idx1) + lcs(line1, line2, idx1 + 1, idx2 + 1, dp);
		String s1 = lcs(line1, line2, idx1 + 1, idx2, dp), s2 = lcs(line1, line2, idx1, idx2 + 1, dp);
		return dp[idx1][idx2] = s1.length() > s2.length() ? s1 : s2;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}