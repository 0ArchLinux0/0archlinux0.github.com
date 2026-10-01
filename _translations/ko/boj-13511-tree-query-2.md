---
title: BOJ 13511 - 트리와 쿼리 2
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트리, 최소 공통 조상, LCA, 희소 테이블]
pin: false
lang: ko
translation_key: boj-13511-tree-query-2
permalink: /ko/posts/boj-13511-tree-query-2/
source_permalink: /posts/BOJ-13511/
---

[문제: BOJ 13511 — 트리와 쿼리 2](https://www.acmicpc.net/problem/13511) · [English](/posts/BOJ-13511/) · [日本語](/ja/posts/boj-13511-tree-query-2/)

정점 1을 루트로 정하고 반복문으로 트리를 순회합니다. 각 정점의 깊이와 부모를 기록합니다. 루트의 부모는 루트 자신으로 두어 조상 테이블의 모든 값이 유효하도록 합니다. `up[v][j]`는 `v`에서 간선 `2^j`개를 올라간 조상이고, `weight[v][j]`는 그 간선들의 가중치 합입니다. `2^j`번 점프를 두 개의 `2^(j-1)`번 점프로 나누어 두 테이블을 구성합니다.

경로 질의에서는 먼저 깊이가 더 깊은 끝점을 다른 끝점과 같은 깊이까지 올린 뒤, 두 정점의 부모가 같아질 때까지 큰 점프부터 두 정점을 함께 올려 최소 공통 조상(LCA)을 찾습니다. 경로 거리는 각 끝점에서 LCA까지 올라가는 두 가중치 합을 더해 구합니다. 경로의 양의 간선 가중치 합은 커질 수 있으므로 누적 변수는 `long`으로 둡니다.

2번 유형 질의에서 `upEdges`는 `a`에서 LCA까지의 간선 수이고, `downEdges`는 LCA에서 `b`까지의 간선 수입니다. 경로에는 총 `upEdges + downEdges + 1`개의 정점이 있습니다. 1부터 세는 `k`가 `upEdges + 1` 이하라면 `a`에서 `k - 1`개 간선을 올라갑니다. 그렇지 않으면 `b`에서 경로의 뒤쪽을 기준으로 `upEdges + downEdges + 1 - k`개 간선을 올라갑니다. 이 방법으로 LCA와 양쪽 끝점도 별도의 경로 처리 없이 정확히 구할 수 있습니다.

순회와 테이블 구성 모두 반복문으로 수행하므로 정점 100,000개가 일렬로 연결된 트리에서도 호출 스택이 넘치지 않습니다. 전처리는 `O(N log N)` 시간, `O(N log N)` 공간을 사용하며 각 질의는 `O(log N)` 시간에 처리합니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static class FastScanner {
        private final BufferedInputStream in = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int pointer = 0;
        private int length = 0;

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

        private int read() throws IOException {
            if (pointer == length) {
                length = in.read(buffer);
                pointer = 0;
                if (length == -1) return -1;
            }
            return buffer[pointer++];
        }
    }

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }
    }

    static int[][] up;
    static long[][] weight;
    static int[] depth;

    static int lift(int vertex, int steps) {
        for (int j = 0; steps > 0; j++, steps >>= 1) {
            if ((steps & 1) != 0) vertex = up[vertex][j];
        }
        return vertex;
    }

    static int lca(int a, int b) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }
        a = lift(a, depth[a] - depth[b]);
        if (a == b) return a;

        for (int j = up[0].length - 1; j >= 0; j--) {
            if (up[a][j] != up[b][j]) {
                a = up[a][j];
                b = up[b][j];
            }
        }
        return up[a][0];
    }

    static long climbWeight(int vertex, int steps) {
        long total = 0;
        for (int j = 0; steps > 0; j++, steps >>= 1) {
            if ((steps & 1) != 0) {
                total += weight[vertex][j];
                vertex = up[vertex][j];
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int levels = 32 - Integer.numberOfLeadingZeros(n);
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int w = input.nextInt();
            graph[a].add(new Edge(b, w));
            graph[b].add(new Edge(a, w));
        }

        up = new int[n][levels];
        weight = new long[n][levels];
        depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        visited[0] = true;
        up[0][0] = 0;

        while (top > 0) {
            int vertex = stack[--top];
            for (Edge edge : graph[vertex]) {
                if (visited[edge.to]) continue;
                visited[edge.to] = true;
                up[edge.to][0] = vertex;
                weight[edge.to][0] = edge.weight;
                depth[edge.to] = depth[vertex] + 1;
                stack[top++] = edge.to;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                int middle = up[vertex][j - 1];
                up[vertex][j] = up[middle][j - 1];
                weight[vertex][j] = weight[vertex][j - 1] + weight[middle][j - 1];
            }
        }

        int queryCount = input.nextInt();
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            int type = input.nextInt();
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int ancestor = lca(a, b);

            if (type == 1) {
                output.append(climbWeight(a, depth[a] - depth[ancestor])
                        + climbWeight(b, depth[b] - depth[ancestor])).append('\n');
            } else {
                int k = input.nextInt();
                int upEdges = depth[a] - depth[ancestor];
                int downEdges = depth[b] - depth[ancestor];
                int answer = k <= upEdges + 1
                        ? lift(a, k - 1)
                        : lift(b, upEdges + downEdges + 1 - k);
                output.append(answer + 1).append('\n');
            }
        }
        System.out.print(output);
    }
}
```
