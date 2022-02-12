import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int test = toi(br.readLine());
		int[] arr;
		int[][] mex;
		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine()); //len of arry
			arr = getArr();
			mex = new int[n][n];
			for(int i = 0; i < n; i++) mex[i] = (i == 0 ? 1 : (i - 1));
			for(int diff = 1; diff < n; diff++) {
				for(int left = 0; left < n - diff; left++) {
					mex[left][left + diff] = getMex(mex[])
				}
			}
		}
		print(sb);
	}

	static int getMex(int i) {
		ireturn i - 1;
	}
	
	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}