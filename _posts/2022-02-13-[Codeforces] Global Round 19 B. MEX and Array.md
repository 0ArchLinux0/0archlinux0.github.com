---
title: Codeforces Global Round 19 B. MEX and Array
author: MINJUN PARK
date: 2022-02-13 11:30:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Codeforces, Codeforces Global Round, MEX and Array, Math]
pin: false
lang: en
translation_key: cf-1637b-mex-and-array
permalink: /posts/Codeforces-Global-Round-19-B.-MEX-and-Array/
---

[Problem: Codeforces 1637B — MEX and Array](https://codeforces.com/contest/1637/problem/B) · [한국어](/ko/posts/cf-1637b-mex-and-array/) · [日本語](/ja/posts/cf-1637b-mex-and-array/)

For an array, a partition splits it into contiguous non-empty segments. Its cost is the number of segments plus the sum of the MEX of each segment, and the array's value is the maximum such cost. We must sum the values of every non-empty subsegment of the given array.

The official constraints are `1 <= t <= 30`, `1 <= n <= 100`, and `0 <= a[i] <= 10^9`; the sum of `n` over all test cases is at most `100`. In particular, values may be zero or much larger than `n`.

## Value of one subsegment

For a subsegment of length `m` containing `z` zeroes, its value is exactly `m + z`.

To see the upper bound, consider any one segment of a partition, with length `L`, containing `q` zeroes. If its MEX is `x`, then it contains every integer from `0` to `x - 1`. Thus it has at least `x` elements, and when `x > 0` it contains at least one zero. In either case, `1 + x <= L + q`. Summing this inequality over all partition segments bounds the cost by the total length plus the total number of zeroes, namely `m + z`.

This bound is attainable by partitioning into single-element segments: a nonzero element has MEX `0` and contributes `1`, while a zero has MEX `1` and contributes `2`. The resulting cost is `m + z`, proving the claim.

## Sum over all subsegments

There are `(n - k + 1)` subsegments of length `k`, so summing their lengths gives

`sum(k * (n - k + 1), k = 1..n) = n * (n + 1) * (n + 2) / 6`.

Each zero at zero-based index `i` belongs to exactly `(i + 1) * (n - i)` subsegments: choose the subsegment's left endpoint from the `i + 1` positions up to `i`, and its right endpoint from the `n - i` positions starting at `i`. Therefore the full answer is

`n * (n + 1) * (n + 2) / 6 + sum((i + 1) * (n - i))` over all indices where `a[i] == 0`.

So the formula in the original implementation is correct for the official statement. The answer is accumulated in `long`; at `n <= 100` it is well within range. The algorithm takes `O(n)` time and `O(1)` auxiliary space per test case.

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int ptr;
        private int len;

        private int read() throws IOException {
            if (ptr == len) {
                len = in.read(buffer);
                ptr = 0;
                if (len == -1) return -1;
            }
            return buffer[ptr++];
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

    public static void main(String[] args) throws IOException {
        FastScanner fs = new FastScanner();
        int tests = fs.nextInt();
        StringBuilder answer = new StringBuilder();

        while (tests-- > 0) {
            int n = fs.nextInt();
            long valueSum = (long) n * (n + 1) * (n + 2) / 6;
            for (int i = 0; i < n; i++) {
                if (fs.nextInt() == 0) {
                    valueSum += (long) (i + 1) * (n - i);
                }
            }
            answer.append(valueSum).append('\n');
        }
        System.out.print(answer);
    }
}
```
