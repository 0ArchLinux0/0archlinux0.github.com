import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int test = toi(br.readLine());
		int[] arr;
		int[] deg;
		ArrayList<ArrayList<Integer>> edges = new ArrayList<>();

		loop:
		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine());
			arr = getArr();
			deg = new int[n + 1];
			edges.clear();
			for(int i = 0; i <= n; i++) edges.add(new ArrayList<Integer>());

			for(int i = 0; i < n; i++) {
				deg[arr[i]] = i;
				for(int j = i + 1; j < n; j++) edges.get(arr[i]).add(arr[j]);
			}

			int m = toi(br.readLine());

			for(int i = 0; i < m; i++) {
				arr = getArr();
				int a = arr[0], b = arr[1];
				if(edges.get(a).contains(b)) {
					edges.get(a).remove((Integer) b);
					edges.get(b).add(a);
					deg[a]++;
					deg[b]--;
				} else {
					edges.get(b).remove((Integer) a);
					edges.get(a).add(b);
					deg[b]++;
					deg[a]--;
				}
			}

			boolean[] visit = new boolean[n + 1];
			Queue<Integer> q = new LinkedList<>();
			StringBuilder sbTmp = new StringBuilder();
			int groupNum = 0;
			
			for(int i = 1; i <= n; i++) {
				if(visit[i] || deg[i] != 0) continue;
				groupNum++;
				q.add(i);

				while(!q.isEmpty()) {
					int cur = q.poll();
					if(visit[cur]) continue;
					else {
						visit[cur] = true;
						sbTmp.append(cur).append(" ");
					}
					for(int v : edges.get(cur)) {
						if(visit[v]) continue;
						if(--deg[v] == 0) q.add(v);
					}
				}
			}

			boolean visitAll = true;
			for(int i = 1; i <= n; i++) 
				if(!visit[i]) {
					visitAll = false;
					break;
				}

			if(!visitAll) sb.append("IMPOSSIBLE\n");
			else if(groupNum == 1) sb.append(sbTmp).append("\n");
			else sb.append("?\n");
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