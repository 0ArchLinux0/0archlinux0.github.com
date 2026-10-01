---
title: LeetCode. 33. Search in Rotated Sorted Array
author: MINJUN PARK
date: 2021-12-24 01:57:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Binary Search, LeetCode, Search in Rotated Sorted Array]
pin: false
lang: ko
translation_key: leetcode-33-rotated-search
permalink: /ko/posts/leetcode-33-rotated-search/
---

![image](https://user-images.githubusercontent.com/55131164/147270157-45fae206-9729-42bf-a73a-4217693941c6.png)

[문제 링크](https://leetcode.com/problems/search-in-rotated-sorted-array/)

## 풀이

오름차순으로 정렬된 배열을 회전한 입력이 주어지며, 모든 값은 서로 다릅니다. 각 이진 탐색 단계에서 현재 구간의 절반 중 적어도 한쪽은 정렬되어 있습니다. 정렬된 절반의 값 범위 안에 `target`이 있는지 확인합니다. 그 범위에 있으면 반대쪽 절반을 버리고, 없으면 정렬된 절반을 버립니다. 따라서 `target`이 배열에 존재한다면 남은 구간에 계속 포함됩니다. 구간이 비면 탐색이 끝나며, 빈 배열도 별도 처리 없이 안전하게 처리됩니다.

시간 복잡도는 `O(log N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {
                // 왼쪽 절반이 정렬되어 있습니다.
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 오른쪽 절반이 정렬되어 있습니다.
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
        }

        return -1;
    }
}
```
