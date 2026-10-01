---
title: LeetCode 461. 해밍 거리
author: MINJUN PARK
date: 2021-11-17 23:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Hamming Distance]
pin: false
lang: ko
translation_key: leetcode-461-hamming-distance
permalink: /ko/posts/leetcode-461-hamming-distance/
---

![image](https://user-images.githubusercontent.com/55131164/142594775-6052d68a-c4df-42c0-abae-618072f1da3b.png)

[문제 링크] <https://leetcode.com/problems/hamming-distance/>

XOR 연산은 `x`와 `y`의 같은 위치 비트가 서로 다를 때 해당 위치를 `1`로 만듭니다. 따라서 `x ^ y`는 서로 다른 모든 비트 위치를 표시하고, `Integer.bitCount`는 그 `1` 비트의 개수, 즉 해밍 거리를 셉니다.

Java 정수의 너비는 항상 32비트이므로 시간 복잡도와 추가 공간 복잡도는 각각 $O(1)$입니다.

```java
class Solution {
    public int hammingDistance(int x, int y) {
        // XOR는 두 입력이 다른 비트 위치를 1로 표시한다.
        // bitCount는 표시된 위치의 개수를 센다.
        return Integer.bitCount(x ^ y);
    }
}
```
