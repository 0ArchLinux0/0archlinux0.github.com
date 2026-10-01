---
title: AtCoder. Regular Contest 135 B Sum of Three Terms
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    XOR,
    Regular Contest
  ]
pin: false
lang: en
translation_key: arc135-b-sum-three-terms
---

[Problem: AtCoder ARC 135 B — Sum of Three Terms](https://atcoder.jp/contests/arc135/tasks/arc135_b) · [한국어](/ko/posts/arc135-b-sum-three-terms/) · [日本語](/ja/posts/arc135-b-sum-three-terms/)

We are given an array `A` of length `N`. We must determine whether there is a nonnegative integer array `B` of length `N + 2` such that

`A[i] = B[i] + B[i + 1] + B[i + 2]`

for every `0 <= i < N`. If one exists, print `Yes` and any such array; otherwise print `No`.

Subtract two consecutive equations:

`A[i + 1] - A[i] = B[i + 3] - B[i]`.

Thus, for each residue modulo 3, the values of `B` form an independent chain: once its first value is chosen, every later value in that chain is determined by the differences of `A`. For residue `r`, define `prefix[r]` as the cumulative sum of these differences along the chain, including the initial sum 0. Then every value in that chain is `B[r] + prefix[r]`. It is nonnegative exactly when `B[r]` is at least `-minPrefix[r]`.

Choose the smallest possible starting values for residues 0 and 1: `B[0] = -minPrefix[0]` and `B[1] = -minPrefix[1]`. The first equation requires `B[0] + B[1] + B[2] = A[0]`, so it forces `B[2] = A[0] - B[0] - B[1]`. If `B[2] < -minPrefix[2]`, no solution is possible: the minimum required starting values for all three chains already sum to more than `A[0]`. Otherwise all chains are nonnegative, and the recurrence constructs a valid `B`.

This reasoning also covers `N = 1`: there are no differences, all three minimum prefixes are zero, and any nonnegative split of `A[0]` works. In particular, when `A[0]` is zero the constructed values are all zero.

The algorithm processes each adjacent pair of `A` once, so its time complexity is `O(N)`. It stores `A` and the constructed array, using `O(N)` space. `long` is used for differences, prefix sums, and constructed values.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        long[] a = new long[n];
        StringTokenizer tokens = new StringTokenizer(input.readLine());
        for (int i = 0; i < n; i++) {
            a[i] = Long.parseLong(tokens.nextToken());
        }

        long[] prefix = new long[3];
        long[] minPrefix = new long[3];
        for (int i = 0; i + 1 < n; i++) {
            int residue = i % 3;
            prefix[residue] += a[i + 1] - a[i];
            minPrefix[residue] = Math.min(minPrefix[residue], prefix[residue]);
        }

        long[] b = new long[n + 2];
        b[0] = -minPrefix[0];
        b[1] = -minPrefix[1];
        b[2] = a[0] - b[0] - b[1];
        if (b[2] < -minPrefix[2]) {
            System.out.println("No");
            return;
        }

        for (int i = 0; i + 3 < n + 2; i++) {
            b[i + 3] = b[i] + a[i + 1] - a[i];
        }

        StringBuilder output = new StringBuilder("Yes\n");
        for (int i = 0; i < b.length; i++) {
            if (i > 0) {
                output.append(' ');
            }
            output.append(b[i]);
        }
        System.out.println(output);
    }
}
```
