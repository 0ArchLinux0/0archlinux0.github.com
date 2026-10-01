---
title: AtCoder. ABC 238 C digitnum
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
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
translation_key: abc238-c-digitnum
---

[Problem: AtCoder ABC 238 C — digitnum](https://atcoder.jp/contests/abc238/tasks/abc238_c) · [한국어](/ko/posts/abc238-c-digitnum/) · [日本語](/ja/posts/abc238-c-digitnum/)

For every integer from `1` through `N`, add its number of decimal digits, then print the sum modulo `998244353`.

Group the integers by digit length. For a length `d`, the applicable range is `[10^(d-1), min(N, 10^d - 1)]`; add `d` times the number of integers in that range. The loop stops once its range reaches `N`.

The number of digit lengths is `O(log N)`, so the time complexity is `O(log N)` and the extra space complexity is `O(1)`. All range endpoints are computed as `long`. To avoid overflowing when forming `10^d`, the code checks whether the next power of ten is at most `N` before multiplying by ten.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static final long MOD = 998244353L;

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(input.readLine().trim());

        long answer = 0;
        long start = 1;
        for (long digits = 1; start <= n; digits++) {
            long end = n;
            if (start <= n / 10) {
                end = start * 10 - 1;
            }
            long count = end - start + 1;
            answer = (answer + (digits % MOD) * (count % MOD)) % MOD;
            if (end == n) {
                break;
            }
            start *= 10;
        }

        System.out.println(answer);
    }
}
```
