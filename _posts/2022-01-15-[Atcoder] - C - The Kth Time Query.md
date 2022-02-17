---
title: AtCoder. ABC 235 C - The Kth Time Query
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

[Link] <https://AtCoder.jp/contests/abc235/tasks/abc235_c>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static int max = 0, maxIdx = 0;
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		String[] line = getLine();
		StringBuilder sb = new StringBuilder();
		int n = toi(line[0]), q = toi(line[1]);
		HashMap<Integer, ArrayList<Integer>> hm = new HashMap<>();
		line = getLine();
		for(int i = 0; i < n; i++) {
			if(!hm.containsKey(toi(line[i]))) hm.put(toi(line[i]), new ArrayList<Integer>());
			hm.get(toi(line[i])).add(i + 1);
		}
		for(int i = 0; i < q; i++) {
			line = getLine();
			int x = toi(line[0]), k = toi(line[1]);
			if(!hm.containsKey(x) || hm.get(x).size() <= k - 1) sb.append("-1\n");
			else sb.append(hm.get(x).get(k - 1)).append("\n");
		}
		print(sb);
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```
