---
title: LeetCode. 34. 정렬된 배열에서 첫 번째와 마지막 위치 찾기
author: MINJUN PARK
date: 2021-12-24 01:57:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, 코딩 인터뷰, 이진 탐색, LeetCode, 정렬된 배열에서 첫 번째와 마지막 위치 찾기]
pin: false
lang: ko
translation_key: leetcode-34-search-range
permalink: /ko/posts/leetcode-34-search-range/
---

![image](https://user-images.githubusercontent.com/55131164/147276476-8c6c4cc0-75a1-478d-bd17-a5a01bc4487c.png)

[문제 링크](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

<br>

배열이 정렬되어 있으므로 이진 탐색 두 번으로 경계를 찾을 수 있다. 첫 번째 탐색은 값이 `target` 이상인 첫 인덱스를 찾고, 두 번째 탐색은 값이 `target`보다 큰 첫 인덱스를 찾는다. 첫 번째 경계가 배열 끝이거나 해당 위치의 값이 `target`이 아니면 목표값은 없다. 그렇지 않으면 첫 번째 경계와 두 번째 경계보다 1 작은 인덱스가 답이다. `target + 1`을 계산하는 대신 `target`보다 큰 값을 직접 탐색하므로 `Integer.MAX_VALUE`에서 오버플로가 발생하지 않는다.

각 반복에서 찾을 경계는 반열린 구간 `[left, right)` 안에 남아 있다는 것이 루프 불변식이다. 반복마다 남은 인덱스의 절반 이상을 제외하므로 두 탐색의 시간 복잡도는 `O(log N)`이다. 추가 공간 복잡도는 `O(1)`이다.

```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int left = lowerBound(nums, target);
        if (left == nums.length || nums[left] != target) {
            return new int[] {-1, -1};
        }
        int right = upperBound(nums, target);
        return new int[] {left, right - 1};
    }

    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length;
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

    private int upperBound(int[] nums, int target) {
        int left = 0, right = nums.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }
}
```
