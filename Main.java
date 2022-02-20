import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		int t = toi(br.readLine());
		while(t-- > 0) {
			int n = toi(br.readLine());
			int[] arr = getArr();
			int secondLast = arr[n - 2], last = arr[n -1];
			if(secondLast > last) {
				sb.append("-1\n");
				continue;
			}
			if(last >= 0) {
				sb.append(n - 2).append("\n");
				for(int i = 0; i < n - 2; i++) {
					sb.append(i + 1).append(" ").append(n - 1).append(" ").append(n).append("\n");
				}
				continue;
			}
			boolean incArr = true;
			for(int i = 0; i < n - 1; i++) {
				if(arr[i] > arr[i+1]) {
					incArr = false;
					break;
				} 
			}
			sb.append(incArr ? "0\n" : "-1\n" );
		}
		print(sb);
	}


	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}