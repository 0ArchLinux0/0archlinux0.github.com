---
title: LeetCode. 35. Search Insert Position
author: MINJUN PARK
date: 2021-12-22 14:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Search Insert Position]
pin: false
lang: ja
translation_key: leetcode-35-search-insert
permalink: /ja/posts/leetcode-35-search-insert/
---

![image](https://user-images.githubusercontent.com/55131164/147125423-f6ae1d03-23a6-4a9a-82a5-4ddb87571ebe.png)

[問題](https://leetcode.com/problems/search-insert-position/)

## 解法

二分探索で下限（lower bound）、つまり `target` 以上となる最初の値のインデックスを求めます。半開区間 `[left, right)` を探索範囲として維持し、最初は配列の有効なインデックスをすべて含めます。各段階で、`left` より前の値はすべて `target` 未満であり、`right` 以降の値はすべて `target` 以上という不変条件を保ちます。中央の値が `target` より小さければ `left` を中央インデックスの次へ進め、そうでなければ `right` を中央インデックスに移します。範囲が縮まり `left == right` になった位置が下限です。すべての値が `target` より小さい場合は `nums.length` が返り、空配列でも自然に 0 を返します。同じ値が複数ある場合も、最初の位置を見つけます。

時間計算量は `O(log N)`、補助領域は `O(1)` です。

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
