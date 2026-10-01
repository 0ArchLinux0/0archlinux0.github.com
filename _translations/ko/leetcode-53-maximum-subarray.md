---
title: LeetCode. 53. Maximum Subarray
author: MINJUN PARK
date: 2021-11-16 14:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Maximum Subarray,
  ]
pin: false
lang: ko
translation_key: leetcode-53-maximum-subarray
permalink: /ko/posts/leetcode-53-maximum-subarray/
---

![image](https://user-images.githubusercontent.com/55131164/142044547-ce338c3a-cb49-4497-85d6-37aea1b7717b.png)

[문제 링크](https://leetcode.com/problems/maximum-subarray/)

## 풀이

입력 배열은 비어 있지 않다고 보장됩니다. 배열을 한 번 순회하면서 현재 인덱스에서 끝나야 하는 부분 배열의 최대 합(`ending`)과 지금까지 확인한 전체 최대 합(`best`)을 추적합니다. 각 원소에서 이전 부분 배열을 이어 가거나 현재 원소부터 새로 시작합니다.

`ending = max(nums[i], ending + nums[i])`

`ending`을 갱신한 뒤, 새 끝부분 합이 더 크면 `best`도 갱신합니다. 두 값을 모두 `nums[0]`으로 초기화하면 모든 후보가 비어 있지 않은 부분 배열이 됩니다. 특히 모든 값이 음수인 경우, 0을 잘못 반환하는 대신 가장 큰(음수 중 가장 0에 가까운) 단일 원소 부분 배열의 합을 반환합니다.

인덱스 `i`를 처리한 뒤의 불변식은 `ending`이 `i`에서 끝나는 모든 비어 있지 않은 부분 배열 중 최대 합이고, `best`가 처리된 접두부에 있는 모든 비어 있지 않은 부분 배열 중 최대 합이라는 것입니다.

시간 복잡도는 `O(N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public int maxSubArray(int[] nums) {
        int ending = nums[0];
        int best = nums[0];
        for (int i = 1; i < nums.length; i++) {
            ending = Math.max(nums[i], ending + nums[i]);
            best = Math.max(best, ending);
        }
        return best;
    }
}
```
