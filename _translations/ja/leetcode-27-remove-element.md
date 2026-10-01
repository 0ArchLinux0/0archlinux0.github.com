---
title: LeetCode. 27. Remove Element
author: MINJUN PARK
date: 2021-12-13 16:21:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, Remove Element]
pin: false
lang: ja
translation_key: leetcode-27-remove-element
permalink: /ja/posts/leetcode-27-remove-element/
---

[問題](https://leetcode.com/problems/remove-element/)

## 方針

`nums`を左から右へ走査し、次に残す値を書き込む位置を示すインデックスを保持します。現在の値が`val`と異なる場合は`nums[write]`へコピーし、`write`を進めます。`val`と等しい値は読み飛ばします。

各要素を処理する前、先頭の`write`個の位置には、これまでに確認した値のうち`val`と異なるものだけが元の順序のまま正確に格納されています。`write`は現在の走査位置を超えないため、残す値を書き込み位置へコピーしても、まだ読んでいない要素を上書きすることはありません。走査が終わると、`write`が新しい長さとなり、`nums[0..write)`が順序を保ったフィルタ結果です。それ以降の配列部分は未規定なので、依存してはいけません。

時間計算量は`O(N)`、追加領域の計算量は`O(1)`です。

## Java

```java
class Solution {
    public int removeElement(int[] nums, int val) {
        int write = 0;

        for (int value : nums) {
            if (value != val) {
                nums[write++] = value;
            }
        }

        return write;
    }
}
```
