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
lang: ja
translation_key: leetcode-53-maximum-subarray
permalink: /ja/posts/leetcode-53-maximum-subarray/
---

![image](https://user-images.githubusercontent.com/55131164/142044547-ce338c3a-cb49-4497-85d6-37aea1b7717b.png)

[問題](https://leetcode.com/problems/maximum-subarray/)

## 解法

入力配列は空でないことが保証されています。配列を一度走査し、現在のインデックスで終わる部分配列の最大和 (`ending`) と、これまでに見つかった全体の最大和 (`best`) を追跡します。各要素では、直前の部分配列を延長するか、現在の要素から新たに開始します。

`ending = max(nums[i], ending + nums[i])`

`ending` を更新した後、新しい末尾部分の和が大きければ `best` も更新します。両方の値を `nums[0]` で初期化することで、すべての候補が空でない部分配列になります。特に、すべての値が負の場合、誤って 0 を返すのではなく、最大（最も 0 に近い負の値）の単一要素部分配列の和を返します。

インデックス `i` の処理後の不変条件は、`ending` が `i` で終わる空でない部分配列の最大和であり、`best` が処理済みの先頭部分に含まれる空でない部分配列の最大和であることです。

時間計算量は `O(N)`、追加領域の計算量は `O(1)` です。

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
