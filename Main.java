import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		while(!s.equals(".")) {
			// sb.append(solve(s)).append("\n");
			int[] pi = kmp(s);
			int commonlen = pi[s.length() - 1], chunkLen = s.length() - commonlen;
			if(s.length() % chunkLen != 0) {
				sb.append(1).append("\n"); 
				continue;
			}
			sb.append(s.length() / chunkLen).append("\n");
			s = br.readLine();
		}

		print(sb);
	}

	static int[] kmp(String p) {
		int l = 0;
		int[] kmp = new int[p.length()];
		for(int r = 1; r < p.length(); r++) {
			while(l > 0 && !(p.charAt(l) == p.charAt(r))) l = kmp[l - 1];
			if(p.charAt(l) == p.charAt(r)) kmp[r] = ++l;
		}
		return kmp;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}