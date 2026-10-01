---
title: BOJ 1991 — 트리 순회
author: MINJUN PARK
date: 2022-01-07 00:50:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Tree Traversal, 트리 순회]
pin: false
lang: ko
translation_key: boj-1991-tree-traversals
permalink: /ko/posts/boj-1991-tree-traversals/
---

[문제 링크](https://www.acmicpc.net/problem/1991)

각 입력 줄은 노드 하나와 왼쪽·오른쪽 자식을 나타냅니다. 노드 이름은 대문자 한 글자이므로 `label - 'A'`를 인덱스로 하는 배열에 두 자식을 저장합니다. 자식이 없으면 `.`이 입력되며, 배열에 그대로 보존한 뒤 순회할 때 이 값을 만나면 해당 가지를 건너뜁니다. `A`에서 시작해 전위 순회는 노드-왼쪽-오른쪽, 중위 순회는 왼쪽-노드-오른쪽, 후위 순회는 왼쪽-오른쪽-노드 순으로 방문합니다. 각 결과를 별도의 문자열에 모아 순서대로 세 줄에 출력합니다.

각 순회는 모든 노드를 한 번씩 방문합니다. 세 순회의 총 시간 복잡도는 `O(N)`이고, 자식 정보와 출력 문자열의 공간은 `O(N)`입니다. 재귀 호출 스택은 트리 높이를 `H`라고 할 때 `O(H)`입니다.

```java
import java.io.*;
import java.util.*;

public class Main {
  static char[][] children = new char[26][2];
  static StringBuilder preorder = new StringBuilder();
  static StringBuilder inorder = new StringBuilder();
  static StringBuilder postorder = new StringBuilder();

  static void traverse(int node) {
    if (node < 0) return;

    preorder.append((char) ('A' + node));
    traverse(children[node][0] == '.' ? -1 : children[node][0] - 'A');
    inorder.append((char) ('A' + node));
    traverse(children[node][1] == '.' ? -1 : children[node][1] - 'A');
    postorder.append((char) ('A' + node));
  }

  public static void main(String[] args) throws IOException {
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(input.readLine().trim());

    for (int i = 0; i < n; i++) {
      StringTokenizer row = new StringTokenizer(input.readLine());
      int node = row.nextToken().charAt(0) - 'A';
      children[node][0] = row.nextToken().charAt(0);
      children[node][1] = row.nextToken().charAt(0);
    }

    traverse('A' - 'A');
    System.out.println(preorder);
    System.out.println(inorder);
    System.out.println(postorder);
  }
}
```
