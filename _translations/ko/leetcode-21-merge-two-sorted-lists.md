---
title: LeetCode. 21. Merge Two Sorted Lists
author: MINJUN PARK
date: 2021-12-09 13:08:00 +0900
categories: [Record, Code]
tags:
  [Code Block, Code Snippet, Java, Algorithm, Coding Interview, LeetCode, Merge Two Sorted Lists]
pin: false
lang: ko
translation_key: leetcode-21-merge-two-sorted-lists
permalink: /ko/posts/leetcode-21-merge-two-sorted-lists/
---

![image](https://user-images.githubusercontent.com/55131164/145332953-cfcca450-6c92-4ab1-aa43-9e488bca40a5.png)

[문제](https://leetcode.com/problems/merge-two-sorted-lists/)

## 풀이

더미 헤드를 두면 결과 리스트를 간단하게 만들 수 있습니다. tail 포인터는 마지막에 추가한 노드를 가리킵니다. 두 입력 리스트가 모두 비어 있지 않은 동안 현재 노드 중 더 작은 노드를 결과에 연결하고, 그 노드가 속한 입력 리스트만 한 칸 전진시킵니다. 이 과정에서 결과는 항상 정렬된 상태이며, 입력에서 처리한 노드가 순서대로 포함됩니다. 한 리스트가 끝나면 나머지 리스트의 접미 부분을 연결합니다. 이 부분은 이미 정렬되어 있습니다.

더미 노드를 사용하면 빈 입력도 같은 방식으로 처리할 수 있습니다. 한 입력이 null이면 다른 리스트를 변경 없이 반환하고, 둘 다 null이면 결과도 null입니다. 입력 노드를 복사하지 않으며, 모든 입력 노드를 각각 한 번씩 결과에 재사용합니다.

입력 길이가 각각 `N`, `M`일 때 각 노드를 한 번씩 방문하므로 시간 복잡도는 `O(N + M)`입니다. 고정된 더미 노드와 포인터를 제외한 보조 공간 복잡도는 `O(1)`입니다.

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
