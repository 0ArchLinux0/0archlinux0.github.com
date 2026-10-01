---
title: BOJ. 최소 스패닝 트리 (1197)
author: MINJUN PARK
date: 2022-01-22 01:28:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    Graph,
    BOJ,
    MST (Minimum Spanning Tree),
    최소 스패닝 트리
  ]
pin: false
lang: ko
translation_key: boj-1197-minimum-spanning-tree
permalink: /ko/posts/boj-1197-minimum-spanning-tree/
source_permalink: /posts/BOJ-1197/
---

[문제 링크](https://www.acmicpc.net/problem/1197) · [English](/posts/BOJ-1197/) · [日本語](/ja/posts/boj-1197-minimum-spanning-tree/)

## 크루스칼 알고리즘

신장 트리는 모든 정점을 사이클 없이 연결하는 트리입니다. 최소 스패닝 트리(MST)는 가능한 신장 트리 중 간선 가중치 합이 가장 작은 트리입니다. 크루스칼 알고리즘은 간선을 가중치가 작은 순서로 정렬한 뒤, 양 끝점이 서로 다른 연결 요소에 속할 때만 간선을 선택합니다. 서로 연결되어 있는지는 서로소 집합(DSU) 자료구조로 확인하고, 선택한 간선의 두 요소를 합칩니다.

이 탐욕적 선택은 안전합니다. 현재 서로 다른 요소를 잇는 간선 중 가장 가벼운 간선은 컷 성질에 따라 어떤 MST에 포함될 수 있습니다. 이 선택을 반복하면 사이클 없는 포리스트가 만들어지고, `V - 1`개의 간선을 선택한 순간 신장 트리이자 MST가 됩니다. 간선 가중치가 음수여도 정렬과 컷 성질은 그대로 유효하므로, 음수 간선도 서로 다른 요소를 연결하면 선택해야 합니다.

간선의 양 끝점은 대칭적으로 취급하므로 입력에서 받은 두 정점을 그대로 보존하면 됩니다. 크루스칼 알고리즘을 위해 끝점을 바꿀 필요가 없습니다. 가중치는 뺄셈 비교 대신 오버플로가 없는 `Integer.compare`를 사용합니다. 최대 `V - 1`개의 `int` 가중치를 더한 결과는 `int` 범위를 넘을 수 있으므로 합계는 `long`으로 저장합니다. 간선 `E`개, 정점 `V`개일 때 정렬을 포함한 시간 복잡도는 `O(E log E)`이고, DSU 연산은 각 연산당 분할 상환 `O(α(V))`입니다. 공간 복잡도는 `O(V + E)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();
        List<Edge> edges = new ArrayList<>(edgeCount);

        for (int i = 0; i < edgeCount; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int weight = input.nextInt();
            edges.add(new Edge(a, b, weight));
        }

        edges.sort(Comparator.comparingInt(edge -> edge.weight));
        DisjointSet sets = new DisjointSet(vertexCount);
        long totalWeight = 0;
        int acceptedEdges = 0;

        for (Edge edge : edges) {
            if (!sets.union(edge.a, edge.b)) {
                continue;
            }
            totalWeight += edge.weight;
            acceptedEdges++;
            if (acceptedEdges == vertexCount - 1) {
                break;
            }
        }

        System.out.println(totalWeight);
    }

    private static final class Edge {
        final int a;
        final int b;
        final int weight;

        Edge(int a, int b, int weight) {
            this.a = a;
            this.b = b;
            this.weight = weight;
        }
    }

    private static final class DisjointSet {
        private final int[] parent;
        private final int[] size;

        DisjointSet(int n) {
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        private int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        boolean union(int a, int b) {
            int rootA = find(a);
            int rootB = find(b);
            if (rootA == rootB) {
                return false;
            }
            if (size[rootA] < size[rootB]) {
                int temp = rootA;
                rootA = rootB;
                rootB = temp;
            }
            parent[rootB] = rootA;
            size[rootA] += size[rootB];
            return true;
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
                if (length == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }

        int nextInt() throws IOException {
            int c;
            do {
                c = read();
            } while (c <= ' ' && c != -1);
            int sign = 1;
            if (c == '-') {
                sign = -1;
                c = read();
            }
            int value = 0;
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = read();
            }
            return sign * value;
        }
    }
}
```
