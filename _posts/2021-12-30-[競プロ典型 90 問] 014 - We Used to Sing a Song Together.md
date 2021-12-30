---
title: Atcoder. 014 - We Used to Sing a Song Together(3)
author: MINJUN PARK
date: 2021-12-30 03:00:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    We Used to Sing a Song Together
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_n>

<br>

```java
import java.util.*;
import java.io.*;

public class Main{
  static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  static StringTokenizer st;
  public static void main(String[] args) throws IOException {
    StringBuilder sb = new StringBuilder();
    readLine(); 
    int n = getInt();
    int[] a = new int[n], b = new int[n];
    readLine();
    for(int i = 0; i < n; i++) {
      a[i] = getInt();
    }
    readLine();
    for(int i = 0; i < n; i++) {
      b[i] = getInt();
    }
    Arrays.sort(a);
    Arrays.sort(b);
    long sum = 0;
    for(int i = 0; i < n; i++) sum += Math.abs(a[i] - b[i]);
    System.out.println(sum);
  }

  static int getInt() { return Integer.parseInt(st.nextToken()); }
  static void readLine() throws IOException { st = new StringTokenizer(br.readLine()); }
}
```
