---
title: BOJ. Strongly Connected Component (2150)
author: MINJUN PARK
date: 2022-01-26 00:17:00 +0900
categories: [Record, Code]
tags: [Java, Algorithm, Graph, SCC, BOJ, 강한 연결 요소]
pin: false
lang: ko
translation_key: boj-2150-scc
permalink: /ko/posts/boj-2150-scc/
source_permalink: /posts/BOJ-2150/
---

[문제: BOJ 2150 — Strongly Connected Component](https://www.acmicpc.net/problem/2150)

## 반복형 코사라주 알고리즘

코사라주 알고리즘은 두 번의 깊이 우선 탐색으로 강한 연결 요소(SCC)를 찾습니다. 이 구현은 재귀 호출 대신 명시적인 정수 스택을 사용하므로 정점 10,000개로 이루어진 긴 경로에서도 Java 호출 스택이 넘치지 않습니다.

첫 번째 탐색은 원래 그래프에서 수행합니다. 각 스택 프레임에는 정점과 다음으로 확인할 간선의 인덱스를 저장합니다. 모든 간선을 확인한 정점은 `finishOrder`에 추가합니다. 이는 재귀 DFS와 같은 후위 순서입니다. 두 번째 탐색은 간선을 뒤집은 그래프에서 종료 순서의 역순으로 정점을 방문합니다. 이렇게 시작한 각 탐색은 정확히 하나의 SCC를 모읍니다. 종료 순서의 성질상 뒤집힌 그래프에서 아직 방문하지 않은 다른 요소로 빠져나갈 수 없으며, 시작 정점과 서로 도달 가능한 정점은 모두 함께 방문됩니다.

각 요소의 정점을 오름차순으로 정렬하고, 요소 자체는 가장 작은 정점을 기준으로 정렬합니다. 출력은 요소 개수로 시작하며 각 요소를 한 줄에 출력하고 정점 목록 뒤에 `-1`을 붙입니다. 이는 BOJ 2150의 출력 형식입니다.

두 탐색은 각 정점과 간선을 상수 번만 확인합니다. 모든 SCC에 속한 정점을 정렬하는 비용은 합계 `O(V log V)` 이하입니다. 따라서 전체 시간 복잡도는 `O(V + E + V log V)`, 공간 복잡도는 `O(V + E)`입니다.

```java
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        FastScanner input = new FastScanner();
        int vertexCount = input.nextInt();
        int edgeCount = input.nextInt();

        List<Integer>[] graph = new List[vertexCount + 1];
        List<Integer>[] reversed = new List[vertexCount + 1];
        for (int vertex = 1; vertex <= vertexCount; vertex++) {
            graph[vertex] = new ArrayList<>();
            reversed[vertex] = new ArrayList<>();
        }
        for (int i = 0; i < edgeCount; i++) {
            int from = input.nextInt();
            int to = input.nextInt();
            graph[from].add(to);
            reversed[to].add(from);
        }

        boolean[] visited = new boolean[vertexCount + 1];
        int[] stack = new int[vertexCount];
        int[] nextEdge = new int[vertexCount];
        int[] finishOrder = new int[vertexCount];
        int finishCount = 0;

        for (int start = 1; start <= vertexCount; start++) {
            if (visited[start]) {
                continue;
            }
            int top = 0;
            stack[0] = start;
            nextEdge[0] = 0;
            visited[start] = true;

            while (top >= 0) {
                int vertex = stack[top];
                if (nextEdge[top] < graph[vertex].size()) {
                    int neighbor = graph[vertex].get(nextEdge[top]++);
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[++top] = neighbor;
                        nextEdge[top] = 0;
                    }
                } else {
                    finishOrder[finishCount++] = vertex;
                    top--;
                }
            }
        }

        Arrays.fill(visited, false);
        List<List<Integer>> components = new ArrayList<>();
        for (int i = finishCount - 1; i >= 0; i--) {
            int start = finishOrder[i];
            if (visited[start]) {
                continue;
            }

            List<Integer> component = new ArrayList<>();
            int top = 0;
            stack[0] = start;
            visited[start] = true;
            while (top >= 0) {
                int vertex = stack[top--];
                component.add(vertex);
                for (int neighbor : reversed[vertex]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        stack[++top] = neighbor;
                    }
                }
            }
            Collections.sort(component);
            components.add(component);
        }

        components.sort(Comparator.comparingInt(component -> component.get(0)));
        StringBuilder output = new StringBuilder().append(components.size()).append('\n');
        for (List<Integer> component : components) {
            for (int vertex : component) {
                output.append(vertex).append(' ');
            }
            output.append(-1).append('\n');
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
