---
title: LeetCode. 33. Search in Rotated Sorted Array
author: MINJUN PARK
date: 2021-12-24 01:57:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, Binary Search, LeetCode, Search in Rotated Sorted Array]
pin: false
lang: ja
translation_key: leetcode-33-rotated-search
permalink: /ja/posts/leetcode-33-rotated-search/
---

![image](https://user-images.githubusercontent.com/55131164/147270157-45fae206-9729-42bf-a73a-4217693941c6.png)

[問題リンク](https://leetcode.com/problems/search-in-rotated-sorted-array/)

## 解法

昇順に並んだ配列を回転させた入力が与えられ、値はすべて異なります。二分探索の各ステップでは、現在の区間の少なくとも片方の半分が整列しています。整列している半分の値の範囲に `target` が含まれるかを確認します。含まれていれば反対側の半分を捨て、含まれていなければ整列している半分を捨てます。これにより、`target` が配列に存在する場合は、必ず残りの区間に保たれます。区間が空になれば探索を終了します。空配列も特別な処理なしで安全に扱えます。

時間計算量は `O(log N)`、追加の空間計算量は `O(1)` です。

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
                // 左半分は整列しています。
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                // 右半分は整列しています。
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
