import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static long[] seg;
	static int n;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int[] rl = getArr();
		int[] arr;
		n = rl[0];
		int q = rl[1];
		seg = new long[2*n];
		arr = getArr();
		init(arr);

		
		print(sb);
	}
	
	
	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}