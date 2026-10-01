---
title: LeetCode. 23. Merge k Sorted Lists
author: MINJUN PARK
date: 2021-12-10 18:46:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, List, Merge k Sorted Lists]
pin: false
lang: ja
translation_key: leetcode-23-merge-k-sorted-lists
permalink: /ja/posts/leetcode-23-merge-k-sorted-lists/
---

![image](https://user-images.githubusercontent.com/55131164/145535985-fa06cae4-80b3-46f3-9885-972dff5e9be1.png)

[問題](https://leetcode.com/problems/merge-k-sorted-lists/)

## 方針

空でない各入力リストの現在の先頭ノードを最小ヒープに入れます。各ステップで、ヒープにはまだノードが残っている各リストから、未マージ部分の先頭ノードがちょうど一つずつ入っています。したがって、ヒープの最小ノードは全リストに残っているノードの中で最も小さく、結果に追加できます。そのノードを取り出したら、結果に接続する前に次のノードをヒープへ追加します。先に次のノードを保存して追加することで、ノードを再利用しても未マージの入力チェーンをたどり続けられます。

結果には新しいノードを割り当てず、入力の元ノードを再利用します。ダミーヘッドを使うとノードを簡単に追加できます。ヒープが空になったら最後の tail の `next` を明示的に `null` に設定し、結果リストを終端させます。これにより、古いリンクが残って循環が生じることを防ぎます。空の配列、またはすべての先頭ノードが null の配列は、そのまま `null` を返します。

全ノード数を `N`、入力リスト数を `K` とすると、各ノードはヒープに最大一度挿入され、一度取り出されます。ヒープ操作一回の計算量は `O(log K)` です。時間計算量は `O(N log K)`、補助ヒープ空間は `O(K)` です。

## Java

```java
import java.util.PriorityQueue;

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
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> minHeap =
                new PriorityQueue<>((a, b) -> Integer.compare(a.val, b.val));

        for (ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        ListNode dummy = new ListNode();
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            ListNode node = minHeap.poll();
            ListNode successor = node.next;
            if (successor != null) {
                minHeap.offer(successor);
            }
            tail.next = node;
            tail = node;
        }

        tail.next = null;
        return dummy.next;
    }
}
```
