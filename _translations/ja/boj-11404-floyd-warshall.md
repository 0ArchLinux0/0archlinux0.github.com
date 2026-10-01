---
title: BOJ 11404 — Floyd–Warshall
author: MINJUN PARK
date: 2022-01-04 19:12:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Floyd-Warshall,
    Graph,
  ]
pin: false
lang: ja
translation_key: boj-11404-floyd-warshall
permalink: /ja/posts/boj-11404-floyd-warshall/
---

[問題リンク](https://www.acmicpc.net/problem/11404)

`dist[i][j]` を、都市 `i` から都市 `j` までの既知の最短運賃とします。初期状態では、同じ都市にとどまる経路のコスト 0 と、直接結ばれたバス路線だけが分かっています。同じ出発地と到着地を結ぶ路線が複数ある場合は、最も安い運賃だけを残します。

各都市 `k` を中間地点として順に考えます。`k` を処理する前の `dist[i][j]` には、すでに処理した都市だけを中間地点として使う最短経路が入っています。新しい最短経路は、`k` を通らない現在の経路か、`i` から `k` へ進み、さらに `k` から `j` へ進む経路です。そのため、`dist[i][k] + dist[k][j]` と現在の値を比較して更新します。どちらかの区間に到達できない場合は加算を行わず、無限大を表す番兵値が距離に加算されるのを防ぎます。すべての都市を中間地点として処理すれば、最短経路に必要な候補をすべて調べたことになります。

中間地点・出発地・到着地を都市数分ずつ調べる三重ループを使うため、時間計算量は `O(N^3)` です。距離行列の空間計算量は `O(N^2)` です。`long` 型の行列と十分大きな番兵値で、到達不能な状態と経路コストを安全に扱います。到達できない都市間の出力は問題の指定どおり `0` にします。

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
    int n = input.nextInt();
    int m = input.nextInt();
    long infinity = Long.MAX_VALUE / 4;
    long[][] dist = new long[n][n];

    for (int i = 0; i < n; i++) {
      Arrays.fill(dist[i], infinity);
      dist[i][i] = 0;
    }

    for (int i = 0; i < m; i++) {
      int from = input.nextInt() - 1;
      int to = input.nextInt() - 1;
      long cost = input.nextInt();
      dist[from][to] = Math.min(dist[from][to], cost);
    }

    for (int mid = 0; mid < n; mid++) {
      for (int from = 0; from < n; from++) {
        if (dist[from][mid] == infinity) continue;
        for (int to = 0; to < n; to++) {
          if (dist[mid][to] == infinity) continue;
          dist[from][to] = Math.min(
              dist[from][to], dist[from][mid] + dist[mid][to]);
        }
      }
    }

    StringBuilder output = new StringBuilder();
    for (int from = 0; from < n; from++) {
      for (int to = 0; to < n; to++) {
        if (to > 0) output.append(' ');
        output.append(dist[from][to] == infinity ? 0 : dist[from][to]);
      }
      output.append('\n');
    }
    System.out.print(output);
  }
}
```
