---
title: BOJ 4196 - 도미노
author: MINJUN PARK
date: 2022-02-11 04:36:00 +0900
categories: [Record, Code]
tags: [Java, 알고리즘, BOJ, 그래프, 강한 연결 요소, SCC, 도미노]
pin: false
lang: ko
translation_key: boj-4196-domino-scc
permalink: /ko/posts/boj-4196-domino-scc/
source_permalink: /posts/BOJ-4196/
---

[문제: BOJ 4196 — 도미노](https://www.acmicpc.net/problem/4196) · [English](/posts/BOJ-4196/) · [日本語](/ja/posts/boj-4196-domino-scc/)

각 테스트 케이스에는 방향 그래프가 주어집니다. 도미노 `u`를 밀면 방향 간선을 따라 `u`에서 도달할 수 있는 모든 도미노가 쓰러집니다. 모든 정점을 쓰러뜨리는 데 필요한 최초의 밀기 횟수의 최솟값을 구합니다.

먼저 정점들을 강한 연결 요소(SCC)로 묶습니다. 한 SCC 안에서는 모든 정점이 서로 도달할 수 있으므로, 그 요소 안의 도미노 하나를 밀면 나머지도 모두 쓰러집니다. 각 SCC를 정점 하나로 합치고 서로 다른 SCC 사이의 간선을 유지하면 응축 그래프가 만들어지며, 이 그래프는 DAG입니다.

응축 그래프에서 들어오는 간선이 없는 SCC에는 반드시 직접 밀기가 필요합니다. 다른 SCC에서 이 SCC로 도달할 수 없기 때문입니다. 반대로 모든 진입 차수 0인 SCC에서 하나씩 밀면 충분합니다. 유한 DAG의 모든 정점은 어떤 시작점(진입 간선이 없는 정점)으로부터 도달할 수 있습니다. 각 정점에서 들어오는 간선을 거슬러 올라가면 유한한 그래프이므로 결국 진입 간선이 없는 정점에 도달하기 때문입니다. 따라서 답은 진입 차수가 0인 SCC의 개수입니다.

구현은 반복형 코사라주 알고리즘을 사용합니다. 첫 번째 DFS는 명시적인 스택으로 종료 순서를 기록하고, 역방향 그래프를 종료 순서의 역순으로 순회하여 SCC ID를 부여합니다. 재귀 호출이 없어 정점이 100,000개인 긴 경로에서도 호출 스택이 넘치지 않습니다. 마지막으로 간선을 훑어 다른 SCC에서 들어오는 간선이 있는 SCC를 표시합니다. 각 정점과 간선을 상수 횟수만큼 처리하므로 시간 복잡도는 `O(V + E)`, 공간 복잡도도 `O(V + E)`입니다.

## Java

```java
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    private static final class FastScanner {
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
                if (limit == -1) {
                    return -1;
                }
            }
            return buffer[position++];
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner input = new FastScanner();
        int testCases = input.nextInt();
        StringBuilder answer = new StringBuilder();

        while (testCases-- > 0) {
            int n = input.nextInt();
            int m = input.nextInt();
            int[] head = new int[n];
            int[] reverseHead = new int[n];
            Arrays.fill(head, -1);
            Arrays.fill(reverseHead, -1);
            int[] to = new int[m];
            int[] next = new int[m];
            int[] reverseTo = new int[m];
            int[] reverseNext = new int[m];

            for (int edge = 0; edge < m; edge++) {
                int from = input.nextInt() - 1;
                int destination = input.nextInt() - 1;
                to[edge] = destination;
                next[edge] = head[from];
                head[from] = edge;
                reverseTo[edge] = from;
                reverseNext[edge] = reverseHead[destination];
                reverseHead[destination] = edge;
            }

            boolean[] visited = new boolean[n];
            int[] order = new int[n];
            int orderSize = 0;
            int[] nodeStack = new int[n];
            int[] edgeStack = new int[n];

            for (int start = 0; start < n; start++) {
                if (visited[start]) {
                    continue;
                }
                int top = 0;
                nodeStack[0] = start;
                edgeStack[0] = head[start];
                visited[start] = true;

                while (top >= 0) {
                    int edge = edgeStack[top];
                    if (edge == -1) {
                        order[orderSize++] = nodeStack[top--];
                        continue;
                    }
                    edgeStack[top] = next[edge];
                    int neighbor = to[edge];
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;
                        nodeStack[++top] = neighbor;
                        edgeStack[top] = head[neighbor];
                    }
                }
            }

            int[] component = new int[n];
            Arrays.fill(component, -1);
            int[] stack = new int[n];
            int componentCount = 0;
            for (int i = orderSize - 1; i >= 0; i--) {
                int start = order[i];
                if (component[start] != -1) {
                    continue;
                }
                int size = 0;
                stack[size++] = start;
                component[start] = componentCount;
                while (size > 0) {
                    int node = stack[--size];
                    for (int edge = reverseHead[node]; edge != -1; edge = reverseNext[edge]) {
                        int neighbor = reverseTo[edge];
                        if (component[neighbor] == -1) {
                            component[neighbor] = componentCount;
                            stack[size++] = neighbor;
                        }
                    }
                }
                componentCount++;
            }

            boolean[] hasIncoming = new boolean[componentCount];
            for (int from = 0; from < n; from++) {
                for (int edge = head[from]; edge != -1; edge = next[edge]) {
                    int sourceComponent = component[from];
                    int destinationComponent = component[to[edge]];
                    if (sourceComponent != destinationComponent) {
                        hasIncoming[destinationComponent] = true;
                    }
                }
            }

            int pushes = 0;
            for (boolean incoming : hasIncoming) {
                if (!incoming) {
                    pushes++;
                }
            }
            answer.append(pushes).append('\n');
        }

        System.out.print(answer);
    }
}
```
