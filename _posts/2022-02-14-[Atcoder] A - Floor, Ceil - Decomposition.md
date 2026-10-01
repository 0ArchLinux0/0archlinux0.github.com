---
title: AtCoder. Regular Contest 135 A Floor, Ceil Decomposition
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
		Coding Interview,
    AtCoder,
    Regular Contest
  ]
pin: false
lang: en
translation_key: arc135-a-floor-ceil-decomposition
---

[Problem: AtCoder ARC 135 A — Floor, Ceil Decomposition](https://atcoder.jp/contests/arc135/tasks/arc135_a)
[English] · [한국어](/ko/posts/arc135-a-floor-ceil-decomposition/) · [日本語](/ja/posts/arc135-a-floor-ceil-decomposition/)

For a positive integer `x`, define `f(x) = x` when `x <= 4`. Otherwise, split `x` into `floor(x / 2)` and `ceil(x / 2)`, and define `f(x)` as the product of their values modulo `998244353`:

`f(x) = f(floor(x / 2)) * f(ceil(x / 2)) mod 998244353`.

The split makes both arguments smaller, so the recurrence eventually reaches the base cases. Memoized recursion avoids recomputing the same arguments. At each recursion depth, the values are only the rounded halves of the original input, so there are at most two distinct values per depth. Thus there are `O(log x)` distinct states, recursion depth is `O(log x)`, and the memo table uses `O(log x)` space.

Reduce each recursive result modulo `998244353` before multiplying. Since each factor is less than the modulus, their product is less than `998244353²`, which fits in a signed Java `long`. Computing the upper half as `x / 2 + x % 2` also avoids overflowing `x + 1`.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Main {
    private static final long MOD = 998244353L;
    private static final Map<Long, Long> memo = new HashMap<>();

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        long x = Long.parseLong(input.readLine().trim());
        System.out.println(value(x));
    }

    private static long value(long x) {
        if (x <= 4) {
            return x;
        }

        Long cached = memo.get(x);
        if (cached != null) {
            return cached;
        }

        long lower = x / 2;
        long upper = lower + x % 2;
        long result = value(lower) * value(upper) % MOD;
        memo.put(x, result);
        return result;
    }
}
```
