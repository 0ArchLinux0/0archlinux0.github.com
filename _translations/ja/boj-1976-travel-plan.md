---
title: BOJ. 旅行に行こう (1976)
author: MINJUN PARK
date: 2022-01-05 13:30:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Union Find, Let's go on a trip, 여행 가자, Review]
pin: false
lang: ja
translation_key: boj-1976-travel-plan
permalink: /ja/posts/boj-1976-travel-plan/
---

[問題](https://www.acmicpc.net/problem/1976)

都市を頂点、道路を無向辺と考えます。旅行計画に含まれるすべての都市が同じ連結成分に属するとき、そしてそのときに限り旅行できます。その場合、計画内で隣り合う都市の間に経路があり、それらの経路をつなげて移動できます。したがって、都市が1つだけの計画は常に可能です。

素集合データ構造（DSU）を使います。最初は各都市がそれぞれ別の集合です。隣接行列で値が `1` のすべての組について都市を併合します。無向行列の両方向を処理しても、すでに同じ集合に属する都市を再び併合する操作は何もしないため問題ありません。その後、旅行計画を読み、各都市の代表元を最初の都市の代表元と比較します。経路圧縮によって後続の探索を速くし、サイズによる併合で木の高さを抑えます。

`N × N` の隣接行列を読む時間は `O(N^2)` です。DSU の各操作は償却 `O(α(N))` なので、全体の時間計算量は `O(N^2 α(N) + M α(N))`、空間計算量は `O(N)` です。入力処理では行ごとに分割せず空白文字を読み飛ばすため、空白や改行の配置に依存しません。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner();
    int cityCount = input.nextInt();
    int planLength = input.nextInt();
    DisjointSet dsu = new DisjointSet(cityCount);

    for (int from = 0; from < cityCount; from++) {
      for (int to = 0; to < cityCount; to++) {
        if (input.nextInt() == 1) {
          dsu.union(from, to);
        }
      }
    }

    int firstCity = input.nextInt() - 1;
    int representative = dsu.find(firstCity);
    for (int i = 1; i < planLength; i++) {
      int city = input.nextInt() - 1;
      if (dsu.find(city) != representative) {
        System.out.println("NO");
        return;
      }
    }

    System.out.println("YES");
  }

  private static final class DisjointSet {
    private final int[] parent;
    private final int[] size;

    DisjointSet(int n) {
      parent = new int[n];
      size = new int[n];
      for (int i = 0; i < n; i++) {
        parent[i] = i;
        size[i] = 1;
      }
    }

    int find(int city) {
      if (parent[city] != city) {
        parent[city] = find(parent[city]);
      }
      return parent[city];
    }

    void union(int a, int b) {
      int rootA = find(a);
      int rootB = find(b);
      if (rootA == rootB) return;
      if (size[rootA] < size[rootB]) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
      }
      parent[rootB] = rootA;
      size[rootA] += size[rootB];
    }
  }

  private static final class FastScanner {
    private final BufferedInputStream input = new BufferedInputStream(System.in);
    private final byte[] buffer = new byte[1 << 16];
    private int length;
    private int position;

    private int read() throws IOException {
      if (position == length) {
        length = input.read(buffer);
        position = 0;
        if (length == -1) return -1;
      }
      return buffer[position++];
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
}
```
