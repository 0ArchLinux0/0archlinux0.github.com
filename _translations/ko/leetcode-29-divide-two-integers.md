---
title: LeetCode 29. 두 정수 나누기
author: MINJUN PARK
date: 2021-12-21 01:42:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Divide Two Integers]
pin: false
lang: ko
translation_key: leetcode-29-divide-two-integers
permalink: /ko/posts/leetcode-29-divide-two-integers/
---

[문제 링크](https://leetcode.com/problems/divide-two-integers/)

곱셈, 나눗셈, 나머지 연산자를 사용하지 않고 두 정수를 나누는 문제다. 몫은 0을 향해 버림한다. 문제의 제약 조건에서 제수는 0이 아니다.

두 피연산자의 절댓값을 `long`으로 구한다. 절댓값을 구하기 전에 `long`으로 변환해야 한다. `Integer.MIN_VALUE`의 양의 절댓값은 `int`에 담을 수 없지만 `long`에는 담을 수 있기 때문이다.

그다음 몫의 비트를 31부터 0까지 확인한다. 각 비트에서 제수의 절댓값을 해당 비트만큼 왼쪽으로 이동한 값이 현재 남은 피제수 이하인지 비교한다. 그 값이 들어맞으면 남은 피제수에서 빼고 몫의 해당 비트를 설정한다. 매 단계에서 가능한 가장 큰 2의 거듭제곱 배수를 탐욕적으로 선택하므로, 32개 비트를 모두 확인한 뒤에는 나머지가 제수보다 작고 설정된 비트가 몫의 절댓값을 나타낸다.

몫의 부호는 절댓값을 구한 뒤 적용한다. 수학적인 몫이 `int` 범위를 벗어나는 유일한 경우는 `Integer.MIN_VALUE / -1`이며, 이때 양의 결과를 `Integer.MAX_VALUE`로 포화시킨다.

항상 32개 비트를 확인하므로 시간 복잡도는 `O(32)`이며, 고정 너비 정수 기준으로는 `O(1)`이다. 추가 공간 복잡도도 `O(1)`이다.

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
