---
title: AtCoder Typical 90 005 — 制限された桁 (7)
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
lang: ja
translation_key: atcoder-typical90-005-restricted-digits
permalink: /ja/posts/atcoder-typical90-005-restricted-digits/
---

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_e)

許可された数字だけを使い、値が `B` で割り切れる長さ `N` の数字列の個数を求めます。先頭の桁は 0 でも構いません。状態は `B` で割った余りです。余り `r` の状態に数字 `d` を追加すると、余りは `(10r + d) % B` になります。

`T[next][current]` を、余り `current` から `next` に遷移させる許可数字の個数と定義します。つまり `T[next][current]` は `next = (10 * current + d) % B` を満たす数字 `d` の個数です。初期ベクトルでは、余り 0 の空の接頭辞が 1 個です。`T` を 1 回掛けると数字を 1 桁追加できるため、`N` 回遷移した後の余り 0 の個数が答えになります。二分累乗法を使い、初期ベクトルに `T^N` を適用します。入力された数字の一覧に同じ数字が複数回含まれる場合は、許可数字 1 種類として扱います。

行列の乗算と累乗にかかる時間計算量は `O(B^3 log N)`、許可数字の読み込みは `O(K)` です。行列に必要な空間計算量は `O(B^2)` で、`B <= 100` の制約で十分実用的です。

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
