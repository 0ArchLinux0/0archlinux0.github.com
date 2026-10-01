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
lang: ko
translation_key: atcoder-typical90-007-cp-classes
permalink: /ko/posts/atcoder-typical90-007-cp-classes/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_g)

각 클래스의 평가 점수가 주어지고, 각 질문 점수와 가장 가까운 클래스 점수까지의 절대 차이를 출력합니다. 클래스 점수를 오름차순으로 한 번 정렬한 뒤, 각 질문에 대해 이분 탐색으로 삽입 위치를 찾습니다.

삽입 위치의 바로 앞과 바로 뒤에 있는 점수만 비교하면 충분합니다. 삽입 위치보다 앞의 모든 점수는 앞의 이웃보다 작거나 같으므로 더 가까울 수 없고, 뒤쪽도 같은 논리로 뒤의 이웃보다 가까울 수 없습니다. 삽입 위치가 배열의 양 끝이면 존재하는 이웃만 비교합니다. 중복 점수도 올바르게 처리됩니다.

평가 점수와 질문 점수의 차이를 `long`으로 계산하여 절댓값 연산에서 정수 오버플로를 방지합니다. 정렬에 `O(N log N)`, 질문마다 이분 탐색에 `O(log N)`이 걸리므로 전체 시간 복잡도는 `O((N + Q) log N)`입니다. 점수 배열에 `O(N)`의 공간을 사용합니다.

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
