---
title: BOJ 1009 - 分散処理
author: MINJUN PARK
date: 2022-02-06 00:39:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 分散処理, 累乗]
pin: false
lang: ja
translation_key: boj-1009-distributed-processing
permalink: /ja/posts/boj-1009-distributed-processing/
source_permalink: /posts/BOJ-1009/
---

[問題: BOJ 1009 — 分散処理](https://www.acmicpc.net/problem/1009) · [English](/posts/BOJ-1009/) · [한국어](/ko/posts/boj-1009-distributed-processing/)

各テストケースについて、`a^b` の一の位、つまり 10 で割った余りを二分累乗法で計算します。繰り返し周期を別途探す必要はなく、底が 10 で割り切れる場合も正しく処理できます。得られた余りはコンピューターの番号を表します。ただし余りが 0 の場合は 10 番を意味します（コンピューターの番号は 1 から 10 です）。

プログラムはテストケース数を読み取り、それぞれの `(a, b)` の組を独立して処理します。各ケースの計算量は `O(log b)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int testCases = Integer.parseInt(input.readLine().trim());
        StringBuilder output = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            String[] values = input.readLine().trim().split("\\s+");
            int a = Integer.parseInt(values[0]);
            long b = Long.parseLong(values[1]);
            int lastDigit = (int) powerModuloTen(a, b);
            output.append(lastDigit == 0 ? 10 : lastDigit).append('\n');
        }

        System.out.print(output);
    }

    static long powerModuloTen(int base, long exponent) {
        long result = 1;
        long factor = base % 10;
        while (exponent > 0) {
            if ((exponent & 1) != 0) result = (result * factor) % 10;
            factor = (factor * factor) % 10;
            exponent >>= 1;
        }
        return result;
    }
}
```
