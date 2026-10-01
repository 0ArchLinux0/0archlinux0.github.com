---
title: BOJ. 二分探索木 (5639)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Binary Search Tree, 이진 검색 트리]
pin: false
lang: ja
translation_key: boj-5639-bst-postorder
permalink: /ja/posts/boj-5639-bst-postorder/
source_permalink: /posts/BOJ-5639/
---

[BOJ 5639: 二分探索木](https://www.acmicpc.net/problem/5639)

## 先行順巡回からBSTを構築する

入力は、異なるキーを持つ二分探索木の先行順巡回結果です。先行順巡回では、ノードはすべての子孫より先に現れます。ルートから直近に訪問したノードまでの経路をスタックに保持します。スタックの先頭が現在の挿入位置です。次のキーが先頭のキーより小さければ、そのノードの左の子です。そうでなければ、新しいキーより小さい祖先をスタックから取り出します。最後に取り出したノードが新しいキーの親で、新しいノードはその右の子になります。スタックに残った先頭ノードがあれば、まだその右部分木を抜けていない祖先です。

このスタック不変条件により、各ノードはスタックに一度入り一度出るため、構築時間は `O(N)` です。再帰を使わず、スタック範囲を超えて取り出す可能性のあるセンチネルも不要なので、左または右だけに伸びる木も処理できます。

後行順巡回は、左の子を右の子より先にスタックへ入れてルート-右-左の順に巡回し、集めた値を逆順に出力すれば得られます。結果は左-右-ルートの順です。各ノードを一度ずつ訪問するため、出力時間は `O(N)`、追加領域も `O(N)` です。入力はEOFまで読み込みます。

```java
import java.io.BufferedInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        Node[] ancestors = new Node[100_000];
        Node[] traversal = new Node[100_000];
        int[] postorder = new int[100_000];
        int size = 0;

        int first = input.nextInt();
        if (first == -1) return;

        Node root = new Node(first);
        ancestors[size++] = root;

        int value;
        while ((value = input.nextInt()) != -1) {
            Node node = new Node(value);
            Node parent = null;

            while (size > 0 && ancestors[size - 1].value < value) {
                parent = ancestors[--size];
            }

            if (parent == null) {
                ancestors[size - 1].left = node;
            } else {
                parent.right = node;
            }
            ancestors[size++] = node;
        }

        int traversalSize = 0;
        int postorderSize = 0;
        traversal[traversalSize++] = root;
        while (traversalSize > 0) {
            Node node = traversal[--traversalSize];
            postorder[postorderSize++] = node.value;
            if (node.left != null) traversal[traversalSize++] = node.left;
            if (node.right != null) traversal[traversalSize++] = node.right;
        }

        StringBuilder output = new StringBuilder();
        for (int i = postorderSize - 1; i >= 0; i--) {
            output.append(postorder[i]).append('\n');
        }
        System.out.print(output);
    }

    private static final class Node {
        final int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
        }
    }

    private static final class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int length;
        private int position;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) return -1;
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);
            if (c == -1) return -1;

            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return value;
        }
    }
}
```
