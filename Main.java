import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int[] arr = getArr();
		int n = arr[0], m = arr[1];
		int[][] map = new int[n][m];
    Point[][] parent = new Point[n][m];
		PriorityQueue<Edge> pq = new PriorityQueue<>((l, r) -> Integer.compare(l.val, r.val));
    for(int i = 0; i < n; i++) {
      map[i] = getArr();
    }

    for(int i = 0; i < n; i++) 
      for(int j = 0; j < m; j++)
				parent[i][j] = new Point(i , j);

    for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(map[i][j] == 0) continue;
				if(i > 0 && map[i - 1][j] == 1) {
					union(getParent(parent, i, j), getParent(parent, i - 1, j));
				} if(j > 0 && map[i][j - 1] == 1) {
					union(getParent(parent, i, j), getParent(parent, i, j - 1));
				}
			}
		}

		int ans = 0;
		int[] di = { -1, 0, 1, 0 };
		int[] dj = { 0, 1, 0, -1 };
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(map[i][j] == 1) continue;
				int[] dis = new int[4];
				for(int iter = 0; iter < 4; iter++) {
					int ii = i, jj = j, cnt = 0;
					while(true) {
						ii += di[iter];
						jj += dj[iter];
						cnt++;
						if(ii >= n || ii < 0 || jj >= m || jj < 0) break;
						if(map[ii][jj] == 1) {
							dis[iter] = cnt; 
							break;
						}
					}
				}
				if(dis[0] != 0 && dis[2] != 0 && dis[0] + dis[2] >= 3) {
						pq.add(new Edge(new Point(i - dis[0], j), new Point(i + dis[2], j), dis[0] + dis[2] - 1));
				}
				if(dis[1] != 0 && dis[3] != 0 && dis[1] + dis[3] >= 3) {
					pq.add(new Edge(new Point(i, j + dis[1]), new Point(i, j - dis[3]), dis[1] + dis[3] - 1));
				}
			}
		}

		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			Point p1 = getParent(parent, edge.v1.i , edge.v1.j), p2 = getParent(parent, edge.v2.i, edge.v2.j);
			if(p1.i == p2.i && p1.j == p2.j) continue;
			ans += edge.val;
			union(p1, p2);
		}

		Point topParent = null;
		boolean connected = true;

		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(map[i][j] == 0) continue;
				if(topParent == null) {
					topParent = getParent(parent, i, j);
					continue;
				}
				Point p = getParent(parent, i, j);
				if(p.i == topParent.i && p.j == topParent.j) continue;
				connected = false;
			}
		}
		println(connected ? ans : -1);
		print(getParent(parent, 1, 2).i + " " + getParent(parent, 1, 2).j);
	}
	static class Point {
    int i, j;
    public Point(int i, int j) { this.i = i; this.j = j; }
  }

	static Point getParent(Point[][] parent, int ii, int jj) {
		Point p = parent[ii][jj];
		if(p.i == ii && p.j == jj) return p;
		return parent[ii][jj] = getParent(parent, p.i, p.j); 
	}

	static void union(Point p1, Point p2) {
		int i1 = p1.i, j1 = p1.j, i2 = p2.i, j2 = p2.j;

		if(i1 == i2 && j1 == j2) return;
		if(i1 > i2) {
			p1.i = i2;
			p1.j = j2;
		} else if(i1 < i2) {
			p2.i = i1;
			p2.j = j1;
		} else {
			if(j1 > j2) p1.j = j2;
			else p2.j = j1;
		}
	}

	static class Edge {
		Point v1;
		Point v2;
		int val;
		public Edge(Point v1, Point v2, int val) { this.v1 = v1; this.v2 = v2; this.val = val; }
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}