---
title: LeetCode. 26. Remove Duplicates from Sorted Array
author: MINJUN PARK
date: 2021-12-13 14:21:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Remove Duplicates from Sorted Array,
  ]
pin: false
lang: ko
translation_key: leetcode-26-remove-duplicates
permalink: /ko/posts/leetcode-26-remove-duplicates/
---

![image](https://user-images.githubusercontent.com/55131164/145756841-812d2956-d8b2-48a4-8c8a-0f9f68974191.png)

[문제 링크](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

## 풀이

입력이 정렬되어 있으므로 같은 값은 서로 인접해 있습니다. 읽기 인덱스로 배열을 왼쪽부터 순회하고, 쓰기 인덱스로 새로운 고유 값이 들어갈 다음 위치를 추적합니다. 현재 값이 마지막으로 쓴 값과 다르면 현재 값을 쓰기 위치에 복사한 다음 쓰기 인덱스를 증가시킵니다.

각 읽기 단계에서 `nums[0..write)` 접두부에는 이미 처리한 입력에 나온 고유 값만 정렬된 순서로 들어 있습니다. 배열에 값이 하나라도 있으면 첫 값을 기록하며, 그 뒤의 값은 직전 고유 값과 다를 때만 기록합니다. 따라서 빈 배열도 안전하게 처리되어 `0`을 반환하고, 그 외에는 고유 값의 개수를 반환합니다. 결과로 얻은 고유 값은 입력 배열의 앞부분에 저장되며, 그 뒤의 내용은 무시해도 됩니다.

시간 복잡도는 `O(N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (write == 0 || nums[read] != nums[write - 1]) {
                nums[write++] = nums[read];
            }
        }
        return write;
    }
}
```
