import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line;
		int t = toi(br.readLine());

		for(int iter = 0; iter < t; iter++) {
			line = getLine();
			int n = toi(line[0]), m = toi(line[1]), k = toi(line[2]);
			int[][] dp = new int[n][100001];
			for(int[] dpar: dp) Arrays.fill(dpar, Integer.MAX_VALUE);
			ArrayList<ArrayList<Ticket>> tickets = new ArrayList<>();
			for(int i = 0; i < n; i++) {
				tickets.add(new ArrayList<Ticket>());
				Arrays.fill(dp[i], Integer.MAX_VALUE);
			}
			PriorityQueue<int[]> pq = new PriorityQueue<>((l, r) -> l[1] - r[1]);

			for(int i = 0; i < k; i++) {
				line = getLine();
				int u = toi(line[0]) - 1, v = toi(line[1]) - 1, c = toi(line[2]), d = toi(line[3]);
				tickets.get(u).add(new Ticket(v, c, d));
			}

			dp[0][0] = 0;
			boolean canGo = false;
			pq.add(new int[] { 0, 0, 0 });

			while(!pq.isEmpty()) {
				int[] cur = pq.poll();
				int curIdx = cur[0], curTime = cur[1], curCost = cur[2];
				if(dp[curIdx][curCost] < curTime) continue;
				if(curIdx == n - 1) {
					sb.append(curTime+"\n");
					canGo = true;
					break;
				}
				for(Ticket ticket: tickets.get(curIdx)) {
					int tcost = curCost + ticket.cost, ttime = curTime + ticket.time;
					if(tcost > m) continue;
					if(ttime < dp[ticket.dest][tcost]) {
							dp[ticket.dest][tcost] = ttime;
							pq.add(new int[]{ ticket.dest, dp[ticket.dest][tcost], tcost });
					} 
					for(int cost = tcost + 1; cost <= m; cost++) {
						if(ttime < dp[ticket.dest][cost]) 
							dp[ticket.dest][cost] = ttime;
						else break;
					}
				}
			}
			if(!canGo) sb.append("Poor KCM\n");
		}
		print(sb);
	}

	static class Tickets {
		LinkedList<Ticket> list = new LinkedList<>();		
	}

	static class Ticket {
		int dest;
		int cost;
		int time;

		public Ticket(int dest, int cost, int time) {
			this.dest = dest;
			this.cost = cost; 
			this.time = time;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static <T> void print(T s) { System.out.print(s); }
}