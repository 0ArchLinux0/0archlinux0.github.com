---
title: AtCoder. ABC 237 C - kasaka
author: MINJUN PARK
date: 2022-01-30 21:20:00 +0900
categories: [AtCoder, Java]
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

[Link] <https://AtCoder.jp/contests/abc237/tasks/abc237_c>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();
		int l = -1, r = s.length();
		int rlen = 0, llen = 0;

		for(int i = 0; i < s.length(); i++) {
			if(s.charAt(i) != 'a') {
				break;
			} else l = i;
		}

		for(int i = s.length() - 1; i >= 0; i--) {
			if(s.charAt(i) != 'a') {
				break;
			} else r = i;
		}

		if(r <= l) {
			println("Yes");
			return;
		}

		if(l > s.length() - 1 - r) {
			println("No");
			return;
		}

		l++;
		r--;

		while(l < r) {
			if(s.charAt(l) != s.charAt(r)) {
				println("No");
				return;
			}
			l++;
			r--;
		}
		println("Yes");
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
