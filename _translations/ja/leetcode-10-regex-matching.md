---
title: LeetCode. 10. Regular Expression Matching
author: MINJUN PARK
date: 2021-12-09 02:28:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Regular Expression Matching]
pin: false
lang: ja
translation_key: leetcode-10-regex-matching
permalink: /ja/posts/leetcode-10-regex-matching/
---

[問題](https://leetcode.com/problems/regular-expression-matching/)

## 動的計画法

この問題で使うパターン演算子は 2 つだけです。`.` は任意の 1 文字に一致し、`*` は直前の原子を 0 回以上繰り返したものに一致します。そのため、`*` は直前の原子と組み合わせて処理し、単独で使ったりパターンのより前の部分に適用したりすることはありません。問題ではすべてのパターンが有効であると保証されるため、各 `*` の直前には原子があります。

`dp[i][j]` を、`s` の先頭 `i` 文字と `p` の先頭 `j` 文字が一致するかどうかを表す値とします。答えは `dp[s.length()][p.length()]` です。空の接頭辞同士は一致するため、`dp[0][0]` は true です。空文字列と一致する空でないパターンは、末尾の原子とアスタリスクの組を読み飛ばせる場合に限られます。有効な各 `x*` の組について、`dp[0][j] = dp[0][j - 2]` と初期化します。

パターン文字が `*` でなければ、現在の入力文字と一致する必要があります（同じリテラル文字、または `.`）。さらに、直前の接頭辞同士も一致していなければなりません。つまり、`dp[i][j] = matches(s[i - 1], p[j - 1]) && dp[i - 1][j - 1]` です。

`x*` の組には 2 通りの処理があります。`x` を 0 回使う場合は `dp[i][j - 2]` を使います。または、一致する入力文字を 1 文字消費し、同じパターンの組をさらに繰り返せるように残します。この場合の条件は `matches(s[i - 1], p[j - 2]) && dp[i - 1][j]` です。

テーブルの計算時間は `O(|s||p|)`、空間計算量も `O(|s||p|)` です。

## Java

```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;

        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char patternChar = p.charAt(j - 1);
                if (patternChar == '*') {
                    char atom = p.charAt(j - 2);
                    dp[i][j] = dp[i][j - 2]
                            || (matches(s.charAt(i - 1), atom) && dp[i - 1][j]);
                } else {
                    dp[i][j] = matches(s.charAt(i - 1), patternChar)
                            && dp[i - 1][j - 1];
                }
            }
        }

        return dp[m][n];
    }

    private boolean matches(char input, char atom) {
        return atom == '.' || input == atom;
    }
}
```
