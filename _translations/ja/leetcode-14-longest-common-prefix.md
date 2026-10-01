---
title: LeetCode 14 - Longest Common Prefix
author: MINJUN PARK
date: 2021-11-12 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Longest Common Prefix]
pin: false
lang: ja
translation_key: leetcode-14-longest-common-prefix
permalink: /ja/posts/leetcode-14-longest-common-prefix/
---

![image](https://user-images.githubusercontent.com/55131164/142044732-09788710-e771-4dff-896c-da62993ed2ee.png)

[問題へのリンク](https://leetcode.com/problems/longest-common-prefix/)

## 方針

最初の文字列を左から右へ走査します。各位置で、最初の文字列の文字と、ほかのすべての文字列の同じ位置にある文字を比較します。最初に不一致が見つかった位置で共通接頭辞は終わるため、最初の文字列のその位置より前の部分を返します。

比較する前に、ほかの各文字列がその位置の文字を持つ長さかどうかを確認します。文字列がそれより短ければ、共通接頭辞はその文字列の長さで終わります。この境界確認によって文字列の末尾を越えてアクセスすることはなく、最初の文字列を接頭辞の候補として使うため、別の接頭辞用バッファも不要です。

最も短い文字列の長さまで、入力中の各文字列の文字を調べます。すべての文字列の長さの合計を $S$ とすると、調べる文字数の時間計算量は $O(S)$ です。返す部分文字列を除く追加領域の計算量は $O(1)$ です。

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        String first = strs[0];
        for (int i = 0; i < first.length(); i++) {
            char current = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != current) {
                    return first.substring(0, i);
                }
            }
        }
        return first;
    }
}
```
