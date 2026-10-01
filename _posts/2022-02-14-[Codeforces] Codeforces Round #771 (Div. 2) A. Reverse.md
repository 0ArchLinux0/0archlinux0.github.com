---
title: Codeforces Round 771 (Div. 2) A. Reverse
author: MINJUN PARK
date: 2022-02-14 23:35:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
		Codeforces Round,
		Div 2,
    Codeforces,
    Reverse
  ]
pin: false
lang: en
translation_key: cf-1638a-reverse
---

[Problem: Codeforces 1638A — Reverse](https://codeforces.com/contest/1638/problem/A) · [한국어](/ko/posts/cf-1638a-reverse/) · [日本語](/ja/posts/cf-1638a-reverse/)

The array is a permutation of `1..n`. We may choose one segment and reverse it; the goal is to make the permutation lexicographically smallest.

Scan from left to right to find the first index `i` where `p[i] != i + 1`. Every earlier position already contains its smallest possible value, so it must stay fixed. The value `i + 1` occurs somewhere to the right; let its position be `j`. Reversing `[i, j]` moves `i + 1` to the first mismatching position while leaving the already-correct prefix unchanged. This is the lexicographically smallest possible result.

If no mismatch exists, the permutation is already sorted, so reversing a length-one segment leaves it unchanged. Finding the mismatch and the target value takes `O(n)` time, and the input permutation uses `O(n)` space per test case.

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
            int[] permutation = new int[n];
            for (int i = 0; i < n; i++) {
                permutation[i] = fs.nextInt();
            }

            int left = 0;
            while (left < n && permutation[left] == left + 1) {
                left++;
            }

            if (left < n) {
                int right = left;
                while (permutation[right] != left + 1) {
                    right++;
                }
                while (left < right) {
                    int value = permutation[left];
                    permutation[left++] = permutation[right];
                    permutation[right--] = value;
                }
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) answer.append(' ');
                answer.append(permutation[i]);
            }
            answer.append('\n');
        }

        System.out.print(answer);
    }
}
```
