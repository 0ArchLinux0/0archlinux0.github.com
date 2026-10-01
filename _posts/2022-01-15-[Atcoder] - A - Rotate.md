---
title: AtCoder. ABC 235 A - Rotate
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    ABC contest
  ]
pin: false
lang: en
translation_key: abc235-a-digit-rotations
---

[Problem: AtCoder ABC 235 A — Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_a) · [한국어](/ko/posts/abc235-a-digit-rotations/) · [日本語](/ja/posts/abc235-a-digit-rotations/)

Given a three-digit decimal number, let its hundreds, tens, and ones digits be `A`, `B`, and `C`. The three left cyclic rotations are `ABC`, `BCA`, and `CAB`; output their sum. These are permutations of the three input digits, so repeated digits and zero are handled without any special cases. For example, input `123` produces `123`, `231`, and `312`, whose sum is `666`.

Read the input as a string to retain the three digit positions even when `B` or `C` is `0`. Convert each character to a digit and evaluate the place values directly: `100A + 10B + C`, `100B + 10C + A`, and `100C + 10A + B`. Their sum is at most `2997`, so an `int` is sufficient. The time and additional space complexities are both `O(1)`.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String digits = input.readLine();
        int a = digits.charAt(0) - '0';
        int b = digits.charAt(1) - '0';
        int c = digits.charAt(2) - '0';

        int answer = (100 * a + 10 * b + c)
                + (100 * b + 10 * c + a)
                + (100 * c + 10 * a + b);
        System.out.println(answer);
    }
}
```
