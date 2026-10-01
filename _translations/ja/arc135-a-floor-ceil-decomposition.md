---
title: AtCoder ARC 135 A — Floor, Ceil Decomposition
author: MINJUN PARK
date: 2022-02-14 02:32:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ARC 135]
pin: false
lang: ja
translation_key: arc135-a-floor-ceil-decomposition
permalink: /ja/posts/arc135-a-floor-ceil-decomposition/
source_permalink: /posts/Atcoder-A-Floor,-Ceil-Decomposition/
---

[問題: AtCoder ARC 135 A — Floor, Ceil Decomposition](https://atcoder.jp/contests/arc135/tasks/arc135_a)
[English](/posts/Atcoder-A-Floor,-Ceil-Decomposition/) · [한국어](/ko/posts/arc135-a-floor-ceil-decomposition/)

正の整数`x`について、`x <= 4`なら`f(x) = x`です。それ以外の場合、`x`を`floor(x / 2)`と`ceil(x / 2)`に分け、対応する関数値の積を`998244353`で割った余りとして定義します。

`f(x) = f(floor(x / 2)) * f(ceil(x / 2)) mod 998244353`。

分割後の2つの値はいずれも元の値より小さいため、再帰計算は最終的に基底条件に到達します。メモ化により同じ値の再計算を避けます。各再帰の深さで現れるのは元の数を繰り返し半分にした値の切り捨てまたは切り上げだけなので、深さごとの異なる状態は最大2つです。したがって状態数と再帰の深さはいずれも`O(log x)`で、メモ表の空間計算量も`O(log x)`です。

掛け算の前に各再帰結果を法で剰余にします。各因数は法より小さいため、その積は`998244353²`未満であり、Javaの符号付き`long`に収まります。また、切り上げた半分は`x / 2 + x % 2`で計算するため、`x + 1`のオーバーフローも起こりません。

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
