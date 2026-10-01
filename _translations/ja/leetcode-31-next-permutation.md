---
title: LeetCode. 31. Next Permutation
author: MINJUN PARK
date: 2021-12-22 02:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Next Permutation]
pin: false
lang: ja
translation_key: leetcode-31-next-permutation
permalink: /ja/posts/leetcode-31-next-permutation/
---

![image](https://user-images.githubusercontent.com/55131164/146971258-218298b0-8e79-424a-81a9-1d44ca4ab52a.png)

[問題](https://leetcode.com/problems/next-permutation/)

## 解法

現在の順列より大きく、なおかつ可能な限り小さい順列を作るには、右から見て増加させられる最も右側の位置を変更します。右から左へ走査し、最初に `nums[i] < nums[i + 1]` を満たすインデックス `i` を探します。`i` より後ろの接尾部は非増加順です。そのようなインデックスがなければ、配列全体が非増加順で、すでに最大の順列です。配列を反転して最小の順序にします。

ピボットが見つかった場合、接尾部で `nums[i]` より大きい値のうち最も右にあるものを探し、ピボットと交換します。接尾部は非増加順なので、右端にあるより大きな値がピボットを置き換えられる最小の値であり、順列を大きくする幅を最小限にできます。接尾部を反転すると値が昇順になり、残りの部分を可能な限り小さくできます。

時間計算量は `O(N)`、追加領域は `O(1)` です。

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
