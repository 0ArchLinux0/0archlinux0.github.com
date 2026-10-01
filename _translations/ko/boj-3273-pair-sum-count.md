---
title: BOJ 3273 - 두 수의 합
author: MINJUN PARK
date: 2022-01-27 07:08:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 두 수의 합, 투 포인터]
pin: false
lang: ko
translation_key: boj-3273-pair-sum-count
permalink: /ko/posts/boj-3273-pair-sum-count/
source_permalink: /posts/BOJ-3273/
---

[문제: BOJ 3273 — 두 수의 합](https://www.acmicpc.net/problem/3273)

서로 다른 양의 정수 `N`개와 목표값 `X`가 주어집니다(`1 ≤ N ≤ 100,000`, 각 수는 최대 `1,000,000`). 합이 `X`인 서로 다른 두 입력 원소의 순서 없는 쌍의 개수를 구합니다.

## 정렬과 투 포인터

먼저 수를 정렬하고, 아직 살펴보지 않은 구간의 가장 왼쪽과 오른쪽에 포인터를 둡니다. 두 수의 합이 `X`보다 작으면 왼쪽 수는 남은 어떤 수와 더해도 `X`를 만들 수 없으므로 왼쪽 포인터를 오른쪽으로 옮깁니다. 합이 `X`보다 크면 오른쪽 수는 남은 어떤 수와 짝을 지어도 너무 크므로 오른쪽 포인터를 왼쪽으로 옮깁니다.

합이 `X`와 같으면 쌍의 수를 하나 늘리고 두 포인터를 모두 안쪽으로 옮깁니다. 이로써 두 원소를 다시 사용할 수 없게 하여 같은 쌍을 중복 계산하지 않습니다. 포인터가 만날 때까지 후보를 처리하며, 일치하는 쌍이 없으면 개수는 계속 `0`입니다. 최소 입력인 `N = 1`에서도 포인터가 처음부터 같은 위치이므로 쌍은 세지 않습니다.

합은 `long`으로 계산해 오버플로를 방지합니다. 정렬은 `O(N log N)`, 포인터 탐색은 `O(N)`이므로 전체 시간 복잡도는 `O(N log N)`, 입력 배열을 포함한 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int[] values = new int[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextInt();
        }
        int target = input.nextInt();

        Arrays.sort(values);
        int left = 0;
        int right = n - 1;
        int count = 0;
        while (left < right) {
            long sum = (long) values[left] + values[right];
            if (sum == target) {
                count++;
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(count);
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
