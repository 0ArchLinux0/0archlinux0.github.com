---
title: AtCoder ABC 237 A — Not Overflow
author: MINJUN PARK
date: 2022-01-30 21:05:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 237]
pin: false
lang: ja
translation_key: abc237-a-not-overflow
permalink: /ja/posts/abc237-a-not-overflow/
source_permalink: /posts/Atcoder-A-Not-Overflow/
---

[問題: AtCoder ABC 237 A — Not Overflow](https://atcoder.jp/contests/abc237/tasks/abc237_a)
[English](/posts/Atcoder-A-Not-Overflow/) · [한국어](/ko/posts/abc237-a-not-overflow/) · [日本語]

入力値は32ビット符号付き整数の範囲を超える可能性があるため、`long`として読み込みます。32ビット符号付き整数の範囲は`Integer.MIN_VALUE`（`-2^31`）から`Integer.MAX_VALUE`（`2^31 - 1`）までで、両端を含みます。`long`で読み込んだ値を両方の境界と比較し、この範囲内なら`Yes`、そうでなければ`No`を出力します。端点を含む比較にすることで、境界値を正しく受け入れ、そのすぐ外側の値を拒否できます。

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
