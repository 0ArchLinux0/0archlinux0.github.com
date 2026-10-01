---
title: BOJ. 旅行巡回問題 (2098)
author: MINJUN PARK
date: 2022-02-07 17:53:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Bitmask, 旅行巡回問題]
pin: false
lang: ja
translation_key: boj-2098-tsp-bitmask-dp
permalink: /ja/posts/boj-2098-tsp-bitmask-dp/
source_permalink: /posts/BOJ-2098/
---

[問題: BOJ 2098 — 旅行巡回問題](https://www.acmicpc.net/problem/2098)

[English](/posts/BOJ-2098/) · [한국어](/ko/posts/boj-2098-tsp-bitmask-dp/)

## ビットマスク動的計画法

出発都市を`0`に固定します。どの巡回路も開始位置を回転させれば、都市`0`から出発する形で表せます。状態`(current, visited)`の`visited`は、出発都市`0`を含む訪問済み都市のビットマスクです。`dp[current][visited]`は、未訪問の都市をそれぞれ一度ずつ訪問してから都市`0`に戻るための最小追加コストを表します。

すべての都市を訪問済みなら、残りのコストは現在の都市から`0`への辺のコストです。その向きの辺がなければ、この状態は実現できません。それ以外の場合は、未訪問都市`next`のうち`current → next`の辺が存在するものだけを調べ、辺のコストと次の状態のコストの合計の最小値を選びます。

`dp[current][visited] = min(cost[current][next] + dp[next][visited | (1 << next)])`

最小値を取る対象は、実在する辺で移動できる次の都市だけです。入力コスト`0`は辺が存在しないことを示すため、その遷移はスキップします。巡回路を完成できない場合、BOJ 2098では`0`を出力します。内部では実現不可能な状態を`INF`で表し、他のコストに加算しません。

状態数は`N · 2^N`で、各状態から最大`N`都市を調べるため、時間計算量は`O(N² 2^N)`、空間計算量は`O(N 2^N)`です。コストには`long`、`INF`には`Long.MAX_VALUE / 4`を使い、有効な経路コストを加算してもオーバーフローしないようにします。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;
    private static int n;
    private static int[][] cost;
    private static long[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(input.readLine().trim());
        cost = new int[n][n];
        for (int from = 0; from < n; from++) {
            String[] row = input.readLine().trim().split("\\s+");
            for (int to = 0; to < n; to++) {
                cost[from][to] = Integer.parseInt(row[to]);
            }
        }

        dp = new long[n][1 << n];
        for (long[] row : dp) {
            Arrays.fill(row, -1);
        }

        long answer = visit(0, 1);
        System.out.println(answer == INF ? 0 : answer);
    }

    private static long visit(int current, int visited) {
        if (visited == (1 << n) - 1) {
            return cost[current][0] == 0 ? INF : cost[current][0];
        }
        if (dp[current][visited] != -1) {
            return dp[current][visited];
        }

        long best = INF;
        for (int next = 0; next < n; next++) {
            int bit = 1 << next;
            if ((visited & bit) != 0 || cost[current][next] == 0) {
                continue;
            }

            long remaining = visit(next, visited | bit);
            if (remaining != INF) {
                best = Math.min(best, cost[current][next] + remaining);
            }
        }
        return dp[current][visited] = best;
    }
}
```
