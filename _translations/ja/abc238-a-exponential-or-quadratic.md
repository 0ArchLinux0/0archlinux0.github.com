---
title: AtCoder ABC 238 A — Exponential or Quadratic
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 238]
pin: false
lang: ja
translation_key: abc238-a-exponential-or-quadratic
permalink: /ja/posts/abc238-a-exponential-or-quadratic/
source_permalink: /posts/Atcoder-A-Exponential-or-Quadratic/
---

[問題: AtCoder ABC 238 A — Exponential or Quadratic](https://atcoder.jp/contests/abc238/tasks/abc238_a)
[English](/posts/Atcoder-A-Exponential-or-Quadratic/) · [한국어](/ko/posts/abc238-a-exponential-or-quadratic/) · [日本語]

`2^N > N^2`かどうかを判定します。べき乗を実際に計算する必要はありません。`N = 1`では不等式が成り立ち、`N = 2, 3, 4`では成り立ちません（`2^4 = 4^2`）。`N = 5`以降では常に成り立ちます。ある`N >= 5`で`2^N > N^2`が成り立つとすると、`2^(N + 1) > 2N^2 >= (N + 1)^2`です。したがって、さらに大きいすべての整数でも不等式が成り立ちます。

よって、`N = 1`または`N >= 5`の場合に限り`Yes`を出力し、それ以外では`No`を出力します。入力可能な`N`が大きくても、整数の比較を2回行うだけで判定できます。

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
