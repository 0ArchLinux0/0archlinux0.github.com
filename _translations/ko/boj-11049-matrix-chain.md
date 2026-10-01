---
title: BOJ. 행렬 곱셈 순서 (11049)
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
    행렬 곱셈 순서,
  ]
pin: false
lang: ko
translation_key: boj-11049-matrix-chain
permalink: /ko/posts/boj-11049-matrix-chain/
source_permalink: /posts/BOJ-11049/
---

[문제: BOJ 11049 — 행렬 곱셈 순서](https://www.acmicpc.net/problem/11049) · [English](/posts/BOJ-11049/) · [日本語](/ja/posts/boj-11049-matrix-chain/)

## 구간 동적 계획법

`dp[i][j]`를 행렬 `i`부터 `j`까지 순서대로 곱하는 데 필요한 스칼라 곱셈 횟수의 최솟값이라고 정의합니다. 행렬 하나는 곱셈이 필요 없으므로 `dp[i][i] = 0`입니다. 여러 행렬로 이루어진 구간에서는 마지막으로 곱할 위치 `k`를 선택합니다. 먼저 왼쪽 구간 `i..k`와 오른쪽 구간 `k+1..j`를 각각 계산한 뒤, 두 결과 행렬을 곱합니다.

행렬 `i`의 크기를 `r_i × c_i`라고 하면, 문제에서 이웃한 행렬의 크기가 맞도록 `c_i = r_(i+1)`임을 보장합니다. 따라서 왼쪽 결과의 크기는 `r_i × c_k`, 오른쪽 결과의 크기는 `r_(k+1) × c_j`이며 두 결과를 곱할 때 공유하는 차원은 `c_k = r_(k+1)`입니다. 마지막 곱셈 비용은 `r_i * c_k * c_j`이므로 점화식은 다음과 같습니다.

`dp[i][j] = min(dp[i][k] + dp[k+1][j] + r_i * c_k * c_j)` (`i <= k < j`)

짧은 구간부터 긴 구간 순서로 계산하면 두 부분 구간의 값이 이미 준비되어 있습니다. 센티널은 실제 답보다 작은 임의의 수가 아니라 `Number.MAX_SAFE_INTEGER`를 사용합니다. 문제의 제한에서 실제 비용은 이 값보다 작고 정수 연산도 정확하게 유지됩니다. 시간 복잡도는 `O(N^3)`, 공간 복잡도는 `O(N^2)`입니다. `N = 1`이면 대각선에 초기화한 값이 답이므로 결과는 `0`입니다.

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
