---
title: BOJ. 仕事の割り当て (1311)
author: MINJUN PARK
date: 2022-01-26 22:09:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Bitmask, BOJ, Determine task, 할 일 정하기 1]
pin: false
lang: ja
translation_key: boj-1311-assignment-dp
permalink: /ja/posts/boj-1311-assignment-dp/
source_permalink: /posts/BOJ-1311/
---

[問題: BOJ 1311 — 仕事の割り当て](https://www.acmicpc.net/problem/1311)

`N`人に`N`個の仕事を1つずつ割り当てます。それぞれの仕事をちょうど1回使い、割り当てコストの合計を最小化します。

## ビットマスク動的計画法

`dp[mask]`を、`mask`でビットが立っている仕事を最初の`Integer.bitCount(mask)`人に割り当てたときの最小コストと定義します。次に割り当てる人のインデックスは`Integer.bitCount(mask)`です。まだ割り当てていない各仕事について、そのビットを立てて、その人のコストを加えます。

`dp[mask | (1 << task)] = min(dp[mask | (1 << task)], dp[mask] + cost[person][task])`。

空のマスクのコストは`0`、それ以外の状態の初期値は`INF`です。すべての仕事を割り当てた全ビットのマスクの値が答えです。このボトムアップ方式では、コストが0の状態もそのまま保持できます。`N <= 20`、各コストが最大`1,000,000`なので、割り当て全体のコストは最大`20,000,000`であり、`INF = 1,000,000,000`より小さくなります。

マスクは`2^N`個あり、各マスクから最大`N`個の遷移を調べるため、時間計算量は`O(N * 2^N)`、空間計算量は`O(2^N)`です。

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int INF = 1_000_000_000;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[][] cost = new int[n][n];
        for (int person = 0; person < n; person++) {
            for (int task = 0; task < n; task++) {
                cost[person][task] = input.nextInt();
            }
        }

        int stateCount = 1 << n;
        int[] dp = new int[stateCount];
        for (int mask = 1; mask < stateCount; mask++) {
            dp[mask] = INF;
        }

        for (int mask = 0; mask < stateCount; mask++) {
            int person = Integer.bitCount(mask);
            if (person == n) {
                continue;
            }

            for (int task = 0; task < n; task++) {
                int taskBit = 1 << task;
                if ((mask & taskBit) == 0) {
                    int nextMask = mask | taskBit;
                    dp[nextMask] = Math.min(
                        dp[nextMask],
                        dp[mask] + cost[person][task]
                    );
                }
            }
        }

        System.out.println(dp[stateCount - 1]);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
