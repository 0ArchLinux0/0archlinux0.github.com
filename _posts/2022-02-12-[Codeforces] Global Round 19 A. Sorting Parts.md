---
title: Codeforces Global Round 19 A. Sorting Parts
author: MINJUN PARK
date: 2022-02-12 23:35:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
		Codeforces Global Round,
    Codeforces,
    Sorting Parts
  ]
pin: false
lang: en
translation_key: cf-1637a-sorting-parts
---

[Problem: Codeforces 1637A — Sorting Parts](https://codeforces.com/contest/1637/problem/A) · [한국어](/ko/posts/cf-1637a-sorting-parts/) · [日本語](/ja/posts/cf-1637a-sorting-parts/)

For each test case, choose a split `k` with `1 <= k < n`. Sort the prefix `a[1..k]` and the suffix `a[k+1..n]` independently. The question is whether there is a valid split for which the resulting whole array is **not** non-decreasing. This is not a choice of one arbitrary segment: both sides of the split are sorted.

If the original array is non-decreasing, then sorting either part leaves its values in their existing order, and the boundary between the parts is also ordered. Every split therefore produces a sorted array, so print `NO`.

If the array is not non-decreasing, it has an adjacent inversion `a[i] > a[i + 1]`. Choose `k = i`, so the inversion lies exactly across the split. After sorting the prefix, its last value is the prefix maximum, which is at least `a[i]`; after sorting the suffix, its first value is the suffix minimum, which is at most `a[i + 1]`. Thus the boundary still has left value greater than right value, and the resulting array is unsorted. Print `YES`. This works even if there is only one inversion or values repeat; only a strict `>` is an inversion.

The constraints have `n >= 2`, so a valid split always exists. For a length-one array, if considered outside those constraints, there is no valid split and the answer is `NO`. (The code also returns `NO` because there is no adjacent pair.)

The scan checks each adjacent pair once, taking `O(n)` time. The input array uses `O(n)` space; aside from that array, the decision uses `O(1)` extra space. The program reads the test-case count, then each `n` and its array, and appends one uppercase `YES` or `NO` line per test case.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int n = input.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = input.nextInt();

            boolean hasInversion = false;
            for (int i = 0; i + 1 < n; i++) {
                if (a[i] > a[i + 1]) {
                    hasInversion = true;
                    break;
                }
            }

            output.append(hasInversion ? "YES\n" : "NO\n");
        }

        System.out.print(output);
    }

    static class FastScanner {
        private final BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        int nextInt() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return Integer.parseInt(tokenizer.nextToken());
        }
    }
}
```
