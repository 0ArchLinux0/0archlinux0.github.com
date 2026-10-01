---
title: BOJ. ナップサック問題 (1450)
author: MINJUN PARK
date: 2022-01-27 22:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Knapsack problem, Meet in middle, Binary Search, ナップサック問題]
pin: false
lang: ja
translation_key: boj-1450-meet-in-middle
permalink: /ja/posts/boj-1450-meet-in-middle/
source_permalink: /posts/BOJ-1450/
---

[問題: BOJ 1450 — ナップサック問題](https://www.acmicpc.net/problem/1450)

## ミート・イン・ザ・ミドル

`N = 30` のとき、すべての部分集合を列挙すると `O(2^N)` の時間がかかります。そこで品物を二つのグループに分け、それぞれの部分集合の合計を列挙します。各リストの要素数は最大 `2^(N/2)` です。右側の合計をソートし、左側の各合計 `s` に対して `C - s` より大きい最初の右側合計の位置を二分探索します。その位置より前にある合計の個数が、`s` と組み合わせて容量内に収まる右側の選び方の数です。

両方のリストは空集合の合計 `0` から始まるため、空集合も正確に一度だけ数えられます。合計が 0 の部分集合を除外してはいけません。合計が同じでも、異なる部分集合はそれぞれ別の選択です。品物の重さはすべて正なので、`C` を超える片側の合計が有効な組み合わせに含まれることはありません。実装ではそのような合計だけを除外します。答えは最大 `2^30` になるため、部分和と答えには `long` を使います。

時間計算量は `O(2^(N/2) log 2^(N/2))`、空間計算量は `O(2^(N/2))` です。

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        long capacity = input.nextLong();
        int middle = n / 2;

        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = input.nextInt();
        }

        List<Long> leftSums = subsetSums(weights, 0, middle, capacity);
        List<Long> rightSums = subsetSums(weights, middle, n, capacity);
        Collections.sort(rightSums);

        long count = 0;
        for (long leftSum : leftSums) {
            count += upperBound(rightSums, capacity - leftSum);
        }

        System.out.println(count);
    }

    private static List<Long> subsetSums(int[] weights, int from, int to, long capacity) {
        List<Long> sums = new ArrayList<>();
        sums.add(0L);
        for (int i = from; i < to; i++) {
            int currentSize = sums.size();
            for (int j = 0; j < currentSize; j++) {
                long nextSum = sums.get(j) + weights[i];
                if (nextSum <= capacity) {
                    sums.add(nextSum);
                }
            }
        }
        return sums;
    }

    private static int upperBound(List<Long> sorted, long value) {
        int low = 0;
        int high = sorted.size();
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (sorted.get(middle) <= value) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
        return low;
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            return (int) nextLong();
        }

        long nextLong() throws IOException {
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);

            long value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
