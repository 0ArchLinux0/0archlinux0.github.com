---
title: BOJ 1786 - 見つける
author: MINJUN PARK
date: 2022-01-29 23:10:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, BOJ, String, KMP]
pin: false
lang: ja
translation_key: boj-1786-kmp-search
permalink: /ja/posts/boj-1786-kmp-search/
source_permalink: /posts/BOJ-1786/
---

[問題: BOJ 1786 — 見つける](https://www.acmicpc.net/problem/1786) · [English](/posts/BOJ-1786/) · [한국어](/ko/posts/boj-1786-kmp-search/)

文字列 `T` の中にパターン `P` が現れるすべての位置を求めます。KMPでは不一致が起きても、比較済みの `T` の部分を再び走査しません。問題の制約ではパターンは空ではありませんが、以下の検索関数も空パターンの場合はインデックス参照をせず、結果なしとして扱います。

## 接頭辞関数と検索

`pi[i]` は `P[0..i]` の proper prefix（文字列全体ではない接頭辞）であり、同時に suffix でもあるもののうち最長の長さです。接頭辞関数の計算中に文字が一致しなければ `pi[j - 1]` に戻り、より短い接頭辞候補を調べます。一致すれば長さを増やします。

検索中の `j` は、現在までに一致したパターン文字数です。不一致なら同様に `pi[j - 1]` まで戻って照合を続けます。`j == P.length()` になったとき、末尾位置 `i` から開始位置 `i - P.length() + 2` を計算します。これは0始まりの添字を、問題が求める1始まりの位置に変換した値です。一致した直後に `j = pi[j - 1]` として、接尾辞と次の接頭辞が重なる可能性を保持します。そのため、重複する一致もすべて昇順で記録できます。

接頭辞関数の計算と検索を合わせた時間計算量は `O(|T| + |P|)` です。追加空間計算量は接頭辞配列と結果リストを含め `O(|P| + K)` です（`K` は一致数）。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String text = input.readLine();
        String pattern = input.readLine();

        List<Integer> positions = findMatches(text, pattern);
        StringBuilder output = new StringBuilder();
        output.append(positions.size()).append('\n');
        for (int i = 0; i < positions.size(); i++) {
            if (i > 0) output.append(' ');
            output.append(positions.get(i));
        }
        System.out.print(output);
    }

    private static List<Integer> findMatches(String text, String pattern) {
        List<Integer> positions = new ArrayList<>();
        if (pattern.isEmpty()) return positions;

        int[] pi = prefixFunction(pattern);
        int matched = 0;
        for (int i = 0; i < text.length(); i++) {
            while (matched > 0 && text.charAt(i) != pattern.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (text.charAt(i) == pattern.charAt(matched)) {
                matched++;
                if (matched == pattern.length()) {
                    positions.add(i - pattern.length() + 2);
                    matched = pi[matched - 1];
                }
            }
        }
        return positions;
    }

    private static int[] prefixFunction(String pattern) {
        int[] pi = new int[pattern.length()];
        int matched = 0;
        for (int i = 1; i < pattern.length(); i++) {
            while (matched > 0 && pattern.charAt(i) != pattern.charAt(matched)) {
                matched = pi[matched - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(matched)) {
                pi[i] = ++matched;
            }
        }
        return pi;
    }
}
```
