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
lang: ja
translation_key: leetcode-32-longest-valid-parentheses
permalink: /ja/posts/leetcode-32-longest-valid-parentheses/
---

![image](https://user-images.githubusercontent.com/55131164/147270718-660ee1fa-a52e-43a9-9f65-1cd683e33906.png)

[問題](https://leetcode.com/problems/longest-valid-parentheses/)

## 解法

文字列を左から右へ走査し、各文字のインデックスを `ArrayDeque<Integer>` に格納します。スタックは、有効な部分文字列の直前の境界を表すセンチネルインデックス `-1` から開始します。

開き括弧を見たら、そのインデックスをスタックに積みます。閉じ括弧を見たら、最も直近の未対応の開き括弧の位置（またはセンチネル）を取り出します。その結果スタックが空になった場合、対応する開き括弧のない閉じ括弧なので、そのインデックスを新しい不正な境界として積みます。スタックが空でなければ、スタックの先頭は現在の位置で終わる有効な接尾部分の直前のインデックスです。したがって長さは `i - stack.peek()` となり、この値で最大長を更新します。

不変条件は、各文字の処理後にスタックの先頭が、現在のインデックスで終わる最長の有効な接尾部分の直前の境界を示すか、そのような接尾部分がない場合には、最も後に現れた未対応の閉じ括弧のインデックスを示すことです。先頭より下にある開き括弧のインデックスは、未対応の開き括弧を区切ります。閉じ括弧で開き括弧を一つ取り出すと、有効な接尾部分の境界が現れるか、現在の閉じ括弧に対応する開き括弧がないことが分かります。`-1` のセンチネルにより、インデックス 0 から始まる有効な部分文字列も特別な処理なしで計算できます。

各インデックスは高々一度だけスタックに積まれ、一度だけ取り出されます。文字列の長さを `N` とすると、時間計算量は `O(N)`、補助空間計算量は `O(N)` です。

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
