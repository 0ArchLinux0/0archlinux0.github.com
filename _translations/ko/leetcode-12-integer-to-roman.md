---
title: LeetCode 12번 - Integer to Roman
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Integer to Roman]
pin: false
lang: ko
translation_key: leetcode-12-integer-to-roman
permalink: /ko/posts/leetcode-12-integer-to-roman/
---

![image](https://user-images.githubusercontent.com/88752447/130301842-30ff5467-5bf1-4e27-aa5e-e938de539cee.png)

[문제 링크](https://leetcode.com/problems/integer-to-roman/)

## 그리디한 액면가 선택

로마 숫자는 큰 자리 기호부터 작은 자리 기호 순으로 씁니다. 뺄셈 표기 여섯 가지인 IV와 IX는 각각 4와 9, XL과 XC는 40과 90, CD와 CM은 400과 900을 나타냅니다. 남은 값 이하인 액면가 중 가장 큰 것을 선택해 기호를 결과에 붙이고, 그 액면가만큼 남은 값에서 뺍니다. 남은 값이 0이 될 때까지 반복합니다. 표에서 뺄셈 표기를 각각 하나의 액면가로 취급하므로 그리디 선택만으로 표준 표기를 만들 수 있습니다.

이 표현은 문제의 범위인 1부터 3999까지에서 정형적(canonical)입니다. 4000 미만의 천의 자리는 `M`을 0~3개 사용합니다. 나머지 십진 자릿수는 서로 독립적으로 표현됩니다. 백의 자리는 `C`, `D`, `CD`, `CM`; 십의 자리는 `X`, `L`, `XL`, `XC`; 일의 자리는 `I`, `V`, `IV`, `IX`를 사용합니다. 각 자릿수에서 가장 큰 적용 가능 형식을 그리디하게 선택하면 해당 자릿수의 관례적인 로마 숫자 표현이 정확히 만들어집니다. 자릿수별 표현은 서로 겹치지 않고 각 자릿수마다 가능한 형식이 하나로 정해지므로, 큰 자리부터 이어 붙이면 유일한 표준 로마 숫자가 됩니다. 내림차순 표는 이 분해를 그대로 수행합니다.

## Java

```java
class Solution {
    public String intToRoman(int num) {
        int[] values = {
            1000, 900, 500, 400, 100, 90, 50, 40,
            10, 9, 5, 4, 1
        };
        String[] symbols = {
            "M", "CM", "D", "CD", "C", "XC", "L", "XL",
            "X", "IX", "V", "IV", "I"
        };

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(symbols[i]);
                num -= values[i];
            }
        }
        return result.toString();
    }
}
```

출력 길이를 $L$이라 하면, 반복문은 출력 기호를 각각 한 번씩 추가하므로 시간 복잡도는 $O(L)$이고 결과 저장 공간은 $O(L)$입니다. 문제의 제한인 $1 \le num \le 3999$에서는 출력 길이가 상수로 제한됩니다.
