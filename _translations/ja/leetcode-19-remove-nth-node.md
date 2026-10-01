---
title: LeetCode 19. 後ろからn番目のノードを削除
author: MINJUN PARK
date: 2021-11-26 06:54:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Remove Nth Node From End of List,
  ]
pin: false
lang: ja
translation_key: leetcode-19-remove-nth-node
permalink: /ja/posts/leetcode-19-remove-nth-node/
---

![image](https://user-images.githubusercontent.com/55131164/143502763-a5d2b94b-072d-4ae3-a34e-63be478a4323.png)

[問題リンク] <https://leetcode.com/problems/remove-nth-node-from-end-of-list/>

単方向連結リストの先頭 `head` が与えられたら、後ろから `n` 番目のノードを削除し、新しい先頭を返します。`n` は有効な値であることが保証されています。値をコピーするのではなく、既存ノードのリンクをつなぎ直します。

`head` の前にダミーノードを置き、`fast` と `slow` の2つのポインターを使います。まずダミーノードから `fast` を `n` 個リンク分進め、2つのポインターの間に n ノード分の間隔を作ります。次に `fast` が最後のノードになるまで、両方を同時に進めます。このとき `slow` は削除対象の直前を指すため、`slow.next = slow.next.next` として対象ノードを取り除きます。

ダミーノードは、削除対象が元の先頭である場合にもその直前のノードとして機能します。そのため、先頭を削除するときも他の位置と同じ方法でリンクをつなぎ直せ、結果の先頭は `dummy.next` になります。`n` は有効と保証されているため、`fast` を n 回進めてもリストの範囲を超えません。

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast を n 個リンク分先に進めてから、両方のポインターを同時に進める。
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 元の先頭を削除する場合も、slow は削除対象の直前を指す。
        slow.next = slow.next.next;
        return dummy.next;
    }
}
```
