---
title: LeetCode. 5. Longest Palindromic Substring
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Longest Palindromic Substring]
pin: false
lang: ko
translation_key: leetcode-5-longest-palindromic-substring
permalink: /ko/posts/leetcode-5-longest-palindromic-substring/
---

[문제](https://leetcode.com/problems/longest-palindromic-substring/)

## 풀이

모든 회문은 중심이 문자 하나인 홀수 길이 회문이거나, 인접한 두 문자 사이의 간격을
중심으로 하는 짝수 길이 회문입니다. 각 인덱스에서 두 종류의 중심을 모두 잡고,
양쪽 문자가 같은 동안 바깥쪽으로 확장합니다. 이 과정을 통해 해당 중심을 갖는
모든 회문을 확인할 수 있습니다.

가장 긴 답은 반열린 구간 `[bestStart, bestEnd)`로 저장합니다. 더 긴 회문을 찾았을
때만 구간을 갱신하므로, 기존 최장 답이 그대로 유지됩니다. 빈 문자열에는 중심이
없으므로 빈 부분 문자열을 반환합니다.

## Java

```java
class Solution {
    public String longestPalindrome(String s) {
        int bestStart = 0;
        int bestEnd = 0;

        for (int center = 0; center < s.length(); center++) {
            int left = center;
            int right = center;
            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (right - left - 1 > bestEnd - bestStart) {
                bestStart = left + 1;
                bestEnd = right;
            }

            left = center;
            right = center + 1;
            while (left >= 0 && right < s.length()
                    && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
            }
            if (right - left - 1 > bestEnd - bestStart) {
                bestStart = left + 1;
                bestEnd = right;
            }
        }

        return s.substring(bestStart, bestEnd);
    }
}
```

각 `N`개 위치에서 두 번의 확장을 수행하며, 각 확장은 최대 `N`개의 문자를 살펴볼 수
있으므로 시간 복잡도는 `O(N²)`입니다. 인덱스만 사용하므로 추가 공간 복잡도는 `O(1)`입니다.
