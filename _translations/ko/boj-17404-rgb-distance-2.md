---
title: BOJ. RGB거리 2 (17404)
author: MINJUN PARK
date: 2022-02-07 02:30:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, RGB Distance 2, RGB거리 2]
pin: false
lang: ko
translation_key: boj-17404-rgb-distance-2
permalink: /ko/posts/boj-17404-rgb-distance-2/
source_permalink: /posts/BOJ-17404/
---

[문제: BOJ 17404 — RGB거리 2](https://www.acmicpc.net/problem/17404)

[English](/posts/BOJ-17404/) · [日本語](/ja/posts/boj-17404-rgb-distance-2/)

## 풀이

각 집은 빨강, 초록, 파랑 중 하나로 칠하며 이웃한 집의 색은 달라야 합니다. 집들이 원형으로 이어져 있으므로 첫 번째 집과 마지막 집도 이웃입니다. 따라서 두 집의 색도 서로 달라야 합니다.

첫 번째 집의 색을 하나로 고정한 뒤 선형 동적 계획법을 실행합니다. `dp[c]`를 현재 집까지 칠했을 때 현재 집의 색이 `c`인 최소 비용이라고 합시다. 세 상태를 모두 무한대로 초기화하고, 고정한 첫 색 상태만 첫 집의 칠하기 비용으로 설정합니다. 이후 각 집에서 색 `c`를 고를 때는 직전 집의 다른 두 색 상태 중 작은 값에 현재 칠하기 비용을 더합니다.

`next[c] = cost[i][c] + min(dp[(c + 1) % 3], dp[(c + 2) % 3])`

모든 집을 처리한 뒤에는 고정한 첫 색과 다른 마지막 색 상태만 답 후보로 봅니다. 이 조건으로 원형의 마지막-첫 번째 인접 조건을 만족시킵니다. 가능한 첫 색 세 가지를 각각 고정해 실행하고 그중 최소값을 선택합니다. 모든 유효한 색칠은 첫 집 색이 이 세 경우 중 하나이므로, 이 방법으로 모든 경우를 빠짐없이 다룹니다.

`N = 2`에서도 같은 방식이 적용됩니다. 두 집은 원에서도 서로 이웃하므로 다른 색이어야 합니다. 마지막 색 검사가 첫 색과 같은 경우를 제외하여 유효한 배치만 남깁니다.

DP 상태는 `long` 세 개씩인 두 개의 롤링 배열만 사용합니다. 입력 비용은 배열에 저장하고, DP 자체의 추가 공간은 `O(1)`이며 세 번의 실행을 합친 시간은 `O(N)`입니다. 비용 합을 `long`으로 계산하고 충분히 작은 무한대 값을 사용해 덧셈 오버플로를 피합니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long INF = Long.MAX_VALUE / 4;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[][] cost = new int[n][3];
        for (int house = 0; house < n; house++) {
            for (int color = 0; color < 3; color++) {
                cost[house][color] = input.nextInt();
            }
        }

        long answer = INF;
        for (int firstColor = 0; firstColor < 3; firstColor++) {
            long[] previous = {INF, INF, INF};
            long[] current = new long[3];
            previous[firstColor] = cost[0][firstColor];

            for (int house = 1; house < n; house++) {
                for (int color = 0; color < 3; color++) {
                    current[color] = cost[house][color]
                            + Math.min(previous[(color + 1) % 3], previous[(color + 2) % 3]);
                }
                long[] temp = previous;
                previous = current;
                current = temp;
            }

            for (int lastColor = 0; lastColor < 3; lastColor++) {
                if (lastColor != firstColor) {
                    answer = Math.min(answer, previous[lastColor]);
                }
            }
        }

        System.out.println(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```
