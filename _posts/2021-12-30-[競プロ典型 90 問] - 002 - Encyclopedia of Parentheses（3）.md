---
title: AtCoder. 002 Encyclopedia of Parentheses(3)
author: MINJUN PARK
date: 2021-12-30 02:38:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    Encyclopedia of Parentheses,
  ]
pin: false
lang: en
translation_key: atcoder-typical90-002-parentheses
permalink: /posts/競プロ典型-90-問-002-Encyclopedia-of-Parentheses-3/
---

Generate each parenthesis string from left to right. At any prefix, the number of closing parentheses cannot exceed the number of opening parentheses; otherwise no suffix could make the string balanced. Also, a complete balanced string of length `N` has exactly `N/2` opening parentheses.

The recursion therefore adds `(` while fewer than `N/2` openings have been used, and adds `)` only when `close < open`. A prefix with equal counts is allowed: subsequent openings can still make it valid. When the length reaches `N`, these rules guarantee the string is balanced, so it can be emitted. Trying `(` before `)` visits the valid strings in lexicographic order. For odd `N`, no leaf can satisfy the equal-count condition, so no result is printed.

The prefix-balance invariant is `0 <= close <= open <= N/2`. Each result is generated once, and the work is proportional to the total output size; the recursion and current string use `O(N)` working space, in addition to the buffered output.

[Problem link](https://AtCoder.jp/contests/typical90/tasks/typical90_b)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static int n;
    private static final StringBuilder current = new StringBuilder();
    private static final StringBuilder output = new StringBuilder();

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        n = input.nextInt();
        generate(0, 0);
        System.out.print(output);
    }

    private static void generate(int open, int close) {
        if (current.length() == n) {
            if (open == close) {
                output.append(current).append('\n');
            }
            return;
        }

        if (open < n / 2) {
            current.append('(');
            generate(open + 1, close);
            current.setLength(current.length() - 1);
        }
        if (close < open) {
            current.append(')');
            generate(open, close + 1);
            current.setLength(current.length() - 1);
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
