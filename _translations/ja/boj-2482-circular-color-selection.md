---
title: BOJ 2482 - 色環
author: MINJUN PARK
date: 2022-02-04 00:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, BOJ, Dynamic Programming, 色環]
pin: false
lang: ja
translation_key: boj-2482-circular-color-selection
permalink: /ja/posts/boj-2482-circular-color-selection/
source_permalink: /posts/BOJ-2482/
---

[問題: BOJ 2482 — 色環](https://www.acmicpc.net/problem/2482) · [English](/posts/BOJ-2482/) · [한국어](/ko/posts/boj-2482-circular-color-selection/)

円形に並んだ `N` 個の位置から、互いに隣り合わないように `K` 個を選びます。先頭と末尾の位置も隣り合うものとして扱います。長さ `L` の直線から互いに隣り合わない `k` 個を選ぶ方法数を `line(L, k)` とすると、選んだ位置の間には少なくとも1つの未選択位置が必要なので、次の式になります。

`line(L, k) = C(L - k + 1, k)`

ただし `0 <= k <= L - k + 1` の場合に限り、この値を使い、それ以外は0です。何も選ばない方法は1通りであり、負の値や不可能な引数に対する組み合わせ数は0とします。

円形での選び方を、互いに重複しない次の2つの場合に分けます。

1. 先頭の位置を選ぶ場合、その両隣は選べません。残る `N - 3` 個の位置は直線になるため、その中から `K - 1` 個を選ぶ方法数は `line(N - 3, K - 1)` です。
2. 先頭の位置を選ばない場合、残る `N - 1` 個の位置は直線になります。その中から `K` 個を選ぶ方法数は `line(N - 1, K)` です。

2つの場合の方法数を足し、`1,000,000,003` で割った余りを出力します。この分け方では、すべての選び方をちょうど1回ずつ数えます。実装では必要な `K` 列までのパスカルの三角形を作って二項係数を求めるため、時間・空間計算量は `O(NK)` です。`N <= 1000` なら十分小さく、素数ではない法で階乗の割り算をする必要もありません。

表を作る前に境界条件を処理します。`K = 0` なら方法数は1です。`N = 1` のとき `K = 1` は1通り、`N = 2` のとき `K = 1` は2通りです。どちらもそれより大きい正の `K` は0通りです。不正な `N` または `K`、および `N > 1` で `K > floor(N / 2)` の場合も0通りです。最初の2つの円周サイズは、通常の分割が異なる3つの位置を前提とするため、個別に処理します。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final int MOD = 1_000_000_003;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        int k = Integer.parseInt(input.readLine().trim());

        if (n < 1 || k < 0 || k > n) {
            System.out.println(0);
            return;
        }
        if (k == 0) {
            System.out.println(1);
            return;
        }
        if (n == 1) {
            System.out.println(k == 1 ? 1 : 0);
            return;
        }
        if (n == 2) {
            System.out.println(k == 1 ? 2 : 0);
            return;
        }
        if (k > n / 2) {
            System.out.println(0);
            return;
        }

        int[][] binomial = new int[n + 1][k + 1];
        for (int row = 0; row <= n; row++) {
            binomial[row][0] = 1;
            for (int column = 1; column <= Math.min(row, k); column++) {
                int value = binomial[row - 1][column]
                        + binomial[row - 1][column - 1];
                binomial[row][column] = value >= MOD ? value - MOD : value;
            }
        }

        int answer = binomial[n - k - 1][k - 1]
                + binomial[n - k][k];
        if (answer >= MOD) {
            answer -= MOD;
        }
        System.out.println(answer);
    }
}
```
