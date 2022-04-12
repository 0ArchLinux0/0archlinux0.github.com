---
title: AtCoder. Regular Contest 135 C XOR to All
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    XOR,
    Regular Contest
  ]
pin: false
---

[Link] <https://AtCoder.jp/contests/arc135/tasks/arc135_c>
<br>

## IDEA

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		var n = toi(br.readLine());
		var arr = getArr();
		var bitCnt = new int[30];
		var sum = 0l;

		for(int e: arr) {
			int digit = 0;
			while(e > 0) {
				if((e & 1) == 1) bitCnt[digit]++;
				digit++;
				e >>= 1;
			}
		}


		for(var e: arr) sum += e;
		for(var i = 0; i < n; i++) {
			long tmp = 0;
			for(var j = 0; j < 30; j++) {
				if((arr[i] & 1 << j) == 0) tmp += (1 << j) * bitCnt[j];
				else tmp += (1 << j) * (n - bitCnt[j]);
			}
			sum = Math.max(sum, tmp);
		}
		println(sum);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
