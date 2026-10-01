---
title: LeetCode. 25. Reverse Nodes in k-Group
author: MINJUN PARK
date: 2021-12-11 21:25:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, LeetCode, List, Reverse Nodes in k-Group]
pin: false
lang: ja
translation_key: leetcode-25-reverse-k-group
permalink: /ja/posts/leetcode-25-reverse-k-group/
---

![image](https://user-images.githubusercontent.com/55131164/145676418-be6a-463c-991c-4fab78c57e36.png)

[問題](https://leetcode.com/problems/reverse-nodes-in-k-group/)

## 方針

リストをグループごとに処理します。各グループの先頭から `k - 1` 個のリンクをたどり、k 番目のノードを探します。残りのノードが `k` 個未満なら、その末尾部分は元の順序を保つ必要があるため処理を終了します。グループを作れる場合は、グループの次のノードを `groupNext` に保存し、グループ内のリンクを `groupNext` に向けて反転します。反転の開始時に前ノードを `groupNext` にしておくと、反転後、元のグループ先頭が変更されていない末尾部分を指します。

k 番目のノードが新しいグループの先頭になり、元の先頭ノードが新しい末尾になります。前のグループの末尾を新しい先頭に接続します。最初のグループの場合はリスト全体の先頭を更新します。その後、末尾になった元の先頭ノードから次のグループを処理します。新しいリストノードは作成せず、入力の全ノードを再利用します。

ノード数を `N` とすると、各ノードを定数回訪問するため、時間計算量は `O(N)` です。補助空間計算量は `O(1)` です。

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
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode groupPrevious = null;
        ListNode groupStart = head;

        while (groupStart != null) {
            ListNode kth = groupStart;
            for (int i = 1; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break;
            }

            ListNode groupNext = kth.next;
            ListNode previous = groupNext;
            ListNode current = groupStart;
            while (current != groupNext) {
                ListNode next = current.next;
                current.next = previous;
                previous = current;
                current = next;
            }

            if (groupPrevious == null) {
                head = kth;
            } else {
                groupPrevious.next = kth;
            }
            groupPrevious = groupStart;
            groupStart = groupNext;
        }

        return head;
    }
}
```
