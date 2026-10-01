---
title: LeetCode 13번 - Roman to Integer
author: MINJUN PARK
date: 2021-11-14 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Roman to Integer]
pin: false
lang: ko
translation_key: leetcode-13-roman-to-integer
permalink: /ko/posts/leetcode-13-roman-to-integer/
---

![image](https://user-images.githubusercontent.com/55131164/142044066-cd56939b-5d22-4479-ac01-b0d29022a682.png)

[문제 링크](https://leetcode.com/problems/roman-to-integer/)

## 왼쪽에서 오른쪽으로 순회하기

표준 로마 숫자에서 기호는 바로 다음에 더 큰 값의 기호가 올 때만 뺍니다. 따라서 `IV`는 `-1 + 5 = 4`이며, 뺄셈 표기에 속하지 않는 기호는 원래 값을 더합니다. 각 기호와 바로 다음 기호를 비교하면 이 규칙을 그대로 적용할 수 있습니다. 반복문 불변식은 각 반복이 끝날 때까지 처리한 모든 기호의 부호 있는 기여분이 `sum`에 들어 있다는 것입니다. 바로 다음 기호가 더 큰 경우에만 현재 기호의 값을 빼며, 표준 표기에서는 이러한 기여분의 합이 로마 숫자의 값을 나타냅니다. 다음 기호가 없는 마지막 기호는 항상 더합니다.

입력은 1부터 3999까지의 값을 나타내는 표준 로마 숫자로 보장되므로 별도의 유효성 검사는 필요하지 않습니다.

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
            default: return 1000; // M; 입력은 유효한 로마 숫자로 보장됩니다.
        }
    }
}
```

각 기호를 상수 번씩 확인하므로 문자열 길이를 $N$이라 할 때 시간 복잡도는 $O(N)$입니다. 추가 공간 복잡도는 $O(1)$입니다.
