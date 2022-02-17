---
title: AtCoder. ABC 235 E - MST + 1
author: MINJUN PARK
date: 2022-01-16 02:00:00 +0900
categories: [AtCoder, ABC Contest]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    ABC contest
  ]
pin: false
---

[Link] <https://AtCoder.jp/contests/abc235/tasks/abc235_e>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		String[] line = getLine();
		int n = toi(line[0]), m = toi(line[1]), q = toi(line[2]);
		ArrayList<int[]> edges = new ArrayList<>();
		for(int i = 0; i < m; i++) {
			line = getLine();
			int a = toi(line[0]) - 1, b = toi(line[1]) - 1, c = toi(line[2]);
			edges.add(new int[] { a, b , c, -1 });
		}

		for(int i = 0; i < q; i++) {
			line = getLine();
			int u = toi(line[0]) - 1, v = toi(line[1]) - 1, w = toi(line[2]);
			edges.add(new int[] { u, v, w, i });
		}

		edges.sort((l, r) -> l[2] - r[2]);
		boolean[] isTrue = new boolean[q]; // to store answers

		int[] parent = new int[n];
		for(int i = 0; i < n; i++) parent[i] = i;
		for(int[] cur: edges) {
			int from = cur[0], to = cur[1], cost = cur[2], queryIdx = cur[3];
			boolean isQuery = queryIdx == -1 ? false : true;
			int pfrom = getParent(parent, from), pto = getParent(parent, to);
			if(pfrom == pto) continue;
			if(isQuery) {
				isTrue[queryIdx] = true;
				continue;
			}
			if(pfrom > pto) parent[pto] = pfrom;
			else parent[pfrom] = pto;
		}
		for(boolean b : isTrue) sb.append(b ? "Yes" : "No").append("\n");
		print(sb);
	}

	static int getParent(int[] arr, int idx) {
		if(arr[idx] == idx) return idx;
		return arr[idx] = getParent(arr, arr[idx]);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
