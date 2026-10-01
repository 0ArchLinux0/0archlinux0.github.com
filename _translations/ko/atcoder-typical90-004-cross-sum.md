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
lang: ko
translation_key: atcoder-typical90-004-cross-sum
permalink: /ko/posts/atcoder-typical90-004-cross-sum/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_d)

각 칸에 대해 해당 행과 열에 있는 모든 값의 합을 출력합니다. 각 행의 합과 각 열의 합을 한 번씩 구한 뒤, 모든 칸의 답을 계산합니다. 해당 칸의 값은 행의 합과 열의 합에 모두 포함되므로, 한 번 빼서 중복 계산을 바로잡습니다.

`rowSum[i] + colSum[j] - grid[i][j]`

큰 합이 `int` 범위를 넘지 않도록 값과 합은 `long`으로 저장합니다. 격자를 읽고 합을 계산하는 데 `O(HW)` 시간이 걸립니다. 격자에 `O(HW)` 공간을 사용하고, 행·열 합 배열에 추가로 `O(H + W)` 공간을 사용합니다.

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
