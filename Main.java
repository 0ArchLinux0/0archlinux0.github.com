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
		int l1 = line1.length(), l2 = line2.length();
		String[][] dp = new String[l1][l2]; // dp[i][j]: line1 0 ~ i line 0 ~ j
		
		for(int i = 0; i < l1; i++) {
			for(int j = 0; j < l2; j++) {
				if(line1.charAt(i) == line2.charAt(j)) {

				}
			}
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}