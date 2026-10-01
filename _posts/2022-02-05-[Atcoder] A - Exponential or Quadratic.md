---
title: AtCoder. ABC 238 A Exponential or Quadratic
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
translation_key: abc238-a-exponential-or-quadratic
---

[Problem: AtCoder ABC 238 A — Exponential or Quadratic](https://atcoder.jp/contests/abc238/tasks/abc238_a)
[English] · [한국어](/ko/posts/abc238-a-exponential-or-quadratic/) · [日本語](/ja/posts/abc238-a-exponential-or-quadratic/)

We need to determine whether `2^N > N^2`. There is no need to calculate the power: for `N = 1`, the inequality holds; for `N = 2, 3, 4`, it does not (`2^4 = 4^2`). Starting at `N = 5`, it is always true. If `2^N > N^2` for some `N >= 5`, then `2^(N + 1) > 2N^2 >= (N + 1)^2`, so the inequality continues to hold for every larger integer.

Therefore, the answer is `Yes` exactly when `N = 1` or `N >= 5`; otherwise it is `No`. A pair of integer comparisons is enough, even for large permitted values of `N`.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine().trim());
        System.out.println(n == 1 || n >= 5 ? "Yes" : "No");
    }
}
```
