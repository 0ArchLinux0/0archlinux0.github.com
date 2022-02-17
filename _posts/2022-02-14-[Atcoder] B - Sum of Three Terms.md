---
title: AtCoder. Regular Contest 135 B - Sum of Three Terms
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [AtCoder, Regular Contest]
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

[Link] <https://AtCoder.jp/contests/arc135/tasks/arc135_b>
<br>

## IDEA) First three element

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
		int n = toi(br.readLine());
		int[] arr = getArr();
		int iterThree = 0;
		long[] min = new long[3];
		long[] store = new long[3];
		for(int i = 0, len = arr.length; i < len - 1; i++) {
			int diff = arr[i + 1] - arr[i];
			store[iterThree] += diff;
			min[iterThree] = Math.min(min[iterThree], store[iterThree]);
			if(++iterThree == 3) iterThree = 0;
		}

		long initThreeSum = 0;
		for(long e: min) initThreeSum += -1 * e;
		if(initThreeSum > arr[0]) sb.append("No");
		else {
			long[] ans = new long[n + 2];
			ans[0] = -1 * min[0];
			ans[1] = -1 * min[1];
			ans[2] = arr[0] - ans[0] - ans[1];
			for(int i = 3; i < n + 2; i++) {
				ans[i] = ans[i - 3] + arr[i - 2] - arr[i - 3];
			}
			sb.append("Yes\n");
			for(long l : ans) sb.append(l).append(" ");
		}
		println(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static long tol(String s) { return Long.parseLong(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
