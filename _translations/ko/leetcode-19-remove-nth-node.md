---
title: LeetCode 19. 뒤에서 n번째 노드 삭제하기
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
lang: ko
translation_key: leetcode-19-remove-nth-node
permalink: /ko/posts/leetcode-19-remove-nth-node/
---

![image](https://user-images.githubusercontent.com/55131164/143502763-a5d2b94b-072d-4ae3-a34e-63be478a4323.png)

[문제 링크] <https://leetcode.com/problems/remove-nth-node-from-end-of-list/>

단일 연결 리스트의 시작 노드 `head`가 주어지면 뒤에서 `n`번째 노드를 삭제하고 리스트의 시작 노드를 반환합니다. 입력의 `n`은 유효하다고 보장됩니다. 값을 복사하는 대신 기존 노드의 연결을 다시 설정합니다.

`head` 앞에 더미 노드를 두고 `fast`와 `slow` 두 포인터를 사용합니다. 먼저 더미 노드에서 `fast`를 `n`개의 링크만큼 이동시켜 두 포인터 사이에 n개의 노드 간격을 만듭니다. 그런 다음 `fast`가 마지막 노드가 될 때까지 두 포인터를 함께 이동합니다. 이때 `slow`는 삭제할 노드 바로 앞을 가리키므로 `slow.next = slow.next.next`로 연결을 끊습니다.

더미 노드는 삭제 대상이 원래 리스트의 첫 노드인 경우에도 그 앞의 노드 역할을 합니다. 따라서 첫 노드를 삭제할 때도 다른 위치와 같은 방식으로 연결을 끊을 수 있으며, 결과 리스트의 시작 노드는 `dummy.next`입니다. `n`이 유효하다고 보장되므로 `fast`를 `n`번 이동해도 리스트 범위를 벗어나지 않습니다.

시간 복잡도는 $O(N)$이고 추가 공간 복잡도는 $O(1)$입니다.

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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy;
        ListNode slow = dummy;

        // fast를 n개 링크만큼 앞서게 한 뒤, 두 포인터를 함께 이동한다.
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // 원래 첫 노드를 삭제하는 경우에도 slow는 삭제 대상의 직전 노드다.
        slow.next = slow.next.next;
        return dummy.next;
    }
}
```
