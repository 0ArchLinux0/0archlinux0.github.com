---
title: AtCoder ABC 238 A — Exponential or Quadratic
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, AtCoder, ABC 238]
pin: false
lang: ko
translation_key: abc238-a-exponential-or-quadratic
permalink: /ko/posts/abc238-a-exponential-or-quadratic/
source_permalink: /posts/Atcoder-A-Exponential-or-Quadratic/
---

[문제: AtCoder ABC 238 A — Exponential or Quadratic](https://atcoder.jp/contests/abc238/tasks/abc238_a)
[English](/posts/Atcoder-A-Exponential-or-Quadratic/) · [한국어] · [日本語](/ja/posts/abc238-a-exponential-or-quadratic/)

`2^N > N^2`인지 판정하는 문제입니다. 거듭제곱을 직접 계산할 필요는 없습니다. `N = 1`일 때 부등식은 참이고, `N = 2, 3, 4`일 때는 거짓입니다(`2^4 = 4^2`). `N = 5`부터는 항상 참입니다. 어떤 `N >= 5`에서 `2^N > N^2`라고 가정하면, `2^(N + 1) > 2N^2 >= (N + 1)^2`이므로 더 큰 모든 정수에서도 부등식이 참임을 알 수 있습니다.

따라서 `N = 1` 또는 `N >= 5`일 때만 `Yes`를 출력하고, 나머지 경우에는 `No`를 출력하면 됩니다. 입력 가능한 `N`이 커도 정수 비교 두 번이면 충분합니다.

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
