---
title: BOJ 4354 - 文字列のべき乗
author: MINJUN PARK
date: 2022-01-29 07:11:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 文字列のべき乗, KMP]
pin: false
lang: ja
translation_key: boj-4354-string-power
permalink: /ja/posts/boj-4354-string-power/
source_permalink: /posts/BOJ-4354/
---

[問題: BOJ 4354 — 文字列のべき乗](https://www.acmicpc.net/problem/4354)

[English](/posts/BOJ-4354/) · [한국어](/ko/posts/boj-4354-string-power/) · [日本語](/ja/posts/boj-4354-string-power/)

## 接頭辞関数と周期の候補

長さ`L`の各入力文字列`s`について、接頭辞関数`pi`を計算します。`pi[i]`は`s[0..i]`の接尾辞でもある最長の真の接頭辞の長さです。最後の値`pi[L - 1]`は、文字列全体で最長のボーダー（接頭辞かつ接尾辞）の長さを表します。このボーダーを除いた長さ`p = L - pi[L - 1]`を周期の候補とします。

候補が実際の繰り返しブロックになるのは、`L`が`p`で割り切れる場合だけです。そのとき文字列は同じブロックをちょうど`L / p`回繰り返しているため、答えは`L / p`です。割り切れない場合、文字列全体を均等に繰り返すより短いブロックはないので、答えは`1`です。1文字の文字列も同様に処理できます。接頭辞関数の値は`0`となり、`p = 1`、答えも`1`です。

入力は`.`だけの行が現れるまで処理します。入力が終わった場合もnullチェックによって安全に終了します。問題の有効な入力には終了記号が含まれます。終了記号以外の文字列は空ではないため、接頭辞関数の配列と最後の要素は必ず存在します。

接頭辞関数の計算は、各文字列について時間計算量`O(L)`、空間計算量`O(L)`です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder output = new StringBuilder();
        String s;
        while ((s = input.readLine()) != null && !s.equals(".")) {
            output.append(repetitionCount(s)).append('\n');
        }
        System.out.print(output);
    }

    private static int repetitionCount(String s) {
        int length = s.length();
        int[] prefix = new int[length];

        for (int i = 1, matched = 0; i < length; i++) {
            while (matched > 0 && s.charAt(i) != s.charAt(matched)) {
                matched = prefix[matched - 1];
            }
            if (s.charAt(i) == s.charAt(matched)) {
                matched++;
                prefix[i] = matched;
            }
        }

        int period = length - prefix[length - 1];
        return length % period == 0 ? length / period : 1;
    }
}
```
