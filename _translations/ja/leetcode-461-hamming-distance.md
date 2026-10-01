---
title: LeetCode 461. ハミング距離
author: MINJUN PARK
date: 2021-11-17 23:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Hamming Distance]
pin: false
lang: ja
translation_key: leetcode-461-hamming-distance
permalink: /ja/posts/leetcode-461-hamming-distance/
---

![image](https://user-images.githubusercontent.com/55131164/142594775-6052d68a-c4df-42c0-abae-618072f1da3b.png)

[問題へのリンク](https://leetcode.com/problems/hamming-distance/)

XOR は、`x` と `y` の同じ位置のビットが異なるとき、その位置を `1` にします。そのため `x ^ y` は異なるビット位置をすべて示し、`Integer.bitCount` はその `1` の個数、つまりハミング距離を数えます。

Java の整数幅は常に 32 ビットなので、時間計算量と追加領域の計算量はいずれも $O(1)$ です。

```java
class Solution {
    public int hammingDistance(int x, int y) {
        // XOR は入力間で異なるビット位置を 1 にする。
        // bitCount はその位置の数を数える。
        return Integer.bitCount(x ^ y);
    }
}
```
