---
title: AtCoder Typical 90 003 — 最長の円形道路 (4)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
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
lang: ja
translation_key: atcoder-typical90-003-tree-diameter
permalink: /ja/posts/atcoder-typical90-003-tree-diameter/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_c)

この問題では、木の中の経路に含まれる町の数の最大値を求めます。木では任意の2頂点間の経路が一意に定まり、そのような経路のうち最長のものを木の直径と呼びます。

2回の探索で直径を求めます。任意の頂点から木を探索して最も遠い端点 `u` を見つけ、次に `u` から探索します。`u` から最も遠い頂点は直径のもう一方の端点なので、2回目の探索で得られる最大距離が、辺の本数で表した直径の長さです。答えは両端点を含む頂点数であるため、距離に1を足して出力します。`N = 1` の場合、どちらの探索でも最大距離は0となり、出力は `1` です。

各探索ではキューと距離配列を使います。木では各頂点までの経路が一意なので、最初に訪問したときの距離が始点からの経路長です。時間計算量は `O(N)`、空間計算量は `O(N)` です。反復処理で実装するため、Javaの呼び出しスタックの深さに依存しません。

```java
import java.io.*;
import java.util.*;

public class Main {
  static int farthest(List<Integer>[] graph, int start, int[] distance) {
    Arrays.fill(distance, -1);
    int[] queue = new int[graph.length];
    int head = 0;
    int tail = 0;
    queue[tail++] = start;
    distance[start] = 0;
    int farthest = start;

    while (head < tail) {
      int town = queue[head++];
      if (distance[town] > distance[farthest]) {
        farthest = town;
      }
      for (int next : graph[town]) {
        if (distance[next] == -1) {
          distance[next] = distance[town] + 1;
          queue[tail++] = next;
        }
      }
    }
    return farthest;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(input.readLine());
    List<Integer>[] graph = new ArrayList[n];
    for (int town = 0; town < n; town++) {
      graph[town] = new ArrayList<>();
    }

    for (int i = 0; i < n - 1; i++) {
      StringTokenizer edge = new StringTokenizer(input.readLine());
      int a = Integer.parseInt(edge.nextToken()) - 1;
      int b = Integer.parseInt(edge.nextToken()) - 1;
      graph[a].add(b);
      graph[b].add(a);
    }

    int[] distance = new int[n];
    int endpoint = farthest(graph, 0, distance);
    farthest(graph, endpoint, distance);
    int diameterEdges = 0;
    for (int d : distance) {
      diameterEdges = Math.max(diameterEdges, d);
    }
    System.out.println(diameterEdges + 1);
  }
}
```
