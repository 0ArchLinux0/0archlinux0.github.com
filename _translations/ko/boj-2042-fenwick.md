---
title: BOJ. Prefix sum (2042)
author: MINJUN PARK
date: 2021-12-28 01:28:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Fenwick Tree, BOJ, Prefix sum, 구간 합 구하기]
pin: false
lang: ko
translation_key: boj-2042-fenwick
permalink: /ko/posts/boj-2042-fenwick/
---

펜윅 트리(이진 인덱스 트리)는 부분 합을 저장하여 한 원소의 변경과 구간의 접두 합 조회를 각각 `O(log N)` 시간에 처리합니다. 원본 값은 별도로 보관합니다. 1-based 인덱스 `i`의 값이 `old`에서 `new`로 바뀌면 차이 `new - old`를 트리의 인덱스 `i`에 더합니다.

1-based 인덱스 `i`에서 `i & -i`는 가장 낮은 비트 하나를 추출합니다. 접두 합을 구할 때는 이 값을 반복해서 빼며 겹치지 않는 블록으로 이동하고, 값을 갱신할 때는 반복해서 더하며 해당 값을 포함하는 모든 블록을 방문합니다. `a`부터 `b`까지의 합은 `prefix(b) - prefix(a - 1)`입니다. 초기 노드를 부모 노드에 전파해 트리를 만들면 `O(N)` 시간이 걸립니다. 변경과 조회는 각각 `O(log N)` 시간이며, 값 배열과 트리가 사용하는 공간은 `O(N)`입니다. 원소와 누적 합을 저장하기 위해 `long`을 사용합니다.

[문제 링크](https://www.acmicpc.net/problem/2042)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int updateCount = input.nextInt();
        int queryCount = input.nextInt();

        long[] values = new long[n];
        for (int i = 0; i < n; i++) {
            values[i] = input.nextLong();
        }
        FenwickTree tree = new FenwickTree(values);

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < updateCount + queryCount; i++) {
            int type = input.nextInt();
            int a = input.nextInt();
            long b = input.nextLong();
            if (type == 1) {
                int index = a - 1;
                long delta = b - values[index];
                values[index] = b;
                tree.add(a, delta);
            } else {
                output.append(tree.prefixSum((int) b) - tree.prefixSum(a - 1)).append('\n');
            }
        }
        System.out.print(output);
    }

    private static final class FenwickTree {
        private final long[] tree;

        FenwickTree(long[] values) {
            tree = new long[values.length + 1];
            for (int i = 1; i < tree.length; i++) {
                tree[i] += values[i - 1];
                int parent = i + (i & -i);
                if (parent < tree.length) {
                    tree[parent] += tree[i];
                }
            }
        }

        void add(int index, long delta) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i] += delta;
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

            boolean negative = c == '-';
            if (negative) {
                c = read();
            }
            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return negative ? -value : value;
        }

        int nextInt() throws IOException {
            return (int) nextLong();
        }
    }
}
```
