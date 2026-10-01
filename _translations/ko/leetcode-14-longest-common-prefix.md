---
title: LeetCode 14번 - Longest Common Prefix
author: MINJUN PARK
date: 2021-11-12 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Longest Common Prefix]
pin: false
lang: ko
translation_key: leetcode-14-longest-common-prefix
permalink: /ko/posts/leetcode-14-longest-common-prefix/
---

![image](https://user-images.githubusercontent.com/55131164/142044732-09788710-e771-4dff-896c-da62993ed2ee.png)

[문제 링크](https://leetcode.com/problems/longest-common-prefix/)

## 접근 방법

첫 번째 문자열을 왼쪽에서 오른쪽으로 순회합니다. 각 위치에서 첫 번째 문자열의 문자와 나머지 모든 문자열의 같은 위치에 있는 문자를 비교합니다. 처음으로 문자가 다르면 그 위치부터 공통 접두사가 끝나므로, 첫 번째 문자열에서 해당 위치 직전까지를 반환합니다.

비교하기 전에 각 문자열이 해당 위치의 문자를 포함할 만큼 충분히 긴지 확인합니다. 다른 문자열이 더 짧다면 공통 접두사는 그 문자열의 길이에서 끝납니다. 이 경계 검사를 통해 문자열의 끝을 넘어 접근하지 않으며, 첫 번째 문자열을 후보 접두사로 사용하므로 별도의 접두사 버퍼도 필요하지 않습니다.

가장 짧은 문자열의 길이까지만 각 문자열의 문자를 확인합니다. 입력에 있는 모든 문자열 길이의 합을 $S$라고 하면 전체 확인 문자 수에 대한 시간 복잡도는 $O(S)$입니다. 반환 문자열을 제외한 추가 공간 복잡도는 $O(1)$입니다.

```java
class Solution {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }

        String first = strs[0];
        for (int i = 0; i < first.length(); i++) {
            char current = first.charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != current) {
                    return first.substring(0, i);
                }
            }
        }
        return first;
    }
}
```
