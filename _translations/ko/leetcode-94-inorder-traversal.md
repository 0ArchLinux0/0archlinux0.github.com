---
title: LeetCode 94. Binary Tree Inorder Traversal
author: MINJUN PARK
date: 2021-11-17 23:11:00 +0900
categories: [Record, Code]
tags:
  [
    Code Block,
    Code Snippet,
    Java,
    Algorithm,
    Coding Interview,
    LeetCode,
    Binary Tree Inorder Traversal,
  ]
pin: false
lang: ko
translation_key: leetcode-94-inorder-traversal
permalink: /ko/posts/leetcode-94-inorder-traversal/
---

![image](https://user-images.githubusercontent.com/55131164/142216338-a94a51bf-060b-452b-8efb-b28076378011.png)

[문제 링크](https://leetcode.com/problems/binary-tree-inorder-traversal/)

## 풀이

중위 순회는 각 노드를 왼쪽-노드-오른쪽 순서로 방문합니다. 명시적인 스택에는 왼쪽 서브트리를 아직 모두 처리하지 않은 노드를 저장합니다. 루트에서 시작해 왼쪽으로 내려갈 수 있는 만큼 내려가며 각 노드를 스택에 넣습니다. 그런 다음 다음 노드를 꺼내 값을 결과에 추가하고 오른쪽 자식으로 이동합니다. 현재 노드와 스택이 모두 비면 순회를 마칩니다. 루트가 `null`인 경우에도 빈 리스트를 반환합니다.

스택에는 루트에서 리프까지의 경로 하나만 저장되므로 보조 공간은 트리 높이 `H`에 대해 `O(H)`입니다. 모든 노드를 한 번씩 넣고 꺼내므로 시간 복잡도는 `O(N)`입니다. 순회는 트리를 읽기만 하며 노드를 변경하지 않습니다.

## Java

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            current = stack.pop();
            result.add(current.val);
            current = current.right;
        }

        return result;
    }
}
```
