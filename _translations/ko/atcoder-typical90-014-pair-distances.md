---
title: AtCoder Typical 90 014 — 함께 노래를 부르곤 했지
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
lang: ko
translation_key: atcoder-typical90-014-pair-distances
permalink: /ko/posts/atcoder-typical90-014-pair-distances/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_n)

정수 `N`개가 들어 있는 두 배열이 주어집니다. 첫 번째 배열의 각 원소를 두 번째 배열의 원소 하나와 짝지어, 모든 절댓값 차이의 합을 최소화해야 합니다.

두 배열을 각각 오름차순으로 정렬한 뒤 같은 인덱스의 원소끼리 짝지으면 최솟값을 얻습니다. 이를 위해 `x <= y`, `u <= v`인 두 원소를 생각해 봅시다. 같은 순서로 짝지을 때의 비용은 `|x - u| + |y - v|`이고, 교차해서 짝지을 때의 비용은 `|x - v| + |y - u|`입니다. 수직선에서 순서대로 연결하면 교차 연결보다 총 길이가 길어지지 않습니다. 따라서 교차한 두 쌍은 순서를 바꾸어도 비용이 증가하지 않으며, 이를 반복하면 정렬된 순위끼리 짝지은 결과가 됩니다.

두 배열을 정렬하는 데 `O(N log N)`, 정렬 후 모든 쌍을 확인하는 데 `O(N)` 시간이 걸리므로 전체 시간 복잡도는 `O(N log N)`입니다. 두 배열이 `O(N)` 공간을 사용합니다. 두 `int` 값의 차는 `Integer.MAX_VALUE`를 넘을 수 있으므로 뺄셈 전에 `long`으로 변환하고, 합계도 `long`에 저장합니다.

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
