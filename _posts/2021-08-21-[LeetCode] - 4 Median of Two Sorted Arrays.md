---
title: LeetCode. 4 Median of Two Sorted Arrays
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Median of Two Sorted Arrays,
  ]
pin: false
permalink: /posts/LeetCode-4-Median-of-Two-Sorted-Arrays/
lang: en
translation_key: leetcode-4-median-of-two-sorted-arrays
redirect_from:
  - /posts/LeetCode-4.-Median-of-Two-Sorted-Arrays/
---

![image](https://user-images.githubusercontent.com/88752447/130302411-78bf9bf2-ad00-4dcb-a19f-37fa1ce7d3c6.png)

[Link] <https://leetcode.com/problems/median-of-two-sorted-arrays/>

## Binary-search partition

Because both arrays are sorted, we can divide them into left and right halves without merging them. The left half must contain exactly half of the elements (rounded up), and every value on the left must be less than or equal to every value on the right.

Binary-search a partition in the shorter array, `nums1`. If `i` elements of `nums1` belong to the left half, then `j = (m + n + 1) / 2 - i` elements of `nums2` must belong there, where `m` and `n` are their lengths. Adjust `i` until the two boundary comparisons establish the invariant:

- `left1 <= right2`
- `left2 <= right1`

Here, `left1` and `left2` are the greatest values on each array's left side, and `right1` and `right2` are the least values on each right side. Once both comparisons hold, the combined left side contains the smaller half of the values. For an odd total length, its greatest value is the median. For an even total length, the median is the average of that value and the least value on the combined right side.

When a partition leaves one side of an array empty, use `Integer.MIN_VALUE` or `Integer.MAX_VALUE` as the corresponding boundary. These sentinels let the same comparisons handle partitions at either end without special cases. At least one input array is non-empty, as required by the problem.

Each binary-search step halves the search interval in the shorter array, so the time complexity is `O(log(min(m, n)))`; only a constant number of values are stored, giving `O(1)` extra space.

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
