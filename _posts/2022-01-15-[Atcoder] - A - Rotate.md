---
title: AtCoder. ABC 235 A - Rotate
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [AtCoder, ABC Contest]
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

[Link] <https://AtCoder.jp/contests/abc235/tasks/abc235_a>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		String input = br.readLine();
		int[] digit = new int[3];
		for(int i = 0; i < 3; i++) digit[i] = input.charAt(i) - '0';
		int ans = sum(digit[0], digit[1], digit[2]) + sum(digit[1], digit[2], digit[0]) + sum(digit[2], digit[0], digit[1]);
		println(ans);
	}

	static int sum(int a, int b, int c) {
		return 100 * a + 10 * b + c;
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static <T> void println(T s) { System.out.println(s); }
}
```
