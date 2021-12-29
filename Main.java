import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String[] line = br.readLine().split(" ");
		int n = toi(line[0]), m = toi(line[1]);
		int[] rootIdx = new int[n + 1];
kak
		for(int i = 1 ; i <= n; i++) rootIdx[i] = i;

		for(int i = 0; i < m; i++) {
			line = br.readLine().split(" ");
			int a = toi(line[1]), b = toi(line[2]);
			if(line[0].equals("0")) {
				rootIdx[getRoot(rootIdx, a)] = getRoot(rootIdx, b);
 			} else {
				if(a == b) sb.append("YES\n");
				else sb.append(getRoot(rootIdx, a) == getRoot(rootIdx, b) ? "YES\n" : "NO\n");
			}
		}
		print(sb);
	}

	static int getRoot(int[] arr, int idx) {
		if(idx == arr[idx]) return idx;
		return arr[idx] = getRoot(arr, arr[idx]);
	}

	static int toi(String s) { return Integer.parseInt(s); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}