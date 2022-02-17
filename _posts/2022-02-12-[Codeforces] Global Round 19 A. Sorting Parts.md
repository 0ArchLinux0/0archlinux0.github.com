---
title: Codeforces Global Round 19 A. Sorting Parts
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Codeforces, Codeforces Global Round]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
		Codeforces Global Round,
    Codeforces,
    Sorting Parts
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1637/problem/A>

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
			boolean sorted = true;
			for(int i = 0; i < n - 1; i++) {
				if(arr[i] > arr[i + 1]) sorted = false;
			}
			sb.append(sorted ? "NO\n" : "YES\n");
		}
		print(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
