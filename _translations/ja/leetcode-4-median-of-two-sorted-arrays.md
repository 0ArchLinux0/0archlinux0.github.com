---
title: LeetCode. 4. 2つのソート済み配列の中央値
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Median of Two Sorted Arrays]
pin: false
lang: ja
translation_key: leetcode-4-median-of-two-sorted-arrays
permalink: /ja/posts/leetcode-4-median-of-two-sorted-arrays/
---

![image](https://user-images.githubusercontent.com/88752447/130302411-78bf9bf2-ad00-4dcb-a19f-37fa1ce7d3c6.png)

[問題](https://leetcode.com/problems/median-of-two-sorted-arrays/)

## 二分探索による分割

2つの配列はどちらもソート済みなので、マージせずにそれぞれを左半分と右半分に分けられます。左半分には要素数の合計の半分（切り上げ）の要素を含め、左側のすべての値が右側のすべての値以下になるようにします。

短い方の配列 `nums1` で分割位置を二分探索します。`nums1` の `i` 個の要素を左半分に置くなら、`nums2` からは `j = (m + n + 1) / 2 - i` 個を左半分に置く必要があります。ここで `m` と `n` はそれぞれの配列の長さです。次の2つの境界条件が成り立つまで `i` を調整します。

- `left1 <= right2`
- `left2 <= right1`

`left1` と `left2` は各配列の左側にある最大値、`right1` と `right2` は右側にある最小値です。両方の条件が成り立てば、2つの配列を合わせた左側に小さい方の半分の値が入ります。要素数の合計が奇数なら、左側の最大値が中央値です。偶数なら、左側の最大値と、全体の右側の最小値の平均が中央値になります。

分割によって配列の片側が空になる場合、その境界には `Integer.MIN_VALUE` または `Integer.MAX_VALUE` を使います。この番兵値により、配列の端での分割も特別扱いせず同じ比較で処理できます。問題の条件上、少なくとも一方の入力配列には要素があります。

二分探索の各ステップで短い方の配列の探索範囲が半分になるため、時間計算量は `O(log(min(m, n)))` です。一定個数の値だけを保持するので、追加の空間計算量は `O(1)` です。

```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
        }

        int m = nums1.length;
        int n = nums2.length;
        int half = (m + n + 1) / 2;
        int low = 0;
        int high = m;

        while (low <= high) {
            int i = low + (high - low) / 2;
            int j = half - i;

            int left1 = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int right1 = i == m ? Integer.MAX_VALUE : nums1[i];
            int left2 = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
            int right2 = j == n ? Integer.MAX_VALUE : nums2[j];

            if (left1 <= right2 && left2 <= right1) {
                if ((m + n) % 2 == 1) {
                    return Math.max(left1, left2);
                }
                long middleValues = (long) Math.max(left1, left2)
                        + Math.min(right1, right2);
                return middleValues / 2.0;
            } else if (left1 > right2) {
                high = i - 1;
            } else {
                low = i + 1;
            }
        }

        throw new IllegalArgumentException("Input arrays must be sorted.");
    }
}
```
