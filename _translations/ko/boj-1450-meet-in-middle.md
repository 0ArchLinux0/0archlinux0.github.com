---
title: BOJ. 냅색 문제 (1450)
author: MINJUN PARK
date: 2022-01-27 22:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Knapsack problem, Meet in middle, Binary Search, 냅색 문제]
pin: false
lang: ko
translation_key: boj-1450-meet-in-middle
permalink: /ko/posts/boj-1450-meet-in-middle/
source_permalink: /posts/BOJ-1450/
---

[문제: BOJ 1450 — 냅색 문제](https://www.acmicpc.net/problem/1450)

## 중간에서 만나기

`N = 30`일 때 모든 부분집합을 열거하면 `O(2^N)` 시간이 필요합니다. 대신 물건을 두 그룹으로 나누고 각 그룹의 부분집합 합을 구합니다. 각 목록의 크기는 최대 `2^(N/2)`입니다. 오른쪽 그룹의 합을 정렬한 다음, 모든 왼쪽 그룹 합 `s`에 대해 `C - s`보다 큰 첫 번째 오른쪽 합의 위치를 이분 탐색으로 찾습니다. 그 위치 앞의 합 개수가 `s`와 함께 담을 수 있는 오른쪽 그룹 선택의 수입니다.

두 부분집합 합 목록은 빈 부분집합의 합 `0`에서 시작하므로 빈 부분집합도 정확히 한 번 계산됩니다. 합이 0인 부분집합 합을 버리면 안 됩니다. 합이 같더라도 서로 다른 부분집합은 각각 별개의 선택입니다. 물건의 무게는 모두 양수이므로 `C`보다 큰 한쪽 합은 유효한 조합에 포함될 수 없습니다. 구현은 그런 합만 제외합니다. 정답은 최대 `2^30`이 될 수 있으므로 부분합과 정답에 `long`을 사용합니다.

시간 복잡도는 `O(2^(N/2) log 2^(N/2))`, 공간 복잡도는 `O(2^(N/2))`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long capacity = input.nextLong();
        int middle = n / 2;

        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = input.nextInt();
        }

        List<Long> leftSums = subsetSums(weights, 0, middle, capacity);
        List<Long> rightSums = subsetSums(weights, middle, n, capacity);
        Collections.sort(rightSums);

        long count = 0;
        for (long leftSum : leftSums) {
            count += upperBound(rightSums, capacity - leftSum);
        }

        System.out.println(count);
    }

    private static List<Long> subsetSums(int[] weights, int from, int to, long capacity) {
        List<Long> sums = new ArrayList<>();
        sums.add(0L);
        for (int i = from; i < to; i++) {
            int currentSize = sums.size();
            for (int j = 0; j < currentSize; j++) {
                long nextSum = sums.get(j) + weights[i];
                if (nextSum <= capacity) {
                    sums.add(nextSum);
                }
            }
        }
        return sums;
    }

    private static int upperBound(List<Long> sorted, long value) {
        int low = 0;
        int high = sorted.size();
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (sorted.get(middle) <= value) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            return (int) nextLong();
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
