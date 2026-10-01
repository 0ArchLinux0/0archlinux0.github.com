---
title: BOJ 1967 — 木の直径
author: MINJUN PARK
date: 2022-01-07 00:23:00 +0900
categories: [Record, Code]
tags:
  - Java
  - Algorithm
  - Coding Interview
  - BOJ
  - Tree
  - Data Structure
  - Diameter of Tree
  - 木の直径
pin: false
lang: ja
translation_key: boj-1967-tree-diameter
permalink: /ja/posts/boj-1967-tree-diameter/
---

[BOJ 1967: 木の直径](https://www.acmicpc.net/problem/1967)

入力では無向辺がそれぞれ一度だけ与えられます。どちらの向きにも移動できるよう、各辺を両端の隣接リストに一度ずつ追加します。同じ向きの辺を重複して追加しないようにします。

木では、任意の2頂点を結ぶ単純路は一意であり、その路の長さが頂点間の距離です。任意の頂点 `s` から木全体を走査して各頂点までの距離を求め、最も遠い頂点を `a` とします。このようにして直径の端点を見つけられます。直径の路と、`s` からその路へ向かう経路が合流する点を考えます。`s` が直径上にあるなら、直径の両端のうち一方は、その合流点以上に `s` から遠くなります。`s` が直径の外にある場合も、木の経路が一意であることから、直径の両端のいずれかが最遠点になります（同距離でも問題ありません）。したがって `a` はある直径の端点です。続いて `a` からもう一度走査し、そこからの最長距離を求めれば直径の長さになります。

どちらの走査も再帰ではなく明示的なスタックを使うため、長い鎖状の木でも呼び出しスタックを使い切りません。距離は `long` で計算します。`n = 1` の場合は辺がなく、どちらの走査も頂点 0 に留まるため答えは 0 です。各走査の時間計算量は `O(n)`、隣接リストと走査用配列の空間計算量は `O(n)` です。

```java
import java.io.*;
import java.util.*;

public class Main {
  static class Edge {
    final int to;
    final int weight;
    Edge(int to, int weight) { this.to = to; this.weight = weight; }
  }

  static class Farthest {
    final int vertex;
    final long distance;
    Farthest(int vertex, long distance) {
      this.vertex = vertex;
      this.distance = distance;
    }
  }

  static class FastScanner {
    private final InputStream input = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0, pointer = 0;
    private int read() throws IOException {
      if (pointer == length) {
        length = input.read(buffer);
        pointer = 0;
        if (length == -1) return -1;
      }
      return buffer[pointer++];
    }
    int nextInt() throws IOException {
      int c;
      do { c = read(); } while (c <= ' ' && c != -1);
      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value;
    }
  }

  static Farthest farthest(ArrayList<Edge>[] graph, int start) {
    int n = graph.length;
    int[] parent = new int[n];
    Arrays.fill(parent, -2);
    int[] stack = new int[n];
    long[] distance = new long[n];
    int size = 0;
    stack[size++] = start;
    parent[start] = -1;
    int bestVertex = start;
    long bestDistance = 0;
    while (size > 0) {
      int vertex = stack[--size];
      if (distance[vertex] > bestDistance) {
        bestDistance = distance[vertex];
        bestVertex = vertex;
      }
      for (Edge edge : graph[vertex]) {
        if (edge.to == parent[vertex]) continue;
        parent[edge.to] = vertex;
        distance[edge.to] = distance[vertex] + edge.weight;
        stack[size++] = edge.to;
      }
    }
    return new Farthest(bestVertex, bestDistance);
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner();
    int n = input.nextInt();
    ArrayList<Edge>[] graph = new ArrayList[n];
    for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
    for (int i = 0; i < n - 1; i++) {
      int a = input.nextInt() - 1;
      int b = input.nextInt() - 1;
      int weight = input.nextInt();
      graph[a].add(new Edge(b, weight));
      graph[b].add(new Edge(a, weight));
    }
    int endpoint = farthest(graph, 0).vertex;
    System.out.println(farthest(graph, endpoint).distance);
  }
}
```
