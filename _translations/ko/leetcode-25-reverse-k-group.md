---
title: LeetCode. 25. Reverse Nodes in k-Group
author: MINJUN PARK
date: 2021-12-11 21:25:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, LeetCode, List, Reverse Nodes in k-Group]
pin: false
lang: ko
translation_key: leetcode-25-reverse-k-group
permalink: /ko/posts/leetcode-25-reverse-k-group/
---

![image](https://user-images.githubusercontent.com/55131164/145676418-be6a-463c-991c-4fab78c57e36.png)

[문제](https://leetcode.com/problems/reverse-nodes-in-k-group/)

## 풀이

리스트를 한 그룹씩 처리합니다. 각 그룹의 첫 노드부터 `k - 1`개의 링크를 따라가 k번째 노드를 찾습니다. 남은 노드가 `k`개보다 적으면 그 접미 리스트는 원래 순서를 유지해야 하므로 처리를 멈춥니다. 그룹을 완성할 수 있으면 그룹 다음 노드를 `groupNext`에 저장한 뒤, 그룹 안의 링크를 `groupNext` 방향으로 뒤집습니다. 뒤집기를 시작할 때 이전 포인터를 `groupNext`로 설정하면, 원래 그룹의 첫 노드는 뒤집기가 끝났을 때 변경되지 않은 접미 리스트를 가리킵니다.

k번째 노드는 새 그룹의 헤드가 되고, 원래 첫 노드는 새 그룹의 테일이 됩니다. 이전 그룹의 테일을 새 헤드에 연결합니다. 첫 그룹이라면 전체 리스트의 헤드를 갱신합니다. 그런 다음 테일이 된 원래 첫 노드부터 다음 그룹 처리를 계속합니다. 새 리스트 노드를 만들지 않고 입력의 모든 노드를 재사용합니다.

노드가 `N`개라면 각 노드를 상수 횟수만큼 방문하므로 시간 복잡도는 `O(N)`입니다. 보조 공간 복잡도는 `O(1)`입니다.

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
