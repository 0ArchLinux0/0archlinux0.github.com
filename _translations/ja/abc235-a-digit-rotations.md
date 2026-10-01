---
title: AtCoder ABC 235 A - Rotate
author: MINJUN PARK
date: 2022-01-15 21:00:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC]
pin: false
lang: ja
translation_key: abc235-a-digit-rotations
permalink: /ja/posts/abc235-a-digit-rotations/
source_permalink: /posts/Atcoder-A-Rotate/
---

[問題: AtCoder ABC 235 A — Rotate](https://atcoder.jp/contests/abc235/tasks/abc235_a) · [English](/posts/Atcoder-A-Rotate/) · [한국어](/ko/posts/abc235-a-digit-rotations/)

3桁の十進整数が与えられます。百の位から順に `A`、`B`、`C` とすると、左に1桁ずつ回転してできる3つの数 `ABC`、`BCA`、`CAB` の合計を求めます。回転とは3つの数字の順番を入れ替えることなので、同じ数字が複数あったり `0` が含まれたりしても、そのまま同じ方法で扱えます。たとえば入力が `123` なら、数は `123`、`231`、`312` で、合計は `666` です。

入力文字列の各文字を数字に変換し、各回転後の数を位取りで計算します。答えは `100A + 10B + C`、`100B + 10C + A`、`100C + 10A + B` の合計です。3桁の数の最大値は `999` なので、合計は最大でも `2997` であり、`int` で十分です。時間計算量と追加領域計算量はいずれも `O(1)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String digits = input.readLine();
        int a = digits.charAt(0) - '0';
        int b = digits.charAt(1) - '0';
        int c = digits.charAt(2) - '0';

        int answer = (100 * a + 10 * b + c)
                + (100 * b + 10 * c + a)
                + (100 * c + 10 * a + b);
        System.out.println(answer);
    }
}
```
