---
title: BOJ. LCS 2 (9252)
author: MINJUN PARK
date: 2022-01-13 02:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
		Dynamic Programming,
    BOJ,
    LCS 2,
  ]
pin: false
lang: ja
translation_key: boj-9252-lcs-reconstruction
permalink: /ja/posts/boj-9252-lcs-reconstruction/
---

[問題](https://www.acmicpc.net/problem/9252)

`dp[i][j]` を、文字列 `a` の先頭 `i` 文字と文字列 `b` の先頭 `j` 文字の最長共通部分列の長さとします。空の接頭辞とのLCSの長さは0です。両方の接頭辞の末尾文字が一致する場合、その文字を1つ前の接頭辞同士のLCSに追加できます。一致しない場合は、末尾文字の少なくとも一方を使わない必要があります。

```text
dp[0][j] = dp[i][0] = 0

dp[i][j] = dp[i - 1][j - 1] + 1                         if a[i - 1] == b[j - 1]
           max(dp[i - 1][j], dp[i][j - 1])              otherwise
```

短い接頭辞から順に表を埋めます。LCSを1つ復元するには、`(n, m)` から逆向きにたどります。末尾文字が一致したら、その文字を記録して斜めに移動します。一致しない場合は、LCSの長さが同じ近傍のセルへ移動します。表の不変条件により、斜めの一致は結果の文字を1つ確定し、斜め以外の移動では達成可能な最長の長さが保たれます。記録した文字列を反転すると、長さ `dp[n][m]` のLCSが得られます。近傍の長さが同じ場合はどちらへ進んでもよいため、同じ文字が繰り返される場合も正しく1つの答えを復元できます。時間計算量は `O(NM)`、空間計算量は `O(NM)` です。長さの後にLCSを1つ出力し、長さが0の場合は2行目を空行にします。

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String a = input.readLine();
        String b = input.readLine();
        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        StringBuilder reversed = new StringBuilder();
        int i = n;
        int j = m;
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                reversed.append(a.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] >= dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        System.out.println(dp[n][m]);
        System.out.println(reversed.reverse());
    }
}
```
