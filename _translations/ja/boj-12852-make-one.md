---
title: BOJ. Make into 1(2) (12852)
author: MINJUN PARK
date: 2022-01-11 18:07:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Dynamic Programming,
    Make into 1(2),
    1로 만들기 2,
  ]
pin: false
lang: ja
translation_key: boj-12852-make-one
permalink: /ja/posts/boj-12852-make-one/
source_permalink: /posts/BOJ-12852/
---

## 解法

`dp[x]` を `x` を 1 にするために必要な最小操作回数とします。`x` に対して最後に行える操作は、1 を引く、2 で割り切れる場合に 2 で割る、3 で割り切れる場合に 3 で割る、のいずれかです。したがって、`2` から `N` まで順に各 `x` について、有効な遷移先の `dp` の最小値に 1 を加えます。遷移先はすべて `x` より小さいため、すでに計算済みです。

最小値を与えた遷移先を各 `dp[x]` とともに記録します。`N` から始めて記録した値をたどれば、必要な降順の経路を復元できます。複数の候補が同じ最小値を持つ場合は、どれを選んでも最適な経路になります。`N = 1` の場合、`dp[1]` は 0 で、復元結果は `1` のみです。

不変条件は、`x` の処理後に `dp[x]` が `x` から 1 までの最小操作回数であり、記録した遷移先がその回数を達成する有効な次の値であることです。漸化式は最後に可能な操作をすべて確認し、より小さい値の最適解を使うため、この不変条件が保たれます。DP テーブルと遷移先配列はそれぞれ `N + 1` 個の要素を持ち、計算と経路復元を合わせた時間計算量は `O(N)`、空間計算量は `O(N)` です。

[問題リンク](https://www.acmicpc.net/problem/12852)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        int[] dp = new int[n + 1];
        int[] predecessor = new int[n + 1];

        for (int value = 2; value <= n; value++) {
            int bestPrevious = value - 1;
            int bestSteps = dp[bestPrevious];

            if (value % 2 == 0 && dp[value / 2] < bestSteps) {
                bestPrevious = value / 2;
                bestSteps = dp[bestPrevious];
            }
            if (value % 3 == 0 && dp[value / 3] < bestSteps) {
                bestPrevious = value / 3;
                bestSteps = dp[bestPrevious];
            }

            dp[value] = bestSteps + 1;
            predecessor[value] = bestPrevious;
        }

        StringBuilder output = new StringBuilder();
        output.append(dp[n]).append('\n');
        for (int value = n; ; value = predecessor[value]) {
            output.append(value).append(' ');
            if (value == 1) {
                break;
            }
        }
        System.out.print(output);
    }
}
```
