---
title: LeetCode. 9. Palindrome Number
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Palindrome Number]
pin: false
lang: ko
translation_key: leetcode-9-palindrome-number
permalink: /ko/posts/leetcode-9-palindrome-number/
---

[문제](https://leetcode.com/problems/palindrome-number/)

## 풀이

음수는 부호 `-`가 왼쪽에만 있으므로 회문이 될 수 없습니다. 또한 0이 아닌 수가 0으로 끝나면 회문이 아닙니다. 뒤집었을 때 앞자리가 0이 되지만, 원래 정수에는 맨 앞의 0이 표현되지 않기 때문입니다.

수를 문자열로 변환하거나 모든 자릿수를 뒤집는 대신, 마지막 절반의 자릿수만 뒤집습니다. 반복할 때마다 `x`에서 마지막 자릿수를 제거하고 `reversedHalf`의 끝에 붙입니다. 뒤집은 절반의 자릿수가 남은 `x`의 자릿수 이상이 되면 반복을 멈춥니다.

이 시점에서 두 절반이 같을 때에만 원래 수가 회문입니다. 자릿수가 짝수라면 `x`와 `reversedHalf`를 비교합니다. 홀수라면 가운데 자릿수는 `reversedHalf`에 포함되지만 대응하는 자릿수가 없으므로, 비교 전에 정수 나눗셈 `/ 10`으로 가운데 자릿수를 버립니다.

자릿수의 절반만 뒤집으므로 중간값은 `Integer.MAX_VALUE`를 포함한 모든 `int` 입력에서 `int` 범위를 넘지 않습니다. 시간 복잡도는 `O(log10 N)`, 추가 공간 복잡도는 `O(1)`입니다.

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
