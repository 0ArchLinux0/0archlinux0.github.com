---
title: BOJ. 외판원 순회 (2098)
author: MINJUN PARK
date: 2022-02-07 17:53:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Bitmask, 외판원 순회]
pin: false
lang: ko
translation_key: boj-2098-tsp-bitmask-dp
permalink: /ko/posts/boj-2098-tsp-bitmask-dp/
source_permalink: /posts/BOJ-2098/
---

[문제: BOJ 2098 — 외판원 순회](https://www.acmicpc.net/problem/2098)

[English](/posts/BOJ-2098/) · [日本語](/ja/posts/boj-2098-tsp-bitmask-dp/)

## 비트마스크 동적 계획법

출발 도시를 `0`으로 고정합니다. 어떤 순회 경로든 시작점을 회전해 도시 `0`에서 출발하도록 나타낼 수 있습니다. 상태 `(current, visited)`에서 `visited`는 지금까지 방문한 도시의 비트마스크이며, 출발 도시 `0`을 포함합니다. `dp[current][visited]`는 아직 방문하지 않은 도시를 각각 한 번씩 방문한 뒤 도시 `0`으로 돌아오는 데 필요한 최소 추가 비용입니다.

모든 도시를 방문했다면 남은 비용은 현재 도시에서 `0`으로 돌아가는 간선의 비용입니다. 이 방향의 간선이 없으면 해당 상태는 불가능합니다. 그 외에는 아직 방문하지 않은 도시 `next` 중 `current → next` 간선이 존재하는 경우만 살펴보고, 간선 비용과 다음 상태의 비용을 더한 값 중 최솟값을 선택합니다.

`dp[current][visited] = min(cost[current][next] + dp[next][visited | (1 << next)])`

최솟값은 실제 간선이 있는 다음 도시만 대상으로 구합니다. 입력 비용이 `0`이면 간선이 없다는 뜻이므로 해당 전이를 건너뜁니다. 완전한 순회 경로가 없을 때 BOJ 2098은 `0`을 출력하도록 요구합니다. 내부에서는 불가능한 상태를 `INF`로 나타내며, 이를 다른 비용에 더하지 않습니다.

상태는 `N · 2^N`개이고 각 상태에서 최대 `N`개의 다음 도시를 확인하므로 시간 복잡도는 `O(N² 2^N)`, 공간 복잡도는 `O(N 2^N)`입니다. 비용과 `INF`에 각각 `long`, `Long.MAX_VALUE / 4`를 사용해 유효한 경로 비용을 더할 때 오버플로가 발생하지 않도록 합니다.

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
