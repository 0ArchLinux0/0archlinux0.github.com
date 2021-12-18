---
title: Programmers. Stock Price
author: MINJUN PARK
date: 2021-12-19 06:25:00 +0900
categories: [Programmers, Java]
tags:
  [
    Java,
    Algorithm,
    Stack,
    Coding Interview,
    Programmers격
    Stock Price,
    주식 가격,
    프로그래머스
  ]
pin: false
---

[Link] <https://programmers.co.kr/learn/courses/30/lessons/42584?language=java>

<br>

```java
import java.util.*;
class Solution {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        int polledIdx = 0, curIdx;
        Stack<Integer> stack = new Stack<>();
        for(curIdx = 0; curIdx < prices.length; curIdx++) {
            int price = prices[curIdx];
            while(!stack.isEmpty() && prices[stack.peek()] > price) {
                int idx = stack.pop();
                answer[idx] = curIdx - idx;
            }
            stack.add(curIdx);
        }
        curIdx--;
        while(!stack.isEmpty()) {
            int idx = stack.pop();
            answer[idx] = curIdx - idx;
        }
        return answer;
    }
}
```
