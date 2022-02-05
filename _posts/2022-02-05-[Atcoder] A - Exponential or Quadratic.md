---
title: Atcoder. ABC 238 A - Exponential or Quadratic
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Atcoder, Java]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    Atcoder,
    ABC contest
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/abc238/tasks/abc238_a>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	static int div =  1000000003;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int n = toi(br.readLine());
		if(n > 4 || n == 1) println("Yes");
		else println("No");	
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```