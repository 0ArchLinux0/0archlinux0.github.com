---
title: LeetCode. 27. Remove Element
author: MINJUN PARK
date: 2021-12-13 16:21:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Remove Element]
pin: false
lang: ko
translation_key: leetcode-27-remove-element
permalink: /ko/posts/leetcode-27-remove-element/
---

[문제](https://leetcode.com/problems/remove-element/)

## 풀이

`nums`를 왼쪽에서 오른쪽으로 순회하면서 남길 다음 값을 기록할 쓰기 인덱스를 유지합니다. 현재 값이 `val`과 다르면 `nums[write]`에 복사하고 `write`를 증가시킵니다. `val`과 같은 값은 건너뜁니다.

각 원소를 처리하기 전, 앞의 `write`개 위치에는 지금까지 만난 값 중 `val`과 다른 값만 원래 순서대로 정확히 들어 있습니다. `write`는 현재 순회 위치를 넘지 않으므로 남길 값을 쓰기 위치에 복사해도 아직 읽지 않은 원소를 덮어쓰지 않습니다. 순회가 끝나면 `write`가 새 길이이며, `nums[0..write)`가 원래 순서를 유지한 필터링 결과입니다. 그 뒤의 배열 구간은 지정되지 않으므로 의존해서는 안 됩니다.

시간 복잡도는 `O(N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int write = 0;

        for (int value : nums) {
            if (value != val) {
                nums[write++] = value;
            }
        }

        return write;
    }
}
```
