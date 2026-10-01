---
title: BOJ 20040 — サイクルゲーム
author: MINJUN PARK
date: 2022-01-06 03:45:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Union Find, Cycle game, 사이클 게임]
pin: false
lang: ja
translation_key: boj-20040-cycle-game
permalink: /ja/posts/boj-20040-cycle-game/
---

[問題リンク](https://www.acmicpc.net/problem/20040)

辺を入力順に処理します。辺 `(a, b)` を追加する前に、それまでに追加した辺からなるグラフで、両端点が属する連結成分の代表元を調べます。代表元が同じなら、すでに `a` と `b` を結ぶ経路があるため、この辺を追加するとサイクルができます。その時点のターンを1から数えてすぐに出力します。代表元が異なる場合は、2つの連結成分を併合します。経路ですでにつながっている頂点同士を結ぶ辺を加えたときに限りサイクルができるので、この方法で最初にサイクルを作る辺を特定できます。自己ループも入力されたターンで検出され、同じ2頂点を結ぶ平行辺は2本目でサイクルを作ります。

素集合データ構造では、根に連結成分のサイズを負数で格納します。`find` は経路圧縮を行い、`union` はサイズの小さい連結成分を大きい連結成分の下に併合します。この2つの最適化により、各操作の償却時間計算量は `O(α(N))` です。`M` 本の辺を処理する時間計算量は `O(M α(N))`、データ構造の空間計算量は `O(N)` です。サイクルを作る辺がなければ `0` を出力します。

```java
import java.io.*;

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

  static class DisjointSet {
    private final int[] parentOrSize;

    DisjointSet(int n) {
      parentOrSize = new int[n];
      for (int i = 0; i < n; i++) parentOrSize[i] = -1;
    }

    int find(int node) {
      if (parentOrSize[node] < 0) return node;
      return parentOrSize[node] = find(parentOrSize[node]);
    }

    boolean union(int a, int b) {
      int rootA = find(a);
      int rootB = find(b);
      if (rootA == rootB) return false;

      if (parentOrSize[rootA] > parentOrSize[rootB]) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
      }
      parentOrSize[rootA] += parentOrSize[rootB];
      parentOrSize[rootB] = rootA;
      return true;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    int m = input.nextInt();
    DisjointSet dsu = new DisjointSet(n);

    for (int turn = 1; turn <= m; turn++) {
      int a = input.nextInt() - 1;
      int b = input.nextInt() - 1;
      if (!dsu.union(a, b)) {
        System.out.println(turn);
        return;
      }
    }

    System.out.println(0);
  }
}
```
