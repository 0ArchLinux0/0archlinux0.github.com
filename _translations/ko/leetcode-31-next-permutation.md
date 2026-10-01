---
title: LeetCode. 31. Next Permutation
author: MINJUN PARK
date: 2021-12-22 02:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Next Permutation]
pin: false
lang: ko
translation_key: leetcode-31-next-permutation
permalink: /ko/posts/leetcode-31-next-permutation/
---

![image](https://user-images.githubusercontent.com/55131164/146971258-218298b0-8e79-424a-81a9-1d44ca4ab52a.png)

[문제 링크](https://leetcode.com/problems/next-permutation/)

## 풀이

현재 순열보다 크면서 가능한 한 가장 작은 순열을 만들려면, 오른쪽에서부터 증가시킬 수 있는 가장 오른쪽 위치를 바꿔야 합니다. 오른쪽에서 왼쪽으로 탐색해 `nums[i] < nums[i + 1]`을 만족하는 첫 인덱스 `i`를 찾습니다. `i` 뒤의 접미부는 비증가 순서입니다. 그러한 인덱스가 없다면 배열 전체가 비증가 순서이므로 이미 가장 큰 순열이며, 배열을 뒤집어 가장 작은 순서로 만듭니다.

피벗이 있으면 접미부에서 `nums[i]`보다 큰 값 중 가장 오른쪽 값을 찾아 피벗과 교환합니다. 접미부가 비증가 순서이므로 이 가장 오른쪽의 큰 값이 피벗을 대체할 수 있는 가장 작은 값이며, 전체 순열을 더 크게 만드는 변화도 최소화합니다. 접미부를 뒤집으면 값이 오름차순이 되어 나머지 부분을 가능한 한 작게 만들 수 있습니다.

시간 복잡도는 `O(N)`, 추가 공간 복잡도는 `O(1)`입니다.

## Java

```java
class Solution {
    public void nextPermutation(int[] nums) {
        int pivot = nums.length - 2;
        while (pivot >= 0 && nums[pivot] >= nums[pivot + 1]) {
            pivot--;
        }

        if (pivot >= 0) {
            int successor = nums.length - 1;
            while (nums[successor] <= nums[pivot]) {
                successor--;
            }
            swap(nums, pivot, successor);
        }

        reverse(nums, pivot + 1, nums.length - 1);
    }

    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left++, right--);
        }
    }

    private void swap(int[] nums, int left, int right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
    }
}
```
