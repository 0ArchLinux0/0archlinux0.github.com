---
title: AtCoder ABC 237 C — kasaka
author: MINJUN PARK
date: 2022-01-30 21:20:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, AtCoder, ABC 237]
pin: false
lang: ja
translation_key: abc237-c-kasaka
permalink: /ja/posts/abc237-c-kasaka/
source_permalink: /posts/Atcoder-C-kasaka/
---

[問題: AtCoder ABC 237 C — kasaka](https://atcoder.jp/contests/abc237/tasks/abc237_c)
[English](/posts/Atcoder-C-kasaka/) · [한국어](/ko/posts/abc237-c-kasaka/) · [日本語]

操作でできるのは文字列の先頭に `a` を追加することだけなので、末尾の文字は変えられません。先頭に連続する `a` の個数を `leadingA`、末尾に連続する `a` の個数を `trailingA` とします。`leadingA > trailingA` なら、先頭にある `a` と対応する末尾の `a` が足りないため、回文にはできません。

そうでなければ、先頭に `trailingA - leadingA` 個の `a` を追加すれば、両端の連続する `a` の数をそろえられます。すると、先頭と末尾の `a` の並びを除いた中央部分は、もともと回文でなければなりません。両端の `a` を飛ばし、残った部分を両側からポインタで比較します。ポインタが交差した場合、中央部分は空か1文字なので回文です。

すべての文字が `a` の場合（中央部分が空の場合）も、両端に `a` がない場合（文字列全体を調べる場合）もこの処理で判定できます。時間計算量は `O(|S|)`、追加の空間計算量は `O(1)` です。

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        String s = input.readLine();

        int left = 0;
        while (left < s.length() && s.charAt(left) == 'a') {
            left++;
        }

        int right = s.length() - 1;
        while (right >= 0 && s.charAt(right) == 'a') {
            right--;
        }

        int leadingA = left;
        int trailingA = s.length() - 1 - right;
        if (leadingA > trailingA) {
            System.out.println("No");
            return;
        }

        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                System.out.println("No");
                return;
            }
            left++;
            right--;
        }
        System.out.println("Yes");
    }
}
```
