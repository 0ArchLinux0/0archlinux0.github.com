import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	public static void main(String[] args) throws IOException {
		StringBuilder sb = new StringBuilder();
		Queue<Integer> q = new LinkedList<>();
		int[] line;
		int[][] step;
		int tot = toi(br.readLine());

		loop:
		for(int i = 0; i < tot; i++) {
			q.clear();
			line = getLine();
			int v = line[0], e = line[1];
			int[] group = new int[v];
			ArrayList<LinkedList<Integer>> al = new ArrayList<>();
			for(int j = 0; j < v; j++) al.add(new LinkedList<Integer>());
			for(int j = 0; j < e; j++) {
				line = getLine();
				al.get(line[0] - 1).add(line[1] - 1);
				al.get(line[1] - 1).add(line[0] - 1);
			}
			q.add(0);
			group[0] = 1;
			while(!q.isEmpty()) {
				int bottom = q.poll();
				for(int e1: al.get(bottom)) {
					if(group[e1] != 0) {
						if((group[bottom] & group[e1]) != 0) {
							sb.append("NO\n");
							continue loop;
						}
						continue;
					}
					group[e1] = (group[bottom] & 1) + 1; // group[bottom] == 1 ? 2 : 1
					q.add(e1);
				}
			}
			sb.append("YES\n");
		}
		print(sb);
	}
	// == is fater than &, |
	// + is faster than &, |

	static int toi(String s) { return Integer.parseInt(s); }	
	static int[] getLine() throws IOException{ 
		return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
	}
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}