---
title: LeetCode 24. 2つずつノードを入れ替える
author: MINJUN PARK
date: 2021-12-10 19:39:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    List,
    Swap Nodes in Pairs,
    Review,
    difficult,
  ]
pin: false
lang: ja
translation_key: leetcode-24-swap-nodes-in-pairs
permalink: /ja/posts/leetcode-24-swap-nodes-in-pairs/
---

![image](https://user-images.githubusercontent.com/55131164/145560777-2ada3d8b-b2a0-4201-84df-889fa5f2e7fb.png)

[問題リンク] <https://leetcode.com/problems/swap-nodes-in-pairs/>

隣り合う2つのノードをペアごとに入れ替えます。値を書き換えるのではなく `next` 参照をつなぎ直すため、元のリストのノードをそのまま再利用します。

ダミーノードを置くことで、先頭のペアにも後続のペアと同じように直前のノードを用意できます。`before` は、すでに最終的な順序になった部分の末尾を指します。各反復では `before -> first -> second -> 残り` を `before -> second -> first -> 残り` につなぎ直し、次のペアを処理するため `before` を `first` に進めます。この不変条件により、最初のペアとそれ以降のペアを個別に扱う必要がありません。

残りのノードが1つだけなら完全なペアではないため、そのままにします。リストの長さが奇数の場合、最後のノードは変更されません。新しく作るリストノードはダミー1つだけで、返されるリストには入力ノードのみが含まれます。

時間計算量は $O(N)$、追加領域は $O(1)$ です。

```java
/**
 * 単方向連結リストのノード定義。
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        // ダミーノードにより、先頭のペアも後続のペアと同じ方法で処理できる。
        ListNode dummy = new ListNode(0, head);
        ListNode before = dummy;

        // 不変条件: before より前のノードはすでに最終的な順序になっている。
        while (before.next != null && before.next.next != null) {
            ListNode first = before.next;
            ListNode second = first.next;

            // ペアをつなぎ直し、残りのリストをそのまま保つ。
            first.next = second.next;
            second.next = first;
            before.next = second;

            // 入れ替えた2ノードの後ろから次のペアを処理する。
            before = first;
        }

        // 最後にペアにならないノードはそのままにし、新しいリストノードは返さない。
        return dummy.next;
    }
}
```
