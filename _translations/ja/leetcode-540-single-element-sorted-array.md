---
title: LeetCode 540. ソート済み配列から単一要素を検索
author: MINJUN PARK
date: 2021-11-17 23:11:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Binary Search, Single Element in a Sorted Array]
pin: false
lang: ja
translation_key: leetcode-540-single-element-sorted-array
permalink: /ja/posts/leetcode-540-single-element-sorted-array/
---

![image](https://user-images.githubusercontent.com/55131164/142717646-05cb5bbe-41a1-4b39-aa96-3e9ec29a9161.png)

[問題リンク] <https://leetcode.com/problems/single-element-in-a-sorted-array/>

ソート済み配列では、単一要素を除くすべての値がちょうど2回ずつ現れます。単一要素より前では各ペアは偶数インデックスから始まり、単一要素より後ではペアの並びがずれるため、各ペアは奇数インデックスから始まります。

各反復で `mid` をペアの先頭である偶数インデックスに合わせます。`nums[mid] == nums[mid + 1]` ならこのペアは正しく揃っているので、単一要素はその右側にあります。そうでなければ `mid` まででペアの並びが崩れているため、単一要素は `mid` またはその左側にあります。これにより、単一要素を含む側へ探索範囲を絞れます。

入力配列は空ではなく、長さは奇数で、単一要素がちょうど1つ含まれることが保証されています。ループが終了するとそのインデックスに収束し、値を返します。二分探索の時間計算量は $O(\log N)$、追加領域は $O(1)$ です。

```java
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int low = 0, high = nums.length - 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if ((mid & 1) == 1) mid--;
            if (nums[mid] == nums[mid + 1]) {
                low = mid + 2;
            } else {
                high = mid;
            }
        }
        return nums[low];
    }
}
```
