---
title: BOJ. 行列積の順序 (11049)
author: MINJUN PARK
date: 2022-01-26 18:44:00 +0900
categories: [Record, Code]
tags:
  [
    JavaScript,
    Algorithm,
    Coding Interview,
    Dynamic Programming,
    BOJ,
    Matrix multiplication order,
    行列積の順序,
  ]
pin: false
lang: ja
translation_key: boj-11049-matrix-chain
permalink: /ja/posts/boj-11049-matrix-chain/
source_permalink: /posts/BOJ-11049/
---

[問題: BOJ 11049 — 行列積の順序](https://www.acmicpc.net/problem/11049) · [English](/posts/BOJ-11049/) · [한국어](/ko/posts/boj-11049-matrix-chain/)

## 区間動的計画法

`dp[i][j]` を、行列 `i` から `j` までを順番に掛け合わせるために必要なスカラー乗算回数の最小値とします。行列が1つだけなら乗算は不要なので、`dp[i][i] = 0` です。複数の行列からなる区間では、最後に掛け合わせる境界 `k` を選びます。まず左の区間 `i..k` と右の区間 `k+1..j` をそれぞれ計算し、その後に2つの結果行列を掛けます。

行列 `i` のサイズを `r_i × c_i` とすると、隣り合う行列のサイズが適合する、つまり `c_i = r_(i+1)` であることが問題で保証されています。したがって左の結果は `r_i × c_k`、右の結果は `r_(k+1) × c_j` であり、両者を掛けるときの共通次元は `c_k = r_(k+1)` です。最後の乗算コストは `r_i * c_k * c_j` となるため、漸化式は次のとおりです。

`dp[i][j] = min(dp[i][k] + dp[k+1][j] + r_i * c_k * c_j)` (`i <= k < j`)

短い区間から長い区間へ順に計算すると、2つの部分区間の値はすでに求まっています。実際の答えより小さい任意の値ではなく、番兵値に `Number.MAX_SAFE_INTEGER` を使います。問題の制約では実際のコストはこの値より小さく、整数演算も正確に保たれます。時間計算量は `O(N^3)`、空間計算量は `O(N^2)` です。`N = 1` の場合、対角成分に初期化した値が答えなので、結果は `0` です。

```javascript
const stream = require('fs').readFileSync(0, 'utf-8').trim().split(/\n/);
const n = Number(stream[0]);
const dimensions = stream.slice(1).map((line) => line.split(' ').map(Number));
const dp = Array.from({ length: n }, () =>
  new Array(n).fill(Number.MAX_SAFE_INTEGER),
);

for (let i = 0; i < n; i++) dp[i][i] = 0;

for (let length = 2; length <= n; length++) {
  for (let start = 0; start + length <= n; start++) {
    const end = start + length - 1;
    for (let split = start; split < end; split++) {
      const cost =
        dp[start][split] +
        dp[split + 1][end] +
        dimensions[start][0] * dimensions[split][1] * dimensions[end][1];
      dp[start][end] = Math.min(dp[start][end], cost);
    }
  }
}

console.log(dp[0][n - 1]);
```
