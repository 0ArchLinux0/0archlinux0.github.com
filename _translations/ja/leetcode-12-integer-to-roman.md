---
title: LeetCode 12. Integer to Roman
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Integer to Roman]
pin: false
lang: ja
translation_key: leetcode-12-integer-to-roman
permalink: /ja/posts/leetcode-12-integer-to-roman/
---

![image](https://user-images.githubusercontent.com/88752447/130301842-30ff5467-5bf1-4e27-aa5e-e938de539cee.png)

[問題へのリンク](https://leetcode.com/problems/integer-to-roman/)

## 貪欲な額面選択

ローマ数字は、大きい位の記号から小さい位の記号へ順に表します。減算表記は6種類あり、IVとIXはそれぞれ4と9、XLとXCは40と90、CDとCMは400と900を表します。残りの値以下で最大の額面を選んで記号を結果に追加し、その額面を残りの値から引きます。残りがなくなるまでこれを繰り返します。表では減算表記もそれぞれ1つの額面として扱うため、この貪欲な選択で標準的な表記を直接生成できます。

この表現は、問題の範囲である1から3999について標準形（canonical）です。4000未満では、千の位は`M`を0～3個使って表します。残りの各十進位は独立して表現できます。百の位には`C`、`D`、`CD`、`CM`、十の位には`X`、`L`、`XL`、`XC`、一の位には`I`、`V`、`IV`、`IX`を使います。各位で当てはまる最大の形式を貪欲に選ぶと、その位の慣例的なローマ数字表記が得られます。各位の表記は互いに重ならず、各位の形式は一意なので、大きい位から連結すれば唯一の標準ローマ数字になります。降順の表はこの分解をそのまま実行します。

## Java

```java
class Solution {
    public String intToRoman(int num) {
        int[] values = {
            1000, 900, 500, 400, 100, 90, 50, 40,
            10, 9, 5, 4, 1
        };
        String[] symbols = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL",
            "X", "IX", "V", "IV", "I"
        };

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        return result.toString();
    }
}
```

出力の長さを$L$とすると、ループは出力記号をそれぞれ1回ずつ追加するため、時間計算量は$O(L)$、結果の保存領域は$O(L)$です。問題の制約$1 \le num \le 3999$のもとでは、出力長は定数で上限が決まっています。
