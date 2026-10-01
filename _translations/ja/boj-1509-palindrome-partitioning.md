---
title: BOJ 1509 - 回文の分割
author: MINJUN PARK
date: 2022-02-10 01:42:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 動的計画法, 文字列, 回文]
pin: false
lang: ja
translation_key: boj-1509-palindrome-partitioning
permalink: /ja/posts/boj-1509-palindrome-partitioning/
source_permalink: /posts/BOJ-1509/
---

[問題: BOJ 1509 — 回文の分割](https://www.acmicpc.net/problem/1509) · [English](/posts/BOJ-1509/) · [한국어](/ko/posts/boj-1509-palindrome-partitioning/)

長さ `N` の文字列 `s` を連続する回文の部分文字列に分割するとき、部分の数の最小値を求めます。まず `palindrome[l][r]` を計算します。これは両端を含む部分文字列 `s[l..r]` が回文かどうかを表します。部分文字列の長さが短い順に計算すると、両端の文字が一致し、長さが2以下であるか、内側の部分文字列がすでに回文であれば回文です。これにより `O(N²)` 個の部分文字列をそれぞれ一度ずつ調べます。

接頭辞DPを使います。`dp[r]` を半開区間 `s[0..r)` を覆う回文部分の最小数と定義し、`dp[0] = 0` とします。終端境界 `r` を1から `N` まで順に処理し、それぞれについて前の境界 `l` をすべて調べます。`s[l..r)` が回文なら、直前の接頭辞の最適な分割にこの部分を追加できるため、`dp[r] = min(dp[r], dp[l] + 1)` と更新します。`l = 0` は先頭文字から始まる部分を扱い、文字列全体が回文なら `dp[N] = 1` になります。どの分割にも最後の回文部分があるため、すべての `l` を調べることで最小値を得られます。同じ文字の繰り返しや複数の回文部分が混在する場合も、特別な処理は不要です。

回文表の計算は `O(N²)` 時間、接頭辞DPも候補区間をすべて調べるため `O(N²)` 時間です。空間計算量は回文表が `O(N²)`、`dp` が `O(N)` です。すべて反復処理なので、再帰の深さに依存しません。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();
        int n = s.length();

        boolean[][] palindrome = new boolean[n][n];
        for (int length = 1; length <= n; length++) {
            for (int left = 0; left + length <= n; left++) {
                int right = left + length - 1;
                palindrome[left][right] = s.charAt(left) == s.charAt(right)
                        && (length <= 2 || palindrome[left + 1][right - 1]);
            }
        }

        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 1);
        dp[0] = 0;
        for (int right = 1; right <= n; right++) {
            for (int left = 0; left < right; left++) {
                if (palindrome[left][right - 1]) {
                    dp[right] = Math.min(dp[right], dp[left] + 1);
                }
            }
        }

        System.out.println(dp[n]);
    }
}
```
