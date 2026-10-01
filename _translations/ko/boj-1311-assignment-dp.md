---
title: BOJ. 할 일 정하기 1 (1311)
author: MINJUN PARK
date: 2022-01-26 22:09:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Bitmask, BOJ, Determine task, 할 일 정하기 1]
pin: false
lang: ko
translation_key: boj-1311-assignment-dp
permalink: /ko/posts/boj-1311-assignment-dp/
source_permalink: /posts/BOJ-1311/
---

[문제: BOJ 1311 — 할 일 정하기 1](https://www.acmicpc.net/problem/1311)

`N`명의 사람에게 `N`개의 일을 하나씩 배정합니다. 각 일은 정확히 한 번만 사용하며, 배정 비용의 합을 최소화합니다.

## 비트마스크 동적 계획법

`dp[mask]`를 `mask`에서 비트가 켜진 일들을 처음 `Integer.bitCount(mask)`명의 사람에게 배정했을 때의 최소 비용이라고 정의합니다. 따라서 다음에 배정할 사람의 인덱스는 `Integer.bitCount(mask)`입니다. 아직 배정하지 않은 각 일에 대해 해당 비트를 켜고 그 사람의 비용을 더합니다.

`dp[mask | (1 << task)] = min(dp[mask | (1 << task)], dp[mask] + cost[person][task])`.

빈 마스크의 비용은 `0`, 나머지 상태의 초기값은 `INF`입니다. 모든 일을 배정한 전체 마스크의 값이 정답입니다. 이 바텀업 방식은 비용이 0인 상태도 그대로 저장합니다. `N <= 20`, 각 비용이 최대 `1,000,000`이므로 완성된 배정의 총비용은 최대 `20,000,000`이며 `INF = 1,000,000,000`보다 작습니다.

마스크는 `2^N`개이고 각 마스크에서 최대 `N`개의 전이를 확인하므로 시간 복잡도는 `O(N * 2^N)`, 공간 복잡도는 `O(2^N)`입니다.

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
