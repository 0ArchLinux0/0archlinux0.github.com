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
lang: ko
translation_key: atcoder-typical90-010-score-sum-queries
permalink: /ko/posts/atcoder-typical90-010-score-sum-queries/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_j)

학생 `N`명의 점수와 반 번호가 주어집니다. 각 질문에서 지정한 포함 구간 `[L, R]`에 대해 1반과 2반의 점수 합을 각각 출력합니다.

반별 누적 합 배열 두 개를 만듭니다. `classOne[i]`는 1번부터 `i`번 학생까지의 1반 점수 합이고, `classTwo[i]`는 같은 구간의 2반 점수 합입니다. 학생 `i`의 반에 해당하는 배열에만 점수를 더하고, 다른 배열은 이전 누적 합을 그대로 이어받습니다. 따라서 한 학생은 정확히 한 반의 합에만 기여합니다.

질문의 구간은 양 끝을 포함하므로 `L`번 학생까지의 누적 합에서 `L-1`번까지의 합을 빼면 `[L, R]`의 합을 얻습니다. 즉 각 반의 답은 `prefix[R] - prefix[L - 1]`입니다. 배열의 0번 칸을 0으로 두면 `L = 1`인 경우도 별도 처리 없이 계산할 수 있습니다.

각 학생을 한 번 처리하고 각 질문을 상수 시간에 답하므로 시간 복잡도는 `O(N + Q)`입니다. 두 누적 합 배열이 `O(N)` 공간을 사용합니다. 누적 합과 출력값에는 큰 총합에서도 오버플로가 나지 않도록 Java `long`을 사용합니다.

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
