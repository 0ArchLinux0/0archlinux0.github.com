---
title: AtCoder. ABC 235 B Climbing Takahashi
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
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

[Link] <https://AtCoder.jp/contests/abc235/tasks/abc235_b>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int n = toi(br.readLine());
		String[] line = getLine();
		int val = 0;
		for(int i = 0; i < n; i++) {
			if(val < toi(line[i])) val = toi(line[i]);
			else break;
		}
		println(val);
	}

	static int sum(int a, int b, int c) {
		return 100 * a + 10 * b + c;
	}


	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static <T> void println(T s) { System.out.println(s); }
}
```
