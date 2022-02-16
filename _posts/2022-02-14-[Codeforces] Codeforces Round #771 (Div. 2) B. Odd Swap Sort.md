---
title: Codeforces Round 771 (Div. 2) B. Odd Swap Sort
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
    Odd Swap Sort
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1638/problem/B>

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
		loop:
		for(int iter = 0; iter < test; iter++) {
			int n = toi(br.readLine());
			arr = getArr();
			// ArrayList<Integer> sorted = new ArrayList<>();
			int oddMax = 0, evenMax = 0;
			for(int i = 0; i < n; i++) {
				int num = arr[i];
				if((num & 1) == 0) {
					if(evenMax > num) {
						sb.append("No\n");
						continue loop;
					}
					evenMax = Math.max(evenMax, num);
				} else {
					if(oddMax > num) {
						sb.append("No\n");
						continue loop;
					}
					oddMax = Math.max(oddMax, num);
				}
			}
			sb.append("Yes\n");
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
