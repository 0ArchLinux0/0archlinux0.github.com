---
title: BOJ. Tree (4803)
author: MINJUN PARK
date: 2021-01-10 18:01:00 +0900
categories: [Record, Code]
tags:
  [
    Java,
    Algorithm,
    Coding Interview,
    BOJ,
    DFS,
    Tree,
    Data Structure,
  ]
pin: false
lang: ko
translation_key: boj-4803-count-trees
permalink: /ko/posts/boj-4803-count-trees/
source_permalink: /posts/BOJ-4803/
---

## 풀이

트리는 연결되어 있고 사이클이 없는 무방향 그래프입니다. 방문하지 않은 정점마다 너비 우선 탐색을 시작해 해당 연결 요소 전체를 방문합니다. 정점은 큐에 넣는 순간 방문 표시를 하므로 같은 정점이 큐에 중복해서 들어가지 않습니다. 간선을 따라 이웃을 확인할 때 이미 방문한 이웃이 현재 정점의 부모가 아니라면 사이클의 증거입니다. 무방향 그래프에서는 부모로 향하는 간선 하나는 제외해야 합니다. 사이클이 없는 연결 요소만 트리 개수에 포함하며, 고립 정점도 트리로 셉니다.

각 정점과 인접 목록의 각 항목을 상수 번씩 확인하므로 시간 복잡도는 `O(V + E)`입니다. 그래프와 방문/큐 저장 공간을 포함한 공간 복잡도는 `O(V + E)`입니다.

[문제 링크](https://www.acmicpc.net/problem/4803)

## Java

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        StringBuilder output = new StringBuilder();
        int caseNumber = 1;

        while (true) {
            int vertexCount = input.nextInt();
            int edgeCount = input.nextInt();
            if (vertexCount == 0 && edgeCount == 0) {
                break;
            }

            @SuppressWarnings("unchecked")
            ArrayList<Integer>[] graph = new ArrayList[vertexCount];
            for (int vertex = 0; vertex < vertexCount; vertex++) {
                graph[vertex] = new ArrayList<>();
            }

            for (int edge = 0; edge < edgeCount; edge++) {
                int first = input.nextInt() - 1;
                int second = input.nextInt() - 1;
                graph[first].add(second);
                graph[second].add(first);
            }

            boolean[] visited = new boolean[vertexCount];
            int[] parent = new int[vertexCount];
            java.util.Arrays.fill(parent, -1);
            int treeCount = 0;
            for (int start = 0; start < vertexCount; start++) {
                if (visited[start]) {
                    continue;
                }

                visited[start] = true;
                Queue<Integer> queue = new ArrayDeque<>();
                queue.add(start);
                boolean hasCycle = false;

                while (!queue.isEmpty()) {
                    int current = queue.remove();
                    for (int neighbor : graph[current]) {
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            parent[neighbor] = current;
                            queue.add(neighbor);
                        } else if (neighbor != parent[current]) {
                            hasCycle = true;
                        }
                    }
                }

                if (!hasCycle) {
                    treeCount++;
                }
            }

            if (treeCount == 0) {
                output.append("Case ").append(caseNumber).append(": No trees.\n");
            } else if (treeCount == 1) {
                output.append("Case ").append(caseNumber).append(": There is one tree.\n");
            } else {
                output.append("Case ").append(caseNumber).append(": A forest of ")
                        .append(treeCount).append(" trees.\n");
            }
            caseNumber++;
        }

        System.out.print(output);
    }

    private static class FastScanner {
        private final BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        private StringTokenizer tokenizer;

        String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
```
