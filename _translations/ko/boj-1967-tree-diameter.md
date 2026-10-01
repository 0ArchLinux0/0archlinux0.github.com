---
title: BOJ 1967 — 트리의 지름
author: MINJUN PARK
date: 2022-01-07 00:23:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Tree,
    Data Structure,
    Diameter of Tree,
    트리의 지름,
  ]
pin: false
lang: ko
translation_key: boj-1967-tree-diameter
permalink: /ko/posts/boj-1967-tree-diameter/
---

[BOJ 1967: 트리의 지름](https://www.acmicpc.net/problem/1967)

입력에는 무방향 간선이 한 번씩만 주어집니다. 어느 방향으로든 탐색할 수 있도록 각 간선을 양쪽 정점의 인접 리스트에 한 번씩 추가합니다. 각 방향을 중복해서 추가하지 않습니다.

트리에서는 두 정점 사이의 단순 경로가 유일하며, 그 경로의 길이가 두 정점 사이의 거리입니다. 임의의 정점 `s`에서 트리 전체를 순회해 각 정점까지의 거리를 구하고, 가장 먼 정점을 `a`라고 합니다. 이렇게 찾은 정점은 지름 경로의 끝점이 될 수 있습니다. 지름 경로와 `s`에서 그 경로로 이어지는 경로가 만나는 지점을 생각해 봅시다. `s`가 지름 경로 위에 있으면 양 끝점 중 하나는 그 만나는 지점보다 `s`에서 더 멀거나 같은 거리에 있습니다. `s`가 경로 바깥에 있으면 트리의 유일한 경로 성질에 따라 지름의 두 끝점 중 하나가 가장 먼 정점이 됩니다(거리가 같은 경우도 문제없습니다). 따라서 `a`는 어떤 지름의 끝점이며, `a`에서 두 번째 순회를 시작해 가장 먼 거리까지의 최댓값을 구하면 지름의 길이가 됩니다.

두 순회 모두 재귀 대신 명시적인 스택을 사용하므로 긴 체인에서도 호출 스택이 넘치지 않습니다. 거리는 `long`으로 계산합니다. `n = 1`이면 간선이 없으므로 두 순회 모두 정점 0에 머물고 답은 0입니다. 순회당 시간은 `O(n)`이며 인접 리스트와 순회 배열의 공간은 `O(n)`입니다.

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
