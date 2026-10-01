---
title: LeetCode. 9. Palindrome Number
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Palindrome Number]
pin: false
lang: ja
translation_key: leetcode-9-palindrome-number
permalink: /ja/posts/leetcode-9-palindrome-number/
---

[問題](https://leetcode.com/problems/palindrome-number/)

## 解法

負の整数は符号 `-` が左側にしかないため、回文にはなりません。また、0 以外で末尾が 0 の整数も回文ではありません。反転すると先頭が 0 になりますが、整数では先頭の 0 は表現されないためです。

数値を文字列に変換したり、すべての桁を反転したりする代わりに、後半の桁だけを反転します。ループの各反復で `x` から末尾の桁を取り除き、それを `reversedHalf` の末尾に追加します。反転した側の桁数が、残った `x` の桁数以上になったらループを終了します。

この時点で、2 つの半分が一致する場合に限り、元の数は回文です。桁数が偶数なら `x` と `reversedHalf` を比較します。奇数なら中央の桁が `reversedHalf` に含まれていて対応する桁がないため、比較前に整数除算 `/ 10` で中央の桁を取り除きます。

桁の半分だけを反転するため、中間値は `Integer.MAX_VALUE` を含むすべての `int` 入力で `int` の範囲内に収まります。時間計算量は `O(log10 N)`、追加領域の計算量は `O(1)` です。

## Java

```java
class Solution {
    public boolean isPalindrome(int x) {
        if (x < 0 || (x != 0 && x % 10 == 0)) {
            return false;
        }

        int reversedHalf = 0;
        while (x > reversedHalf) {
            reversedHalf = reversedHalf * 10 + x % 10;
            x /= 10;
        }

        return x == reversedHalf || x == reversedHalf / 10;
    }
}
```
