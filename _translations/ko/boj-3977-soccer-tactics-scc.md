---
title: BOJ 3977 - 축구 전술
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 그래프, 강한 연결 요소, 축구 전술]
pin: false
lang: ko
translation_key: boj-3977-soccer-tactics-scc
permalink: /ko/posts/boj-3977-soccer-tactics-scc/
source_permalink: /posts/BOJ-3977/
---

[문제: BOJ 3977 — 축구 전술](https://www.acmicpc.net/problem/3977) · [English](/posts/BOJ-3977/) · [日本語](/ja/posts/boj-3977-soccer-tactics-scc/)

그래프를 강한 연결 요소(SCC)로 나누면, 각 SCC를 정점으로 하고 서로 다른 SCC 사이의 간선을 유지한 축약 그래프를 얻습니다. 진입 간선이 없는 SCC는 다른 SCC에서 도달할 수 없는 시작 요소입니다. 이런 SCC가 하나뿐이면 그 안의 모든 정점이 답이고, 둘 이상이면 `Confused`를 출력합니다.

Kosaraju 알고리즘의 두 번의 탐색은 모두 반복문으로 구현합니다. 첫 번째 탐색은 원래 그래프에서 DFS를 수행하며 각 정점의 탐색이 끝나는 순서를 기록합니다. 명시적인 정점 스택과 정점별 다음 간선 위치를 사용하면 재귀 호출 없이 DFS의 종료 순서를 그대로 얻을 수 있습니다. 두 번째 탐색은 역방향 그래프에서 종료 순서의 역순으로 진행하며, 한 번의 탐색마다 SCC 하나를 찾습니다.

간선을 읽을 때 원래 그래프와 역방향 그래프를 모두 만들고, 원래 간선 목록도 보관합니다. SCC 번호를 정한 뒤 서로 다른 SCC를 잇는 간선의 도착 SCC에 진입 간선이 있음을 표시합니다. 진입 간선이 없는 SCC의 수를 센 다음, 유일한 경우에는 정점을 오름차순으로 확인하여 해당 SCC의 정점만 출력합니다. 고립 정점이나 간선이 하나도 없는 그래프에서도 각 정점은 각각 하나의 원천 SCC가 됩니다.

시간 복잡도와 공간 복잡도는 모두 `O(V + E)`입니다. 재귀를 사용하지 않으므로 정점 100,000개의 긴 경로에서도 호출 스택이 넘치지 않습니다. 테스트 케이스 사이에는 빈 줄을 출력합니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        for (int test = 0; test < testCases; test++) {
            int n = input.nextInt();
            int m = input.nextInt();
            ArrayList<Integer>[] graph = new ArrayList[n];
            ArrayList<Integer>[] reverse = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new ArrayList<>();
                reverse[i] = new ArrayList<>();
            }

            int[] from = new int[m];
            int[] to = new int[m];
            for (int i = 0; i < m; i++) {
                int a = input.nextInt();
                int b = input.nextInt();
                from[i] = a;
                to[i] = b;
                graph[a].add(b);
                reverse[b].add(a);
            }

            boolean[] visited = new boolean[n];
            int[] nextEdge = new int[n];
            int[] stack = new int[n];
            int[] order = new int[n];
            int orderSize = 0;

            for (int start = 0; start < n; start++) {
                if (visited[start]) {
                    continue;
                }
                int top = 0;
                stack[0] = start;
                visited[start] = true;
                while (top >= 0) {
                    int node = stack[top];
                    if (nextEdge[node] < graph[node].size()) {
                        int neighbor = graph[node].get(nextEdge[node]++);
                        if (!visited[neighbor]) {
                            visited[neighbor] = true;
                            stack[++top] = neighbor;
                        }
                    } else {
                        order[orderSize++] = node;
                        top--;
                    }
                }
            }

            int[] component = new int[n];
            int componentCount = 0;
            for (int i = orderSize - 1; i >= 0; i--) {
                int start = order[i];
                if (component[start] != 0) {
                    continue;
                }
                int top = 0;
                stack[0] = start;
                component[start] = ++componentCount;
                while (top >= 0) {
                    int node = stack[top--];
                    for (int neighbor : reverse[node]) {
                        if (component[neighbor] == 0) {
                            component[neighbor] = componentCount;
                            stack[++top] = neighbor;
                        }
                    }
                }
            }

            boolean[] hasIncoming = new boolean[componentCount + 1];
            for (int i = 0; i < m; i++) {
                if (component[from[i]] != component[to[i]]) {
                    hasIncoming[component[to[i]]] = true;
                }
            }

            int source = 0;
            int sourceCount = 0;
            for (int id = 1; id <= componentCount; id++) {
                if (!hasIncoming[id]) {
                    source = id;
                    sourceCount++;
                }
            }

            if (test > 0) {
                answer.append('\n');
            }
            if (sourceCount != 1) {
                answer.append("Confused\n");
            } else {
                for (int vertex = 0; vertex < n; vertex++) {
                    if (component[vertex] == source) {
                        answer.append(vertex).append('\n');
                    }
                }
            }
        }

        System.out.print(answer);
    }

    private static class FastScanner {
        private final BufferedInputStream input = new BufferedInputStream(System.in);

        int nextInt() throws IOException {
            int value = 0;
            int c;
            do {
                c = input.read();
            } while (c <= ' ' && c != -1);
            while (c > ' ') {
                value = value * 10 + c - '0';
                c = input.read();
            }
            return value;
        }
    }
}
```
