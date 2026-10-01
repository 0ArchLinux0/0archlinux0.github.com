---
title: BOJ. 최솟값과 최댓값 (2357)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Segment Tree, Data Structure, Maximum and Minimum Values, 최솟값과 최댓값]
pin: false
lang: ko
translation_key: boj-2357-range-min-max
permalink: /ko/posts/boj-2357-range-min-max/
source_permalink: /posts/BOJ-2357/
---

[BOJ 2357: 최솟값과 최댓값](https://www.acmicpc.net/problem/2357)

## 반복형 세그먼트 트리

입력 값은 두 개의 평면 배열에서 각각 리프 `n + i`에 저장합니다. 각 내부 노드는 자식 두 개를 병합합니다. 최솟값 트리에는 `min(left, right)`, 최댓값 트리에는 `max(left, right)`를 저장합니다. 이 병합 연산은 결합법칙을 만족하므로 구간을 서로 겹치지 않는 트리 구간으로 나누고, 그 결과를 어떤 순서로든 결합할 수 있습니다.

포함 범위인 1-based 쿼리 `[a, b]`는 0-based 반개구간 `[a - 1, b)`로 바꾸어 트리 인덱스 `a - 1 + n`과 `b + n`으로 표현합니다. 왼쪽과 오른쪽 끝점을 함께 위로 이동합니다. 왼쪽 끝점이 오른쪽 자식이면 그 노드를 결과에 포함하고 왼쪽 끝점을 증가시킵니다. 오른쪽 끝점이 경계이고 홀수이면 한 칸 왼쪽으로 옮긴 노드를 포함합니다. 최솟값의 항등원 `Integer.MAX_VALUE`와 최댓값의 항등원 `Integer.MIN_VALUE`는 실제 결과에 영향을 주지 않으므로, 음수만 있거나 양수만 있는 쿼리도 올바르게 처리합니다.

두 트리의 구축에는 `O(N)` 시간과 `O(N)` 공간이 필요합니다. 각 쿼리는 최대 `O(log N)` 레벨을 지나며 각 레벨에서 선택한 구간을 상수 시간에 합치므로 쿼리 시간은 `O(log N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int queryCount = input.nextInt();

        int[] minTree = new int[2 * n];
        int[] maxTree = new int[2 * n];
        for (int i = 0; i < n; i++) {
            int value = input.nextInt();
            minTree[n + i] = value;
            maxTree[n + i] = value;
        }

        for (int i = n - 1; i > 0; i--) {
            minTree[i] = Math.min(minTree[2 * i], minTree[2 * i + 1]);
            maxTree[i] = Math.max(maxTree[2 * i], maxTree[2 * i + 1]);
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            int left = input.nextInt() - 1 + n;
            int right = input.nextInt() + n;
            int minimum = Integer.MAX_VALUE;
            int maximum = Integer.MIN_VALUE;

            while (left < right) {
                if ((left & 1) == 1) {
                    minimum = Math.min(minimum, minTree[left]);
                    maximum = Math.max(maximum, maxTree[left]);
                    left++;
                }
                if ((right & 1) == 1) {
                    --right;
                    minimum = Math.min(minimum, minTree[right]);
                    maximum = Math.max(maximum, maxTree[right]);
                }
                left >>= 1;
                right >>= 1;
            }

            output.append(minimum).append(' ').append(maximum).append('\n');
        }
        System.out.print(output);
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }
}
```
