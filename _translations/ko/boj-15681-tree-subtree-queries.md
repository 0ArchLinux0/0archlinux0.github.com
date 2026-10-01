---
title: BOJ. 트리와 쿼리 (15681)
author: MINJUN PARK
date: 2022-01-24 21:30:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Binary Tree,
    Dynamic Programming,
    BOJ,
    Tree And Query,
    트리와 쿼리
  ]
pin: false
lang: ko
translation_key: boj-15681-tree-subtree-queries
permalink: /ko/posts/boj-15681-tree-subtree-queries/
source_permalink: /posts/BOJ-15681/
---

[문제: BOJ 15681 — 트리와 쿼리](https://www.acmicpc.net/problem/15681) · [English](/posts/BOJ-15681/) · [日本語](/ja/posts/boj-15681-tree-subtree-queries/)

## 반복형 루트 설정과 역순 누적

무방향 트리를 `R`을 루트로 삼아 탐색합니다. 스택을 사용하는 순회에서 각 노드의 부모를 기록하고 방문 순서를 `order` 배열에 저장합니다. 노드는 부모에서 발견될 때만 스택에 추가하고, 현재 노드의 부모로 돌아가는 간선은 건너뜁니다. 따라서 각 노드는 정확히 한 번 스택에 들어가고 순서 배열에도 한 번 기록됩니다. 부모가 자식을 발견하기 전에 처리되므로 `order`에서 부모는 항상 자식보다 앞에 있습니다.

각 서브트리 크기를 자기 자신을 포함해 1로 초기화한 뒤, `order`를 역순으로 순회하면서 각 노드의 누적 크기를 부모에게 더합니다. 부모보다 자식이 먼저 처리되는 역순에서는 모든 자식의 크기가 먼저 반영된 다음 부모에게 전달됩니다. 따라서 전처리가 끝나면 `subtreeSize[u]`는 `u`를 루트로 하는 서브트리의 노드 수입니다. 쿼리는 배열에서 바로 읽어 `O(1)`에 답할 수 있습니다. 루트 쿼리는 `N`, 리프 쿼리는 `1`을 반환합니다.

순회와 누적 과정은 각각 모든 노드를 한 번씩 방문하며, `N - 1`개 간선을 읽는 시간도 `O(N)`입니다. 전처리 시간은 총 `O(N)`이고, 쿼리 처리에는 `O(Q)`가 듭니다. 인접 리스트, 부모/순서 배열, 스택, 서브트리 크기 배열의 공간 복잡도는 `O(N)`입니다. 재귀 호출을 사용하지 않으므로 노드 100,000개가 일렬로 이어진 트리에서도 Java 호출 스택이 넘치지 않습니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int nodeCount = input.nextInt();
        int root = input.nextInt();
        int queryCount = input.nextInt();

        List<Integer>[] graph = new List[nodeCount + 1];
        for (int node = 1; node <= nodeCount; node++) {
            graph[node] = new ArrayList<>();
        }
        for (int i = 0; i < nodeCount - 1; i++) {
            int u = input.nextInt();
            int v = input.nextInt();
            graph[u].add(v);
            graph[v].add(u);
        }

        int[] parent = new int[nodeCount + 1];
        int[] order = new int[nodeCount];
        int[] stack = new int[nodeCount];
        int orderSize = 0;
        int top = 0;
        stack[top] = root;
        parent[root] = -1;

        while (top >= 0) {
            int node = stack[top--];
            order[orderSize++] = node;
            for (int neighbor : graph[node]) {
                if (neighbor == parent[node]) {
                    continue;
                }
                parent[neighbor] = node;
                stack[++top] = neighbor;
            }
        }

        int[] subtreeSize = new int[nodeCount + 1];
        for (int node = 1; node <= nodeCount; node++) {
            subtreeSize[node] = 1;
        }
        for (int i = orderSize - 1; i > 0; i--) {
            int node = order[i];
            subtreeSize[parent[node]] += subtreeSize[node];
        }

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            output.append(subtreeSize[input.nextInt()]).append('\n');
        }
        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int length;

        private int read() throws IOException {
            if (position == length) {
                length = input.read(buffer);
                position = 0;
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        private int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);

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
