---
title: LeetCode. 3. 반복 문자가 없는 가장 긴 부분 문자열
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
lang: ko
translation_key: leetcode-3-longest-unique-substring
permalink: /ko/posts/leetcode-3-longest-unique-substring/
---

[문제: Longest Substring Without Repeating Characters](https://leetcode.com/problems/longest-substring-without-repeating-characters/)

## 슬라이딩 윈도우

문자열의 `s[left..right]` 구간을 윈도우로 유지하고, 그 안의 문자를 집합에 저장합니다. 활성 윈도우에 같은 UTF-16 `char`가 중복되지 않는다는 것이 불변 조건입니다. `right` 위치의 문자가 이미 집합에 있다면 중복 문자가 사라질 때까지 왼쪽 문자를 제거하며 `left`를 증가시킵니다. 그런 다음 새 문자를 추가하고 지금까지의 가장 긴 유효한 윈도우 길이를 갱신합니다.

각 반복이 끝날 때 윈도우에는 중복이 없습니다. 현재 위치에서 끝나는 가장 긴 부분 문자열은 `left`보다 앞에서 시작할 수 없습니다. 그렇게 시작하면 중복 문자가 포함되기 때문입니다. 따라서 현재 윈도우 길이를 기록하면 해당 위치에서 끝나는 유효한 부분 문자열 중 최댓값을 고려하게 됩니다.

## Java 구현

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

각 문자는 윈도우에 최대 한 번 들어가고 최대 한 번 나가므로 시간 복잡도는 `O(N)`입니다. 집합은 입력에 있는 서로 다른 문자만큼만 저장하므로 LeetCode의 문자 집합을 기준으로 추가 공간 복잡도는 `O(min(N, alphabet))`입니다.
