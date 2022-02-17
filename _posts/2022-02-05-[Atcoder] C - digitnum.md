---
title: AtCoder. ABC 238 C - digitnum
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
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

[Link] <https://AtCoder.jp/contests/abc238/tasks/abc238_c>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static int div = 998244353;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		long n = tol(br.readLine()), ndiv = n % div;
		long sum = (ndiv * (ndiv + 1) / 2) % div;
		long ans = sum  - getDigit(n) + ndiv;
		if(ans < 0) ans += div;
		println(ans % div);
	}

	static long getDigit(long n) {
		int len = String.valueOf(n).length();
		long tenExp = 1l, remainder = 0l, tenExpDiv = 0l;
		for(int i = 1; i < len; i++) {
			tenExpDiv = tenExp % div;
			remainder += (((tenExpDiv * tenExpDiv) % div) * 9) % div;
			remainder %= div;
			tenExp = tenExp * 10;
		}
		tenExpDiv = tenExp % div;
		remainder += (tenExpDiv * ((n - tenExp + 1) % div)) % div;
		remainder %= div;
		return remainder;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
