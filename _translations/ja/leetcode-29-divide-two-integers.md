---
title: LeetCode 29. 2つの整数の除算
author: MINJUN PARK
date: 2021-12-21 01:42:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Divide Two Integers]
pin: false
lang: ja
translation_key: leetcode-29-divide-two-integers
permalink: /ja/posts/leetcode-29-divide-two-integers/
---

[問題リンク](https://leetcode.com/problems/divide-two-integers/)

乗算、除算、剰余演算子を使わずに、2つの整数を割り算する問題です。商は 0 に向かって切り捨てます。問題の制約により、除数は 0 ではありません。

両方の値の絶対値を `long` として求めます。絶対値を計算する前に `long` にキャストすることが重要です。`Integer.MIN_VALUE` の正の絶対値は `int` に収まりませんが、`long` なら表現できます。

次に、商のビットを 31 から 0 まで順に調べます。各ビットについて、除数の絶対値をそのビット数だけ左シフトした値が、現在の被除数の残り以下かを比較します。収まる場合はその値を残りから引き、商の対応するビットを立てます。各段階で選べる最大の 2 のべき乗倍を貪欲に取り出すため、32 ビットすべてを調べ終えると、余りは除数より小さくなり、設定したビットが商の絶対値を表します。

絶対値から商を作った後で符号を適用します。数学的な商が `int` の範囲を超えるのは `Integer.MIN_VALUE / -1` の場合だけで、この正の結果は `Integer.MAX_VALUE` に飽和させます。

常に 32 ビットを調べるため、時間計算量は `O(32)`、固定幅整数では `O(1)` です。追加の空間計算量も `O(1)` です。

```java
class Solution {
    public int divide(int dividend, int divisor) {
        long dividendMagnitude = Math.abs((long) dividend);
        long divisorMagnitude = Math.abs((long) divisor);
        long quotientMagnitude = 0;

        for (int bit = 31; bit >= 0; bit--) {
            long shiftedDivisor = divisorMagnitude << bit;
            if (shiftedDivisor <= dividendMagnitude) {
                dividendMagnitude -= shiftedDivisor;
                quotientMagnitude |= 1L << bit;
            }
        }

        boolean negative = (dividend < 0) != (divisor < 0);
        long quotient = negative ? -quotientMagnitude : quotientMagnitude;
        if (quotient > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        return (int) quotient;
    }
}
```
