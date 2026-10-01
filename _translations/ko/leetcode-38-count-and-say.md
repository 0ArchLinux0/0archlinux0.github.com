---
title: LeetCode. 38. Count and Say
author: MINJUN PARK
date: 2021-12-26 12:55:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Count and Say]
pin: false
lang: ko
translation_key: leetcode-38-count-and-say
permalink: /ko/posts/leetcode-38-count-and-say/
---

![image](https://user-images.githubusercontent.com/55131164/147403432-286902e7-fe8a-41c2-9da3-fab0fb07a57d.png)

[문제 링크](https://leetcode.com/problems/count-and-say/)

## 풀이

첫 번째 항인 `"1"`에서 시작합니다. 다음 항을 만들 때는 현재 항을 왼쪽에서 오른쪽으로 훑으며 같은 숫자가 연속되는 최대 구간을 찾고, 그 구간의 길이와 숫자를 차례로 추가합니다. 구간 전체를 처리한 뒤 다음 위치로 이동하므로 각 숫자는 정확히 한 번씩 인코딩됩니다. 이 변환을 `n - 1`회 반복하면 원하는 항을 얻습니다.

각 회차의 현재 항 길이를 `L_k`라고 합시다. 한 항을 처리하는 데 그 길이에 비례하는 시간이 걸리고 다음 항을 생성하므로, 전체 시간 복잡도는 `O(생성된 모든 항의 길이 합)`입니다. 각 회차에서 현재 항과 다음 항만 유지하므로 작업 공간은 `O(현재 항의 길이)`입니다. 다음 항의 길이는 현재 항 길이의 상수 배 이내입니다.

## Java

```java
class Solution {
    public String countAndSay(int n) {
        String term = "1";

        for (int round = 1; round < n; round++) {
            StringBuilder next = new StringBuilder();
            int i = 0;

            while (i < term.length()) {
                char digit = term.charAt(i);
                int end = i + 1;
                while (end < term.length() && term.charAt(end) == digit) {
                    end++;
                }

                next.append(end - i).append(digit);
                i = end;
            }

            term = next.toString();
        }

        return term;
    }
}
```
