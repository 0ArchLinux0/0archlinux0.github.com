---
title: AtCoder Typical 90 010 — Score Sum Queries (2)
author: MINJUN PARK
date: 2021-12-30 02:50:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Score Sum Queries,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-010-score-sum-queries
permalink: /ja/posts/atcoder-typical90-010-score-sum-queries/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_j)

`N` 人の生徒それぞれのクラスと得点が与えられます。各クエリで指定された両端を含む区間 `[L, R]` について、1 組と 2 組それぞれの得点合計を出力します。

クラスごとに累積和配列を 2 つ用意します。`classOne[i]` は生徒 1 番から `i` 番までの 1 組の得点合計、`classTwo[i]` は同じ区間の 2 組の得点合計です。各生徒のクラスに対応する配列だけに得点を加え、もう一方の配列には直前の累積和を引き継ぎます。これにより、各生徒の得点は必ずどちらか一方のクラスの合計にだけ加算されます。

クエリ区間は両端を含むため、`L` 番目までの累積和から `L - 1` 番目までの累積和を引けば `[L, R]` の合計になります。つまり、各クラスの答えは `prefix[R] - prefix[L - 1]` です。配列の 0 番目を 0 にしておけば、`L = 1` の場合も特別扱いせずに計算できます。

生徒を一度ずつ処理し、各クエリを定数時間で答えるため、時間計算量は `O(N + Q)` です。2 つの累積和配列に `O(N)` の領域を使います。得点の総和が大きくてもオーバーフローしないよう、累積和と出力値には Java の `long` を使います。

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

    long nextLong() throws IOException {
      int c;
      do {
        c = read();
      } while (c <= ' ' && c != -1);

      int sign = 1;
      if (c == '-') {
        sign = -1;
        c = read();
      }
      long value = 0;
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
    long[] classOne = new long[n + 1];
    long[] classTwo = new long[n + 1];

    for (int i = 1; i <= n; i++) {
      int classNumber = input.nextInt();
      long score = input.nextLong();
      classOne[i] = classOne[i - 1];
      classTwo[i] = classTwo[i - 1];
      if (classNumber == 1) {
        classOne[i] += score;
      } else {
        classTwo[i] += score;
      }
    }

    int q = input.nextInt();
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < q; i++) {
      int left = input.nextInt();
      int right = input.nextInt();
      long sumOne = classOne[right] - classOne[left - 1];
      long sumTwo = classTwo[right] - classTwo[left - 1];
      output.append(sumOne).append(' ').append(sumTwo).append('\n');
    }
    System.out.print(output);
  }
}
```
