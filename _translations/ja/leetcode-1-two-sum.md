---
title: LeetCode. 1. Two Sum
author: MINJUN PARK
date: 2021-11-19 23:03:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Two Sum]
pin: false
lang: ja
translation_key: leetcode-1-two-sum
permalink: /ja/posts/leetcode-1-two-sum/
---

[問題](https://leetcode.com/problems/two-sum/)

## 解法

配列を左から右へ一度だけ走査します。`nums[i]` を処理する前、マップにはそれより前に確認した値とそのインデックスだけが格納されています。現在の値に対する補数 `target - nums[i]` をマップから検索します。見つかった場合、保存されていたインデックスと `i` が答えになります。見つからなければ、現在の値とインデックスを保存し、後続の要素で使えるようにします。

マップにあるのは前のインデックスだけなので、現在の要素を自分自身と組み合わせることはありません。LeetCode では解がちょうど 1 つ存在すると保証されているため、走査中にそのインデックスの組が見つかります。

補数は検索前に `long` で計算します。補数が `int` の範囲外なら、配列内のどの値とも一致しないため検索を省略します。これにより `target - nums[i]` の整数オーバーフローを防げます。

平均時間計算量は `O(N)`、追加領域の計算量は `O(N)` です。

## Java

```java
import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> earlierIndices = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            long complement = (long) target - nums[i];
            if (complement >= Integer.MIN_VALUE && complement <= Integer.MAX_VALUE) {
                Integer earlierIndex = earlierIndices.get((int) complement);
                if (earlierIndex != null) {
                    return new int[] { earlierIndex, i };
                }
            }

            earlierIndices.put(nums[i], i);
        }

        throw new IllegalStateException("LeetCode guarantees exactly one solution");
    }
}
```
