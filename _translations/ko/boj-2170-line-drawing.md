---
title: BOJ 2170 - 선 긋기
author: MINJUN PARK
date: 2022-02-16 02:27:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 정렬, 스위핑, 선 긋기]
pin: false
lang: ko
translation_key: boj-2170-line-drawing
permalink: /ko/posts/boj-2170-line-drawing/
source_permalink: /posts/BOJ-2170/
---

[문제: BOJ 2170 — 선 긋기](https://www.acmicpc.net/problem/2170) · [English](/posts/BOJ-2170/) · [日本語](/ja/posts/boj-2170-line-drawing/)

각 선분은 두 끝점 좌표 사이의 모든 점을 덮습니다. 적어도 하나의 선분이 덮는 전체 길이를 구합니다. 구간을 왼쪽 끝점 기준 오름차순으로 정렬한 뒤 왼쪽에서 오른쪽으로 순회하며 현재 합쳐진 구간의 가장 오른쪽 끝점을 유지합니다. 다음 구간의 시작점이 현재 끝점 이하라면 겹치거나 맞닿으므로, 필요할 때만 오른쪽 끝점을 늘립니다. 시작점이 현재 끝점보다 크면 앞 구간의 길이를 합산하고 새 구간을 시작합니다. 끝점이 맞닿는 경우에는 사이에 빈틈이 없으므로 합쳐도 길이가 달라지지 않습니다.

입력의 두 끝점 순서가 뒤집혀 있어도 처리할 수 있도록 각 쌍을 `left <= right`가 되게 정규화합니다. 길이가 0인 구간은 길이에 기여하지 않습니다. 순회가 끝난 뒤 마지막으로 유지 중인 구간도 반드시 합산해야 합니다. 좌표 차와 전체 합이 `int` 범위를 넘을 수 있으므로 누적 길이에는 `long`을 사용합니다.

정렬은 `O(N log N)`, 순회는 `O(N)`이므로 전체 시간 복잡도는 `O(N log N)`입니다. 구간 목록을 저장하므로 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int limit;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value * sign;
        }

        private int read() throws IOException {
            if (position == limit) {
                limit = input.read(buffer);
                position = 0;
                if (limit == -1) return -1;
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long[][] intervals = new long[n][2];

        for (int i = 0; i < n; i++) {
            long a = input.nextInt();
            long b = input.nextInt();
            intervals[i][0] = Math.min(a, b);
            intervals[i][1] = Math.max(a, b);
        }

        Arrays.sort(intervals, (a, b) -> Long.compare(a[0], b[0]));

        long left = intervals[0][0];
        long right = intervals[0][1];
        long totalLength = 0;
        for (int i = 1; i < n; i++) {
            long nextLeft = intervals[i][0];
            long nextRight = intervals[i][1];
            if (nextLeft <= right) {
                right = Math.max(right, nextRight);
            } else {
                totalLength += right - left;
                left = nextLeft;
                right = nextRight;
            }
        }
        totalLength += right - left;

        System.out.println(totalLength);
    }
}
```
