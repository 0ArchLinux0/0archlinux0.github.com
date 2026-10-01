---
title: AtCoder Typical 90 014 — 昔は一緒に歌を歌ったものだ
author: MINJUN PARK
date: 2021-12-30 03:00:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    We Used to Sing a Song Together,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-014-pair-distances
permalink: /ja/posts/atcoder-typical90-014-pair-distances/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_n)

`N` 個の整数を含む 2 つの配列が与えられます。1 つ目の配列の各要素を 2 つ目の配列の要素 1 つと組み合わせ、絶対差の合計を最小化します。

両方の配列を昇順に並べ、同じ添字の要素同士を組にすると最小値になります。`x <= y` かつ `u <= v` である 2 つずつの値を考えます。同じ順序で組にする費用は `|x - u| + |y - v|`、交差して組にする費用は `|x - v| + |y - u|` です。数直線上では、順序どおりに結ぶ場合の合計距離は交差させる場合より大きくなりません。したがって、交差した 2 組は入れ替えても費用が増えず、これを繰り返すと、両配列の同じ順位同士を組にした形になります。

2 つの配列のソートに `O(N log N)`、ソート後の全要素の走査に `O(N)` かかるため、全体の時間計算量は `O(N log N)` です。配列に `O(N)` の領域を使います。2 つの `int` の差は `Integer.MAX_VALUE` を超えることがあるため、減算前に `long` に変換し、合計も `long` で保持します。

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
    int[] a = new int[n];
    int[] b = new int[n];

    for (int i = 0; i < n; i++) a[i] = input.nextInt();
    for (int i = 0; i < n; i++) b[i] = input.nextInt();

    Arrays.sort(a);
    Arrays.sort(b);

    long total = 0;
    for (int i = 0; i < n; i++) {
      total += Math.abs((long) a[i] - b[i]);
    }
    System.out.println(total);
  }
}
```
