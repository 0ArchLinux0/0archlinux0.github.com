---
title: AtCoder. 003 Longest Circular Road(4)
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
lang: en
translation_key: atcoder-typical90-003-tree-diameter
permalink: /posts/競プロ典型-90-問-003-Longest-Circular-Road-4/
---

[Link] <https://AtCoder.jp/contests/typical90/tasks/typical90_c>

The task asks for the maximum number of towns on a path in a tree. In a tree, the path between any two vertices is unique, and a longest such path is the tree diameter.

The double-sweep method finds the diameter: start at any vertex and traverse the tree to find a farthest endpoint `u`; then traverse from `u`. A farthest vertex from `u` is the other diameter endpoint, so the greatest distance in this second traversal is the diameter length in edges. We print that distance plus one because the answer counts vertices, including both endpoints. When `N = 1`, both traversals have maximum distance zero and the output is `1`.

Each traversal uses a queue and a distance array; in a tree, the first visit to a vertex gives its unique-path distance from the start. The algorithm takes `O(N)` time and `O(N)` space. It is iterative, so it does not depend on the Java call-stack depth.

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
