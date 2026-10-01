---
title: BOJ. 구간 곱 구하기 (11505)
author: MINJUN PARK
date: 2021-12-31 23:13:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, Segment Tree, BOJ, Prefix Product, 구간 곱 구하기]
pin: false
lang: ko
translation_key: boj-11505-range-product
permalink: /ko/posts/boj-11505-range-product/
---

[문제 링크](https://www.acmicpc.net/problem/11505)

배열의 한 원소를 갱신하면서 임의의 닫힌 구간 곱을 `1,000,000,007`로 나눈 나머지로 구합니다. 갱신 때 한 위치만 바뀌므로 반복형 세그먼트 트리에서 해당 리프와 그 조상만 다시 계산하면 됩니다. 각 내부 노드는 두 자식 값의 곱을 `MOD`로 나눈 값입니다.

트리의 `n`개 리프는 `[n, 2n)`에 둡니다. 인덱스 `p`를 갱신할 때 리프 `p + n`을 새 값으로 바꾸고 부모로 올라가며 각 노드를 다시 계산합니다. 질의 구간 `[left, right]`는 반열린 구간 `[left + n, right + n + 1)`으로 바꿉니다. 매 단계에서 왼쪽 경계가 오른쪽 자식이면 해당 노드를 왼쪽 누적값에 포함하고, 오른쪽 경계가 홀수이면 그 직전 노드를 오른쪽 누적값에 포함한 다음 두 경계를 부모로 올립니다. 이 방식은 질의 구간을 덮는 트리 노드만 방문하며 양 끝점을 각각 정확히 한 번 포함합니다.

`MOD`에서 곱셈의 항등원은 `1`이므로 두 누적값을 `1`로 시작합니다. 따라서 원소 하나만 포함하는 구간이나 0을 포함하는 구간도 올바르게 처리됩니다. 갱신은 새 값을 리프에 직접 저장하므로 값이 `0`이어도 나눗셈이나 별도의 0 관리가 필요하지 않습니다. `MOD`보다 작은 두 수의 곱은 `int` 범위를 넘을 수 있으므로 곱셈 전에 한 피연산자를 `long`으로 변환합니다. 트리 구성은 `O(N)`, 점 갱신과 구간 질의는 각각 `O(log N)`이며, 공간 복잡도는 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int MOD = 1_000_000_007;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int updates = input.nextInt();
        int queries = input.nextInt();
        int operationCount = updates + queries;

        int[] tree = new int[2 * n];
        for (int i = 0; i < n; i++) {
            tree[n + i] = input.nextInt();
        }
        for (int node = n - 1; node > 0; node--) {
            tree[node] = multiply(tree[node << 1], tree[node << 1 | 1]);
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < operationCount; i++) {
            int type = input.nextInt();
            int b = input.nextInt();
            int c = input.nextInt();
            if (type == 1) {
                set(tree, n, b - 1, c);
            } else {
                output.append(product(tree, n, b - 1, c)).append('\n');
            }
        }
        System.out.print(output);
    }

    private static int multiply(int a, int b) {
        return (int) ((long) a * b % MOD);
    }

    private static void set(int[] tree, int n, int index, int value) {
        int node = n + index;
        tree[node] = value;
        while ((node >>= 1) > 0) {
            tree[node] = multiply(tree[node << 1], tree[node << 1 | 1]);
        }
    }

    // Returns the product on the zero-based inclusive interval [left, right].
    private static int product(int[] tree, int n, int left, int right) {
        int l = left + n;
        int r = right + n + 1;
        int leftProduct = 1;
        int rightProduct = 1;
        while (l < r) {
            if ((l & 1) != 0) leftProduct = multiply(leftProduct, tree[l++]);
            if ((r & 1) != 0) rightProduct = multiply(tree[--r], rightProduct);
            l >>= 1;
            r >>= 1;
        }
        return multiply(leftProduct, rightProduct);
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
