---
title: BOJ. 연속된 소수의 합 (1644)
author: MINJUN PARK
date: 2022-01-26 07:51:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Consecutive prime sum,
    소수의 연속합,
    Review,
  ]
pin: false
lang: ko
translation_key: boj-1644-consecutive-prime-sum
permalink: /ko/posts/boj-1644-consecutive-prime-sum/
source_permalink: /posts/BOJ-1644/
---

[문제: BOJ 1644 — 소수의 연속합](https://www.acmicpc.net/problem/1644)

## 에라토스테네스의 체와 슬라이딩 윈도우

먼저 에라토스테네스의 체로 `N` 이하의 모든 소수를 구합니다. 그런 다음 인덱스 `left`부터 `right`까지 연속한 소수들의 구간과 그 합을 유지합니다. 합이 `N`보다 작으면 오른쪽 끝을 늘립니다. 합이 `N` 이상이 되면 합이 같은 경우 정답에 더하고 가장 왼쪽 소수를 구간에서 뺍니다. 모든 소수는 양수이므로 구간을 늘릴 때 합은 증가하고, 첫 원소를 제거하면 합은 감소합니다. 따라서 두 포인터는 앞으로만 이동하며 모든 연속 소수 구간을 한 번씩 살펴봅니다.

`N = 1`이면 `N` 이하의 소수가 없으므로 답은 0입니다. 소수가 없는 입력도 빈 목록을 처리하므로 같은 방식으로 답 0을 얻습니다. 입력의 최댓값은 4,000,000이므로 각 소수와 개수에는 `int`를, 누적 합에는 안전하게 `long`을 사용합니다.

체의 시간 복잡도는 `O(N log log N)`, 공간 복잡도는 `O(N)`입니다. 소수의 개수를 `P`라고 하면 윈도우 탐색은 `O(P)`이며, 상한에서 `O(N)`입니다.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());

        boolean[] composite = new boolean[n + 1];
        for (int p = 2; p <= n / p; p++) {
            if (!composite[p]) {
                for (int multiple = p * p; multiple <= n; multiple += p) {
                    composite[multiple] = true;
                }
            }
        }

        List<Integer> primes = new ArrayList<>();
        for (int value = 2; value <= n; value++) {
            if (!composite[value]) {
                primes.add(value);
            }
        }

        int left = 0;
        long sum = 0;
        int count = 0;
        for (int right = 0; right < primes.size(); right++) {
            sum += primes.get(right);
            while (sum >= n) {
                if (sum == n) {
                    count++;
                }
                sum -= primes.get(left++);
            }
        }

        System.out.println(count);
    }
}
```
