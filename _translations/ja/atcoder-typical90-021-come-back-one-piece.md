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
lang: ja
translation_key: atcoder-typical90-021-come-back-one-piece
permalink: /ja/posts/atcoder-typical90-021-come-back-one-piece/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_u)

この問題では、互いに到達可能な異なる2頂点からなる、順序を区別しないペアの数を求めます。2頂点が互いに到達可能であることと、同じ強連結成分（SCC）に属することは同値です。SCC内では、どの頂点からも他のすべての頂点へ有向路が存在します。

サイズが `s` の各SCCでは、`s * (s - 1) / 2` 個のペアすべてが条件を満たします。反復版のコサラジュ法を使います。1回目の深さ優先探索では明示的なフレームを使って頂点の終了順を記録し、2回目の探索では辺を反転したグラフを終了順の逆順にたどってSCCを集めます。明示的なスタックを使うため、深いグラフでもJavaの呼び出しスタックがあふれません。時間計算量は `O(N + M)`、空間計算量も `O(N + M)` です。

入力の頂点番号は1からNまでです。パーサーは整数の間にある任意の空白文字を処理できます。

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
