---
title: BOJ 1094 - 棒
author: MINJUN PARK
date: 2022-01-27 01:49:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 棒, ビット演算]
pin: false
lang: ja
translation_key: boj-1094-stick
permalink: /ja/posts/boj-1094-stick/
source_permalink: /posts/BOJ-1094/
---

[問題: BOJ 1094 — 棒](https://www.acmicpc.net/problem/1094)
[English](/posts/BOJ-1094/) · [한국어](/ko/posts/boj-1094-stick/)

目標の長さは`1`から`64`です。最初の棒の長さは`64`で、棒を半分ずつ切ると作れる棒の長さは`64、32、16、8、4、2、1`のような2のべき乗になります。

目標の長さを2進数で表すと、立っているビットに対応する異なる2のべき乗の和になります。選んだ各サイズの棒はそれぞれ1本ずつ必要です。例えば`23 = 16 + 4 + 2 + 1`なので、棒は4本必要です。したがって、答えは目標値の立っているビット数です。Javaの`Integer.bitCount`を使えば、切断をシミュレーションせずに直接計算できます。

時間計算量は`O(1)`、追加領域計算量は`O(1)`です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int target = Integer.parseInt(input.readLine());

        System.out.println(Integer.bitCount(target));
    }
}
```
