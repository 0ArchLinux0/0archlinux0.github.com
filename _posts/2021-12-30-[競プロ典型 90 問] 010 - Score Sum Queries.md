---
title: Atcoder. 010 - Score Sum Queries(2)
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Score Sum Queries
  ]
pin: false
---

[Link] <https://atcoder.jp/contests/typical90/tasks/typical90_s>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader reader =  new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;
    StringBuilder sb = new StringBuilder();
    int n = Integer.parseInt(reader.readLine());
    int[] classOne = new int[n + 1];
    int[] classTwo = new int[n + 1];
    for(int i = 1; i <= n; i++) {
      st = new StringTokenizer(reader.readLine());
      int classN = Integer.parseInt(st.nextToken());
      int score = Integer.parseInt(st.nextToken());
      classOne[i] = classOne[i-1];
      classTwo[i] = classTwo[i-1];
      if(classN == 1) classOne[i] += score;
      else classTwo[i] += score;
    }

    int q = Integer.parseInt(reader.readLine());
    for(int i = 1; i <= q; i++) {
      st = new StringTokenizer(reader.readLine());
      int l = Integer.parseInt(st.nextToken());
      int r = Integer.parseInt(st.nextToken());
      int sum1 = classOne[r] - classOne[l-1], sum2 = classTwo[r] - classTwo[l-1];
      sb.append(sum1 + " " + sum2).append("\n");
    }

    System.out.print(sb);
  }
}
```
