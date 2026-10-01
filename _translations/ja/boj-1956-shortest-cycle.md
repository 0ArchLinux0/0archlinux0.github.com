---
title: BOJ 1956 — 運動
author: MINJUN PARK
date: 2022-01-05 03:02:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Dijkstra,
    Floyd Warshall,
    Graph,
    Exercise,
    운동,
  ]
pin: false
lang: ja
translation_key: boj-1956-shortest-cycle
permalink: /ja/posts/boj-1956-shortest-cycle/
---

[問題リンク](https://www.acmicpc.net/problem/1956)

`dist[u][v]` を、頂点 `u` から頂点 `v` への有向最短距離とします。すべての値を無限大に初期化し、`dist[i][i]` は 0 にします。同じ始点と終点を持つ直接辺が複数ある場合は、最小の重みを記録します。対角成分の 0 は Floyd–Warshall で空の経路を表し、頂点自身へ向かう直接辺はサイクル候補の計算用に別の辺行列へ保存します。

Floyd–Warshall では各頂点 `k` を中継点として順に考えます。各 `(u, v)` について、最短経路は `k` を通らない経路か、`u` から `k` へ進み、さらに `k` から `v` へ進む経路です。そのため、`dist[u][k] + dist[k][v]` と現在の値を比較して更新します。どちらかの区間に到達できない場合は加算を行いません。処理後、重み `w` の有向辺 `u -> v` は、`v` から `u` への経路が存在すればサイクルを作り、その長さは `w + dist[v][u]` です。すべての辺についてこの値の最小値を求めれば、最短サイクルが得られます。戻る経路が存在する辺がなければサイクルはなく、`-1` を出力します。この方法は自己ループ（`u == v`）と平行辺にも対応します。

Floyd–Warshall の計算量は `O(V^3)`、辺行列の走査は `O(V^2)` 時間です。距離行列と辺行列の空間計算量は `O(V^2)` です。`long` 型と `Long.MAX_VALUE / 4` の番兵値を使い、到達不能な値の加算を避けることで距離の和を安全に計算します。

```java
import java.io.*;
import java.util.*;

public class Main {
  static class FastScanner {
    private final InputStream input;
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int pointer = 0;

    FastScanner(InputStream input) {
      this.input = input;
    }

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
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int v = input.nextInt();
    int e = input.nextInt();
    long infinity = Long.MAX_VALUE / 4;
    long[][] edge = new long[v][v];
    long[][] dist = new long[v][v];

    for (int i = 0; i < v; i++) {
      Arrays.fill(edge[i], infinity);
      Arrays.fill(dist[i], infinity);
      dist[i][i] = 0;
    }

    for (int i = 0; i < e; i++) {
      int from = input.nextInt() - 1;
      int to = input.nextInt() - 1;
      long weight = input.nextInt();
      edge[from][to] = Math.min(edge[from][to], weight);
      dist[from][to] = Math.min(dist[from][to], weight);
    }

    for (int mid = 0; mid < v; mid++) {
      for (int from = 0; from < v; from++) {
        if (dist[from][mid] == infinity) continue;
        for (int to = 0; to < v; to++) {
          if (dist[mid][to] == infinity) continue;
          dist[from][to] = Math.min(
              dist[from][to], dist[from][mid] + dist[mid][to]);
        }
      }
    }

    long answer = infinity;
    for (int from = 0; from < v; from++) {
      for (int to = 0; to < v; to++) {
        if (edge[from][to] == infinity || dist[to][from] == infinity) continue;
        answer = Math.min(answer, edge[from][to] + dist[to][from]);
      }
    }

    System.out.println(answer == infinity ? -1 : answer);
  }
}
```
