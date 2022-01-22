import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int[] seg;
	static int[] orig;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int[] arr;
		int n = toi(br.readLine());
		int[] parent = new int[n];
		PriorityQueue<Edge> edges = new PriorityQueue<>();
		for(int i = 0; i < n; i++) parent[i] = i;
		Coor[] xx = new Coor[n];
		Coor[] yy = new Coor[n];
		Coor[] zz = new Coor[n];
		long ans = 0;

		for(int i = 0; i < n; i++) {
			arr = getArr();
			xx[i] = new Coor(arr[0], i);
			yy[i] = new Coor(arr[1], i);
			zz[i] = new Coor(arr[2], i);
		}	

		Arrays.sort(xx);
		Arrays.sort(yy);
		Arrays.sort(zz);

		for(int i = 0; i < n - 1; i++) {
			edges.add(new Edge(xx[i].idx, xx[i+1].idx, Math.abs(xx[i].coor - xx[i+1].coor)));
			edges.add(new Edge(yy[i].idx, yy[i+1].idx, Math.abs(yy[i].coor - yy[i+1].coor)));
			edges.add(new Edge(zz[i].idx, zz[i+1].idx, Math.abs(zz[i].coor - zz[i+1].coor)));
		}

		while(!edges.isEmpty()) {
			Edge edge = edges.poll();
			int p1 = getParent(parent, edge.v1), p2 = getParent(parent, edge.v2);
			if(p1 == p2) continue;
			if(p1 < p2) parent[p2] = p1;
			else parent[p1] = p2;
			ans += edge.val;
		}
		print(ans);
	}

	static class Coor implements Comparable<Coor> {
		int coor;
		int idx;
		public Coor(int coor, int idx) { this.coor = coor; this.idx = idx; }
		public int compareTo(Coor coor) {
			if(this.coor > coor.coor) return 1;
			if(this.coor == coor.coor) return 0;
			return -1;
		}
	}

	static int getParent(int[] parent, int idx) {
		if(parent[idx] == idx) return idx;
		return parent[idx] = getParent(parent, parent[idx]);
	}

	static class Edge implements Comparable<Edge> {
		int v1;
		int v2;
		int val;
		public Edge(int v1, int v2, int val) { this.v1 = v1; this.v2 = v2; this.val = val; }
		public int compareTo(Edge edge) {
			return Integer.compare(this.val, edge.val);
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}