---
title: BOJ. Data Structure (12899)
author: MINJUN PARK
date: 2022-01-19 20:42:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
		Segment Tree,
    BOJ,
    Data Structure,
    데이터 구조
  ]
pin: false
lang: ko
translation_key: boj-12899-order-statistics
permalink: /ko/posts/boj-12899-order-statistics/
---

고정된 값 범위 `[1, 2_000_000]`에서 삽입된 값들의 다중집합을 펜윅 트리로 관리합니다. `tree[i]`에는 최하위 비트 `i & -i`로 정해지는 구간의 빈도 합이 저장됩니다. 값을 삽입할 때 해당 빈도에 1을 더하고, 순위에 해당하는 값을 찾아 제거할 때는 1을 빼므로 같은 값도 서로 다른 원소로 셉니다.

`k`번째로 작은 값을 찾을 때는 펜윅 트리의 이진 탐색(binary lifting)으로 가장 큰 2의 거듭제곱부터 답을 구성합니다. 각 단계에서 후보 접두 구간의 빈도가 남은 순위보다 작을 때에만 해당 구간을 건너뛰고, 그 빈도만큼 `k`를 줄입니다. 모든 단계를 마치면 누적 빈도가 원래 순위에 처음 도달하는 값의 인덱스를 얻습니다. 중복 빈도를 포함해 처음과 마지막 유효 순위도 올바르게 처리합니다. 삽입, 선택, 제거는 각각 `O(log V)` 시간이며 `V = 2_000_000`입니다. 트리는 `O(V)` 공간을 사용합니다. 입력의 연산 횟수가 제한되어 있으므로 빈도는 `int`에 저장할 수 있습니다.

[문제 링크](https://www.acmicpc.net/problem/12899)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int MAX_VALUE = 2_000_000;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int operationCount = input.nextInt();
        FenwickTree frequencies = new FenwickTree(MAX_VALUE);
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < operationCount; i++) {
            int operation = input.nextInt();
            int valueOrRank = input.nextInt();
            if (operation == 1) {
                frequencies.add(valueOrRank, 1);
            } else {
                int value = frequencies.kth(valueOrRank);
                output.append(value).append('\n');
                frequencies.add(value, -1);
            }
        }
        System.out.print(output);
    }

    private static final class FenwickTree {
        private final int[] tree;

        FenwickTree(int size) {
            tree = new int[size + 1];
        }

        void add(int index, int delta) {
            for (int i = index; i < tree.length; i += i & -i) {
                tree[i] += delta;
            }
        }

        int kth(int rank) {
            int index = 0;
            for (int step = Integer.highestOneBit(tree.length - 1); step != 0; step >>= 1) {
                int next = index + step;
                if (next < tree.length && tree[next] < rank) {
                    index = next;
                    rank -= tree[next];
                }
            }
            return index + 1;
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

        int nextInt() throws IOException {
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
