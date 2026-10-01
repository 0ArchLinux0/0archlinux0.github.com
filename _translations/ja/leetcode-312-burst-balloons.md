---
title: LeetCode 312. 風船を割る
author: MINJUN PARK
date: 2022-01-01 22:15:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Dynamic Programming, Burst Balloons, Review]
pin: false
lang: ja
translation_key: leetcode-312-burst-balloons
permalink: /ja/posts/leetcode-312-burst-balloons/
---

![image](https://user-images.githubusercontent.com/55131164/147873064-4d275273-184b-49da-8890-7d8116039bba.png)

[問題リンク](https://leetcode.com/problems/burst-balloons/)

## 区間動的計画法

配列の両端に値 `1` の仮想風船を追加する。`dp[l][r]` を、両端の境界風船 `l`、`r` は割らずに、その間にある風船をすべて割って得られる最大コイン数とする。

開区間で最後に割る風船 `k` を選ぶ。その時点では区間内のほかの風船はすべて取り除かれているため、`k` の隣にはちょうど `l` と `r` が残る。最後に得るコインは `values[l] * values[k] * values[r]` であり、左右の部分区間はそれより先に処理されるので、それぞれ独立に最適化できる。したがって漸化式は次のとおり。

`dp[l][r] = max(dp[l][k] + values[l] * values[k] * values[r] + dp[k][r])`

ただし `l < k < r` とする。幅の小さい区間から計算すれば、二つの部分区間の答えを使って漸化式を評価できる。風船が `n` 個のとき、区間は `O(n^2)` 個あり、各区間で `k` を `O(n)` 個試すため、時間計算量は `O(n^3)`、空間計算量は `O(n^2)` となる。入力が空なら内部に風船がないため、答えは `0` である。

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        int[] values = new int[n + 2];
        values[0] = values[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            values[i + 1] = nums[i];
        }

        int[][] dp = new int[n + 2][n + 2];
        for (int width = 2; width < n + 2; width++) {
            for (int left = 0; left + width < n + 2; left++) {
                int right = left + width;
                for (int last = left + 1; last < right; last++) {
                    int coins = dp[left][last]
                            + values[left] * values[last] * values[right]
                            + dp[last][right];
                    dp[left][right] = Math.max(dp[left][right], coins);
                }
            }
        }

        return dp[0][n + 1];
    }
}
```
