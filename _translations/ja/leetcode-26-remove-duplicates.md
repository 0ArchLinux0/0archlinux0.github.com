---
title: LeetCode. 26. Remove Duplicates from Sorted Array
author: MINJUN PARK
date: 2021-12-13 14:21:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Remove Duplicates from Sorted Array,
  ]
pin: false
lang: ja
translation_key: leetcode-26-remove-duplicates
permalink: /ja/posts/leetcode-26-remove-duplicates/
---

![image](https://user-images.githubusercontent.com/55131164/145756841-812d2956-d8b2-48a4-8c8a-0f9f68974191.png)

[問題](https://leetcode.com/problems/remove-duplicates-from-sorted-array/)

## 解法

入力はソート済みなので、同じ値は隣り合っています。読み取りインデックスで配列を左から右へ走査し、書き込みインデックスで新しい重複のない値を置く次の位置を管理します。現在の値が最後に書き込んだ値と異なる場合、その値を書き込み位置にコピーしてから書き込みインデックスを進めます。

各読み取り時点で、接頭辞 `nums[0..write)` には、処理済みの入力に現れた重複のない値だけがソート順で格納されています。要素がある場合は最初の値を必ず書き込み、それ以降の値は直前の重複のない値と異なるときだけ書き込みます。そのため、空配列も安全に処理されて `0` を返し、それ以外では重複のない値の個数を返します。重複のない値は入力配列の先頭部分に並び、それより後ろの内容は無視できます。

時間計算量は `O(N)`、追加領域の計算量は `O(1)` です。

## Java

```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int write = 0;
        for (int read = 0; read < nums.length; read++) {
            if (write == 0 || nums[read] != nums[write - 1]) {
                nums[write++] = nums[read];
            }
        }
        return write;
    }
}
```
