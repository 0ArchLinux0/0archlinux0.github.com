---
title: BOJ. 트리의 부모 찾기 (11725)
author: MINJUN PARK
date: 2022-01-06 05:12:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Coding Interview, BOJ, Tree, Data Structure, 트리의 부모 찾기]
pin: false
lang: ko
translation_key: boj-11725-tree-parents
permalink: /ko/posts/boj-11725-tree-parents/
source_permalink: /posts/BOJ-11725/
---

[BOJ 11725: 트리의 부모 찾기](https://www.acmicpc.net/problem/11725)

## 순회로 트리에 루트 지정하기

입력으로 주어진 무방향 간선을 인접 리스트에 저장하고 1번 노드를 루트로 둡니다. 너비 우선 탐색 큐에 1번 노드를 넣고 탐색하면, 현재 노드에서 처음 발견한 이웃은 현재 노드의 자식입니다. 이 관계는 이웃을 큐에서 꺼낼 때가 아니라 큐에 넣을 때 기록합니다. 발견 즉시 방문 상태를 표시하므로 다른 간선이 같은 노드의 부모를 다시 지정할 수 없습니다. 루트도 탐색 전에 표시하며, 출력할 부모는 없습니다.

트리에서는 루트에서 각 노드로 가는 경로가 하나뿐이므로 노드에 처음 도달하게 한 간선이 유일한 부모 간선입니다. 순회가 끝난 뒤 `2`번부터 `N`번 노드까지 부모를 노드 순서대로 출력합니다. 큐를 정수 배열로 구현하므로 재귀 호출이 없으며 깊이가 최대인 일자형 트리도 안전하게 처리합니다.

간선은 `N - 1`개입니다. 무방향 인접 리스트를 만들고 모든 정점과 간선을 순회하므로 시간 복잡도는 `O(N)`입니다. 인접 리스트, 부모 배열, 큐의 추가 공간 복잡도도 `O(N)`입니다.

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();

        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] graph = new ArrayList[n + 1];
        for (int node = 1; node <= n; node++) {
            graph[node] = new ArrayList<>();
        }

        for (int edge = 0; edge < n - 1; edge++) {
            int a = input.nextInt();
            int b = input.nextInt();
            graph[a].add(b);
            graph[b].add(a);
        }

        int[] parent = new int[n + 1];
        int[] queue = new int[n];
        int head = 0;
        int tail = 0;
        parent[1] = 1;
        queue[tail++] = 1;

        while (head < tail) {
            int current = queue[head++];
            for (int neighbor : graph[current]) {
                if (parent[neighbor] != 0) continue;
                parent[neighbor] = current;
                queue[tail++] = neighbor;
            }
        }

        StringBuilder output = new StringBuilder();
        for (int node = 2; node <= n; node++) {
            output.append(parent[node]).append('\n');
        }
        System.out.print(output);
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
