---
title: Codeforces Round 771 (Div. 2) A. Reverse
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
    Reverse
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1638/problem/A>

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
		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine());
			arr = getArr();
			int l = 0, r = 0, cnt = 1;
			int[] idx = new int[n + 1];
			for(int i = 0; i < n; i++) idx[arr[i]] = i + 1;
			for(int i = 1; i <= n; i++)
				if(idx[i] != i) {
					r = i;
					break;
				} else {
					l++;
				}
			while(l < r) {
				int tmp = arr[l];
				arr[l] = arr[r];
				arr[r] = tmp;
				l++;
				r--;
			}
			for(int e: arr) sb.append(e).append(" ");
			sb.append("\n");
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
