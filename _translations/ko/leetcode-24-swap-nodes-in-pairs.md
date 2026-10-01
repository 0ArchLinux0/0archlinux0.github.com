---
title: LeetCode 24. 쌍별 노드 교환
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
lang: ko
translation_key: leetcode-24-swap-nodes-in-pairs
permalink: /ko/posts/leetcode-24-swap-nodes-in-pairs/
---

![image](https://user-images.githubusercontent.com/55131164/145560777-2ada3d8b-b2a0-4201-84df-889fa5f2e7fb.png)

[문제 링크] <https://leetcode.com/problems/swap-nodes-in-pairs/>

각 인접한 두 노드의 위치를 서로 바꿉니다. 노드의 값만 바꾸는 것이 아니라 `next` 참조를 다시 연결하므로, 원래 리스트의 노드를 그대로 재사용합니다.

더미 노드는 첫 번째 쌍 앞에도 이전 노드가 있는 것처럼 다룰 수 있게 해 줍니다. `before`는 이미 최종 순서로 정리된 부분의 마지막 노드를 가리킵니다. 반복할 때마다 `before -> first -> second -> 나머지`를 `before -> second -> first -> 나머지`로 연결한 뒤, 다음 쌍을 처리하기 위해 `before`를 `first`로 옮깁니다. 이 불변식 덕분에 첫 쌍과 이후 쌍을 별도로 처리할 필요가 없습니다.

남은 노드가 하나뿐이면 완전한 쌍이 없으므로 그대로 둡니다. 리스트의 길이가 홀수인 경우 마지막 노드는 변경되지 않습니다. 새로 만드는 리스트 노드는 더미 노드 하나뿐이며, 반환되는 리스트에는 입력 노드만 포함됩니다.

시간 복잡도는 $O(N)$, 추가 공간 복잡도는 $O(1)$입니다.

```java
/**
 * 단일 연결 리스트 노드 정의.
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
        // 더미 노드 덕분에 첫 쌍도 이후의 모든 쌍과 같은 방식으로 처리할 수 있다.
        ListNode dummy = new ListNode(0, head);
        ListNode before = dummy;

        // 불변식: before 앞의 노드들은 이미 최종 순서로 정렬되어 있다.
        while (before.next != null && before.next.next != null) {
            ListNode first = before.next;
            ListNode second = first.next;

            // 쌍을 다시 연결하고, 나머지 뒷부분은 그대로 보존한다.
            first.next = second.next;
            second.next = first;
            before.next = second;

            // 교환된 두 노드 다음 위치에서 다음 쌍을 처리한다.
            before = first;
        }

        // 마지막에 짝이 없는 노드는 그대로 두며, 새 리스트 노드를 반환하지 않는다.
        return dummy.next;
    }
}
```
