---
title: AtCoder. 003 Longest Circular Road(4)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [AtCoder, 競プロ典型 90 問]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Longest Circular Road,
  ]
pin: false
---

[Link] <https://AtCoder.jp/contests/typical90/tasks/typical90_c>

<br>

```java
import java.util.*;
import java.io.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer st;
    int n = Integer.parseInt(bf.readLine());
    ArrayList<HashSet<Integer>> connection = new ArrayList<HashSet<Integer>>();
    for(int i = 0; i < n; i++) {
      connection.add(new HashSet<>());
    }
    Stack<int[]> stack = new Stack<>();
    for(int i = 1; i < n; i++) {
      st = new StringTokenizer(bf.readLine());
      int a = Integer.parseInt(st.nextToken());
      int b = Integer.parseInt(st.nextToken());
      connection.get(a - 1).add(b);
      connection.get(b - 1).add(a);
    }

    int max = 0;
    int farthestTown = 0;
    int[] temp1 = {1, 0};
    stack.push(temp1);
    boolean[] visited = new boolean[n + 1];

    while(!stack.isEmpty()) {
      int[] top = stack.pop();
      int town = top[0];
      int len = top[1];
      if(visited[town]) continue;
      visited[town] =  true;
      if(max < len) {
        max = len;
        farthestTown = town;
      }
      for(int e : connection.get(town - 1)) {
        if(visited[e]) continue;
        int[] temp2 = { e, len + 1};
        stack.push(temp2);
      }
    }

    int[] temp3 = { farthestTown, 0};
    stack.push(temp3);
    visited = new boolean[n + 1];
    while(!stack.isEmpty()) {
      int[] top = stack.pop();
      int town = top[0];
      int len = top[1];
      if(visited[town]) continue;
      visited[town] =  true;
      for(int e : connection.get(town - 1)) {
        if(visited[e]) continue;
        if(max < len + 1) max = len + 1;
        int[] temp2 = {e, len + 1};
        stack.push(temp2);
      }
    }
    System.out.println(max + 1);
  }
}
```
