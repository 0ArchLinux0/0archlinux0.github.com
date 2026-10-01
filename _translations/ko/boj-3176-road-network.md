---
title: BOJ 3176 - 도로 네트워크
author: MINJUN PARK
date: 2022-02-09 08:03:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트리, 희소 테이블, 최소 공통 조상, LCA, 도로 네트워크]
pin: false
lang: ko
translation_key: boj-3176-road-network
permalink: /ko/posts/boj-3176-road-network/
source_permalink: /posts/BOJ-3176/
---

[문제: BOJ 3176 — 도로 네트워크](https://www.acmicpc.net/problem/3176) · [English](/posts/BOJ-3176/) · [日本語](/ja/posts/boj-3176-road-network/)

정점 1을 루트로 삼고 명시적인 스택으로 트리를 순회하며 각 정점의 깊이, 바로 위 부모, 한 칸 위로 올라갈 때 지나는 간선의 최솟값과 최댓값을 기록합니다. 이후 `2^j`칸 점프를 두 개의 `2^(j-1)`칸 점프로 나누어 조상, 최솟값, 최댓값 테이블을 채웁니다. 루트의 조상은 루트 자신으로 설정하며, 루트의 빈 경로에 쓰는 중립 극값은 실제 경로 결과에 영향을 주지 않습니다.

경로 질의에서는 깊이가 더 깊은 끝점을 먼저 올려 두 정점의 깊이를 같게 하면서 지나온 간선의 최솟값과 최댓값을 답에 반영합니다. 다음으로 가장 큰 점프부터 두 정점의 조상이 서로 다르면 두 정점을 함께 올리고, 이때 지나온 양쪽 구간의 극값도 반영합니다. 마지막 한 칸씩 올라가면 두 정점은 최소 공통 조상에서 만납니다. 양 끝점이 같은 정점이면 경로에 간선이 없으므로 이 구현은 중립 결과 `0 0`을 출력합니다(원래 제약에서는 보통 서로 다른 정점이 주어집니다).

루트 순회가 반복형이므로 정점 100,000개가 일렬로 연결된 트리에서도 호출 스택이 넘치지 않습니다. 전처리 시간과 공간은 `O(N log N)`, 각 경로 질의 시간은 `O(log N)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);
        private final byte[] buffer = new byte[1 << 16];
        private int position;
        private int limit;

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
            if (position == limit) {
                limit = input.read(buffer);
                position = 0;
                if (limit == -1) return -1;
            }
            return buffer[position++];
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

    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int n = input.nextInt();
        int levels = 32 - Integer.numberOfLeadingZeros(n);
        ArrayList<Edge>[] graph = new ArrayList[n];
        for (int vertex = 0; vertex < n; vertex++) graph[vertex] = new ArrayList<>();

        for (int i = 0; i < n - 1; i++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            int weight = input.nextInt();
            graph[a].add(new Edge(b, weight));
            graph[b].add(new Edge(a, weight));
        }

        int[][] up = new int[n][levels];
        int[][] minEdge = new int[n][levels];
        int[][] maxEdge = new int[n][levels];
        int[] depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int size = 0;
        stack[size++] = 0;
        visited[0] = true;
        up[0][0] = 0;
        minEdge[0][0] = Integer.MAX_VALUE;
        maxEdge[0][0] = Integer.MIN_VALUE;

        while (size > 0) {
            int vertex = stack[--size];
            for (Edge edge : graph[vertex]) {
                if (visited[edge.to]) continue;
                visited[edge.to] = true;
                up[edge.to][0] = vertex;
                minEdge[edge.to][0] = edge.weight;
                maxEdge[edge.to][0] = edge.weight;
                depth[edge.to] = depth[vertex] + 1;
                stack[size++] = edge.to;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                int middle = up[vertex][j - 1];
                up[vertex][j] = up[middle][j - 1];
                minEdge[vertex][j] = Math.min(minEdge[vertex][j - 1], minEdge[middle][j - 1]);
                maxEdge[vertex][j] = Math.max(maxEdge[vertex][j - 1], maxEdge[middle][j - 1]);
            }
        }

        int queryCount = input.nextInt();
        StringBuilder output = new StringBuilder();
        for (int query = 0; query < queryCount; query++) {
            int a = input.nextInt() - 1;
            int b = input.nextInt() - 1;
            if (a == b) {
                output.append("0 0\n");
                continue;
            }

            int min = Integer.MAX_VALUE;
            int max = Integer.MIN_VALUE;
            if (depth[a] < depth[b]) {
                int temp = a;
                a = b;
                b = temp;
            }

            int difference = depth[a] - depth[b];
            for (int j = levels - 1; j >= 0; j--) {
                if ((difference & (1 << j)) != 0) {
                    min = Math.min(min, minEdge[a][j]);
                    max = Math.max(max, maxEdge[a][j]);
                    a = up[a][j];
                }
            }

            if (a != b) {
                for (int j = levels - 1; j >= 0; j--) {
                    if (up[a][j] != up[b][j]) {
                        min = Math.min(min, Math.min(minEdge[a][j], minEdge[b][j]));
                        max = Math.max(max, Math.max(maxEdge[a][j], maxEdge[b][j]));
                        a = up[a][j];
                        b = up[b][j];
                    }
                }
                min = Math.min(min, Math.min(minEdge[a][0], minEdge[b][0]));
                max = Math.max(max, Math.max(maxEdge[a][0], maxEdge[b][0]));
            }
            output.append(min).append(' ').append(max).append('\n');
        }
        System.out.print(output);
    }
}
```
