---
title: LeetCode. 5. Longest Palindromic Substring
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Longest Palindromic Substring]
pin: false
lang: ja
translation_key: leetcode-5-longest-palindromic-substring
permalink: /ja/posts/leetcode-5-longest-palindromic-substring/
---

[問題](https://leetcode.com/problems/longest-palindromic-substring/)

## 解説

すべての回文は、1文字を中心とする奇数長の回文か、隣り合う2文字の間を中心とする
偶数長の回文のどちらかです。各インデックスについて両方の中心から外側へ広げ、
左右の文字が一致する限り調べます。これにより、それぞれの中心を持つ回文を
すべて確認できます。

最長の答えは半開区間 `[bestStart, bestEnd)` として保持します。より長い回文を
見つけた場合にだけ区間を更新するため、すでに得た最長の答えは維持されます。
空文字列には中心がないので、空の部分文字列を返します。

## Java

```java
class Solution {
    public String longestPalindrome(String s) {
        int bestStart = 0;
        int bestEnd = 0;

        for (int center = 0; center < s.length(); center++) {
            int left = center;
            int right = center;
            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (right - left - 1 > bestEnd - bestStart) {
                bestStart = left + 1;
                bestEnd = right;
            }

            left = center;
            right = center + 1;
            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (right - left - 1 > bestEnd - bestStart) {
                bestStart = left + 1;
                bestEnd = right;
            }
        }

        return s.substring(bestStart, bestEnd);
    }
}
```

`N` 個の各位置で二種類の拡張を行い、それぞれ最大 `N` 文字を調べるため、時間計算量は
`O(N²)` です。インデックスだけを使うので、追加の空間計算量は `O(1)` です。
