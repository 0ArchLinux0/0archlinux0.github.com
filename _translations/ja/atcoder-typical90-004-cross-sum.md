---
title: AtCoder Typical 90 004 — Cross Sum (2)
author: MINJUN PARK
date: 2021-12-30 02:43:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Cross Sum,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-004-cross-sum
permalink: /ja/posts/atcoder-typical90-004-cross-sum/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_d)

各マスについて、そのマスと同じ行および列にある値の合計を出力します。各行の合計と各列の合計を一度ずつ計算し、それらを使ってすべてのマスの答えを求めます。対象のマス自身は行の合計と列の合計の両方に含まれるため、一度引いて重複分を取り除きます。

`rowSum[i] + colSum[j] - grid[i][j]`

合計が `int` の範囲を超えても正しく扱えるよう、値と合計には `long` を使います。グリッドの読み込みと合計の計算にかかる時間計算量は `O(HW)` です。グリッドに `O(HW)`、行・列の合計配列に追加で `O(H + W)` の空間を使います。

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

    long nextLong() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);

      long value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return value;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int h = (int) input.nextLong();
    int w = (int) input.nextLong();
    long[][] grid = new long[h][w];
    long[] rowSum = new long[h];
    long[] colSum = new long[w];

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        grid[i][j] = input.nextLong();
        rowSum[i] += grid[i][j];
        colSum[j] += grid[i][j];
      }
    }

    StringBuilder output = new StringBuilder();
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        if (j > 0) output.append(' ');
        output.append(rowSum[i] + colSum[j] - grid[i][j]);
      }
      output.append('\n');
    }
    System.out.print(output);
  }
}
```
