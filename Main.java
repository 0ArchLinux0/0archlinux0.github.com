import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static int[] fdp;
	static int[] edp;
	static int[] w;
	static boolean[] visit;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int n = toi(br.readLine());
		String line = br.readLine();
		int[] pi = getPi(line);
		print(line.length() - pi[line.length() - 1]);
	}

	static int[] getPi(String s) {
		int l = 0, len = s.length();
		int[] pi = new int[len];
		for(int r = 1; r < len; r++) {
			while(s.charAt(l) != s.charAt(r) && l > 0) l = pi[l - 1];
			if(s.charAt(l) == s.charAt(r)) {
				pi[r] = ++l;
			}
		}
		return pi;
	}	

	
	
	
	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}