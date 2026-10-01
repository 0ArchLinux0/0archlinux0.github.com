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
lang: ko
translation_key: leetcode-83-remove-duplicates-sorted-list
permalink: /ko/posts/leetcode-83-remove-duplicates-sorted-list/
---

![image](https://user-images.githubusercontent.com/55131164/141489519-33e0bc42-ee25-4fd2-9d0b-88d3e28ccf0a.png)

[문제 링크](https://leetcode.com/problems/remove-duplicates-from-sorted-list/)

## 풀이

리스트가 정렬되어 있으므로 같은 값은 반드시 서로 인접해 있습니다. 포인터 하나를 사용해 현재까지 유지한 마지막 노드를 가리킵니다. 다음 노드의 값이 현재 노드와 같으면 다음 노드를 건너뛰고, 다르면 포인터를 다음 노드로 이동합니다. 각 단계에서 head부터 포인터까지는 지금까지 처리한 서로 다른 값마다 노드 하나씩만 정렬된 순서로 유지합니다. 마지막으로 남는 노드는 원래 리스트의 마지막 노드이므로 `next`가 `null`이며, 결과에 사이클이 생기지 않습니다.

모든 노드를 한 번씩 확인하므로 시간 복잡도는 `O(N)`입니다. 입력 노드를 재사용하며 추가 공간 복잡도는 `O(1)`입니다.

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
