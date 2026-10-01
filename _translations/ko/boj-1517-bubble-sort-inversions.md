---
title: BOJ 1517 — 버블 소트
author: MINJUN PARK
date: 2022-01-08 08:20:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Bubble Sort, Inversion, 버블 소트]
pin: false
lang: ko
translation_key: boj-1517-bubble-sort-inversions
permalink: /ko/posts/boj-1517-bubble-sort-inversions/
---

[문제 링크](https://www.acmicpc.net/problem/1517)

`i < j`이면서 `A[i] > A[j]`인 인덱스 쌍 `(i, j)`를 역전(inversion)이라고 합니다. 버블 정렬은 순서가 잘못된 인접 원소를 교환합니다. 한 번 교환된 쌍은 올바른 순서가 되어 다시 교환되지 않으며, 모든 역전이 하나씩 제거됩니다. 따라서 버블 정렬의 전체 교환 횟수는 역전의 수와 같습니다.

병합 정렬로 실제 교환을 수행하지 않고 역전을 셉니다. 두 구간을 각각 정렬한 뒤 병합할 때, 오른쪽 원소가 현재 왼쪽 원소보다 작다면 그 오른쪽 원소는 왼쪽 구간에 남은 모든 원소보다 작습니다. 따라서 그 수만큼 답에 더한 뒤 오른쪽 원소를 복사합니다. 두 값이 같으면 왼쪽 원소를 먼저 선택합니다. 같은 값은 역전이 아니기 때문입니다. 재귀 호출은 각 절반 안의 역전을 세고, 병합 과정은 두 절반 사이의 역전을 셉니다.

시간 복잡도는 `O(N log N)`이고 입력 배열과 병합 버퍼의 공간 복잡도는 `O(N)`입니다. 최대 역전 수가 `N(N - 1) / 2`이므로 답에는 `long`을 사용합니다.

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
      return value * sign;
    }
  }

  static int[] values;
  static int[] buffer;

  static long sortAndCount(int left, int right) {
    if (right - left <= 1) return 0;

    int middle = left + (right - left) / 2;
    long count = sortAndCount(left, middle) + sortAndCount(middle, right);

    int i = left;
    int j = middle;
    int out = left;
    while (i < middle && j < right) {
      if (values[i] <= values[j]) {
        buffer[out++] = values[i++];
      } else {
        buffer[out++] = values[j++];
        count += middle - i;
      }
    }
    while (i < middle) buffer[out++] = values[i++];
    while (j < right) buffer[out++] = values[j++];
    System.arraycopy(buffer, left, values, left, right - left);

    return count;
  }

  public static void main(String[] args) throws IOException {
    FastScanner input = new FastScanner(System.in);
    int n = input.nextInt();
    values = new int[n];
    buffer = new int[n];
    for (int i = 0; i < n; i++) values[i] = input.nextInt();

    System.out.println(sortAndCount(0, n));
  }
}
```
