---
title: BOJ 1305 - 広告
author: MINJUN PARK
date: 2022-02-03 07:33:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, BOJ, String, KMP, Advertisement]
pin: false
lang: ja
translation_key: boj-1305-advertisement-period
permalink: /ja/posts/boj-1305-advertisement-period/
source_permalink: /posts/BOJ-1305/
---

[問題: BOJ 1305 — 広告](https://www.acmicpc.net/problem/1305) · [English](/posts/BOJ-1305/) · [한국어](/ko/posts/boj-1305-advertisement-period/)

長さ `L` の広告文が与えられます。この文字列全体が先頭に現れるように無限に繰り返せる、最短の文字列の長さを求めます。KMPの接頭辞関数 `pi` を計算します。`pi[i]` は `s[0..i]` の proper prefix（文字列全体ではない接頭辞）であり、同時に suffix でもある文字列のうち、最長のものの長さです。したがって `pi[L - 1]` は文字列全体の最長の境界（border）の長さです。

最長の境界は、広告文のコピーを次につなぐときに重ねられる最大の長さです。そのため、最短の広告長は `L - pi[L - 1]` となります。この長さが `L` を割り切らなくても答えになり得ます。たとえば `ababa` の最長の境界は長さ3なので、`ab` を繰り返すと先頭に `ababa` が現れ、答えは2です。空でない境界がなければ答えは `L` です。また、文字列がより短い文字列の繰り返しなら、この式はその最小の長さを返します。

入力条件は `1 <= L <= 1,000,000` で、文字列の長さは正確に `L` です。コードでは接頭辞配列を参照する前にこの長さを確認します。接頭辞関数の計算と答えの計算は `O(L)` 時間、接頭辞配列は `O(L)` 空間です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int length = Integer.parseInt(input.readLine());
        String advertisement = input.readLine();

        if (length <= 0 || advertisement == null || advertisement.length() != length) {
            throw new IllegalArgumentException("Invalid advertisement length");
        }

        int[] pi = prefixFunction(advertisement);
        System.out.println(length - pi[length - 1]);
    }

    private static int[] prefixFunction(String s) {
        int[] pi = new int[s.length()];
        int matched = 0;
        for (int i = 1; i < s.length(); i++) {
            while (matched > 0 && s.charAt(i) != s.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (s.charAt(i) == s.charAt(matched)) {
                pi[i] = ++matched;
            }
        }
        return pi;
    }
}
```
