---
title: AtCoder. 014 We Used to Sing a Song Together(3)
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
lang: en
translation_key: atcoder-typical90-014-pair-distances
permalink: /posts/競プロ典型-90-問-014-We-Used-to-Sing-a-Song-Together/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_n)

Given two arrays of `N` integers, pair every value in the first array with one value in the second so that the sum of absolute differences is minimized.

Sort both arrays in nondecreasing order and pair elements at the same indices. To see why this is optimal, consider two first-array values `x <= y` and two second-array values `u <= v`. Pairing them in the same order costs `|x - u| + |y - v|`; crossing them costs `|x - v| + |y - u|`. The same-order cost is never greater: on the number line, matching ordered endpoints avoids the extra distance introduced by crossing. Therefore any crossed pair can be uncrossed without increasing the total, and repeatedly uncrossing yields the sorted, rank-by-rank pairing.

The arrays are sorted in `O(N log N)` time, followed by one linear pass. The total running time is `O(N log N)` and the arrays use `O(N)` space. Cast to `long` before subtracting: two valid `int` values can have a difference larger than `Integer.MAX_VALUE`, and the total may also exceed the `int` range.

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
