---
title: AtCoder ABC 238 C — digitnum
author: MINJUN PARK
date: 2022-02-05 09:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC]
pin: false
lang: ja
translation_key: abc238-c-digitnum
permalink: /ja/posts/abc238-c-digitnum/
source_permalink: /posts/Atcoder-C-digitnum/
---

[問題: AtCoder ABC 238 C — digitnum](https://atcoder.jp/contests/abc238/tasks/abc238_c) · [English](/posts/Atcoder-C-digitnum/) · [한국어](/ko/posts/abc238-c-digitnum/)

`1`から`N`までのすべての整数について、10進表記の桁数を合計し、`998244353`で割った余りを出力します。

整数を桁数ごとにまとめます。桁数が`d`の整数の範囲は`[10^(d-1), min(N, 10^d - 1)]`です。この範囲に含まれる整数の個数に`d`を掛け、答えに加算します。範囲の終端が`N`に達したら繰り返しを終了します。

桁数の範囲は`O(log N)`個なので、時間計算量は`O(log N)`、追加の空間計算量は`O(1)`です。範囲の端点は`long`で計算します。`10^d`を求める際のオーバーフローを避けるため、次の10の累乗が`N`以下であることを確認してから10倍します。

## Java

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
