---
title: LeetCode. 20. Valid Parentheses
author: MINJUN PARK
date: 2021-12-04 02:44:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Stack, Valid Parentheses]
pin: false
lang: ja
translation_key: leetcode-20-valid-parentheses
permalink: /ja/posts/leetcode-20-valid-parentheses/
---

![image](https://user-images.githubusercontent.com/55131164/144703918-b2458e45-c365-454a-bd80-25aa0370db82.png)
[問題](https://leetcode.com/problems/valid-parentheses/)

## 方針

文字列を左から右へ走査し、まだ対応する閉じ括弧がない開き括弧をスタックに積みます。各文字を処理する直前、スタックにはこれまでに見た開き括弧のうち、まだ対応付けられていないものだけが元の順序で残っています。そのため、閉じ括弧が現れたら、直前に現れた開き括弧と対応していなければなりません。スタックが空の場合、または括弧の種類が一致しない場合、文字列は無効です。走査後にスタックが空の場合に限り、文字列は有効です。スタックに開き括弧が残っていれば、対応する閉じ括弧がありません。

問題の制約により、文字列に含まれるのは `()[]{}` のみです。各文字を一度処理するため、時間計算量は `O(N)` です。最悪の場合、すべての文字が開き括弧なので、追加の空間計算量は `O(N)` です。

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
