---
title: AtCoder Typical 90 007 — CP Classes (3)
author: MINJUN PARK
date: 2021-12-30 02:46:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    CP Classes,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-007-cp-classes
permalink: /ja/posts/atcoder-typical90-007-cp-classes/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_g)

各クラスのレーティングが与えられます。各クエリについて、クエリの値と最も近いレーティングとの差の絶対値を出力します。まずレーティングを一度だけ昇順にソートし、各クエリでは二分探索で挿入位置を求めます。

挿入位置の直前と直後のレーティングだけを比較すれば十分です。挿入位置より前の値はすべて直前の値以下なので、それより近くなることはありません。後ろ側も同様に、直後の値より近くなることはありません。挿入位置が配列の端にある場合は、存在する隣接値だけを調べます。重複するレーティングも正しく扱えます。

レーティングとクエリ値の差を `long` として計算し、絶対値を求める際の整数オーバーフローを防ぎます。ソートに `O(N log N)`、各クエリの二分探索に `O(log N)` かかるため、全体の時間計算量は `O((N + Q) log N)` です。レーティング配列に `O(N)` の領域を使います。

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

      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      int value = 0;
      while (c > ' ') {
        value = value * 10 + c - '0';
        c = read();
      }
      return sign * value;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    int[] ratings = new int[n];
    for (int i = 0; i < n; i++) {
      ratings[i] = input.nextInt();
    }
    Arrays.sort(ratings);

    int q = input.nextInt();
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < q; i++) {
      int target = input.nextInt();
      int low = 0;
      int high = n;
      while (low < high) {
        int middle = low + (high - low) / 2;
        if (ratings[middle] < target) {
          low = middle + 1;
        } else {
          high = middle;
        }
      }

      long answer = Long.MAX_VALUE;
      if (low < n) {
        answer = Math.abs((long) ratings[low] - target);
      }
      if (low > 0) {
        answer = Math.min(answer, Math.abs((long) ratings[low - 1] - target));
      }
      output.append(answer).append('\n');
    }
    System.out.print(output);
  }
}
```
