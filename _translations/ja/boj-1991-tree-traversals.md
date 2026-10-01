---
title: BOJ 1991 — 二分木の走査
author: MINJUN PARK
date: 2022-01-07 00:50:00 +0900
categories: [Record, Code]
tags:
  [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Tree Traversal, 트리 순회]
pin: false
lang: ja
translation_key: boj-1991-tree-traversals
permalink: /ja/posts/boj-1991-tree-traversals/
---

[問題リンク](https://www.acmicpc.net/problem/1991)

各入力行にはノードとその左・右の子が記されています。ノード名は大文字 1 文字なので、`label - 'A'` を添字にした配列に子を保存します。子がない場合は `.` が入力され、その値を配列に残しておき、巡回時にこの値に出会ったらその枝を飛ばします。`A` から始め、先行順はノード-左-右、中間順は左-ノード-右、後行順は左-右-ノードの順で訪問します。それぞれの結果を別の文字列に蓄積し、この順に 3 行で出力します。

各巡回ではすべてのノードを 1 回ずつ訪問します。3 種類の巡回全体の時間計算量は `O(N)`、子の情報と出力文字列の空間計算量は `O(N)` です。再帰呼び出しスタックは、木の高さを `H` とすると `O(H)` です。

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
