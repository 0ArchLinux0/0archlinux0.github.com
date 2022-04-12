---
title: Codeforces Round 770 (Div. 2) A. Reverse and Concatenate
author: MINJUN PARK
date: 2022-02-07 23:35:00 +0900
categories: [Codeforces, Codeforces Round]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    Codeforces,
		Div 2,
    Reverse and Concatenate
  ]
pin: false
---

[Link] <https://codeforces.com/contest/1634/problem/A>

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
		int[] arr;
		ArrayList<Integer> al = new ArrayList<>();
		for(int iter = 0; iter < t; iter++) {
			arr = getArr();
			int n = arr[0], k = arr[1];
			String s = br.readLine();
			if(isPalindrome(s) || k == 0) {
				sb.append("1\n");
			} else {
				sb.append("2\n");
			}
		}
		print(sb);
	}

	static boolean isPalindrome(String s) {
		int l = 0, r = s.length() - 1;
		while(l < r) {
			if(s.charAt(l) != s.charAt(r)) return false;
			l++;
			r--;
		}
		return true;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
