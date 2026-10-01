---
title: LeetCode 28. strStr() 구현
author: MINJUN PARK
date: 2021-12-13 16:42:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Implement strStr()]
pin: false
lang: ko
translation_key: leetcode-28-strstr
permalink: /ko/posts/leetcode-28-strstr/
---

![image](https://user-images.githubusercontent.com/55131164/145843857-3340663f-ea52-43df-bed7-282638c7203c.png)

[문제 링크] <https://leetcode.com/problems/implement-strstr/>

접두사 함수(“접두사이면서 접미사인 가장 긴 proper prefix”를 저장하므로 LPS 배열이라고도 합니다)는 `needle`의 각 위치에서 끝나는 부분 문자열에 대해, 동시에 접미사이기도 한 가장 긴 proper prefix의 길이를 기록합니다. Proper prefix는 문자열 전체보다 짧은 접두사입니다.

이 배열을 만들 때 다음 문자가 일치하지 않으면, 이미 일치한 접두사의 LPS 값으로 되돌아갑니다. 그 값은 여전히 일치할 가능성이 있는 가장 긴 더 짧은 접두사이므로, 확인한 문자를 다시 살펴볼 필요가 없습니다. 검색에서도 같은 불변식을 사용합니다. `j`는 `i` 직전까지의 접미사와 일치하는 `needle` 접두사의 길이입니다. 불일치하면 처음부터 다시 시작하는 대신 `j = lps[j - 1]`로 설정해 겹칠 수 있는 가장 긴 접두사를 이어서 확인합니다.

빈 `needle`은 인덱스 `0`에서 일치합니다. 그렇지 않으면 `needle` 전체를 찾는 즉시 해당 시작 인덱스를 반환하며, 끝까지 찾지 못하면 `-1`을 반환합니다. 접두사 배열은 $O(M)$의 공간을 사용합니다. 배열 생성과 검색은 각각 선형 시간이므로 전체 시간 복잡도는 $O(N + M)$이며, 여기서 $N$과 $M$은 각각 `haystack`과 `needle`의 길이입니다.

```java
class Solution {
    public int strStr(String haystack, String needle) {
        int n = haystack.length();
        int m = needle.length();
        if (m == 0) return 0;

        int[] lps = new int[m];
        for (int i = 1, prefixLength = 0; i < m; ) {
            if (needle.charAt(i) == needle.charAt(prefixLength)) {
                lps[i++] = ++prefixLength;
            } else if (prefixLength > 0) {
                prefixLength = lps[prefixLength - 1];
            } else {
                lps[i++] = 0;
            }
        }

        // 불변식: j는 i 직전 접미사와 일치하는 가장 긴 needle 접두사의 길이다.
        for (int i = 0, j = 0; i < n; ) {
            if (haystack.charAt(i) == needle.charAt(j)) {
                i++;
                j++;
                if (j == m) return i - m;
            } else if (j > 0) {
                // 여전히 일치할 수 있는 가장 긴 겹치는 접두사를 유지한다.
                j = lps[j - 1];
            } else {
                i++;
            }
        }
        return -1;
    }
}
```
