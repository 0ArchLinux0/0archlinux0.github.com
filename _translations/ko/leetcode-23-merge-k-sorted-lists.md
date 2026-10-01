---
title: LeetCode. 23. Merge k Sorted Lists
author: MINJUN PARK
date: 2021-12-10 18:46:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, LeetCode, List, Merge k Sorted Lists]
pin: false
lang: ko
translation_key: leetcode-23-merge-k-sorted-lists
permalink: /ko/posts/leetcode-23-merge-k-sorted-lists/
---

![image](https://user-images.githubusercontent.com/55131164/145535985-fa06cae4-80b3-46f3-9885-972dff5e9be1.png)

[문제](https://leetcode.com/problems/merge-k-sorted-lists/)

## 풀이

비어 있지 않은 각 입력 리스트의 현재 헤드를 최소 힙에 넣습니다. 각 단계에서 힙에는 아직 노드가 남아 있는 각 리스트의 가장 앞선 미병합 노드가 하나씩 들어 있습니다. 따라서 힙의 최솟값은 모든 리스트에 남은 노드 중 가장 작으므로 결과에 추가할 수 있습니다. 해당 노드를 꺼낸 뒤 결과에 연결하기 전에 그 노드의 다음 노드를 힙에 넣습니다. 먼저 다음 노드를 저장하고 넣어 두면 노드를 재사용하더라도 아직 병합되지 않은 입력 연결을 계속 따라갈 수 있습니다.

결과에는 새 노드를 만들지 않고 입력의 원래 노드를 재사용합니다. 더미 헤드를 사용하면 노드를 간단히 추가할 수 있습니다. 힙이 비면 마지막 tail의 `next`를 명시적으로 `null`로 설정하여 결과 리스트를 끝내고, 기존 연결이 남아 순환이 생기는 것을 방지합니다. 빈 배열이나 모든 헤드가 null인 배열은 자연스럽게 `null`을 반환합니다.

전체 노드 수를 `N`, 입력 리스트 수를 `K`라고 하면 각 노드는 힙에 최대 한 번 삽입되고 한 번 제거되며, 각 힙 연산은 `O(log K)`입니다. 시간 복잡도는 `O(N log K)`, 보조 힙 공간 복잡도는 `O(K)`입니다.

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
