---
title: Atcoder. 018 - Statue of Chokudai(3)
author: MINJUN PARK
date: 2021-12-30 03:03:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Statue of Chokudai
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
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    StringBuilder sb = new StringBuilder();
    
    int time = atoi(br.readLine());
    String[] tmp = br.readLine().split(" ");
    double r = (double)atoi(tmp[0]) / 2;
    int x = atoi(tmp[1]), y = atoi(tmp[2]);
    int q = atoi(br.readLine());
    for(int i = 0; i < q; i++) {
      int elapse = atoi(br.readLine());
      double proportion = (double)elapse / time, height = getH(proportion, r), distance = getDistance(proportion, r, x, y);
      sb.append(Math.atan2(height, distance) / Math.PI * 180).append("\n");
    }
    System.out.print(sb);
  }
  static double getH(double proportion, double r) {
    return r * (1 - Math.cos(2 * Math.PI * proportion));
  }
  static double getDistance(double proportion, double r, int x, int y) {
    double yCord = - r * Math.sin(2 * Math.PI * proportion);
    return Math.sqrt(Math.pow(Math.abs(yCord - y), 2) + (long)x * x);
  }
  static int atoi(String s) { return Integer.parseInt(s); }
}

```
