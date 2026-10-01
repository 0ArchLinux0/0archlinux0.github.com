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
lang: ja
translation_key: leetcode-94-inorder-traversal
permalink: /ja/posts/leetcode-94-inorder-traversal/
---

![image](https://user-images.githubusercontent.com/55131164/142216338-a94a51bf-060b-452b-8efb-b28076378011.png)

[問題](https://leetcode.com/problems/binary-tree-inorder-traversal/)

## 解法

中順走査では、各ノードを左・ノード・右の順に訪問します。明示的なスタックには、左部分木の処理がまだ完了していないノードを保存します。ルートから始めて、左へ進めるところまで進みながら各ノードをスタックに積みます。次に、次のノードを取り出して値を結果に追加し、その右の子へ移動します。現在のノードとスタックが両方空になったら走査を終了します。ルートが `null` の場合も空のリストを返します。

スタックにはルートから葉までの経路だけが入るため、補助領域は木の高さを `H` として `O(H)` です。各ノードを一度ずつ積み、取り出すため、時間計算量は `O(N)` です。この走査は木を読み取るだけで、ノードを変更しません。

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
