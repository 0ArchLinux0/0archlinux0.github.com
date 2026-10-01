---
title: AtCoder. 010 Score Sum Queries(2)
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
lang: en
translation_key: atcoder-typical90-010-score-sum-queries
permalink: /posts/競プロ典型-90-問-010-Score-Sum-Queries/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_j)

Given each of `N` students' classes and scores, answer queries asking for the total scores of Class 1 and Class 2 in the inclusive range `[L, R]`.

Build two prefix-sum arrays, one for each class. `classOne[i]` stores the Class 1 score total among students `1` through `i`, and `classTwo[i]` stores the corresponding Class 2 total. For each student, add the score only to the array for that student's class; carry the previous prefix value forward in the other array. Thus every student's score contributes to exactly one class total.

Because both ends of the query range are included, subtract the prefix sum through `L - 1` from the prefix sum through `R`. The answer for either class is `prefix[R] - prefix[L - 1]`. Initializing index zero to zero also handles `L = 1` without a special case.

Each student is processed once, and each query takes constant time, for `O(N + Q)` total time. The two prefix arrays use `O(N)` space. Prefix sums and answers use Java `long` so large totals do not overflow.

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
