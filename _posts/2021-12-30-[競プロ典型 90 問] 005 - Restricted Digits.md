---
title: AtCoder. 005 Restricted Digits(7)
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
lang: en
translation_key: atcoder-typical90-005-restricted-digits
permalink: /posts/競プロ典型-90-問-005-Restricted-Digits/
---

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_e)

Count length-`N` digit strings whose value is divisible by `B`, using only the allowed digits. The first digit may be zero. A state is a remainder modulo `B`; appending digit `d` to remainder `r` produces `(10r + d) % B`.

Define `T[next][current]` as the number of allowed digits that move `current` to `next`. Thus `T[next][current]` counts digits `d` satisfying `next = (10 * current + d) % B`. The vector starts with one empty prefix at remainder zero. Multiplying by `T` once appends one digit, so after `N` transitions the answer is the count at remainder zero. Binary exponentiation computes `T^N` applied to the start vector. Repeated values in the input digit list are treated as one allowed digit.

Matrix multiplication and exponentiation take `O(B^3 log N)` time; reading the allowed digits takes `O(K)`. The matrices use `O(B^2)` space, which is practical for `B <= 100`.

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
