---
title: BOJ 1086 - パク・ソンウォン
author: MINJUN PARK
date: 2022-02-08 04:32:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, BOJ, 動的計画法, ビットマスク, パク・ソンウォン]
pin: false
lang: ja
translation_key: boj-1086-permutation-probability
permalink: /ja/posts/boj-1086-permutation-probability/
source_permalink: /posts/BOJ-1086/
---

[問題: BOJ 1086 — パク・ソンウォン](https://www.acmicpc.net/problem/1086) · [English](/posts/BOJ-1086/) · [한국어](/ko/posts/boj-1086-permutation-probability/)

## 部分集合動的計画法

入力には `N` 個の文字列があります。順列では文字列そのものではなく、それぞれの**位置**を順に選びます。同じ内容の文字列が複数あっても位置が異なるため別々の選択肢であり、順列の総数は `N!` です。

`dp[mask][r]` を、`mask` に含まれる文字列を連結したとき、その値を `K` で割った余りが `r` となる方法の数とします。空文字列の余りは 0 なので、`dp[0][0] = 1` です。各文字列 `i` の余り `value[i]` をあらかじめ計算し、入力中の文字列の合計長まで `power[len] = 10^len mod K` を求めておきます。

現在の連結文字列の長さが `len`、余りが `r` のとき、文字列 `i` を末尾に追加すると、余りは次のようになります。

`nextRemainder = (r * power[length[i]] + value[i]) mod K`。

まだ使っていない各インデックス `i` について、`dp[mask][r]` を `dp[mask | (1 << i)][nextRemainder]` に加算します。文字列の内容が同一でもインデックスごとに遷移するため、異なる位置による順列をすべて数えます。全位置を使い切った状態の `dp[(1 << N) - 1][0]` が、`K` で割り切れる順列数です。

確率はこの個数を `N!` で割った値です。分子と分母を最大公約数で約分し、分子が 0 の場合はそのまま `0/1` を出力します。`N <= 15` なので、各状態の個数と `15!` はどちらも符号付き `long` に収まります。状態数は `2^N` で、各状態から最大 `N` 個の遷移を調べるため、時間計算量は `O(N K 2^N)`、空間計算量は `O(K 2^N)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        String[] numbers = new String[n];
        int totalLength = 0;
        for (int i = 0; i < n; i++) {
            numbers[i] = input.next();
            totalLength += numbers[i].length();
        }
        int k = input.nextInt();

        int[] value = new int[n];
        int[] length = new int[n];
        for (int i = 0; i < n; i++) {
            length[i] = numbers[i].length();
            int remainder = 0;
            for (int j = 0; j < length[i]; j++) {
                remainder = (remainder * 10 + numbers[i].charAt(j) - '0') % k;
            }
            value[i] = remainder;
        }

        int[] power = new int[totalLength + 1];
        power[0] = 1 % k;
        for (int len = 1; len <= totalLength; len++) {
            power[len] = (int) ((long) power[len - 1] * 10 % k);
        }

        int states = 1 << n;
        long[][] dp = new long[states][k];
        dp[0][0] = 1;
        for (int mask = 0; mask < states; mask++) {
            for (int remainder = 0; remainder < k; remainder++) {
                long ways = dp[mask][remainder];
                if (ways == 0) {
                    continue;
                }
                for (int i = 0; i < n; i++) {
                    if ((mask & (1 << i)) == 0) {
                        int nextRemainder = (int) (
                            ((long) remainder * power[length[i]] + value[i]) % k
                        );
                        dp[mask | (1 << i)][nextRemainder] += ways;
                    }
                }
            }
        }

        long numerator = dp[states - 1][0];
        if (numerator == 0) {
            System.out.println("0/1");
            return;
        }

        long denominator = 1;
        for (int i = 2; i <= n; i++) {
            denominator *= i;
        }
        long divisor = gcd(numerator, denominator);
        System.out.println((numerator / divisor) + "/" + (denominator / divisor));
    }

    private static long gcd(long a, long b) {
        while (b != 0) {
            long remainder = a % b;
            a = b;
            b = remainder;
        }
        return a;
    }

    private static class FastScanner {
        private final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

        String next() throws IOException {
            StringBuilder token = new StringBuilder();
            int c;
            do {
                c = reader.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                token.append((char) c);
                c = reader.read();
            }
            return token.toString();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
