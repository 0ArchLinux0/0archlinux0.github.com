---
title: BOJ. 수열과 쿼리 21 (16975)
author: MINJUN PARK
date: 2022-01-18 11:51:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Segment Tree,
    BOJ,
    Array and Query,
    수열과 쿼리 21
  ]
pin: false
lang: ko
translation_key: boj-16975-range-add-point-query
permalink: /ko/posts/boj-16975-range-add-point-query/
source_permalink: /posts/BOJ-16975/
---

[BOJ 16975: 수열과 쿼리 21](https://www.acmicpc.net/problem/16975)

## 펜윅 트리와 차분 배열로 구간 더하기 처리하기

초기 값은 기본 배열에 보관하고 이후의 더하기 연산은 차분 배열로 표현합니다. 1-based 양 끝점 포함 구간 `[left, right]`의 모든 위치에 `x`를 더하면 차분 배열에서 바뀌는 곳은 두 군데뿐입니다. `left`에 `x`, `right + 1`에 `-x`를 더합니다. 따라서 차분 배열의 `i`까지 누적합은 위치 `i`에 지금까지 더해진 총량이며, 현재 값은 `base[i] + prefix(i)`입니다.

차분 배열의 값은 펜윅 트리로 관리합니다. 구간 갱신은 펜윅 트리 점 갱신 두 번, 점 조회는 접두합 한 번으로 처리하므로 각각 `O(log N)` 시간이 걸립니다. 기본 배열과 펜윅 트리의 공간 복잡도는 `O(N)`입니다. `N`에서 끝나는 갱신도 취소 값을 `N + 1`에 안전하게 기록할 수 있도록 트리에 여분 칸을 둡니다. 조회는 요청한 인덱스까지만 합산합니다. 반복 누적에 따른 `int` 오버플로를 피하도록 값과 더하기 값은 `long`으로 저장합니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long[] base = new long[n + 1];
        for (int i = 1; i <= n; i++) {
            base[i] = input.nextLong();
        }

        Fenwick difference = new Fenwick(n + 1);
        int queryCount = input.nextInt();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            int type = input.nextInt();
            if (type == 1) {
                int left = input.nextInt();
                int right = input.nextInt();
                long amount = input.nextLong();
                difference.add(left, amount);
                difference.add(right + 1, -amount);
            } else {
                int index = input.nextInt();
                output.append(base[index] + difference.prefixSum(index)).append('\n');
            }
        }
        System.out.print(output);
    }

    private static final class Fenwick {
        private final long[] tree;

        Fenwick(int maximumIndex) {
            tree = new long[maximumIndex + 1];
        }

        void add(int index, long value) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i] += value;
            }
        }

        long prefixSum(int index) {
            long sum = 0;
            for (int i = index; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }
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
            return (int) nextLong();
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }
    }
}
```
