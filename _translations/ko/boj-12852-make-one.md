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
lang: ko
translation_key: boj-12852-make-one
permalink: /ko/posts/boj-12852-make-one/
source_permalink: /posts/BOJ-12852/
---

## 풀이

`dp[x]`를 `x`를 1로 만드는 데 필요한 최소 연산 횟수라고 하겠습니다. `x`에서 마지막으로 할 수 있는 연산은 1을 빼거나, 2로 나누어떨어질 때 2로 나누거나, 3으로 나누어떨어질 때 3으로 나누는 것입니다. 따라서 `2`부터 `N`까지 각 `x`에 대해 가능한 이전 값들의 `dp` 중 최솟값에 1을 더하면 됩니다. 모든 이전 값은 `x`보다 작으므로 이미 계산되어 있습니다.

최솟값을 만든 이전 값을 각 `dp[x]`와 함께 저장합니다. `N`에서 시작해 이전 값을 계속 따라가면 필요한 내림차순 경로를 복원할 수 있습니다. 여러 후보가 같은 최솟값을 만들면 어느 후보를 골라도 최적 경로입니다. `N = 1`이면 `dp[1]`은 0이며 복원 경로에는 `1`만 출력됩니다.

불변식은 `x`를 처리한 뒤 `dp[x]`가 `x`에서 1까지의 최소 연산 횟수이고, 저장한 이전 값이 그 횟수를 달성하는 유효한 다음 값이라는 것입니다. 점화식은 마지막에 할 수 있는 모든 연산을 확인하고 더 작은 값들의 최적해를 사용하므로 이 불변식을 유지합니다. DP 테이블과 이전 값 배열은 각각 `N + 1`개 원소를 가지며, 계산과 경로 복원을 합친 시간 복잡도는 `O(N)`, 공간 복잡도는 `O(N)`입니다.

[문제 링크](https://www.acmicpc.net/problem/12852)

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
