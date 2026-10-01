---
title: LeetCode 28. strStr() の実装
author: MINJUN PARK
date: 2021-12-13 16:42:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Implement strStr()]
pin: false
lang: ja
translation_key: leetcode-28-strstr
permalink: /ja/posts/leetcode-28-strstr/
---

![image](https://user-images.githubusercontent.com/55131164/145843857-3340663f-ea52-43df-bed7-282638c7203c.png)

[問題リンク] <https://leetcode.com/problems/implement-strstr/>

接頭辞関数（「接頭辞であり、同時に接尾辞でもある最長の proper prefix」を記録するため、LPS 配列とも呼ばれます）は、`needle` の各位置で終わる部分文字列について、接尾辞でもある最長の proper prefix の長さを記録します。Proper prefix は文字列全体より短い接頭辞です。

この配列を作る際、次の文字が一致しなければ、すでに一致した接頭辞の LPS 値まで戻ります。その値は、まだ一致する可能性がある最長の短い接頭辞なので、確認済みの文字を再び調べる必要はありません。検索でも同じ不変条件を使います。`j` は `i` の直前までの接尾辞と一致する `needle` の接頭辞の長さです。不一致の場合は最初からやり直さず、`j = lps[j - 1]` として、重なり得る最長の接頭辞から照合を続けます。

空の `needle` はインデックス `0` で一致します。それ以外では、`needle` 全体が一致した時点で開始インデックスを返し、最後まで見つからなければ `-1` を返します。接頭辞配列の追加領域は $O(M)$ です。配列の構築と検索はいずれも線形時間なので、全体の時間計算量は $O(N + M)$ です。ここで $N$ と $M$ はそれぞれ `haystack` と `needle` の長さです。

```java
class Solution {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;

        int[] lps = new int[m];
        for (int i = 1, prefixLength = 0; i < m; ) {
            if (needle.charAt(i) == needle.charAt(prefixLength)) {
                lps[i++] = ++prefixLength;
            } else if (prefixLength > 0) {
                prefixLength = lps[prefixLength - 1];
            } else {
                lps[i++] = 0;
            }
        }

        // 不変条件: j は i の直前までの接尾辞と一致する最長の needle 接頭辞の長さ。
        for (int i = 0, j = 0; i < n; ) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) return i - m;
            } else if (j > 0) {
                // 一致する可能性が残る最長の重なった接頭辞を保つ。
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return -1;
    }
}
```
