---
title: AtCoder. 008 AtCounter (4)
author: MINJUN PARK
date: 2021-12-30 02:47:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    AtCoder,
    AtCounter,
  ]
pin: false
lang: ja
translation_key: atcoder-typical90-008-atcounter
permalink: /ja/posts/atcoder-typical90-008-atcounter/
---

文字列 `S` が与えられたとき、`atcoder` と等しい部分列の個数を数えます。部分列は、残す文字の順序を変えずに 0 個以上の文字を削除して作ります。選んだ位置が異なるものは別の部分列として数えます。

`dp[j]` を、これまでに処理した文字から `atcoder` の先頭 `j` 文字を作る方法の数とします。初期状態の `dp[0] = 1` は空の接頭辞を作る 1 通りを表し、それ以外はすべて 0 です。入力文字を 1 文字ずつ処理し、目標文字列内で一致する位置を右から確認して `dp[j]` を `dp[j + 1]` に加算します。値は `1,000,000,007` で割った余りにします。右から更新することで、現在の入力文字の位置を複数回使うことを防げます。更新元の `dp[j]` には、現在の文字を処理する前に作られた部分列だけが含まれているためです。

不変条件は、`S` のある接頭辞を処理した後の `dp[j]` が、その接頭辞内で目標文字列の先頭 `j` 文字を作るインデックス選択の数と一致することです。答えは `dp[7]` です。入力文字ごとに目標文字列の 7 位置を調べるため、時間計算量は `O(7N)`、追加領域は `O(1)` です。

[問題リンク](https://AtCoder.jp/contests/typical90/tasks/typical90_h)

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    private static final long MOD = 1_000_000_007L;
    private static final String TARGET = "atcoder";

    public static void main(String[] args) throws IOException {
        BufferedInputStream in = new BufferedInputStream(System.in);
        int n = 0;
        int c;
        while ((c = in.read()) <= ' ') {
            if (c == -1) return;
        }
        do {
            n = n * 10 + c - '0';
            c = in.read();
        } while (c > ' ');

        char[] s = new char[n];
        int length = 0;
        while (length < n) {
            c = in.read();
            if (c > ' ') s[length++] = (char) c;
        }

        long[] dp = new long[TARGET.length() + 1];
        dp[0] = 1;
        for (char ch : s) {
            for (int j = TARGET.length() - 1; j >= 0; j--) {
                if (ch == TARGET.charAt(j)) {
                    dp[j + 1] = (dp[j + 1] + dp[j]) % MOD;
                }
            }
        }
        System.out.println(dp[TARGET.length()]);
    }
}
```
