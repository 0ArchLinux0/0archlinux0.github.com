---
title: Codeforces Round 770 (Div. 2). B. Fortune Telling
author: MINJUN PARK
date: 2022-02-07 23:35:00 +0900
categories: [Codeforces, Java]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    Codeforces,
    Fortune Telling
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1634/problem/B>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int t = toi(br.readLine());
		long[] arr;
		for(int iter = 0; iter < t; iter++) {
			arr = getArr2();
			long n = arr[0], x = arr[1], y = arr[2];
			//n => y
			int[] a = getArr();
			boolean yIsOdd = (y & 1) == 1;
			boolean xIsOdd = (x & 1) == 1;
			boolean parityChange = false;
			for(int e : a) {
				if((e & 1) == 1) parityChange = !parityChange;
			} 

			if(yIsOdd == xIsOdd) {
				sb.append(parityChange ? "Bob" : "Alice").append("\n");
			}
			else sb.append(parityChange ? "Alice" : "Bob").append("\n");
		}
		print(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static long[] getArr2() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToLong(Long::parseLong).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```