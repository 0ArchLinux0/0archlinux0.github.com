---
title: BOJ 1655 - 真ん中の数を言おう
author: MINJUN PARK
date: 2021-12-24 04:53:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, ヒープ, コーディング面接, BOJ, 最大ヒープ, 中央値]
lang: ja
translation_key: boj-1655-running-median
permalink: /ja/posts/boj-1655-running-median/
pin: false
---

[BOJ 1655: 真ん中の数を言おう](https://www.acmicpc.net/problem/1655)

これまでに読み込んだ値を二つの優先度付きキューに分けて保持する。`lower` は小さい側の半分を格納する最大ヒープ、`upper` は大きい側の半分を格納する最小ヒープである。次の二つの不変条件を保つ。

- `lower` のすべての値は `upper` のすべての値以下である。
- `lower` の要素数は `upper` と同じか、ちょうど一つ多い。

各入力値を値の範囲に応じたヒープに挿入し、必要に応じて一方のルートをもう一方へ移動して要素数の不変条件を戻す。すると `lower` のルートが常に下側の中央値となる。接頭辞の長さが奇数なら `lower` の要素数が一つ多く、そのルートが中央の要素である。偶数なら二つのヒープの要素数が等しくなり、`lower` のルートは中央の二値のうち小さい方、つまり問題で求める下側の中央値となる。

すべての入力値を二つのヒープに一度ずつ格納するため、空間計算量は `O(N)` である。挿入と必要な再平衡移動はそれぞれ `O(log N)` なので、全体の時間計算量は `O(N log N)` となる。空白区切りの整数はバッファ付きバイトリーダーで読み込む。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main {
  private static final class FastScanner {
    private final BufferedInputStream input = new BufferedInputStream(System.in);
    private final byte[] buffer = new byte[1 << 16];
    private int length = 0;
    private int position = 0;

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
      return value * sign;
    }
  }

  public static void main(String[] args) throws IOException {
    FastScanner scanner = new FastScanner();
    int n = scanner.nextInt();
    PriorityQueue<Integer> lower =
        new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> upper = new PriorityQueue<>();
    StringBuilder output = new StringBuilder();

    for (int i = 0; i < n; i++) {
      int value = scanner.nextInt();
      if (lower.isEmpty() || value <= lower.peek()) {
        lower.add(value);
      } else {
        upper.add(value);
      }

      if (lower.size() > upper.size() + 1) {
        upper.add(lower.poll());
      } else if (upper.size() > lower.size()) {
        lower.add(upper.poll());
      }
      output.append(lower.peek()).append('\n');
    }

    System.out.print(output);
  }
}
```
