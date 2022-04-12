---
title: AtCoder. Regular Contest 135 A Floor, Ceil Decomposition
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [AtCoder, Regular Contest]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    Regular Contest
  ]
pin: false
---

[Link] <https://AtCoder.jp/contests/arc135/tasks/arc135_a>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static HashMap<Long, Long> hm = new HashMap<>();
	static long mod = 998244353l;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		long x = tol(br.readLine());
		println(getMax(x));
	}

	static long getMax(long x) {
		if(hm.containsKey(x)) return hm.get(x);
		long lower = x / 2, upper = (x + 1) / 2;
		if((x & 1) == 1) {
			if(x >= 5) {
				long val = (getMax(lower) * getMax(upper)) % mod;
				hm.put(x, val);
				return val % mod;
			}
			else return x;
		} else {
			if(x >= 6) {
				long val = getMax(lower);
				val = (val * val) % mod;
				hm.put(x, val);
				return (val) % mod;
			}
			else return x;
		}
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
