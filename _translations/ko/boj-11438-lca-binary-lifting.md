---
title: BOJ 11438 - LCA 2
author: MINJUN PARK
date: 2022-02-09 05:28:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 트리, 최소 공통 조상, LCA, 희소 테이블]
pin: false
lang: ko
translation_key: boj-11438-lca-binary-lifting
permalink: /ko/posts/boj-11438-lca-binary-lifting/
source_permalink: /posts/BOJ-11438/
---

[문제: BOJ 11438 — LCA 2](https://www.acmicpc.net/problem/11438) · [English](/posts/BOJ-11438/) · [日本語](/ja/posts/boj-11438-lca-binary-lifting/)

정점 1을 루트로 잡고, 각 정점 `v`의 깊이와 `up[v][j]`를 저장합니다. `up[v][j]`는 `v`에서 간선을 `2^j`개 거슬러 올라간 조상입니다. 루트는 모든 단계에서 자기 자신의 조상으로 지정합니다. 이 규칙으로 조상 테이블의 모든 값이 유효해지고 루트에서의 점프도 안전해집니다.

조상 테이블은 `up[v][0] = parent[v]`, `up[v][j] = up[up[v][j - 1]][j - 1]`로 계산합니다. 테이블 열 수는 `N`에서 구합니다. 양의 `N`에 대해 `32 - Integer.numberOfLeadingZeros(N)`개의 열이면 가능한 모든 깊이를 처리할 수 있습니다.

`a`, `b`의 LCA를 구할 때는 먼저 `a`가 더 깊도록 두 정점을 바꿉니다. 깊이 차이의 각 비트에 해당하는 만큼 `a`를 위로 올려 두 정점의 깊이를 같게 합니다. 이때 두 정점이 같아졌다면 그 정점이 LCA입니다. 그렇지 않으면 큰 점프부터 차례로 확인하면서 `2^j`번째 조상이 서로 다른 경우 두 정점을 함께 올립니다. 이 과정에서 두 정점은 공통 조상 아래에 머물고, 마지막에는 두 정점의 바로 위 부모가 LCA입니다.

초기 트리 탐색은 재귀 대신 명시적 스택을 사용하므로 정점 100,000개가 일렬로 연결된 경우에도 Java 호출 스택을 소진하지 않습니다. 전처리는 `O(N log N)` 시간과 공간을 사용하며, 각 질의는 `O(log N)` 시간에 처리합니다.

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(input.readLine());
        ArrayList<Integer>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n - 1; i++) {
            String[] edge = input.readLine().split(" ");
            int a = Integer.parseInt(edge[0]) - 1;
            int b = Integer.parseInt(edge[1]) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        int levels = 32 - Integer.numberOfLeadingZeros(n);
        int[][] up = new int[n][levels];
        int[] depth = new int[n];
        boolean[] visited = new boolean[n];
        int[] stack = new int[n];
        int top = 0;
        stack[top++] = 0;
        visited[0] = true;
        up[0][0] = 0;

        while (top > 0) {
            int vertex = stack[--top];
            for (int neighbor : graph[vertex]) {
                if (visited[neighbor]) {
                    continue;
                }
                visited[neighbor] = true;
                up[neighbor][0] = vertex;
                depth[neighbor] = depth[vertex] + 1;
                stack[top++] = neighbor;
            }
        }

        for (int j = 1; j < levels; j++) {
            for (int vertex = 0; vertex < n; vertex++) {
                up[vertex][j] = up[up[vertex][j - 1]][j - 1];
            }
        }

        int queryCount = Integer.parseInt(input.readLine());
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < queryCount; i++) {
            String[] query = input.readLine().split(" ");
            int a = Integer.parseInt(query[0]) - 1;
            int b = Integer.parseInt(query[1]) - 1;
            output.append(lca(a, b, depth, up)).append('\n');
        }
        System.out.print(output);
    }

    private static int lca(int a, int b, int[] depth, int[][] up) {
        if (depth[a] < depth[b]) {
            int temp = a;
            a = b;
            b = temp;
        }

        int difference = depth[a] - depth[b];
        for (int j = 0; j < up[0].length; j++) {
            if ((difference & (1 << j)) != 0) {
                a = up[a][j];
            }
        }
        if (a == b) {
            return a + 1;
        }

        for (int j = up[0].length - 1; j >= 0; j--) {
            if (up[a][j] != up[b][j]) {
                a = up[a][j];
                b = up[b][j];
            }
        }
        return up[a][0] + 1;
    }
}
```
