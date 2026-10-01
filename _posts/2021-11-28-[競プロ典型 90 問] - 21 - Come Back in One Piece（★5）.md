---
title: AtCoder Typical 90 021 — Come Back in One Piece
author: MINJUN PARK
date: 2021-11-28 3:02:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Graph,
    SCC,
    Kosaraju's algorithm,
  ]
pin: false
lang: en
translation_key: atcoder-typical90-021-come-back-one-piece
permalink: /posts/競プロ典型-90-問-21-Come-Back-in-One-Piece-5/
---
 [Link] <https://AtCoder.jp/contests/typical90/tasks/typical90_u>

This problem asks for the number of unordered pairs of distinct vertices that can reach each other. Two vertices are mutually reachable exactly when they belong to the same strongly connected component (SCC): every vertex in an SCC has a directed path to every other vertex in it.

For each component of size `s`, all `s * (s - 1) / 2` pairs qualify. We use iterative Kosaraju: the first depth-first search records vertices in finish order using explicit frames, and the second traverses the reversed graph in decreasing finish order to collect SCCs. Explicit stacks avoid Java call-stack overflow on deep graphs. The algorithm takes `O(N + M)` time and `O(N + M)` space.

The input vertices are numbered from 1 through N. The parser accepts arbitrary whitespace between all integers.

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream in = System.in;
    private final byte[] buffer = new byte[1 << 16];
    private int ptr = 0, len = 0;

    int nextInt() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);
      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value;
    }

    private int read() throws IOException {
      if (ptr == len) {
        len = in.read(buffer);
        ptr = 0;
        if (len == -1) return -1;
      }
      return buffer[ptr++];
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner fs = new FastScanner();
    int n = fs.nextInt();
    int m = fs.nextInt();
    List<Integer>[] graph = new ArrayList[n + 1];
    List<Integer>[] reverse = new ArrayList[n + 1];
    for (int i = 1; i <= n; i++) {
      graph[i] = new ArrayList<>();
      reverse[i] = new ArrayList<>();
    }
    for (int i = 0; i < m; i++) {
      int from = fs.nextInt();
      int to = fs.nextInt();
      graph[from].add(to);
      reverse[to].add(from);
    }

    boolean[] visited = new boolean[n + 1];
    int[] nextEdge = new int[n + 1];
    int[] stack = new int[n];
    int[] order = new int[n];
    int orderSize = 0;

    for (int start = 1; start <= n; start++) {
      if (visited[start]) continue;
      int top = 0;
      stack[0] = start;
      visited[start] = true;
      while (top >= 0) {
        int v = stack[top];
        if (nextEdge[v] < graph[v].size()) {
          int to = graph[v].get(nextEdge[v]++);
          if (!visited[to]) {
            visited[to] = true;
            stack[++top] = to;
          }
        } else {
          order[orderSize++] = v;
          top--;
        }
      }
    }

    Arrays.fill(visited, false);
    long answer = 0;
    for (int i = orderSize - 1; i >= 0; i--) {
      int start = order[i];
      if (visited[start]) continue;
      int top = 0;
      int size = 0;
      stack[0] = start;
      visited[start] = true;
      while (top >= 0) {
        int v = stack[top--];
        size++;
        for (int to : reverse[v]) {
          if (!visited[to]) {
            visited[to] = true;
            stack[++top] = to;
          }
        }
      }
      answer += (long) size * (size - 1) / 2;
    }

    System.out.println(answer);
  }
}
```
