---
title: BOJ 2263 — 트리 순회
author: MINJUN PARK
date: 2022-01-09 05:37:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Tree Traversal, 트리의 순회]
pin: false
lang: ko
translation_key: boj-2263-tree-reconstruction
permalink: /ko/posts/boj-2263-tree-reconstruction/
source_permalink: /posts/BOJ-2263/
---

[문제 링크](https://www.acmicpc.net/problem/2263)

중위 순회에서는 노드가 왼쪽·오른쪽 서브트리 사이에 놓이고, 후위 순회에서는 각 서브트리의 마지막 값이 루트입니다. 따라서 현재 처리할 두 구간에서 후위 순회의 마지막 값이 해당 서브트리의 루트입니다. 값에서 중위 순회 인덱스로 가는 표를 사용하면 분할 위치를 상수 시간에 찾을 수 있습니다. 그 위치 왼쪽의 노드 수가 왼쪽 서브트리의 크기를 결정하고, 나머지 앞쪽 후위 구간은 오른쪽 서브트리에 해당합니다.

재귀 호출 대신 구간을 명시적 스택으로 처리합니다. 각 프레임은 중위·후위 순회의 양 끝 인덱스를 포함합니다. 프레임을 꺼낼 때 루트를 출력하고, 오른쪽 구간을 먼저 넣은 뒤 왼쪽 구간을 넣으면 왼쪽 서브트리가 먼저 처리되어 전위 순회가 됩니다. 각 노드를 한 번씩 인덱싱하고 처리하므로 시간 및 보조 공간 복잡도는 `O(N)`입니다. 최악의 경우 명시적 스택은 `O(N)` 공간을 사용하지만, 편향 트리에서도 호출 스택이 넘치지 않습니다.

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
