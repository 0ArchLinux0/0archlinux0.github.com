---
title: LeetCode. 38. Count and Say
author: MINJUN PARK
date: 2021-12-26 12:55:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Count and Say]
pin: false
lang: ja
translation_key: leetcode-38-count-and-say
permalink: /ja/posts/leetcode-38-count-and-say/
---

![image](https://user-images.githubusercontent.com/55131164/147403432-286902e7-fe8a-41c2-9da3-fab0fb07a57d.png)

[問題](https://leetcode.com/problems/count-and-say/)

## 解法

最初の項 `"1"` から始めます。次の項を作るには、現在の項を左から右へ走査し、同じ数字が連続する最大の区間を見つけ、その長さ、続いて数字を追加します。区間全体を処理してから次の位置へ進むため、各数字は一度だけエンコードされます。この変換を `n - 1` 回繰り返すと、求める項が得られます。

各ラウンド終了時の項の長さを `L_k` とします。項の処理にはその長さに比例する時間がかかり、次の項を生成するため、全体の時間計算量は `O(生成した項の長さの合計)` です。各ラウンドでは現在の項と次の項だけを保持するため、作業領域は `O(現在の項の長さ)` です。次の項の長さは現在の項の長さの定数倍以内です。

## Java

```java
class Solution {
    public String countAndSay(int n) {
        String term = "1";

        for (int round = 1; round < n; round++) {
            StringBuilder next = new StringBuilder();
            int i = 0;

            while (i < term.length()) {
                char digit = term.charAt(i);
                int end = i + 1;
                while (end < term.length() && term.charAt(end) == digit) {
                    end++;
                }

                next.append(end - i).append(digit);
                i = end;
            }

            term = next.toString();
        }

        return term;
    }
}
```
