import java.util.*;
import java.io.*;

public class Main {
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line = br.readLine().split(" ");
		ArrayList<HashMap<Integer, Integer>> al = new ArrayList<>();
		int gv = toi(line[0]), e = toi(line[1]), k = toi(br.readLine());
		int[] dis = new int[gv];
		PriorityQueue<int[]> pq = new PriorityQueue<>((l, r) -> l[1] - r[1]);
		Arrays.fill(dis, Integer.MAX_VALUE);
		boolean[] visit = new boolean[gv];
		visit[k - 1] = true;

		for(int i = 0; i < gv; i++) {
			al.add(new HashMap<Integer, Integer>());
		}

		for(int i = 0; i < e; i++) {
			line = br.readLine().split(" ");
			int u = toi(line[0]), v = toi(line[1]), w = toi(line[2]);
			Integer v1 = al.get(u - 1).get(v - 1);
			if(v1 == null || w < v1) al.get(u - 1).put(v - 1, w);
		}

		for(Map.Entry<Integer, Integer> entry: al.get(k - 1).entrySet()) {
			dis[entry.getKey()] = entry.getValue();
			pq.add(new int[] { entry.getKey(), entry.getValue()});
		}
			
		while(!pq.isEmpty()) {
			int[] bottom = pq.poll();
			if(visit[bottom[0]]) continue;
			for(Map.Entry<Integer, Integer> entry: al.get(bottom[0]).entrySet()) 
				if(dis[bottom[0]] < dis[entry.getKey()] - entry.getValue()) {
					dis[entry.getKey()] = dis[bottom[0]] + entry.getValue();
					pq.add(new int[] {entry.getKey(), dis[entry.getKey()] });
				}
			visit[bottom[0]] = true;
		}

		for(int i = 0; i < dis.length; i++) {
			if(i == k - 1) sb.append(0).append("\n");
			else {
				if(dis[i] == Integer.MAX_VALUE) sb.append("INF\n");
				else sb.append(dis[i]).append("\n");
			}
		}

		System.out.print(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }	
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}