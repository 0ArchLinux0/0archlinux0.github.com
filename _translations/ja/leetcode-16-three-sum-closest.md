---
title: LeetCode 16 - 3つの数の和に最も近い値
author: MINJUN PARK
date: 2021-08-21 14:11:00 +0900
categories: [Record, Code]
tags: [JavaScript, アルゴリズム, コーディング面接, LeetCode, 3Sum Closest]
pin: false
lang: ja
translation_key: leetcode-16-three-sum-closest
permalink: /ja/posts/leetcode-16-three-sum-closest/
---

[問題リンク](https://leetcode.com/problems/3sum-closest/)

整数配列 `nums` と整数 `target` が与えられます。異なる3要素の和のうち、`target` に最も近い値を返します。最も近い和が複数ある場合は、いずれを返してもかまいません。

配列を昇順にソートし、各要素を順に3つ組の最初の要素として固定します。残りの範囲の両端に2つのポインターを置いて和を探します。和が `target` より小さければ左ポインターを右へ動かして和を大きくし、大きければ右ポインターを左へ動かして和を小さくします。各ステップで、現在の和とこれまでの最良の和のうち `target` との差の絶対値が小さい方を記録します。和が `target` と一致した場合、それ以上近い値はないため直ちに返します。

固定した値やポインターの値が重複する場合は読み飛ばせます。同じ探索を繰り返さないための最適化であり、取り得る最も近い和を見落とすことはないため、正しさには影響しません。

ソートに `O(N log N)`、固定した各要素に対する2ポインター探索に `O(N)` かかるため、全体の時間計算量は `O(N²)` です。ソートを除く探索の追加領域は `O(1)` です。JavaScript の `Array.prototype.sort` が使うメモリはエンジンの実装に依存するため、ソートを含む全体の追加領域を一律に `O(1)` とは言えません。

```javascript
/**
 * @param {number[]} nums
 * @param {number} target
 * @return {number}
 */
function threeSumClosest(nums, target) {
  nums.sort((a, b) => a - b);

  let closest = nums[0] + nums[1] + nums[2];

  for (let i = 0; i < nums.length - 2; i++) {
    if (i > 0 && nums[i] === nums[i - 1]) continue;

    let left = i + 1;
    let right = nums.length - 1;

    while (left < right) {
      const sum = nums[i] + nums[left] + nums[right];

      if (Math.abs(sum - target) < Math.abs(closest - target)) {
        closest = sum;
      }
      if (sum === target) return sum;

      if (sum < target) {
        const leftValue = nums[left];
        while (left < right && nums[left] === leftValue) left++;
      } else {
        const rightValue = nums[right];
        while (left < right && nums[right] === rightValue) right--;
      }
    }
  }

  return closest;
}
```
