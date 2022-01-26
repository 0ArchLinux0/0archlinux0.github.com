import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int n = 0;
	static int[][] cost;
	static int[][] dp;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		// StringBuilder sb = new StringBuilder();
		int n = toi(br.readLine()), cnt = 0;
		for(int i = 0; i <= 6; i++) if((n & 1 << i) != 0) cnt++;
		print(cnt);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}