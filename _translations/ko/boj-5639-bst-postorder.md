---
title: BOJ. 이진 검색 트리 (5639)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, Binary Search Tree, 이진 검색 트리]
pin: false
lang: ko
translation_key: boj-5639-bst-postorder
permalink: /ko/posts/boj-5639-bst-postorder/
source_permalink: /posts/BOJ-5639/
---

[BOJ 5639: 이진 검색 트리](https://www.acmicpc.net/problem/5639)

## 전위 순회로 BST 구성하기

입력은 서로 다른 키를 가진 이진 검색 트리의 전위 순회 결과입니다. 전위 순회에서는 노드가 모든 자손보다 먼저 등장합니다. 루트에서 가장 최근에 방문한 노드까지의 경로를 스택에 유지합니다. 스택의 맨 위는 현재 삽입 위치입니다. 다음 키가 맨 위 키보다 작으면 그 노드의 왼쪽 자식입니다. 그렇지 않으면 새 키보다 작은 조상들을 스택에서 꺼냅니다. 마지막으로 꺼낸 노드가 새 키의 부모이며, 새 노드는 그 노드의 오른쪽 자식입니다. 스택에 남은 맨 위 노드가 있다면 아직 오른쪽 서브트리를 벗어나지 않은 조상입니다.

이 스택 불변식에 따라 각 노드는 스택에 한 번 들어가고 한 번 나오므로 구성 시간은 `O(N)`입니다. 재귀를 사용하지 않고, 스택 범위를 벗어나게 꺼낼 수 있는 센티널도 필요하지 않아 왼쪽 또는 오른쪽으로만 이어진 트리도 처리합니다.

후위 순회는 왼쪽 자식을 오른쪽 자식보다 먼저 스택에 넣어 루트-오른쪽-왼쪽 순서로 순회한 뒤, 모은 값을 역순으로 출력하면 얻을 수 있습니다. 결과는 왼쪽-오른쪽-루트 순서입니다. 모든 노드를 한 번씩 방문하므로 출력 시간은 `O(N)`, 추가 공간은 `O(N)`입니다. 입력은 EOF까지 읽습니다.

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
