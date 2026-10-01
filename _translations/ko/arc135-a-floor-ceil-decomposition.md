---
title: AtCoder ARC 135 A — Floor, Ceil Decomposition
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ARC 135]
pin: false
lang: ko
translation_key: arc135-a-floor-ceil-decomposition
permalink: /ko/posts/arc135-a-floor-ceil-decomposition/
source_permalink: /posts/Atcoder-A-Floor,-Ceil-Decomposition/
---

[문제: AtCoder ARC 135 A — Floor, Ceil Decomposition](https://atcoder.jp/contests/arc135/tasks/arc135_a)
[English](/posts/Atcoder-A-Floor,-Ceil-Decomposition/) · [日本語](/ja/posts/arc135-a-floor-ceil-decomposition/)

양의 정수 `x`에 대해 `x <= 4`이면 `f(x) = x`입니다. 그보다 큰 경우 `x`를 `floor(x / 2)`와 `ceil(x / 2)`로 나누고, 두 함수값의 곱을 `998244353`으로 나눈 나머지로 정의합니다.

`f(x) = f(floor(x / 2)) * f(ceil(x / 2)) mod 998244353`.

두 인수는 모두 원래 값보다 작으므로 재귀적으로 계산하면 결국 기저 조건에 도달합니다. 메모이제이션을 사용하면 같은 인수를 다시 계산하지 않습니다. 각 재귀 깊이에서 등장하는 값은 원래 수를 반복해서 절반으로 나눈 값의 내림 또는 올림뿐이므로 깊이마다 서로 다른 상태는 최대 두 개입니다. 따라서 서로 다른 상태 수와 재귀 깊이는 `O(log x)`이고, 메모리 사용량도 `O(log x)`입니다.

각 재귀 결과는 곱하기 전에 법으로 나머지를 취합니다. 두 인수는 모두 법보다 작으므로 곱은 `998244353²`보다 작고 Java의 부호 있는 `long` 범위에 들어갑니다. 올림 절반은 `x / 2 + x % 2`로 계산하여 `x + 1`의 오버플로도 피합니다.

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
