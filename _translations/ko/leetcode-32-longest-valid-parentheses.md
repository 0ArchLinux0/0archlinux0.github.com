---
title: LeetCode. 32. Longest Valid Parentheses
author: MINJUN PARK
date: 2021-12-24 00:32:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Longest Valid Parentheses,
    Review,
    difficult,
  ]
pin: false
lang: ko
translation_key: leetcode-32-longest-valid-parentheses
permalink: /ko/posts/leetcode-32-longest-valid-parentheses/
---

![image](https://user-images.githubusercontent.com/55131164/147270718-660ee1fa-a52e-43a9-9f65-1cd683e33906.png)

[문제 링크](https://leetcode.com/problems/longest-valid-parentheses/)

## 풀이

문자열을 왼쪽에서 오른쪽으로 순회하면서 각 문자의 인덱스를 `ArrayDeque<Integer>`에 저장합니다. 스택은 현재 유효한 부분 문자열 바로 앞의 경계를 나타내는 센티널 인덱스 `-1`로 시작합니다.

여는 괄호를 만나면 그 인덱스를 스택에 넣습니다. 닫는 괄호를 만나면 가장 최근의 짝이 없는 여는 괄호 위치(또는 센티널)를 꺼냅니다. 그 결과 스택이 비었다면 짝이 없는 닫는 괄호이므로 해당 인덱스를 새로운 잘못된 경계로 넣습니다. 스택이 비지 않았다면 스택의 최상단은 현재 위치에서 끝나는 유효한 접미 부분 바로 앞의 인덱스입니다. 따라서 길이는 `i - stack.peek()`이고, 이 값으로 최댓값을 갱신합니다.

불변식은 각 문자를 처리한 뒤 스택 최상단이 현재 인덱스에서 끝나는 가장 긴 유효한 접미 부분의 바로 앞 경계이거나, 그런 접미 부분이 없을 때 가장 최근에 짝을 찾지 못한 닫는 괄호의 인덱스라는 것입니다. 최상단 아래의 여는 괄호 인덱스는 짝이 없는 여는 괄호를 구분합니다. 닫는 괄호에서 여는 괄호 하나를 꺼내면 유효한 접미 부분의 경계가 드러나거나, 현재 닫는 괄호가 짝이 없다는 점이 드러납니다. `-1` 센티널 덕분에 인덱스 0에서 시작하는 유효한 부분 문자열도 별도 처리 없이 계산할 수 있습니다.

각 인덱스는 최대 한 번 스택에 들어가고 한 번 나옵니다. 문자열 길이를 `N`이라 하면 시간 복잡도는 `O(N)`, 보조 공간 복잡도는 `O(N)`입니다.

## Java

```java
import java.util.ArrayDeque;

class Solution {
    public int longestValidParentheses(String s) {
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(-1);
        int max = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    max = Math.max(max, i - stack.peek());
                }
            }
        }
        return max;
    }
}
```
