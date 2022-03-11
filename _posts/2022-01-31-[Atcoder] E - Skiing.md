---
title: AtCoder. ABC 237 E Skiing
author: MINJUN PARK
date: 2022-01-31 08:48:00 +0900
categories: [AtCoder, ABC Contest]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    Graph,
    Dijkstra,
    review,
    ABC contest
  ]
pin: false
---

[Link] <https://AtCoder.jp/contests/abc237/tasks/abc237_e>
<br>
Consider the following apth: Space 0 => Space x
Path: upper, lower distance: U, D
=> happiness: D - 2 \* U
=> To maximize happiness : minimize U
=> PriorityQue<Edge>: sort with upper distance
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static int[] h;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = getArr();
		int n = arr[0], m = arr[1];
		ArrayList<ArrayList<Integer>> al = new ArrayList<>();
		for(int i = 0; i < n; i++) al.add(new ArrayList<Integer>());

		h = getArr();
		for(int i = 0; i < m; i++) {
			arr = getArr();
			int a = arr[0] - 1, b = arr[1] - 1;
			al.get(a).add(b);
			al.get(b).add(a);
		}

		PriorityQueue<Edge> pq = new PriorityQueue<>((l, r) -> Long.compare(l.elevate, r.elevate));
		long max = 0;
		boolean[] visit = new boolean[n];
		long[] dijk = new long[n];
		Arrays.fill(dijk, Long.MIN_VALUE);
		dijk[0] = 0;
		pq.add(new Edge(0, 0, 0));

		while(!pq.isEmpty()) {
			Edge edge = pq.poll();
			if(visit[edge.to]) continue;
			visit[edge.to] = true;

			for(int e: al.get(edge.to)) {
				if(visit[e]) continue;

				if(h[e] >= h[edge.to]) {
					if(dijk[e] > dijk[edge.to] + getVal(edge.to, e)) continue;
					dijk[e] = dijk[edge.to] + getVal(edge.to, e);
					pq.add(new Edge(edge.to, e, edge.elevate + h[e] - h[edge.to]));
					if(dijk[e] > max) max = dijk[edge.to];
				} else {
					if(dijk[e] > dijk[edge.to] + getVal(edge.to, e)) continue;
					dijk[e] = dijk[edge.to] + getVal(edge.to, e);
					pq.add(new Edge(e, e, edge.elevate));
					if(dijk[e] > max) max = dijk[e];
				}
			}
		}

		print(max);
	}

	static int getVal(int from, int to) {
		if(h[from] == h[to]) return 0;
		if(h[from] > h[to]) return h[from] - h[to];
		return 2 * (h[from] - h[to]);
	}

	static class Edge{
		int from;
		int to;
		long elevate;

		Edge(int from, int to, long elevate) {
			this.from = from;
			this.to =to;
			this.elevate = elevate;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
