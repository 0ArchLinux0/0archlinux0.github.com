---
title: LeetCode. 35. Search Insert Position
author: MINJUN PARK
date: 2021-12-22 14:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Search Insert Position]
pin: false
lang: ko
translation_key: leetcode-35-search-insert
permalink: /ko/posts/leetcode-35-search-insert/
---

![image](https://user-images.githubusercontent.com/55131164/147125423-f6ae1d03-23a6-4a9a-82a5-4ddb87571ebe.png)

[문제 링크](https://leetcode.com/problems/search-insert-position/)

## 풀이

이진 탐색으로 하한(lower bound), 즉 `target`보다 크거나 같은 첫 번째 값의 인덱스를 찾습니다. 반열린 탐색 구간 `[left, right)`을 유지하며, 처음에는 모든 유효한 배열 인덱스를 포함합니다. 매 단계에서 `left`보다 앞에 있는 모든 값은 `target`보다 작고, `right`부터 뒤에 있는 모든 값은 `target`보다 크거나 같다는 불변식을 유지합니다. 중간 값이 `target`보다 작으면 `left`를 중간 인덱스 다음으로 옮기고, 그렇지 않으면 `right`를 중간 인덱스로 옮깁니다. 구간이 줄어들어 `left == right`가 되면 그 위치가 하한입니다. 모든 값이 더 작다면 결과는 `nums.length`이며, 빈 배열도 자연스럽게 0을 반환합니다. `target`과 같은 값이 여러 개 있어도 첫 번째 위치를 찾습니다.

시간 복잡도는 `O(log N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public int searchInsert(int[] nums, int target) {
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
```
