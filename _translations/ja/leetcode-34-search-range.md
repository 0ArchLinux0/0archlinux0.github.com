---
title: LeetCode. 34. ソート済み配列から最初と最後の位置を検索する
author: MINJUN PARK
date: 2021-12-24 01:57:00 +0900
categories: [Record, Code]
tags: [Java, アルゴリズム, コーディング面接, 二分探索, LeetCode, ソート済み配列から最初と最後の位置を検索する]
pin: false
lang: ja
translation_key: leetcode-34-search-range
permalink: /ja/posts/leetcode-34-search-range/
---

![image](https://user-images.githubusercontent.com/55131164/147276476-8c6c4cc0-75a1-478d-bd17-a5a01bc4487c.png)

[問題リンク](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/)

<br>

配列はソート済みなので、二分探索を2回行って境界を求められる。1回目は値が `target` 以上となる最初のインデックスを探し、2回目は値が `target` より大きくなる最初のインデックスを探す。1回目の境界が配列の末尾、またはその位置の値が `target` でなければ、対象の値は存在しない。そうでなければ、1回目の境界と2回目の境界から1を引いたインデックスが答えとなる。`target + 1` を計算せず、`target` より大きい値を直接探索するため、`Integer.MAX_VALUE` でもオーバーフローしない。

探索対象の境界が半開区間 `[left, right)` に残ることがループ不変条件である。各反復で残りのインデックスを少なくとも半分に絞るため、2回の探索の時間計算量は `O(log N)`。追加領域の計算量は `O(1)`。

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
