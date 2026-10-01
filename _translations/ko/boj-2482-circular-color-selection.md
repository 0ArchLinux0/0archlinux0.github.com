---
title: BOJ 2482 - 색상환
author: MINJUN PARK
date: 2022-02-04 00:17:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 동적 계획법, 색상환]
pin: false
lang: ko
translation_key: boj-2482-circular-color-selection
permalink: /ko/posts/boj-2482-circular-color-selection/
source_permalink: /posts/BOJ-2482/
---

[문제: BOJ 2482 — 색상환](https://www.acmicpc.net/problem/2482) · [English](/posts/BOJ-2482/) · [日本語](/ja/posts/boj-2482-circular-color-selection/)

원형으로 놓인 `N`개 위치 중 서로 인접하지 않도록 `K`개를 고릅니다. 첫 위치와 마지막 위치도 인접한 것으로 취급합니다. 길이 `L`인 직선에서 서로 인접하지 않게 `k`개를 고르는 경우의 수를 `line(L, k)`라 하면, 선택 사이마다 최소 한 자리를 비워야 하므로 다음과 같습니다.

`line(L, k) = C(L - k + 1, k)`

단, `0 <= k <= L - k + 1`일 때만 이 값을 사용하고, 그 밖의 경우는 0입니다. 특히 아무것도 고르지 않는 방법은 1가지이며, 음수이거나 불가능한 인수의 조합 수도 0으로 봅니다.

원형의 선택을 서로 겹치지 않는 두 경우로 나눕니다.

1. 첫 위치를 고르면 양쪽 이웃은 고를 수 없습니다. 남은 `N - 3`개 위치는 직선이므로 그중 `K - 1`개를 고르는 경우의 수는 `line(N - 3, K - 1)`입니다.
2. 첫 위치를 고르지 않으면 나머지 `N - 1`개 위치가 직선이 됩니다. 그중 `K`개를 고르는 경우의 수는 `line(N - 1, K)`입니다.

두 경우의 수를 더한 뒤 `1,000,000,003`으로 나눈 나머지를 출력합니다. 두 경우는 모든 선택을 정확히 한 번씩 포함합니다. 구현에서는 필요한 `K`개 열까지만 파스칼 삼각형을 만들어 필요한 이항계수를 구하므로 시간과 공간은 `O(NK)`입니다. `N <= 1000`에서 충분히 작으며, 소수가 아닌 모듈러 수에서 팩토리얼을 나누는 방법도 피할 수 있습니다.

표를 만들기 전에 경계를 처리합니다. `K = 0`이면 방법은 1가지입니다. `N = 1`일 때 `K = 1`은 1가지이고, `N = 2`일 때 `K = 1`은 2가지입니다. 두 경우 모두 그보다 큰 양수 `K`의 방법은 0가지입니다. 유효하지 않은 `N` 또는 `K`, 그리고 `N > 1`에서 `K > floor(N / 2)`인 경우도 0가지입니다. 처음 두 원 크기는 일반적인 분할이 서로 다른 세 위치를 가정하므로 별도로 처리합니다.

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
