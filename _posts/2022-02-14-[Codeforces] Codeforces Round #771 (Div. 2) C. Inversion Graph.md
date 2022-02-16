---
title: Codeforces Round 771 (Div. 2) C. Inversion Graph
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
    Codeforces,
    Inversion Graph
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1638/problem/C>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int test = toi(br.readLine());
		int[] arr;
		int[] parent;
		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine()), max = -1, groupCnt = 0;
			arr = getArr();
			int[] idx = new int[n + 1];
			for(int i = 0; i < n; i++) idx[arr[i]] = i + 1;
			for(int i = 0; i < n; i++) {
				max = Math.max(max, idx[i]);
				if(max == i) groupCnt++;
			}
			sb.append(groupCnt).append("\n");
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
```
