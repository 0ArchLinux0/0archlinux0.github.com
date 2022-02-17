---
title: Codeforces Round 771 (Div. 2) D. Big Brush
author: MINJUN PARK
date: 2022-02-14 23:35:00 +0900
categories: [Codeforces, Java]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
		Div 2, 
		Codeforces Round.
    Codeforces,
    Big Brush
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1638/problem/D>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static int n, m;
	static int[][] graph;
	static int[] di = new int[]{0, 1, 0, 1};
	static int[] dj = new int[]{0, 0, 1, 1};
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		sb = new StringBuilder();
		int[] arr = getArr();
		n = arr[0];
		m = arr[1];
		graph = new int[n][m];
		for(int i = 0; i < n; i++) graph[i] = getArr();
		Queue<Point> q = new LinkedList<>();
		Stack<History> history = new Stack<>();
		int[] dy = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
		int[] dx = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};
		boolean[][] visit = new boolean[n][m];
		int cnt = 0;

		findSquare(q);

		while(!q.isEmpty()) {
			Point point = q.poll();
			int ci = point.i, cj = point.j;
			if(visit[ci][cj]) continue;
			visit[ci][cj] = true;
			history.add(new History(point, point.color));
			fillSquare(point);
			cnt++;
			for(int i = 0; i < 8; i++) {
				int ai = ci + dy[i], aj = cj + dx[i];
				int color = checkSquare(ai, aj);
				if(color == -1) continue;
				Point tmp = new Point(ai, aj, color);
				if(!visit[ai][aj]) q.add(tmp);
			}
		}

		if(checkIfClear()) {
			sb.append(cnt).append("\n");
			while(!history.isEmpty()) {
				History h = history.pop();
				sb.append(h.point.i + 1).append(" ").append(h.point.j + 1).append(" ").append(h.color).append("\n");
			}
		} else {
			sb.append(-1);
		}
		print(sb);
	}

	static boolean checkIfClear() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(graph[i][j] != 0 && graph[i][j] != -1) return false;
			}
		}
		return true;
	}

	static void fillSquare(Point p) {
		for(int i = 0; i < 4; i++) {
			int ii = p.i + di[i], jj = p.j + dj[i];
			graph[ii][jj] = -1;
		}
	}

	static int checkSquare(int ii, int jj) {
		if(!isValid(ii, jj)) return -1;
		int color = -1;
		int cnt = 0;
		int[] val = new int[4];
		for(int i = 0; i < 4; i++) val[i] = graph[ii + di[i]][jj + dj[i]];

		for(int i = 0; i < 4; i++) {
			if(color == -1) {
				if(color != val[i]) {
					color = val[i];
					cnt++;
				}
			}
			else {
				if(val[i] != -1 && color != val[i]) return -1;
				else cnt++;
			}
		}
		return color;
	}

	static void findSquare(Queue<Point> q) {
		for(int i = 0; i < n - 1; i++) {
			for(int j = 0; j < m - 1; j++) {
				if(
					graph[i][j] == graph[i + 1][j] &&
					graph[i][j] == graph[i][j + 1] &&
					graph[i][j] == graph[i + 1][j + 1]
				) {
					q.add(new Point(i, j, graph[i][j]));
				}
			}
		}
	}

	static boolean isValid(int ii, int jj) {
		if(ii < 0 || jj < 0) return false;
		if(ii + 1 >= n || jj + 1 >= m) return false;
		return true;
	}

	static class Point {
		int i, j, color;
		public Point(int i, int j, int color) { this.i = i; this.j = j; this.color = color; }
	}

	static class History {
		Point point;
		int color;
		public History(Point point, int color) {
			this.point = point;
			this.color = color;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
