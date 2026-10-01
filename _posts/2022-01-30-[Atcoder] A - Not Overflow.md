---
title: AtCoder. ABC 237 A Not Overflow
author: MINJUN PARK
date: 2022-01-30 21:05:00 +0900
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
translation_key: abc237-a-not-overflow
permalink: /posts/Atcoder-A-Not-Overflow/
---

[Problem: AtCoder ABC 237 A — Not Overflow](https://atcoder.jp/contests/abc237/tasks/abc237_a)
[English] · [한국어](/ko/posts/abc237-a-not-overflow/) · [日本語](/ja/posts/abc237-a-not-overflow/)

The input value can be much larger than a 32-bit signed integer, so read it as a `long`. A signed 32-bit integer ranges from `Integer.MIN_VALUE` (`-2^31`) through `Integer.MAX_VALUE` (`2^31 - 1`), inclusive. Compare the `long` value against both endpoints; print `Yes` exactly when it lies within that range, and `No` otherwise. Using inclusive comparisons correctly accepts both boundary values and rejects values just below or above them.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        long value = Long.parseLong(input.readLine().trim());
        System.out.println(Integer.MIN_VALUE <= value && value <= Integer.MAX_VALUE ? "Yes" : "No");
    }
}
```
