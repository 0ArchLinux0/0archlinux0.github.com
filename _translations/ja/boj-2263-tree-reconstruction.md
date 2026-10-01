---
title: BOJ 2263 — 二分木の走査
author: MINJUN PARK
date: 2022-01-09 05:37:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Tree Traversal, 二分木の巡回]
pin: false
lang: ja
translation_key: boj-2263-tree-reconstruction
permalink: /ja/posts/boj-2263-tree-reconstruction/
source_permalink: /posts/BOJ-2263/
---

[問題リンク](https://www.acmicpc.net/problem/2263)

中間順ではノードが左・右の部分木の間に置かれ、後行順では各部分木の最後の値がその根になります。そのため、現在処理している 2 つの範囲では、後行順の最後の値が部分木の根です。値から中間順のインデックスを引く表を使えば、分割位置を定数時間で見つけられます。その位置より左にあるノード数で左部分木の大きさが決まり、残りの先頭側の後行順範囲が右部分木に対応します。

再帰呼び出しの代わりに、範囲を明示的なスタックで処理します。各フレームには中間順・後行順それぞれの両端インデックスを格納します。フレームを取り出したときに根を出力し、右の範囲を先に積んでから左の範囲を積むと、左部分木が先に処理され、先行順になります。各ノードのインデックス登録と処理は 1 回ずつなので、時間計算量と補助空間計算量はいずれも `O(N)` です。最悪時、明示的なスタックは `O(N)` の空間を使いますが、偏った木でも呼び出しスタックがあふれません。

```java
import java.io.*;
import java.util.*;

public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(input.readLine().trim());
    int[] inorder = new int[n];
    int[] postorder = new int[n];
    int[] inorderIndex = new int[n + 1];

    StringTokenizer values = new StringTokenizer(input.readLine());
    for (int i = 0; i < n; i++) {
      inorder[i] = Integer.parseInt(values.nextToken());
      inorderIndex[inorder[i]] = i;
    }
    values = new StringTokenizer(input.readLine());
    for (int i = 0; i < n; i++) {
      postorder[i] = Integer.parseInt(values.nextToken());
    }

    int[] inLeftStack = new int[n];
    int[] inRightStack = new int[n];
    int[] postLeftStack = new int[n];
    int[] postRightStack = new int[n];
    int top = 0;
    inLeftStack[top] = 0;
    inRightStack[top] = n - 1;
    postLeftStack[top] = 0;
    postRightStack[top] = n - 1;

    StringBuilder preorder = new StringBuilder();
    while (top >= 0) {
      int inLeft = inLeftStack[top];
      int inRight = inRightStack[top];
      int postLeft = postLeftStack[top];
      int postRight = postRightStack[top--];

      int root = postorder[postRight];
      preorder.append(root).append(' ');
      int rootIndex = inorderIndex[root];
      int leftSize = rootIndex - inLeft;

      if (rootIndex < inRight) {
        top++;
        inLeftStack[top] = rootIndex + 1;
        inRightStack[top] = inRight;
        postLeftStack[top] = postLeft + leftSize;
        postRightStack[top] = postRight - 1;
      }
      if (inLeft < rootIndex) {
        top++;
        inLeftStack[top] = inLeft;
        inRightStack[top] = rootIndex - 1;
        postLeftStack[top] = postLeft;
        postRightStack[top] = postLeft + leftSize - 1;
      }
    }

    System.out.println(preorder);
  }
}
```
