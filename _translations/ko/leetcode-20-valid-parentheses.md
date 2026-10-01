---
title: LeetCode. 20. Valid Parentheses
author: MINJUN PARK
date: 2021-12-04 02:44:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Stack, Valid Parentheses]
pin: false
lang: ko
translation_key: leetcode-20-valid-parentheses
permalink: /ko/posts/leetcode-20-valid-parentheses/
---

![image](https://user-images.githubusercontent.com/55131164/144703918-b2458e45-c365-454a-bd80-25aa0370db82.png)
[문제](https://leetcode.com/problems/valid-parentheses/)

## 풀이

문자열을 왼쪽에서 오른쪽으로 순회하면서 아직 짝을 찾지 못한 여는 괄호를 스택에 저장합니다. 각 문자를 처리하기 직전에 스택에는 지금까지 확인한 여는 괄호 중 아직 닫히지 않은 것만 원래 순서대로 들어 있습니다. 따라서 닫는 괄호가 나오면 가장 최근에 나온 여는 괄호와 짝이 맞아야 합니다. 스택이 비어 있거나 괄호 종류가 맞지 않으면 유효하지 않은 문자열입니다. 순회가 끝난 뒤 스택이 비어 있을 때만 문자열이 유효합니다. 스택에 여는 괄호가 남아 있다면 짝을 찾지 못한 괄호가 있는 것입니다.

문제의 제약 조건에 따라 문자열에는 `()[]{}`만 포함됩니다. 각 문자를 한 번씩 처리하므로 시간 복잡도는 `O(N)`입니다. 최악의 경우 모든 문자가 여는 괄호이므로 추가 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.util.ArrayDeque;
import java.util.Deque;

class Solution {
    public boolean isValid(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
                continue;
            }

            if (stack.isEmpty()) {
                return false;
            }

            char opener = stack.pop();
            if ((c == ')' && opener != '(')
                    || (c == '}' && opener != '{')
                    || (c == ']' && opener != '[')) {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
```
