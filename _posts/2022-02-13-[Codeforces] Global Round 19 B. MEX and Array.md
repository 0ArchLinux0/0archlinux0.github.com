---
title: Codeforces Global Round 19 B. MEX and Array
author: MINJUN PARK
date: 2022-02-13 11:30:00 +0900
categories: [Codeforces, Codeforces Global Round]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    Codeforces,
		Codeforces Global Round,
    review,
    Greedy,
    Math,
    MEX and Array,
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1637/problem/B>

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
		String line;
		int[] arr;
		int test = toi(br.readLine());

		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine());
			arr = getArr();
			ArrayList<Integer> zeroIdxList = new ArrayList<>();
			int ans = n * (n + 1) * (n + 2) / 6;
			for(int i = 0; i < n; i++) if(arr[i] == 0) zeroIdxList.add(i);
			ans += calc(zeroIdxList, n);
			sb.append(ans).append("\n");
		}
		print(sb);
	}

	static int calc(ArrayList<Integer> al, int n) {
		int sum = 0;
		for(int idx: al) sum += (1 + idx) * (n - idx);
		return sum;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
