---
title: BOJ. 디지털 비디오 디스크 (9345)
author: MINJUN PARK
date: 2022-01-20 00:38:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    Segment Tree,
    Data Structure,
    Digital Video Disk,
    디지털 비디오 디스크(DVDs),
		Review
  ]
pin: false
lang: ko
translation_key: boj-9345-dvd-permutations
permalink: /ko/posts/boj-9345-dvd-permutations/
source_permalink: /posts/BOJ-9345/
---

[문제 링크](https://www.acmicpc.net/problem/9345) · [English](/posts/BOJ-9345/) · [日本語](/ja/posts/boj-9345-dvd-permutations/)

## DVD 순열을 위한 반복형 세그먼트 트리

배열은 처음에 `0, 1, ..., N - 1` 순열입니다. 0번 연산은 두 위치의 값을 교환하고, 1번 연산은 현재 구간 `[A, B]`에 번호가 `A`부터 `B`까지인 DVD만 들어 있는지 확인합니다.

배열이 순열이므로 이 조건은 `min(A..B) == A`이면서 `max(A..B) == B`인 것과 같습니다. 구간에는 `B - A + 1`개의 서로 다른 값이 있고 모두 `[A, B]` 안에 있습니다. 최솟값과 최댓값이 양 끝값이라면 그 사이의 모든 값도 반드시 포함됩니다. 반대로 필요한 DVD가 모두 있으면 최솟값과 최댓값은 각각 양 끝값입니다.

반복형 세그먼트 트리는 각 구간의 최솟값과 최댓값을 저장합니다. `size`는 `N` 이상인 가장 작은 2의 거듭제곱이며, 위치 `i`를 나타내는 리프는 `size + i`입니다. 각 내부 노드는 두 자식의 최솟값과 최댓값을 저장합니다. 한 점을 바꾸면 해당 리프를 갱신하고 조상 노드를 다시 계산하므로, 두 위치를 교환한 뒤 두 리프를 모두 갱신합니다. 구간 질의는 반열린 리프 구간 `[A, B + 1)`에서 위로 올라가며 포함되는 노드만 합칩니다. 비어 있는 패딩 리프는 최솟값에 `Integer.MAX_VALUE`, 최댓값에 `Integer.MIN_VALUE`를 넣어 질의 결과에 영향을 주지 않습니다.

점 갱신과 구간 질의는 각각 `O(log N)` 시간이고, 트리와 순열은 `O(N)` 공간을 사용합니다. `N = 1`일 때도 하나의 리프가 `[0, 0]` 질의를 올바르게 처리합니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int operations = input.nextInt();
            int[] dvdAt = new int[n];
            for (int i = 0; i < n; i++) {
                dvdAt[i] = i;
            }

            int size = 1;
            while (size < n) {
                size <<= 1;
            }
            int[] minTree = new int[size << 1];
            int[] maxTree = new int[size << 1];
            for (int i = 0; i < size; i++) {
                int value = i < n ? dvdAt[i] : Integer.MAX_VALUE;
                minTree[size + i] = value;
                maxTree[size + i] = i < n ? dvdAt[i] : Integer.MIN_VALUE;
            }
            for (int node = size - 1; node > 0; node--) {
                minTree[node] = Math.min(minTree[node << 1], minTree[node << 1 | 1]);
                maxTree[node] = Math.max(maxTree[node << 1], maxTree[node << 1 | 1]);
            }

            for (int operation = 0; operation < operations; operation++) {
                int type = input.nextInt();
                int a = input.nextInt();
                int b = input.nextInt();
                if (type == 0) {
                    int valueA = dvdAt[a];
                    int valueB = dvdAt[b];
                    dvdAt[a] = valueB;
                    dvdAt[b] = valueA;
                    update(minTree, maxTree, size, a, valueB);
                    update(minTree, maxTree, size, b, valueA);
                } else {
                    int minimum = Integer.MAX_VALUE;
                    int maximum = Integer.MIN_VALUE;
                    int left = size + a;
                    int right = size + b + 1;
                    while (left < right) {
                        if ((left & 1) != 0) {
                            minimum = Math.min(minimum, minTree[left]);
                            maximum = Math.max(maximum, maxTree[left]);
                            left++;
                        }
                        if ((right & 1) != 0) {
                            --right;
                            minimum = Math.min(minimum, minTree[right]);
                            maximum = Math.max(maximum, maxTree[right]);
                        }
                        left >>= 1;
                        right >>= 1;
                    }
                    output.append(minimum == a && maximum == b ? "YES\n" : "NO\n");
                }
            }
        }

        System.out.print(output);
    }

    private static void update(int[] minTree, int[] maxTree, int size, int position, int value) {
        int node = size + position;
        minTree[node] = value;
        maxTree[node] = value;
        for (node >>= 1; node > 0; node >>= 1) {
            minTree[node] = Math.min(minTree[node << 1], minTree[node << 1 | 1]);
            maxTree[node] = Math.max(maxTree[node << 1], maxTree[node << 1 | 1]);
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```
