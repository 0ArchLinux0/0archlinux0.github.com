---
title: BOJ. 数列とクエリ 21 (16975)
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
lang: ja
translation_key: boj-16975-range-add-point-query
permalink: /ja/posts/boj-16975-range-add-point-query/
source_permalink: /posts/BOJ-16975/
---

[BOJ 16975: 数列とクエリ 21](https://www.acmicpc.net/problem/16975)

## Fenwick Tree と差分配列による区間加算

初期値は基底配列に保持し、その後の加算は差分配列で表します。1-based で両端を含む区間 `[left, right]` のすべての位置に `x` を加えると、差分配列で変わるのは2か所だけです。`left` に `x`、`right + 1` に `-x` を加えます。したがって差分配列の `i` までの累積和は位置 `i` に加えられた合計であり、現在値は `base[i] + prefix(i)` です。

差分配列の値を Fenwick Tree で管理します。区間更新は Fenwick Tree の点更新2回、点クエリは prefix sum 1回で処理できるため、どちらも `O(log N)` 時間です。基底配列と Fenwick Tree の空間計算量は `O(N)` です。`N` で終わる更新でも打ち消しを `N + 1` に安全に記録できるよう、ツリーには追加の要素を設けます。クエリでは指定されたインデックスまでだけを合計します。更新の累積で `int` がオーバーフローしないよう、値と加算量には `long` を使います。

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
