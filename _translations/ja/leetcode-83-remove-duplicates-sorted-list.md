---
title: LeetCode. 83. Remove Duplicates from Sorted List
author: MINJUN PARK
date: 2021-11-13 12:12:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    LinkedList,
    Remove Duplicates from Sorted List,
  ]
pin: false
lang: ja
translation_key: leetcode-83-remove-duplicates-sorted-list
permalink: /ja/posts/leetcode-83-remove-duplicates-sorted-list/
---

![image](https://user-images.githubusercontent.com/55131164/141489519-33e0bc42-ee25-4fd2-9d0b-88d3e28ccf0a.png)

[問題](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)

## 解法

リストはソート済みなので、同じ値は必ず隣り合っています。ポインターを1つ使い、現在まで残した最後のノードを指します。次のノードの値が現在のノードと同じなら次のノードを飛ばし、異なるならポインターを次へ進めます。各ステップで、headからポインターまでには、処理済みの異なる値ごとにノードが1つだけ、ソート順で保たれます。最後に残るノードは元のリストの末尾なので、その `next` は `null` のままであり、結果に循環は生じません。

各ノードを一度ずつ確認するため、時間計算量は `O(N)` です。入力ノードを再利用し、追加領域の計算量は `O(1)` です。

## Java

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        ListNode node = head;
        while (node != null && node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }
}
```
