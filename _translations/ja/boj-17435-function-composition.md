---
title: BOJ. 合成関数とクエリ (17435)
author: MINJUN PARK
date: 2022-02-08 17:13:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Dynamic Programming, Sparse table, Composite Function And Query, 合成関数とクエリ]
pin: false
lang: ja
translation_key: boj-17435-function-composition
permalink: /ja/posts/boj-17435-function-composition/
source_permalink: /posts/BOJ-17435/
---

[問題: BOJ 17435 — 合成関数とクエリ](https://www.acmicpc.net/problem/17435)

[English](/posts/BOJ-17435/) · [한국어](/ko/posts/boj-17435-function-composition/)

## 解法

入力では整数 `1..M` 上の関数 `f` が与えられ、各クエリは開始値 `x` に関数を `K` 回適用した値、つまり `f^K(x)` を求めます。関数を1回ずつ適用すると、クエリごとに最大500,000回の処理が必要になるため、二分リフティングで関数のべき乗を前計算します。

`up[b][x]` を、`x` に `f` をちょうど `2^b` 回適用した結果と定義します。基底行は `up[0][x] = f(x)` です。長さ `2^(b-1)` のジャンプを2回続ければ長さ `2^b` のジャンプになるので、次の漸化式が成り立ちます。

`up[b][x] = up[b - 1][up[b - 1][x]]`

クエリでは `K` の各ビットを調べます。ビット `b` が立っている場合、現在の値を `up[b][現在の値]` に更新します。選んだジャンプ長の合計は `K` なので、正確に `f^K(x)` を求められます。`K = 0` では立っているビットがなく、答えは `x` のままです。`K = 1` では基底行だけを使います。表は20行あり、`K <= 500000` の最上位のビットは18番目なので、どのクエリでも表の範囲を超えません。

前処理の時間計算量とメモリ計算量は `Kmax = 500000` に対して `O(M log Kmax)` です。各クエリは `O(log Kmax)` 時間で処理できます。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final int LEVELS = 20;

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int m = input.nextInt();
        int[][] up = new int[LEVELS][m + 1];

        for (int x = 1; x <= m; x++) {
            up[0][x] = input.nextInt();
        }

        for (int bit = 1; bit < LEVELS; bit++) {
            for (int x = 1; x <= m; x++) {
                up[bit][x] = up[bit - 1][up[bit - 1][x]];
            }
        }

        int queryCount = input.nextInt();
        StringBuilder answer = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            int k = input.nextInt();
            int value = input.nextInt();
            for (int bit = 0; bit < LEVELS; bit++) {
                if ((k & (1 << bit)) != 0) {
                    value = up[bit][value];
                }
            }
            answer.append(value).append('\n');
        }

        System.out.print(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer;
        private int length;

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ');

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[pointer++];
        }
    }
}
```
