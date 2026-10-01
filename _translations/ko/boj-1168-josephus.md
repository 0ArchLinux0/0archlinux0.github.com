---
title: BOJ. Josephus problem(2) (1168)
author: MINJUN PARK
date: 2022-01-20 00:36:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
		Segment Tree,
    BOJ,
    Josephus problem(2),
    요세푸스 문제 2,
		Important
  ]
pin: false
lang: ko
translation_key: boj-1168-josephus
permalink: /ko/posts/boj-1168-josephus/
---

[문제 링크: BOJ 1168 — 요세푸스 문제 2](https://www.acmicpc.net/problem/1168)

1번부터 `N`번까지 번호가 매겨진 사람이 원형으로 서 있습니다. 다음 사람부터 세어 매 `K`번째 사람을 차례로 제거하고, 제거 순서를 `<a, b, ...>` 형식으로 출력합니다.

## 생존자 수를 저장하는 펜윅 트리

각 위치의 값은 해당 번호의 사람이 아직 원에 있으면 1, 제거되었으면 0인 펜윅 트리로 관리합니다. 따라서 구간 접두 합으로 해당 위치까지 남은 사람 수를 알 수 있습니다. 이 트리에서는 사람 한 명을 제거하거나 현재 생존자 순위에 해당하는 사람을 찾는 작업을 각각 `O(log N)`에 수행할 수 있습니다.

`rank`는 다음에 셀 사람의 현재 생존자 중 0부터 시작하는 순위이고, `remaining`은 생존자 수입니다. 이번에 제거할 사람의 순위는 `(rank + K - 1) % remaining`입니다. 펜윅 트리의 이진 탐색으로 1-based 순위 `rank + 1`에 해당하는 원래 번호를 찾아 그 위치의 값을 감소시킵니다. 제거 직후 다음 순회 시작 위치는 그 다음 생존자이므로 새 원에서의 순위는 `rank % (remaining - 1)`입니다. 마지막 한 명을 제거할 때는 이 갱신을 생략해 0으로 나누지 않습니다. `N = 1`이면 순위 0인 한 명만 출력되어 결과는 `<1>`입니다.

각 사람을 제거할 때 순위 검색과 갱신에 `O(log N)`이 걸리므로 시간 복잡도는 `O(N log N)`, 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int k = input.nextInt();

        FenwickTree live = new FenwickTree(n);
        StringBuilder output = new StringBuilder("<");
        int rank = 0;

        for (int remaining = n; remaining > 0; remaining--) {
            rank = (int) ((rank + (long) k - 1) % remaining);
            int person = live.findByOrder(rank + 1);
            live.add(person, -1);
            if (remaining > 1) {
                rank %= remaining - 1;
            }

            if (output.length() > 1) {
                output.append(", ");
            }
            output.append(person);
        }
        output.append('>');
        System.out.print(output);
    }

    private static class FenwickTree {
        private final int size;
        private final int[] tree;
        private final int highestPowerOfTwo;

        FenwickTree(int size) {
            this.size = size;
            this.tree = new int[size + 1];
            for (int i = 1; i <= size; i++) {
                tree[i] = i & -i;
            }

            int power = 1;
            while (power <= size / 2) {
                power <<= 1;
            }
            highestPowerOfTwo = power;
        }

        void add(int index, int delta) {
            for (int i = index; i <= size; i += i & -i) {
                tree[i] += delta;
            }
        }

        int findByOrder(int order) {
            int index = 0;
            for (int step = highestPowerOfTwo; step > 0; step >>= 1) {
                int next = index + step;
                if (next <= size && tree[next] < order) {
                    index = next;
                    order -= tree[next];
                }
            }
            return index + 1;
        }
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
