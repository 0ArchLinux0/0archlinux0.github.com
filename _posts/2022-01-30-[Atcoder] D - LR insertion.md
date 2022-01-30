---
title: Atcoder. ABC 237 D - LR insertion
author: MINJUN PARK
date: 2022-01-30 21:50:00 +0900
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

[Link] <https://atcoder.jp/contests/abc237/tasks/abc237_d>
<br>

```java
import java.util.*;
import java.io.*;

public class Main {
	static BufferedReader br;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		br = new BufferedReader(new InputStreamReader(System.in));
		int n = toi(br.readLine());
		String s = br.readLine();
		Node[] nodes = new Node[n + 1];
		for(int i = 0; i <= n; i++) nodes[i] = new Node(i);

		for(int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if(ch == 'L') {
				nodes[i].left = nodes[i + 1]; 
			} else nodes[i].right = nodes[i + 1];
		}

		solve(nodes[0]);
		println(sb);
	}

	static void solve(Node node) {
		if(node.left != null) solve(node.left);
		sb.append(node.idx).append(" ");
		if(node.right != null) solve(node.right);
	}

	static class Node{
		Node left;
		Node right;
		int idx;
		Node(int idx) { this. idx = idx; }
	}

	static int toi(String s) { return Integer.parseInt(s); }
	static String[] getLine() throws IOException { return br.readLine().split(" "); }	
	static int[] getArr() throws IOException { return Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray(); }
	static <T> void print(T s) { System.out.print(s); }
	static <T> void println(T s) { System.out.println(s); }
}
```