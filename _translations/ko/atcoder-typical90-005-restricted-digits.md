---
title: AtCoder Typical 90 005 — 제한된 숫자 (7)
author: MINJUN PARK
date: 2021-12-30 02:44:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Restricted Digits,
    Review,
    difficult,
  ]
pin: false
lang: ko
translation_key: atcoder-typical90-005-restricted-digits
permalink: /ko/posts/atcoder-typical90-005-restricted-digits/
---

[문제 링크](https://AtCoder.jp/contests/typical90/tasks/typical90_e)

허용된 숫자만 사용해 값이 `B`로 나누어떨어지는 길이 `N`의 숫자 문자열 개수를 구합니다. 첫 자리는 0이어도 됩니다. 상태는 `B`로 나눈 나머지이며, 나머지가 `r`일 때 숫자 `d`를 뒤에 붙이면 나머지는 `(10r + d) % B`가 됩니다.

`T[next][current]`를 나머지 `current`에서 `next`로 이동시키는 허용 숫자의 개수로 정의합니다. 즉, `T[next][current]`는 `next = (10 * current + d) % B`를 만족하는 숫자 `d`의 개수입니다. 시작 벡터에는 나머지가 0인 빈 접두사가 하나 있습니다. `T`를 한 번 곱하면 숫자 하나를 추가하므로, `N`번 이동한 뒤 나머지 0인 경우의 수가 정답입니다. 이진 거듭제곱으로 시작 벡터에 `T^N`을 적용합니다. 입력 숫자 목록에 같은 값이 여러 번 있으면 하나의 허용 숫자로 취급합니다.

행렬 곱셈과 거듭제곱의 시간 복잡도는 `O(B^3 log N)`이고, 허용 숫자를 읽는 데 `O(K)`가 걸립니다. 행렬 공간 복잡도는 `O(B^2)`이며, `B <= 100`에서 충분히 사용할 수 있습니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static final long MOD = 1_000_000_007L;

  static long[][] multiply(long[][] a, long[][] b) {
    int size = a.length;
    long[][] product = new long[size][size];
    for (int row = 0; row < size; row++) {
      for (int middle = 0; middle < size; middle++) {
        if (a[row][middle] == 0) continue;
        for (int column = 0; column < size; column++) {
          product[row][column] =
              (product[row][column] + a[row][middle] * b[middle][column]) % MOD;
        }
      }
    }
    return product;
  }

  static long[] multiply(long[][] matrix, long[] vector) {
    int size = vector.length;
    long[] product = new long[size];
    for (int row = 0; row < size; row++) {
      for (int column = 0; column < size; column++) {
        product[row] = (product[row] + matrix[row][column] * vector[column]) % MOD;
      }
    }
    return product;
  }

  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    StringTokenizer firstLine = new StringTokenizer(input.readLine());
    long n = Long.parseLong(firstLine.nextToken());
    int b = Integer.parseInt(firstLine.nextToken());
    int k = Integer.parseInt(firstLine.nextToken());

    boolean[] allowed = new boolean[10];
    StringTokenizer digits = new StringTokenizer(input.readLine());
    for (int i = 0; i < k; i++) {
      allowed[Integer.parseInt(digits.nextToken())] = true;
    }

    long[][] transition = new long[b][b];
    for (int current = 0; current < b; current++) {
      for (int digit = 0; digit <= 9; digit++) {
        if (allowed[digit]) {
          int next = (10 * current + digit) % b;
          transition[next][current]++;
        }
      }
    }

    long[] ways = new long[b];
    ways[0] = 1;
    while (n > 0) {
      if ((n & 1) != 0) {
        ways = multiply(transition, ways);
      }
      n >>= 1;
      if (n > 0) {
        transition = multiply(transition, transition);
      }
    }

    System.out.println(ways[0]);
  }
}
```
