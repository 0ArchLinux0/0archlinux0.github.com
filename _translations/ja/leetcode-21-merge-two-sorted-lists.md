---
title: LeetCode. 21. Merge Two Sorted Lists
author: MINJUN PARK
date: 2021-12-09 13:08:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Merge Two Sorted Lists]
pin: false
lang: ja
translation_key: leetcode-21-merge-two-sorted-lists
permalink: /ja/posts/leetcode-21-merge-two-sorted-lists/
---

![image](https://user-images.githubusercontent.com/55131164/145332953-cfcca450-6c92-4ab1-aa43-9e488bca40a5.png)

[問題](https://leetcode.com/problems/merge-two-sorted-lists/)

## 方針

ダミーヘッドを使うと、結果リストを簡潔に構築できます。tail ポインターは、最後に追加したノードを指します。両方の入力リストが空でない間、現在のノードのうち値が小さい方を結果に接続し、そのノードが属する入力リストだけを進めます。この処理中、結果リストは常にソート済みで、入力から処理したノードが順番に含まれます。一方のリストが終わったら、もう一方のリストの残りの部分を接続します。残りの部分はすでにソートされています。

ダミーノードを使うことで、空の入力も同じ方法で扱えます。どちらか一方が null なら、もう一方のリストをそのまま返し、両方が null なら結果も null です。入力ノードをコピーせず、すべての入力ノードをそれぞれ一度だけ結果に再利用します。

入力リストの長さをそれぞれ `N`、`M` とすると、各ノードを一度ずつ調べるため、時間計算量は `O(N + M)` です。固定のダミーノードとポインターを除く補助空間計算量は `O(1)` です。

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
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode();
        ListNode tail = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val <= list2.val) {
                tail.next = list1;
                list1 = list1.next;
            } else {
                tail.next = list2;
                list2 = list2.next;
            }
            tail = tail.next;
        }

        tail.next = (list1 != null) ? list1 : list2;
        return dummy.next;
    }
}
```
