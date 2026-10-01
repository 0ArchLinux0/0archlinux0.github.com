---
title: LeetCode. 7. Reverse Integer
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Reverse Integer]
pin: false
lang: ko
translation_key: leetcode-7-reverse-integer
permalink: /ko/posts/leetcode-7-reverse-integer/
---

[문제](https://leetcode.com/problems/reverse-integer/)

## 풀이

입력의 숫자를 하나씩 꺼내 역순으로 만든 값에 붙입니다. Java의 정수 나눗셈은 0 방향으로
버림하고 `%` 연산 결과는 피제수의 부호를 따릅니다. 따라서 양수와 음수 모두 `x % 10`은
부호를 포함한 마지막 숫자이며, 반복해서 나누면 그 숫자가 제거됩니다. 그러므로 0이나
끝에 0이 있는 경우도 별도의 처리가 필요하지 않습니다.

누적값에 10을 곱하기 전에 정수 범위를 10으로 나눈 값과 비교합니다. 양수 경계에서는
마지막 숫자가 `7` 이하여야 하고, 음수 경계에서는 `-8` 이상이어야 합니다. 이는 각각
`Integer.MAX_VALUE`와 `Integer.MIN_VALUE`의 마지막 숫자입니다. 곱셈 전에 범위를 넘는
숫자를 거부하므로 정수 오버플로를 막을 수 있습니다. 반복 불변식은 `reversed`가 지금까지
소비한 입력 숫자들을 정확히 역순으로 나타낸다는 것입니다. 매 반복에서 이 성질을
유지하며, 경계 검사는 모든 중간값이 `int` 범위 안에 있도록 보장합니다.

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

반복할 때마다 십진 숫자 하나를 처리하므로 시간 복잡도는 `O(log |x|)`입니다. 고정된
수의 정수 변수만 사용하므로 추가 공간 복잡도는 `O(1)`입니다.
