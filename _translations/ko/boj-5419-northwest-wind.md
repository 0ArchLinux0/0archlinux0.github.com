---
title: BOJ 5419 - 북서풍
author: MINJUN PARK
date: 2022-02-17 10:57:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 스위핑, 펜윅 트리, 북서풍]
pin: false
lang: ko
translation_key: boj-5419-northwest-wind
permalink: /ko/posts/boj-5419-northwest-wind/
source_permalink: /posts/BOJ-5419/
---

[문제: BOJ 5419 — 북서풍](https://www.acmicpc.net/problem/5419) · [English](/posts/BOJ-5419/) · [日本語](/ja/posts/boj-5419-northwest-wind/)

각 테스트 케이스에서 `x1 <= x2`이고 `y1 >= y2`인 점의 쌍 `(x1, y1)`, `(x2, y2)`의 개수를 셉니다. 왼쪽에서 오른쪽으로 스위핑하며 쌍을 한 번씩 셉니다. 따라서 x좌표가 같은 점은 y좌표가 큰 순서로 처리하며, 좌표가 완전히 같은 점도 입력에서 서로 다른 점이므로 두 복사본을 고르는 경우마다 한 쌍으로 셉니다.

점들을 x좌표 오름차순, 그다음 y좌표 내림차순으로 정렬합니다. 그러면 현재 점을 처리할 때 이미 처리한 모든 점은 x좌표가 현재 점 이하입니다. x좌표가 같다면 y좌표가 더 큰 점을 먼저 처리하므로, 이전 점은 y좌표도 현재 점 이상입니다. 이 순서로 같은 x좌표를 별도로 묶지 않고도 조건을 만족하며 중복 점도 올바르게 셉니다.

모든 y좌표를 오름차순으로 `1..K` 범위의 순위로 압축합니다. 펜윅 트리는 각 순위에 해당하는, 이미 처리한 점의 개수를 저장합니다. 현재 순위가 `r`일 때 `r`부터 `K`까지의 구간 합을 질의하면 y좌표가 현재 점 이상인 이전 점의 수를 얻습니다. 이 값을 `long` 정답에 더한 다음 현재 점을 트리에 추가합니다. 삽입 전에 질의하므로 점 자신과는 쌍을 만들지 않습니다.

정렬 및 좌표 압축은 `O(N log N)`이고, 점마다 펜윅 트리 질의와 갱신도 각각 `O(log N)`이므로 전체 시간 복잡도는 `O(N log N)`입니다. 보조 공간 복잡도는 `O(N)`입니다. 최대 `N(N - 1) / 2`개의 쌍을 셀 수 있으므로 정답에는 `long`을 사용합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
    private static final class Point {
        final int x;
        final int y;
        int yRank;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private static final class FenwickTree {
        private final int[] tree;

        FenwickTree(int size) {
            tree = new int[size + 1];
        }

        void add(int index) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i]++;
            }
        }

        int prefixSum(int index) {
            int sum = 0;
            for (int i = index; i > 0; i -= i & -i) {
                sum += tree[i];
            }
            return sum;
        }
    }

    private static final class FastScanner {
        private final BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokens;

        int nextInt() throws IOException {
            while (tokens == null || !tokens.hasMoreTokens()) {
                tokens = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokens.nextToken());
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            Point[] points = new Point[n];
            int[] ys = new int[n];
            for (int i = 0; i < n; i++) {
                int x = input.nextInt();
                int y = input.nextInt();
                points[i] = new Point(x, y);
                ys[i] = y;
            }

            Arrays.sort(ys);
            int uniqueCount = 0;
            for (int y : ys) {
                if (uniqueCount == 0 || ys[uniqueCount - 1] != y) {
                    ys[uniqueCount++] = y;
                }
            }

            for (Point point : points) {
                point.yRank = Arrays.binarySearch(ys, 0, uniqueCount, point.y) + 1;
            }

            Arrays.sort(points, Comparator
                    .comparingInt((Point point) -> point.x)
                    .thenComparing(Comparator.comparingInt((Point point) -> point.y).reversed()));

            FenwickTree fenwick = new FenwickTree(uniqueCount);
            long answer = 0;
            for (Point point : points) {
                int lessThanY = fenwick.prefixSum(point.yRank - 1);
                int atLeastY = fenwick.prefixSum(uniqueCount) - lessThanY;
                answer += atLeastY;
                fenwick.add(point.yRank);
            }
            output.append(answer).append('\n');
        }
        System.out.print(output);
    }
}
```
