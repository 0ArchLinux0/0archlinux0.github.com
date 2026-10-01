---
title: LeetCode. 3. 重複のない最長部分文字列
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Longest Substring Without Repeating Characters,
  ]
pin: false
lang: ja
translation_key: leetcode-3-longest-unique-substring
permalink: /ja/posts/leetcode-3-longest-unique-substring/
---

[問題: Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

## スライディングウィンドウ

文字列の`s[left..right]`をウィンドウとして保ち、その中の文字を集合に格納します。アクティブなウィンドウに同じUTF-16 `char`が重複して含まれないことが不変条件です。`right`位置の文字がすでに集合にある場合は、その重複文字がなくなるまで左端の文字を取り除き、`left`を進めます。その後、新しい文字を追加し、最長の有効なウィンドウ長を更新します。

各反復の後、ウィンドウ内に重複はありません。現在の位置で終わる最長の部分文字列は`left`より前から始められません。それより前から始めると重複文字が含まれるためです。したがって、現在のウィンドウ長を記録すれば、その位置で終わる有効な部分文字列の最長値を考慮できます。

## Java実装

```java
import java.util.HashSet;
import java.util.Set;

class Solution {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> window = new HashSet<>();
        int left = 0;
        int best = 0;

        for (int right = 0; right < s.length(); right++) {
            char current = s.charAt(right);
            while (window.contains(current)) {
                window.remove(s.charAt(left));
                left++;
            }
            window.add(current);
            best = Math.max(best, right - left + 1);
        }

        return best;
    }
}
```

各文字はウィンドウに最大1回入り、最大1回出るため、時間計算量は`O(N)`です。集合に格納するのは入力中の異なる文字だけなので、LeetCodeの文字集合を前提とした追加領域は`O(min(N, alphabet))`です。
