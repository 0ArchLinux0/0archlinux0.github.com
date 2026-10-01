---
title: LeetCode. 7. Reverse Integer
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Reverse Integer]
pin: false
lang: ja
translation_key: leetcode-7-reverse-integer
permalink: /ja/posts/leetcode-7-reverse-integer/
---

[問題](https://leetcode.com/problems/reverse-integer/)

## 解説

入力から数字を1桁ずつ取り出し、反転後の値に追加します。Javaの整数除算は0方向へ
切り捨てられ、`%` の結果は被除数の符号を引き継ぎます。そのため正負どちらの場合も
`x % 10` は符号付きの最後の桁になり、繰り返し割るとその桁が取り除かれます。したがって
0や末尾に0がある場合も、特別な処理は不要です。

累積値に10を掛ける前に、整数の境界を10で割った値と比較します。正の境界では最後の桁は
`7` 以下、負の境界では `-8` 以上でなければなりません。これはそれぞれ
`Integer.MAX_VALUE` と `Integer.MIN_VALUE` の最後の桁です。乗算前に範囲を超える桁を
拒否することで、整数オーバーフローを防ぎます。不変条件は、`reversed` が処理済みの入力の
桁を正確に逆順にした値であることです。各反復でこの条件を保ち、境界チェックによって
すべての中間値が `int` の範囲内に収まります。

## Java

```java
class Solution {
    public int reverse(int x) {
        int reversed = 0;

        while (x != 0) {
            int digit = x % 10;
            x /= 10;

            if (reversed > Integer.MAX_VALUE / 10
                    || (reversed == Integer.MAX_VALUE / 10 && digit > 7)
                    || reversed < Integer.MIN_VALUE / 10
                    || (reversed == Integer.MIN_VALUE / 10 && digit < -8)) {
                return 0;
            }

            reversed = reversed * 10 + digit;
        }

        return reversed;
    }
}
```

反復ごとに10進数の桁を1つ処理するため、時間計算量は `O(log |x|)` です。固定個数の
整数変数だけを使うため、追加の空間計算量は `O(1)` です。
