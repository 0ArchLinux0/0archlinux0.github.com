import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int[] arr;
		// int[] count;
		int t = toi(br.readLine());
		HashMap<Integer, Integer> hm = new HashMap<>();
		while(t-- > 0) {
			arr = getArr();
			int n = arr[0], x = arr[1];
			hm.clear();
			arr = getArr();
			for(int e: arr) {
				if(hm.containsKey(e)) {
					hm.put(e, hm.get(e) + 1);
				} else {
					hm.put(e, 1);
				}
			}
			HashSet<Integer> checked = new HashSet<>();
			ArrayList<HashMap.Entry<Integer, Integer>> al = new ArrayList<>(hm.entrySet());
			al.sort((l, r) -> l.getKey() - r.getKey());
			for(HashMap.Entry<Integer, Integer> entry : al) {
				int key = entry.getKey(), value = entry.getValue();
				if(key % x == 0 && hm.containsKey(key / x)) {
					int min = Math.min(value, hm.get(key/x));
					hm.put(key/x, hm.get(key/x) - min);
					hm.put(key, value - min);
				} 
			}
			int cnt = 0;
			for(int value : hm.values()) cnt += value;
			sb.append(cnt).append("\n");
		}
		print(sb);
	}

	// static int[] shrink(int e, int x) {
	// 	boolean plus = true;
	// 	while(e % x == 0) {
	// 		e /= x;
	// 		plus = !plus;
	// 	}
	// 	return new int[] {e, plus ? 1 : -1 };
	// }

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
