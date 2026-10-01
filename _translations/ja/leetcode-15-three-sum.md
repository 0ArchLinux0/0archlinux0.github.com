---
title: LeetCode. 15. 3Sum
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, 3Sum]
pin: false
lang: ja
translation_key: leetcode-15-three-sum
permalink: /ja/posts/leetcode-15-three-sum/
---

![image](https://user-images.githubusercontent.com/88752447/130299575-af2573e3-49a8-4230-815f-04b01f832386.png)

[問題](https://leetcode.com/problems/3sum/)

## 解説

配列をソートし、各インデックス `i` を順に固定して、残りの範囲を二つの
ポインター `left = i + 1`、`right = nums.length - 1` で探索します。配列が
ソート済みなので、ポインターは一方向にだけ動かせます。3つの値の合計が0より
小さい場合、現在の `left` と、より小さい右側の値との合計もさらに小さくなるため、
`left` を増やしても解を見落としません。合計が0より大きい場合は、現在の `right`
と、より大きい左側の値との合計もさらに大きくなるため、`right` を減らすのが安全です。
合計が0なら三つの値を結果に追加し、両方のポインターを動かします。

固定した値が直前の固定値と同じ場合、そのインデックスを飛ばし、同じ先頭値を
再探索しないようにします。結果を追加した後は左右のポインターにある重複値も
飛ばします。これにより同じ値の組み合わせを複数回出力しません。ソート済みの
配列で `nums[i] > 0` なら、それ以降もすべて正の値なので探索を終了できます。

## Java

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] > 0) {
                break;
            }
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                long sum = (long) nums[i] + nums[left] + nums[right];
                if (sum < 0) {
                    left++;
                } else if (sum > 0) {
                    right--;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return result;
    }
}
```

ソートには `O(N log N)`、固定した各インデックスでの二ポインター探索には全体で
`O(N²)` の時間がかかります。ソートは入力配列内で行われ、ソート処理の作業領域は
`O(log N)` です。返す三つ組が `K` 個の場合、結果に必要な領域は `O(K)` です。
