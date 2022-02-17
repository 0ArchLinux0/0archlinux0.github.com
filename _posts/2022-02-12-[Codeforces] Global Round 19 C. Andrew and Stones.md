---
title: Codeforces Global Round 19 C. Andrew and Stones
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
    Andrew and Stones
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1637/problem/C>

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
			if(n == 3) { // l (odd) r
				sb.append((arr[1] & 1) == 1 ? -1 : arr[1]/2).append("\n");;
			} else { // l 1...1 r
				boolean allOne = true;
				int oddCnt = 0;
				long sum = 0;
				for(int i = 1; i < n-1; i++) {
					if(arr[i] != 1) allOne = false;
					if((arr[i] & 1) == 1) oddCnt++;
					sum += arr[i];
				}
				if(allOne) sb.append(-1);
				else sb.append((oddCnt + sum) / 2);
				sb.append("\n");
			}
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
