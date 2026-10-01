---
title: LeetCode 13 - Roman to Integer
author: MINJUN PARK
date: 2021-11-14 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Roman to Integer]
pin: false
lang: ja
translation_key: leetcode-13-roman-to-integer
permalink: /ja/posts/leetcode-13-roman-to-integer/
---

![image](https://user-images.githubusercontent.com/55131164/142044066-cd56939b-5d22-4479-ac01-b0d29022a682.png)

[問題へのリンク](https://leetcode.com/problems/roman-to-integer/)

## 左から右への走査

標準的なローマ数字では、ある記号の直後により大きい値の記号が続く場合にだけ、その値を引き算します。たとえば `IV` は `-1 + 5 = 4` です。減算表記に含まれない記号は通常どおり加算します。各記号と直後の記号を比較すれば、この規則をそのまま適用できます。ループ不変条件は、各反復の終了時に、それまでに処理したすべての記号の符号付き寄与分が `sum` に含まれていることです。直後の記号の値が大きい場合にだけ現在の値を引き、標準表記ではこれらの寄与分の合計がローマ数字の値になります。後続の記号がない最後の記号は常に加算します。

入力は1から3999までの値を表す標準的なローマ数字であることが保証されているため、妥当性の検証は不要です。

## Java

```java
class Solution {
    public int romanToInt(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int current = valueOf(s.charAt(i));
            if (i + 1 < s.length() && current < valueOf(s.charAt(i + 1))) {
                sum -= current;
            } else {
                sum += current;
            }
        }
        return sum;
    }

    private int valueOf(char symbol) {
        switch (symbol) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            default: return 1000; // M。入力は有効なローマ数字であることが保証されています。
        }
    }
}
```

各記号を定数回だけ調べるので、文字列の長さを $N$ とすると時間計算量は $O(N)$ です。追加領域の計算量は $O(1)$ です。
